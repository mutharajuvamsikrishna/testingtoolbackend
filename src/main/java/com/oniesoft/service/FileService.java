package com.oniesoft.service;

import java.io.IOException;

public interface FileService {
    void deleteFile(Long regno, String fileType, String fileName) throws IOException;
}
