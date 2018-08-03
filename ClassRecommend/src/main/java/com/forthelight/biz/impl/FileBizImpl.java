package com.forthelight.biz.impl;

import com.forthelight.biz.FileBiz;
import com.forthelight.dao.FileDao;
import com.forthelight.domain.File;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileBizImpl implements FileBiz {
	
	@Autowired
	private FileDao fileDao;

	@Override
	public File findById(int id) {
		// TODO Auto-generated method stub
		return fileDao.findById(id);
	}

	@Override
	public List<File> findByStudentId(int studentId) {
		// TODO Auto-generated method stub
		return fileDao.findByStudentId(studentId);
	}

	@Override
	public List<File> findByCourseId(int courseId) {
		// TODO Auto-generated method stub
		return fileDao.findByCourseId(courseId);
	}

	@Override
	public int uploadsNumberOfCourse(int courseId) {
		// TODO Auto-generated method stub
		return fileDao.uploadsNumberOfCourse(courseId);
	}

    @Override
    public int insert(File file) {
        return fileDao.insert(file);
    }

    @Override
    public boolean saveFile(String dstPath, MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        if (fileName != null) {
            // 生成目标文件夹
            java.io.File dstFolder = new java.io.File(dstPath);
            if (!dstFolder.exists()) {
                dstFolder.mkdirs();
            }
            // 生成目标空文件
            java.io.File dstFile = new java.io.File(dstFolder + java.io.File.separator + fileName);
            FileUtils.copyToFile(file.getInputStream(), dstFile);
            return true;
        }
        return false;
    }
}
