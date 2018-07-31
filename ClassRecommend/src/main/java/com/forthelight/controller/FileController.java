package com.forthelight.controller;

import com.forthelight.util.ImageSize;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class FileController {

    @RequestMapping(value = "/avatar_upload", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String avatarUpload(HttpServletRequest request, HttpServletResponse response, @RequestParam("avatar_file") MultipartFile file) throws IOException {
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        Map<String, Object> rsp = new HashMap<>();

        if (file != null && file.getSize() > 0) {
            String fileName = file.getOriginalFilename();
            if (fileName != null) {
                // 裁剪图片
                ImageSize imageSize = gson.fromJson(request.getParameter("avatar_data"), ImageSize.class);
                BufferedImage image = ImageIO.read(file.getInputStream()).getSubimage(imageSize.getIntX(), imageSize.getIntY(), imageSize.getIntHeight(), imageSize.getIntHeight());
                // 生成目标文件夹
                String dstPath = request.getServletContext().getRealPath("images" + File.separator + "avatar");
                File dstFolder = new File(dstPath);
                if (!dstFolder.exists()) {
                    dstFolder.mkdirs();
                    rsp.put("mkdirs", true);
                } else {
                    rsp.put("mkdirs", false);
                }
                // 生成目标空文件
                String dstFileName = fileName.substring(0, fileName.lastIndexOf("."));
                String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
                File dstFile = new File(new File(dstPath) + File.separator + dstFileName + "." + fileExt);
                // 拷贝文件
                ImageIO.write(image, fileExt, dstFile);

                rsp.put("success", true);
                rsp.put("result", "../../images/avatar/" + file.getOriginalFilename());
            } else {
                rsp.put("success",false);
                rsp.put("errorInfo", "empty file name");
            }
        } else {
            rsp.put("success", false);
        }





        return gson.toJson(rsp);
    }

    @RequestMapping(value = "/fileUpload", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String fileUpload(@RequestParam("files") MultipartFile[] files, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> rsp = new HashMap<>();
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();


        if(files != null && files.length > 0){
            for(MultipartFile file: files){
                if(file!=null && file.getSize() > 0){
                    String fileName = file.getOriginalFilename();
                    if(fileName != null) {
                        // 生成目标文件夹
                        String dstPath = request.getServletContext().getRealPath("files");
                        File dstFolder = new File(dstPath);
                        if (!dstFolder.exists()) {
                            dstFolder.mkdirs();
                            rsp.put("mkdirs", true);
                        } else {
                            rsp.put("mkdirs", false);
                        }
                        // 生成目标空文件
                        File dstFile = new File(new File(dstPath) + File.separator + fileName);
                        FileUtils.copyToFile(file.getInputStream(), dstFile);
                        rsp.put("code", 200);
                        rsp.put("success", true);
                    }
                } else {
                    rsp.put("code", 404);
                    rsp.put("success", false);
                    rsp.put("errorInfo", "empty file");
                }
            }
        } else {
            rsp.put("code", 404);
            rsp.put("success", false);
            rsp.put("errorInfo", "no file accept");
        }

        return gson.toJson(rsp);
    }

    // 非ajax 传文件
    @RequestMapping(value = "/testUpload", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String testUpload(@RequestParam("testFile") MultipartFile file) {
        System.out.println("===");
        return "{\"success\":true}";

    }
}
