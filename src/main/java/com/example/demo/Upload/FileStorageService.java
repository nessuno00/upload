package com.example.demo.Upload;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;



@Service
public class FileStorageService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @PostConstruct
    public void init() {
        try {
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload directory!", e);
        }
    }

    public Path getFilePath(String filename) {
        return Paths.get(uploadDir).resolve(filename).normalize();
    }

    public void saveFile(String filename, byte[] content) throws IOException {
        Path filePath = getFilePath(filename);
        Files.write(filePath, content);
    }

    public Resource loadFileAsResource(String filename) throws IOException {
        Path filePath = getFilePath(filename);
        UrlResource resource = new UrlResource(filePath.toUri());

        if (resource.exists() && resource.isReadable()) {
            return (Resource) resource;
        } else {
            throw new IOException("Could not read file: " + filename);
        }
    }
}
