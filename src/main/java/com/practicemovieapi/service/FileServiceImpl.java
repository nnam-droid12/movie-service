package com.practicemovieapi.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class FileServiceImpl implements FileService{

    @Override
    public String uploadFile(String path, MultipartFile file) throws IOException {
        // get file name

        String fileName = file.getOriginalFilename();

        // get file path

        String filePath = path + File.separator + fileName;

        // check if the file exist if not create a folder for it
        File f = new File(path);
        if(!f.exists()){
            f.mkdir();
        }

        // upload the file to the created folder
        Files.copy(file.getInputStream(), Paths.get(filePath));
        return fileName;
    }

    @Override
    public InputStream getFileUrl(String path, String fileName) throws FileNotFoundException {
        String filePath = path + File.separator + fileName;

        return new FileInputStream(filePath);
    }
}
