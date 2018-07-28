package com.forthelight.util;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;

import java.io.IOException;
import java.net.URISyntaxException;

public class HDFSTest {
    public static void main(String[] args) {
//        Configuration conf = new Configuration();
//        conf.set("fs.hdfs.impl", "org.apache.hadoop.hdfs.DistributedFileSystem");

        try {
            //HDFS.readFile("/test/test.txt");
            //HDFS.copyToHDFS("d:\\aaa.txt", "/test/bbb.txt");
            System.setProperty("HADOOP_USER_NAME", "root");
            FileSystem fs = HDFS.getFileSystem();

            //RemoteIterator<LocatedFileStatus> dir = fs.listFiles(new Path("/"), true);
            fs.mkdirs(new Path("/test"));
            System.out.println();
        } catch (Exception  e) {
            e.printStackTrace();
        }
    }
}
