package com.rubinho.teethshop.services;

import com.rubinho.teethshop.exceptions.AppException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
public class FileService {
    private final String DIR = "uploads";
    private final Path root = Paths.get(DIR);

    public void init() {
        try {
            Files.createDirectories(root);
        } catch (IOException e) {
            throw new AppException("Could not initialize folder for upload!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void save(MultipartFile file) {
        try {
            Files.copy(file.getInputStream(), this.root.resolve(Objects.requireNonNull(file.getOriginalFilename())));
        } catch (FileAlreadyExistsException ignored) {

        } catch (Exception e) {
            throw new AppException("Couldn't save file", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public byte[] load(String filename) {
        try {
            return Files.readAllBytes(new File(DIR + "/" + filename).toPath());
        } catch (IOException e) {
            throw new AppException("Couldn't read file", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
        } catch (IOException e) {
            throw new AppException("Could not load the files!", HttpStatus.BAD_REQUEST);
        }
    }


}
