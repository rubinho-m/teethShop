package com.rubinho.teethshop.controllers;

import com.rubinho.teethshop.dto.FIleDto;
import com.rubinho.teethshop.services.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/")
public class FileController {
    private final FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        fileService.save(file);
        String message = "Uploaded the file successfully: " + file.getOriginalFilename();
        return ResponseEntity.ok(message);

    }

    @GetMapping("/files")
    public ResponseEntity<List<FIleDto>> getListFiles() {
        List<FIleDto> fileInfos = fileService.loadAll().map(path -> {
            String filename = path.getFileName().toString();
            String url = MvcUriComponentsBuilder
                    .fromMethodName(FileController.class, "getFile", path.getFileName().toString()).build().toString();

            return new FIleDto(filename, url);
        }).collect(Collectors.toList());

        return ResponseEntity.ok(fileInfos);
    }

    @GetMapping("/files/{filename}")
    public ResponseEntity<byte[]> getFile(@PathVariable String filename) {
        byte[] file = fileService.load(filename);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.IMAGE_PNG)
                .body(file);

    }
}
