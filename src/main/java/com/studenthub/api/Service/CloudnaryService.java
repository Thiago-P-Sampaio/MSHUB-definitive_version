package com.studenthub.api.Service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    // Método para excluir a imagem pelo public ID
    public String deleteImageByUrl(String imageUrl) {
        try {
            // Extrai o public ID completo, incluindo o caminho da pasta
            String publicId = extractPublicId(imageUrl); // Isso retorna o publicId completo
            Map<String, Object> deleteResult = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());

            // Verifica se a exclusão foi bem-sucedida
            String result = (String) deleteResult.get("result");
            if ("ok".equals(result)) {
                return result; // Retorna "ok" se a exclusão foi bem-sucedida
            } else {
                System.err.println("Falha ao excluir a imagem: " + deleteResult);
                return "falha"; // Retorna "falha" se não foi bem-sucedido
            }
        } catch (Exception e) {
            System.err.println("Erro ao excluir a imagem: " + e.getMessage());
            return "erro";
        }
    }

    // Método para extrair o public ID da URL da imagem
    private String extractPublicId(String imageUrl) {
        // Certifique-se de que a URL não esteja vazia ou nula
        if (imageUrl == null || imageUrl.isEmpty()) {
            throw new IllegalArgumentException("A URL da imagem não pode ser nula ou vazia.");
        }

        // Divide a URL para extrair o public ID
        String[] parts = imageUrl.split("/");
        String lastPart = parts[parts.length - 1];
        String[] idParts = lastPart.split("\\.");

        // Retorna o public ID sem a extensão do arquivo
        return idParts[0]; // Retorna o public ID com o caminho da pasta
    }


    public void deleteImageByUrl(String imageUrl) {
        // Extract public ID from URL (you may need to adjust this based on your URL structure)
        String publicId = extractPublicIdFromUrl(imageUrl);



        try {
            // Call the destroy method
            Map result = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
