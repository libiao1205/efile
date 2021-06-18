package com.sundeinfo.sypglass.service;

import com.mongodb.BasicDBObject;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
import com.sundeinfo.core.mvc.service.AbstractService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Service("StreamTransfersService")
public class GridfsService extends AbstractService<GridfsService,MultipartFile> {

    @Autowired
    private MongoDbFactory mongodbfactory;

    /**
     * 插入文件
     */
    public GridFSInputFile save(MultipartFile file){
        GridFS gridFS = new GridFS(mongodbfactory.getDb());
        try{
            InputStream in = file.getInputStream();
            String name = file.getOriginalFilename();
            GridFSInputFile gridFSInputFile = gridFS.createFile(in);
            gridFSInputFile.setFilename(name);
            gridFSInputFile.setContentType(file.getContentType());
            gridFSInputFile.save();
            return gridFSInputFile;
        }
        catch (Exception e){
            logger.error("文件写入mongo失败：" + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 插入文件
     */
    public GridFSInputFile save(String path,String name){
        String filepath = path + name;
        if(!new File(filepath).exists()){
            logger.error("文件写入mongo失败,没有找到指定文件：" + path + name);
        }

        GridFS gridFS = new GridFS(mongodbfactory.getDb());
        try{
            InputStream in = new FileInputStream(filepath);
            GridFSInputFile gridFSInputFile = gridFS.createFile(in);
            gridFSInputFile.setFilename(name);
            gridFSInputFile.setContentType(Files.probeContentType(Paths.get(filepath)));
            gridFSInputFile.save();
            return gridFSInputFile;
        }
        catch (Exception e){
            logger.error("文件写入mongo失败：" + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 据id返回文件
     */
    public GridFSDBFile getById(ObjectId id){
        GridFS gridFS = new GridFS(mongodbfactory.getDb());
        return gridFS.findOne(new BasicDBObject("_id", id));
    }


    /**
     * 删除
     * @param id
     */
    public void remove(String id) {
        GridFS gridFS = new GridFS(mongodbfactory.getDb());
        gridFS.remove(new ObjectId(id));
    }
}