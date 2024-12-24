package com.oniesoft.serviceimpl;

import com.oniesoft.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Service
public class FileServiceImpl {

    @Autowired
    S3Service s3Service;

    private String uploadDir=System.getProperty("user.dir")+ File.separator+"assets";
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
        // TODO: Uncomment below three lines once S3 bucket is configured
//        s3Service.uploadFile(hashString.toString(), new File(filePath));
//        this.deleteImage(filePath);
//        return hashString.toString();
        return fileName;
    }

    public ResponseEntity<Resource> getFilePath(String fileName){
        try {
            // Resolve the image path
            Path imagePath = Paths.get(uploadDir, "images", fileName);

            // Check if the file exists
            if (!Files.exists(imagePath)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            // Load the resource
            Resource resource = new UrlResource(imagePath.toUri());
            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_PNG)
                        .cacheControl(CacheControl.noCache())
                        .body(resource);
            } else {
//                System.out.println("Resource is not readable: " + imagePath.toString());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private void deleteImage(String filePath) throws IOException {
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