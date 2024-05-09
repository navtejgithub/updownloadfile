package com.example.updownloadfile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class StorageService {

    @Autowired
    private StorageRepository repository;

    public String uploadFile(MultipartFile file) throws IOException {
        String fileType = getFileType(file.getOriginalFilename());

        byte[] fileData = DocUtils.compressDoc(file.getBytes());

        FileData fileDataEntity = repository.save(FileData.builder()
                .name(file.getOriginalFilename())
                .type(fileType)
                .fileData(fileData).build());

        if (fileDataEntity != null) {
            return "File uploaded successfully : " + file.getOriginalFilename();
        }
        return null;
    }

    public String getFileType(String fileName) {
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

    public byte[] downloadFile(String fileName) {
        FileData fileData = repository.findByName(fileName);
        if (fileData != null) {
            return DocUtils.decompressDoc(fileData.getFileData());
        }
        return null;
    }
}
