package com.sundeinfo.file.fulltext.impl;

import com.sundeinfo.file.fulltext.FullTextReader;

import java.io.File;

public class DefaultReader implements FullTextReader {

    protected File file;

    public DefaultReader(File file) {
        this.file = file;
    }

    @Override
    public boolean isSupport() {
        return false;
    }

    @Override
    public String read() {
        return "";
    }
}
