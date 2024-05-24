package com.example.demo.Download;

import com.example.demo.Upload.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

    @RestController
    @RequestMapping("/api/files")
    public class FileDownloadController {

        @Autowired
        private FileStorageService fileStorageService;

        @GetMapping("/download/{filename}")
        public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {
            try {
                Resource resource = (Resource) fileStorageService.loadFileAsResource(filename);

                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } catch (IOException e) {
                return ResponseEntity.status(404).body(null);
            }
        }
    }


