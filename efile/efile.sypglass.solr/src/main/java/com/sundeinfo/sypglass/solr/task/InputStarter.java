package com.sundeinfo.sypglass.solr.task;

import com.github.pagehelper.PageHelper;
import com.mongodb.gridfs.GridFSDBFile;
import com.sundeinfo.core.job.AbstractTaskStarter;
import com.sundeinfo.core.job.Task;
import com.sundeinfo.core.utility.StringUtils;
import com.sundeinfo.file.component.FileOperator;
import com.sundeinfo.file.component.FileReadManager;
import com.sundeinfo.file.fulltext.FullTextReader;
import com.sundeinfo.sypglass.solr.configuration.ConfigService;
import com.sundeinfo.sypglass.solr.define.ReadHandle;
import com.sundeinfo.sypglass.solr.dto.SolrAndMetadataDto;
import com.sundeinfo.sypglass.solr.fulltext.LiteratureReader;
import com.sundeinfo.sypglass.solr.model.Config;
import com.sundeinfo.sypglass.solr.model.File;
import com.sundeinfo.sypglass.solr.model.FileAccessory;
import com.sundeinfo.sypglass.solr.model.SolrHolder;
import com.sundeinfo.sypglass.solr.service.FileService;
import com.sundeinfo.sypglass.solr.service.GridfsService;
import com.sundeinfo.sypglass.solr.service.SolrHolderService;
import com.sundeinfo.sypglass.solr.service.SolrService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component("InputStarter")
public class InputStarter extends AbstractTaskStarter<InputStarter> implements Task {

    @Value("${file.folderTmpPath}")
    private String tmpPath;

    @Value("${solr.update.enforced}")
    protected String enforced;


    private static final int PAGE_SIZE = 100;

    @Autowired
    private FileReadManager fileReadManager;

    @Autowired
    private FileOperator fileOperator;

    @Autowired
    private FileService fileService;

    @Autowired
    private GridfsService gridfsService;

    @Autowired
    private SolrService solrService;

    @Autowired
    private SolrHolderService solrHolderService;

    @Autowired
    private ConfigService configService;

    @Override
    protected Task getTask() {
        return this;
    }

    @Override
    public void execute() {
        logger.info( "-------------?????????????????????-------------");

        List<Config> configs = configService.findByNameAndCode("solr_synchronization_switch","solrSynchronizationSwitch");
        if (configs != null && configs.size() == 1){
            String value = configs.get(0).getValue();
            if ("1".equals(value)){
                logger.info( "-------------??????????????????????????????????????????????????????-------------");
                return;
            }
        }

        long count = fileService.countFile();

        logger.info( "-------------????????????" + count+ "???-------------");

        Date start = new Date();

        long fileCount = 0;
        int pageIndex = 1;
        try {
            do{
                PageHelper.startPage(pageIndex, PAGE_SIZE);
                List<File> files = fileService.getFileList();

                for (File file:files) {
                    logger.info("?????????????????????" + file.getName() + "." + file.getSuffix());

                    SolrHolder holder = solrHolderService.findByFile(file.getId());
                    if (StringUtils.isEqualsString(enforced,"false")){
                        if (holder != null && holder.getHandle() != 9 && (holder.getLastupdatedate().getTime() > file.getLastupdatedate().getTime())){
                            logger.info("?????????" + file.getName() + "." + file.getSuffix() + "?????????????????????????????????????????????");
                            continue;
                        }
                    }

                    if (file.getDoctype() == 10){
                        //????????????
                        GridFSDBFile gridFSDBFile = gridfsService.getById(new ObjectId(file.getSaveid()));
                        if (gridFSDBFile == null){
                            logger.info("?????????" + file.getName() + "." + file.getSuffix() + "????????????mongo??????????????????????????????ID:" + file.getId());
                            continue;
                        }

                        String fileName = file.getName()+"."+file.getSuffix();
                        java.io.File tmpFile = fileOperator.create(tmpPath  +  fileName);
                        gridFSDBFile.writeTo(tmpFile);

                        FullTextReader reader = fileReadManager.getReader(tmpFile);
                        solrService.save(file,holder,reader,null,start);
                    }else if (file.getDoctype() == 20){
                        //????????????
                        boolean isErrorLiterature = false;
                        List<FileAccessory> fileAccessoryList = fileService.getFileAccessory(file.getId());
                        List<FullTextReader> multiReader = new ArrayList<>();
                        for (FileAccessory fileAccessory : fileAccessoryList){
                            if (fileAccessory.getType() == 2) continue;//?????????????????????????????????solr
                            GridFSDBFile gridFSDBFile = gridfsService.getById(new ObjectId(fileAccessory.getSaveid()));
                            if (gridFSDBFile == null){
                                logger.info("?????????" + file.getName() + "????????????mongo??????????????????????????????ID:" + file.getId());
                                isErrorLiterature = true;
                                break;
                            }
                            String fileName = file.getName()+"."+file.getSuffix();
                            java.io.File tmpFile = fileOperator.create(tmpPath  +  fileName);
                            gridFSDBFile.writeTo(tmpFile);
                            multiReader.add(fileReadManager.getReader(tmpFile));
                        }
                        if (isErrorLiterature){
                            continue;//???????????????????????????????????????????????????????????? ??????????????????
                        }

                        FullTextReader literatureReader = new LiteratureReader(null);
                        //????????????????????????????????????
                        ((LiteratureReader) literatureReader).setLiteratureAdditional(multiReader,fileService.getFileMetadata(file.getId()));
                        List<SolrAndMetadataDto> solrAndMetadataDtos = fileService.getFileMetadataAndSolr(file.getId());
                        solrService.save(file,holder,literatureReader,solrAndMetadataDtos,start);
                    }
                }
                
                fileCount = fileCount + PAGE_SIZE;
                logger.info( "-------------????????????" + fileCount+ "???-------------");
                pageIndex++;
            }while (count >= fileCount);
        }catch (Exception e){
            logger.error("-------------??????????????????????????????-------------");
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    protected void start() {
        logger.info(" ??????????????????: " + formatter.format(new Date()));
    }

    @Override
    protected void end() {
        logger.info( " ??????????????????????????????");
    }



}
