package com.oniesoft.serviceimpl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Service
class FileServiceImpl {
    @Value("${file.upload-dir}")
    private String uploadDir;
    public String saveFile(String base64Data) throws IOException, NoSuchAlgorithmException {
        // Assume a default file extension
        String extension = "png";

        // Generate a hash-based file name
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] fileBytes = Base64.getDecoder().decode(base64Data);
        byte[] hash = digest.digest(fileBytes);
        StringBuilder hashString = new StringBuilder();
        for (byte b : hash) {
            hashString.append(String.format("%02x", b));
        }
        String fileName = hashString.toString() + "." + extension;

        // Save the file
        String userDir = uploadDir + "/images";
        String filePath = userDir + "/" + fileName;
        Files.createDirectories(Path.of(userDir));
        Files.write(Path.of(filePath), fileBytes);

        return filePath;
    }

    public void deleteImage(String filePath) throws IOException {
        // Get the absolute path of the file to delete
        Path pathToDelete = Paths.get(filePath);

        // Check if the file exists and delete it
        if (Files.exists(pathToDelete)) {
            Files.delete(pathToDelete);
        } else {
            throw new IOException("File not found: " + filePath);
        }
    }

}