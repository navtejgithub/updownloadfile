package com.example.updownloadfile;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/file")
public class StorageController {

    @Autowired
    private StorageService service;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        String uploadStatus = service.uploadFile(file);
        return ResponseEntity.status(HttpStatus.OK)
                .body(uploadStatus);
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<?> downloadFile(@PathVariable String fileName) {
        byte[] fileData = service.downloadFile(fileName);

        // Determine media type based on file extension
        String contentType = getContentTypeByFileName(fileName);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.parseMediaType(contentType))
                .body(fileData);
    }

    private String getContentTypeByFileName(String fileName) {
        if (fileName.endsWith(".pdf")) {
            return "application/pdf";
        } else if (fileName.endsWith(".txt")) {
            return "text/plain";
        } else if (fileName.endsWith(".doc") || fileName.endsWith(".docx")) {
            return "application/msword";
        } else {
            return "application/octet-stream";
        }
    }
}

