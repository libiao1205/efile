package com.sundeinfo.sypglass.solr.apis;

import com.github.pagehelper.PageHelper;
import com.mongodb.gridfs.GridFSDBFile;
import com.sundeinfo.core.mvc.controller.AbstractController;
import com.sundeinfo.core.request.PostCallback;
import com.sundeinfo.core.request.RequestState;
import com.sundeinfo.core.utility.StringUtils;
import com.sundeinfo.file.component.FileOperator;
import com.sundeinfo.file.component.FileReadManager;
import com.sundeinfo.file.fulltext.FullTextReader;
import com.sundeinfo.sypglass.solr.define.ReadHandle;
import com.sundeinfo.sypglass.solr.dto.SolrAndMetadataDto;
import com.sundeinfo.sypglass.solr.fulltext.LiteratureReader;
import com.sundeinfo.sypglass.solr.model.File;
import com.sundeinfo.sypglass.solr.model.FileAccessory;
import com.sundeinfo.sypglass.solr.model.SolrHolder;
import com.sundeinfo.sypglass.solr.service.FileService;
import com.sundeinfo.sypglass.solr.service.GridfsService;
import com.sundeinfo.sypglass.solr.service.SolrHolderService;
import com.sundeinfo.sypglass.solr.service.SolrService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.solr.client.solrj.SolrServerException;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;


@Api(description = "SolrAPI")
@RestController
@RequestMapping("solr")
public class SolrController extends AbstractController<SolrController> {

    @Value("${file.folderTmpPath}")
    private String tmpPath;

    @Value("${solr.update.enforced}")
    protected String enforced;

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

    private static final int PAGE_SIZE = 100;

    @ApiOperation(value="Solr????????????", notes="Solr????????????")
    @PostMapping("/refresh")
    public ResponseEntity refreshSolr(){
        return requestHandler.doPost(
                new PostCallback() {
                    @Override
                    public RequestState doCheck() throws Exception {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        logger.info( "-------------????????????Solr????????????-------------");

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
                                    inputFile(file,start);
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
                        return RequestState.SUCCESS;
                    }
                }
        );
    }

    @ApiOperation(value="Solr????????????", notes="Solr????????????")
    @PostMapping("/file/{id}")
    public ResponseEntity inputFile(@PathVariable(value = "id") long id){
        return requestHandler.doPost(
                new PostCallback(){

                    @Override
                    public RequestState doCheck() throws Exception {
                        return RequestState.SUCCESS;
                    }

                    @Override
                    public RequestState invoke() throws Exception {
                        File file = fileService.getFile(id);
                        inputFile(file,new Date());
                        return RequestState.SUCCESS;
                    }
                }
        );
    }

    private void inputFile(File file,Date start) throws IOException, SolrServerException {
        logger.info("?????????????????????" + file.getName() + "." + file.getSuffix());

        SolrHolder holder = solrHolderService.findByFile(file.getId());
        if (holder != null){
            logger.info("?????????" + file.getName() + "." + file.getSuffix() + "??????????????????????????????????????????");
        }else{
            logger.info("?????????" + file.getName() + "." + file.getSuffix() + "??????????????????????????????????????????");
        }

        if (file.getDoctype() == 10){
            //????????????
            GridFSDBFile gridFSDBFile = gridfsService.getById(new ObjectId(file.getSaveid()));
            if (gridFSDBFile == null){
                logger.info("?????????" + file.getName() + "." + file.getSuffix() + "????????????mongo??????????????????????????????ID:" + file.getId());
                return;
            }
            String fileName = file.getName()+"."+file.getSuffix();
            java.io.File tmpFile = fileOperator.create(tmpPath  +  fileName);
            gridFSDBFile.writeTo(tmpFile);

            FullTextReader reader = fileReadManager.getReader(tmpFile);
            solrService.save(file,holder,reader,null,start);
        }else if (file.getDoctype() == 20){
            //????????????
            List<FileAccessory> fileAccessoryList = fileService.getFileAccessory(file.getId());
            List<FullTextReader> multiReader = new ArrayList<>();
            for (FileAccessory fileAccessory : fileAccessoryList){
                if (fileAccessory.getType() == 2) continue;//?????????????????????????????????solr
                GridFSDBFile gridFSDBFile = gridfsService.getById(new ObjectId(fileAccessory.getSaveid()));
                if (gridFSDBFile == null){
                    logger.info("?????????" + file.getName() + "????????????mongo??????????????????????????????ID:" + file.getId());
                    return;
                }
                String fileName = file.getName()+"."+file.getSuffix();
                java.io.File tmpFile = fileOperator.create(tmpPath  +  fileName);
                gridFSDBFile.writeTo(tmpFile);
                multiReader.add(fileReadManager.getReader(tmpFile));
            }

            FullTextReader literatureReader = new LiteratureReader(null);
            //????????????????????????????????????
            ((LiteratureReader) literatureReader).setLiteratureAdditional(multiReader,fileService.getFileMetadata(file.getId()));
            List<SolrAndMetadataDto> solrAndMetadataDtos = fileService.getFileMetadataAndSolr(file.getId());
            solrService.save(file,holder,literatureReader,solrAndMetadataDtos,start);
        }
    }

}