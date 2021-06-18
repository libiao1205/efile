package com.sundeinfo.sypglass.solr.fulltext;

import com.sundeinfo.file.fulltext.FullTextReader;
import com.sundeinfo.file.fulltext.impl.DefaultReader;
import com.sundeinfo.sypglass.solr.model.FileAccessory;
import com.sundeinfo.sypglass.solr.model.FileMetadata;

import java.io.File;
import java.util.List;

public class LiteratureReader extends DefaultReader {

    private List<FullTextReader> readers;

    private List<FileMetadata> fileMetadata;

    public LiteratureReader(File file) {
        super(file);
    }

    @Override
    public boolean isSupport() {
        return true;
    }

    public void setLiteratureAdditional(List<FullTextReader> readers, List<FileMetadata> fileMetadata){
        this.readers = readers;
        this.fileMetadata = fileMetadata;
    }

    @Override
    public String read() {
        if ((readers == null || readers.size() == 0) && (fileMetadata == null || fileMetadata.size() == 0)) return "";

        StringBuilder stringBuilder = new StringBuilder();

        if (readers != null){
            for (FullTextReader reader : readers){
                if (reader.isSupport()){
                    stringBuilder.append(reader.read());
                }

            }
        }

        if (fileMetadata != null){
            for (FileMetadata fileMetadata : fileMetadata){
                stringBuilder.append(fileMetadata.getValue());
            }
        }

        return stringBuilder.toString();
    }
}
