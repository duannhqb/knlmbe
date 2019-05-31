package com.team.xiaolin.service.impl;

import com.team.xiaolin.config.Constants;
import com.team.xiaolin.service.FileService;
import com.team.xiaolin.service.dto.UploadResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;

@Service
public class FileServiceImpl implements FileService {

    private final Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    public FileServiceImpl(){
    }

    @Override
    public UploadResponse saveImage(MultipartFile file) throws IOException {

        StringBuilder path = new StringBuilder();
        path.append(Constants.FolderName.FOLDER_ROOT).append("/");

        Long status = save(file, path.toString());

        UploadResponse response = createUploadResponse(file, path);

        return status != 0 ? response : null;
    }

    @Override
    public boolean checkFileIsImage(MultipartFile file) {
        String mimeType= file.getContentType();
        String type = mimeType.split("/")[0];
        return type.equals("image");
    }

    private Long save(MultipartFile file, String path) throws IOException {
        if (file.isEmpty()) {
            throw new RuntimeException("File is empty");
        }
        Path location = Paths.get(path);
        if (!Files.exists(location)) {
            new File(location.toString()).mkdirs();
        }

        return Files.copy(file.getInputStream(),
            location.resolve(Base64.getEncoder().encodeToString(file.getOriginalFilename().getBytes())),
            StandardCopyOption.REPLACE_EXISTING);
    }

    private UploadResponse createUploadResponse(MultipartFile file, StringBuilder path) {
        UploadResponse response = new UploadResponse();
        response.setFileContentType(file.getContentType());
        response.setFileName(file.getOriginalFilename());
        response.setStatus(true);
        response.setFileUrl(path.toString()+Base64.getEncoder().encodeToString(file.getOriginalFilename().getBytes()));
        return response;
    }
}
