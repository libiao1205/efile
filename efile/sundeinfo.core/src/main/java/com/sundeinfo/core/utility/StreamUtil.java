package com.sundeinfo.core.utility;

import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

public class StreamUtil {
    /**
     * 解读输入流
     * @param inputStream
     * @return
     * @throws Exception
     */
    public static String streamInput(InputStream inputStream) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }
        return builder.toString();
    }


    /**
     * 创建文件路径
     * @param path
     */
    public static void createFolder(String path) {
        File file = new File(path);
        file.deleteOnExit();
        file.mkdirs();
    }

    /**
     * 存贮
     * @param pathname
     * @param io
     * @throws IOException
     */
    public static void createFile(String pathname ,InputStream io) throws IOException {
        File file = new File(pathname);

        FileOutputStream fileOutputStream = new FileOutputStream(file);
        int n = 0;
        byte[] b = new byte[1024];
        while ((n = io.read(b)) != -1) {
            fileOutputStream.write(b, 0 , n);
        }
        fileOutputStream.close();
        fileOutputStream.flush();
    }


    public static void readFile(String pathname) {
        File file = new File(pathname);
        try {
            URL url = new URL(file.getPath());
            Image img = Toolkit.getDefaultToolkit().getImage(url);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    /**
     *删除单个文件
     * @param path 文件的路径名
     * @return
     */
    public static boolean deleteFile(String path) {
        boolean flg = false;
        File file = new File(path);
        if(file.isFile() && file.exists()) {
            file.delete();
            flg = true;
        }
        return flg;
    }

    /**
     * 把后台路径转化为url
     * @param request 当前请求
     * @param path   文件的路径
     * @return
     */
    public static String getUrlAddress(HttpServletRequest request, String path) {
        String realURL = request.getRequestURL().toString();
        String classpath = realURL.substring(0,realURL.indexOf(request.getContextPath())+request.getContextPath().length());

        return classpath+path;

    }
}
