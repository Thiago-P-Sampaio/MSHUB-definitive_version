package com.studenthub.api.Service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@Service
public class CloudnaryService {

    private Cloudinary cloudinary;

    @Autowired
    public CloudnaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }


    public Map uploadImage(MultipartFile file) throws IOException {
        // Converte MultipartFile para File
        File tempFile = File.createTempFile("upload", file.getOriginalFilename());
        file.transferTo(tempFile);
        try {
            return cloudinary.uploader().upload(tempFile, ObjectUtils.emptyMap());
        } finally {
            // Apaga o arquivo temporário após o upload
            tempFile.delete();
        }

    }
}