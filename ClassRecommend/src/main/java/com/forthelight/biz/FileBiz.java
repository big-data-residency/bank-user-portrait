package com.forthelight.biz;

import com.forthelight.domain.File;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileBiz {
	File findById(int id);

	List<File> findByStudentId(int studentId);

	List<File> findByCourseId(int courseId);

	int uploadsNumberOfCourse(int courseId);

	int insert(File file);

	boolean saveFile(String dstPath, MultipartFile file) throws IOException;


}
