package com.studenthub.api.Service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Service
public class CloudnaryService {

    private Cloudinary cloudinary;

    @Autowired
    public CloudnaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public Map<String, Object> uploadImage(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("O arquivo não pode estar vazio");
        }

        try {
            // Cria um mapa de parâmetros para o upload
            HashMap<Object, Object> options = new HashMap<>();
            options.put("folder", "student");
            Map uploadedFile = cloudinary.uploader().upload(file.getBytes(), options);
            return uploadedFile;
        } catch (IOException e) {
            throw new IOException("Erro ao fazer upload da imagem: " + e.getMessage(), e);
        }
    }
}
