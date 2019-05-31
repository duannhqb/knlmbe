package com.team.xiaolin.web.rest;

import com.team.xiaolin.service.FileService;
import com.team.xiaolin.service.dto.UploadResponse;
import com.team.xiaolin.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.ResponseUtil;
import io.micrometer.core.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;


@RestController
@RequestMapping("/api")
public class FileResource {

    private final Logger logger = LoggerFactory.getLogger(FileResource.class);

    private static final String ENTITY_NAME = "fileResource";

    private final FileService fileService;

    public FileResource(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/file/upload")
    @Timed
    public ResponseEntity<UploadResponse> uploadFile(@RequestParam MultipartFile file) {
        logger.debug("Rest request to save file {}", file.toString());
        UploadResponse uploadResponse = null;
        try {
            if (!fileService.checkFileIsImage(file))
                throw new BadRequestAlertException("File type not image", ENTITY_NAME, "notImage");
            uploadResponse = fileService.saveImage(file);
        } catch (IOException e) {
            logger.error("Upload file {} error", file.getOriginalFilename());
        }
        if (uploadResponse == null)
            throw new BadRequestAlertException("Upload file error", ENTITY_NAME, "errorUpload");
        logger.debug("Upload file {} susses", file.getOriginalFilename());
        return ResponseUtil.wrapOrNotFound(Optional.of(uploadResponse));

    }
}
