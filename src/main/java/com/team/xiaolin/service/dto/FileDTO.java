package com.team.xiaolin.service.dto;

import org.springframework.web.multipart.MultipartFile;

public class FileDTO {

    private MultipartFile file;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
