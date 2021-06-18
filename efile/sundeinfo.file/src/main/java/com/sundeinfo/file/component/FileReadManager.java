package com.sundeinfo.file.component;

import com.sundeinfo.core.mvc.component.AbstractComponent;
import com.sundeinfo.file.fulltext.FullTextReader;
import com.sundeinfo.file.fulltext.ReaderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

@Component("FileReadManager")
public class FileReadManager extends AbstractComponent<FileReadManager> {

    @Autowired
    private ReaderFactory readerFactory;

    public FullTextReader getReader(File file){
        return readerFactory.get(file);
    }

}
