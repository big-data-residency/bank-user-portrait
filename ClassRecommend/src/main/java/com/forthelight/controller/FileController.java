package com.forthelight.controller;

import com.forthelight.biz.CourseBiz;
import com.forthelight.biz.FileBiz;
import com.forthelight.biz.StudentBiz;
import com.forthelight.domain.Course;
import com.forthelight.domain.File;
import com.forthelight.domain.Student;
import com.forthelight.util.ImageSize;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class FileController {
    @Autowired
    FileBiz fileBiz;
    @Autowired
    StudentBiz studentBiz;
    @Autowired
    CourseBiz courseBiz;

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
                String dstPath = request.getServletContext().getRealPath("images" + java.io.File.separator + "avatar");
                java.io.File dstFolder = new java.io.File(dstPath);
                if (!dstFolder.exists()) {
                    dstFolder.mkdirs();
                    rsp.put("mkdirs", true);
                } else {
                    rsp.put("mkdirs", false);
                }
                // 生成目标空文件
                String dstFileName = fileName.substring(0, fileName.lastIndexOf("."));
                String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
                java.io.File dstFile = new java.io.File(new java.io.File(dstPath) + java.io.File.separator + dstFileName + "." + fileExt);
                // 拷贝文件
                ImageIO.write(image, fileExt, dstFile);
                Student student = studentBiz.findById(Integer.parseInt(request.getParameter("studentId")));
                student.setStudentPortrait("/images/avatar/" + file.getOriginalFilename());
                studentBiz.update(student);
                rsp.put("success", true);
                rsp.put("result", "../../images/avatar/" + file.getOriginalFilename());
            } else {
                rsp.put("success", false);
                rsp.put("errorInfo", "empty file name");
            }
        } else {
            rsp.put("success", false);
        }


        return gson.toJson(rsp);
    }

    @RequestMapping(value = "/fileUpload", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String fileUpload(@RequestParam("file") MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> rsp = new HashMap<>();
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();

        String title = request.getParameter("title");
        String description = request.getParameter("notes");
        String studentId = request.getParameter("studentId");
        String courseId = request.getParameter("courseId");
        Student student = studentBiz.findById(Integer.parseInt(studentId));
        Course course = courseBiz.findById(Integer.parseInt(courseId));

        if (file != null && file.getSize() > 0) {
            String dstPath = request.getServletContext().getRealPath("files");
//            String tgtPath = request.getContextPath()
            if (fileBiz.saveFile(dstPath, file)) {
                File saveFile = new File();
                saveFile.setStudent(student);
                saveFile.setCourse(course);
                saveFile.setFilePath(java.io.File.separator + "files");
                saveFile.setFileName(file.getOriginalFilename());
                saveFile.setDownloadNum(0);
                saveFile.setDescription(description);
                saveFile.setTitle(title);
                saveFile.setUpdateTime(new Timestamp(System.currentTimeMillis()));
//                        Student student = studentBiz.findById(stduentId);
//                        saveFile.setStudent(studentId);
//                        Course course = courseBiz.findById(courseId);
//                        saveFile.setCourse(course);
                fileBiz.insert(saveFile);
                rsp.put("success", true);
            }
        } else {
            rsp.put("success", false);
            rsp.put("errorInfo", "no file accept");
        }
        return gson.toJson(rsp);
    }

    @RequestMapping(value = "/getFilesByCourseId", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getFiles(@RequestParam("courseId") Integer courseId) {
        Map<String, Object> rsp = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();

        Map<Integer, Object> files = new HashMap<>();
        List<File> fileList = fileBiz.findByCourseId(courseId);
        for (int i = 0; i < fileList.size(); i++) {
            Map<String, Object> file = new HashMap<>();
            File temp = fileList.get(i);
            if (temp != null) {
                Student student = temp.getStudent();
                file.put("studentName", student == null ? null : student.getNickName());
                file.put("title", temp.getTitle());
                file.put("description", temp.getDescription());
                file.put("downloadNumber", temp.getDownloadNum());
                file.put("updateTime", temp.getUpdateTime());
                file.put("filePath", temp.getFilePath());
                file.put("fileName", temp.getFileName());
                files.put(i, file);
            }
        }
        data.put("files", files);
        rsp.put("data", data);
        rsp.put("success", true);
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
