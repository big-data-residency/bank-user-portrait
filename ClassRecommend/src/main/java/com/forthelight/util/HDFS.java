package com.forthelight.util;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;

public class HDFS {
    private static String BaseUri = "hdfs://192.168.100.100:9000/";

    public static FileSystem getFileSystem() throws IOException, URISyntaxException {
        Configuration conf = new Configuration();
        FileSystem fileSystem;
        String hdfsUri = BaseUri;
        if (StringUtils.isBlank(hdfsUri)) {
            fileSystem = FileSystem.get(conf);
        } else {
            URI uri = new URI(hdfsUri.trim());
            fileSystem = FileSystem.get(uri, conf);
        }
        return fileSystem;
    }

    public static void copyToHDFS(String srcFile, String dstFile) throws IOException {
        Configuration conf = new Configuration();
        FileSystem fileSystem = FileSystem.get(URI.create(BaseUri + dstFile), conf);

        FileInputStream fileInputStream = new FileInputStream(new File(srcFile));
        OutputStream outputStream = fileSystem.create(new Path(fileSystem.getWorkingDirectory() + "/" + dstFile));

        IOUtils.copyBytes(fileInputStream, outputStream, 4096, true);
        System.out.println(String.format("copy %s to %s success", srcFile, BaseUri + "/" + dstFile));

        fileInputStream.close();
        outputStream.close();
        fileSystem.close();
    }

    public static void downloadFromHDFS(String srcFile, String dstFile) throws IOException, URISyntaxException {
        FileSystem fileSystem = getFileSystem();

        String file = fileSystem.getWorkingDirectory() + "/" + srcFile;
        InputStream inputStream = fileSystem.open(new Path(file));
        FileOutputStream fileOutputStream = new FileOutputStream(new File(dstFile));

        IOUtils.copyBytes(inputStream, fileOutputStream, 2048, true);
        System.out.println(String.format("downLoad %s to %s success", srcFile, dstFile));

        fileOutputStream.close();
        inputStream.close();
        fileSystem.close();
    }

    public static void mkDirs(String path) throws IOException, URISyntaxException {
        FileSystem fileSystem = getFileSystem();

        fileSystem.mkdirs(new Path(path));
        fileSystem.close();

    }

    public static boolean isDir(String filePath, boolean create) throws IOException, URISyntaxException {
        if (StringUtils.isEmpty(filePath)) {
            return false;
        }

        Path path = new Path(filePath);
        FileSystem fileSystem = getFileSystem();
        if (fileSystem.isDirectory(path)) {
            return true;
        } else if (create && !fileSystem.exists(path)) {
            fileSystem.mkdirs(path);
            return true;
        }
        return false;
    }

    public static void rmDir(String path) throws IOException, URISyntaxException {
        FileSystem fileSystem = getFileSystem();
        String hdfsUri = BaseUri;
        if (StringUtils.isNotBlank(hdfsUri)){
            path = hdfsUri + path;
        }
        fileSystem.delete(new Path(path), true);
        fileSystem.close();
    }

    public static void readFile(String targetFile) throws IOException, URISyntaxException {
        FileSystem fileSystem = getFileSystem();
        InputStream inputStream = fileSystem.open(new Path(fileSystem.getWorkingDirectory() + "/" + targetFile));
        IOUtils.copyBytes(inputStream, System.out, 2048, false);
        fileSystem.close();
    }
}
