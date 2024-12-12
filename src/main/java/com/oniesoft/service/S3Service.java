package com.oniesoft.service;

import java.io.File;

public interface S3Service {

    void uploadFile(String key, File file);
    String getPresignedUrl(String key);
}
