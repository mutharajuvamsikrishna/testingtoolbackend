package com.oniesoft.serviceimpl;

import com.oniesoft.service.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Service
class FileServiceImpl implements FileService {
    @Value("${file.upload-dir}")
    private String uploadDir;
    public String saveFile(Long regno, String fileType, MultipartFile file) throws IOException {


        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String userDir = uploadDir + "/" + regno;
        String filePath = userDir + "/" + fileType + "_" + fileName;

        // Create the user directory if it doesn't exist
        Files.createDirectories(Path.of(userDir));

        // Save the file to the specified path
        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, Path.of(filePath), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new IOException("Failed to save file " + fileName, e);
        }

        return filePath;
    }
    @Override
    public void deleteFile(Long regno, String fileType, String fileName) throws IOException {
        String userDir = uploadDir + "/" + regno;
        String filePath = "";

        // Determine if we are deleting a specific file or the entire folder
        if (!fileType.isEmpty()) {
            filePath = userDir + "/" + fileType + "_" + fileName;
        } else {
            filePath = userDir;
        }

        Path pathToDelete = Path.of(filePath);
        System.out.println("Deleting: " + filePath);

        // Check if it's a directory
        if (Files.isDirectory(pathToDelete)) {
            // Recursively delete contents of the directory
            deleteDirectoryRecursively(pathToDelete);
        } else {
            // Delete the file
            try {
                Files.delete(pathToDelete);
            } catch (NoSuchFileException e) {
                throw new IOException("File " + filePath + " not found", e);
            } catch (IOException e) {
                throw new IOException("Failed to delete file " + fileName, e);
            }
        }
    }

    // Helper method to recursively delete directory contents
    private void deleteDirectoryRecursively(Path dir) throws IOException {
        // Walk the file tree and delete files and subdirectories
        Files.walk(dir)
                .sorted((a, b) -> b.compareTo(a))  // Sort in reverse order to delete files before directories
                .forEach(path -> {
                    try {
                        Files.delete(path);
                    } catch (IOException e) {
                        throw new RuntimeException("Failed to delete " + path, e);
                    }
                });
    }

}