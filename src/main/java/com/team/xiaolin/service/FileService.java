package com.team.xiaolin.service;

import com.team.xiaolin.service.dto.UploadResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {

    UploadResponse saveImage(MultipartFile file) throws IOException;

    boolean checkFileIsImage(MultipartFile file);

}
