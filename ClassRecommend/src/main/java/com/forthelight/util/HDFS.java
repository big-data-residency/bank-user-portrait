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
    private static String BaseUri = "hdfs://192.168.100.100:9000";

    public static FileSystem getFileSystem() throws IOException, URISyntaxException {
        Configuration conf = new Configuration();
        FileSystem fileSystem = null;
        String hdfsUri = BaseUri;
        if (StringUtils.isBlank(hdfsUri)) {
            fileSystem = FileSystem.get(conf);
        } else {
            URI uri = new URI(hdfsUri.trim());
            fileSystem = FileSystem.get(uri, conf);
        }
        return fileSystem;
    }

    public static void copyToHDFS(String srcFile, String dstPath) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(new File(srcFile));
        Configuration conf = new Configuration();
        FileSystem fileSystem = FileSystem.get(URI.create(BaseUri + dstPath), conf);
        OutputStream outputStream = fileSystem.create(new Path(dstPath));
        IOUtils.copyBytes(fileInputStream, outputStream, 4096, true);
        System.out.println(String.format("copy %s to %s success", srcFile, dstPath));
        fileInputStream.close();
        outputStream.close();
        fileSystem.close();
    }

    public static void downloadFromHDFS(String srcFile, String dstPath) throws IOException {
        String file = BaseUri + srcFile;
        Configuration conf = new Configuration();

        FileSystem fileSystem = FileSystem.get(URI.create(file), conf);
        InputStream inputStream = fileSystem.open(new Path(file));
        FileOutputStream fileOutputStream = new FileOutputStream(new File(dstPath));
        IOUtils.copyBytes(inputStream, fileOutputStream, 2048, true);
        System.out.println(String.format("downLoad %s to %s success", srcFile, dstPath));
        fileOutputStream.close();
        inputStream.close();
        fileSystem.close();
    }

    public static void mkdirs(String path) throws IOException, URISyntaxException {
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

    public static void rmdir(String path) throws IOException, URISyntaxException {
        FileSystem fileSystem = getFileSystem();
        String hdfsUri = BaseUri;
        if (StringUtils.isNotBlank(hdfsUri)){
            path = hdfsUri + path;
        }
        fileSystem.delete(new Path(path), true);
        fileSystem.close();
    }

    public static void readFile(String filePath) throws IOException, InterruptedException {
        String file = BaseUri + filePath;
        Configuration conf = new Configuration();

        FileSystem fileSystem = FileSystem.get(URI.create(file), conf, "root");
        InputStream inputStream = fileSystem.open(new Path(file));
        IOUtils.copyBytes(inputStream, System.out, 2048, false);
        fileSystem.close();
    }
}
