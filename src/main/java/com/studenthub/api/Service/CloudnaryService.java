package com.studenthub.api.Service;

import com.cloudinary.Cloudinary;
import com.cloudinary.api.ApiResponse;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
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

    public String DeleteImag(String imageUrl) {
        try {
            // Extraindo o public_id da URL
            String publicId = imageUrl.split("/image/upload/")[1].split("\\?")[0].split("\\.")[0]; // Obtém o public_id

            // Deletando a imagem utilizando o deleteResources
            ApiResponse apiResponse = cloudinary.api().deleteResources(
                    Arrays.asList(publicId),
                    ObjectUtils.asMap("type", "upload", "resource_type", "image")
            );
            return "";

        } catch (IOException exception) {
            System.out.println("Erro ao excluir a imagem: " + exception.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    return null;
    }
}
