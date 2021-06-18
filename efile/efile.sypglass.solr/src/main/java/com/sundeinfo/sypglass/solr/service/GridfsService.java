package com.sundeinfo.sypglass.solr.service;

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
import java.nio.file.Paths;


@Service("StreamTransfersService")
public class GridfsService extends AbstractService<GridfsService,MultipartFile> {

    @Autowired
    private MongoDbFactory mongodbfactory;

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