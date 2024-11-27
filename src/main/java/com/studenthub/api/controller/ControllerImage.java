package com.studenthub.api.controller;

import com.studenthub.api.Service.CloudnaryService;
import com.studenthub.api.domain.Aluno;
import com.studenthub.api.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("image")
@CrossOrigin(origins = "*")
public class ControllerImage {

    @Autowired
     CloudnaryService imageService;

    @Autowired
    AlunoRepository repository;




    @PostMapping("/upload")
    public ResponseEntity<Map<String, Object>> uploadImage(
            @RequestParam("file") MultipartFile file) {
        try {
            // Chama o serviço para fazer o upload da imagem
            Map<String, Object> uploadResult = imageService.uploadImage(file);

            // Obtendo a URL da imagem
            String imageUrl = (String) uploadResult.get("url");

            // Atualiza a URL da imagem no objeto Aluno
            Aluno exists = repository.findLastStudent();
            if (exists != null) {
                exists.setImagURL(imageUrl);
                repository.save(exists);
            } else {
                return ResponseEntity.status(404).body(Map.of("error", "Aluno não encontrado"));
            }

            // Retornando a URL em um mapa
            return ResponseEntity.ok(Map.of("url", imageUrl));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Upload failed: " + e.getMessage()));
        }


    }



    @PutMapping("updt/{id}")
    public ResponseEntity<Map<String, Object>> updtImage(@PathVariable UUID id, @RequestParam("file") MultipartFile file) {
        Optional<Aluno> find = repository.findById(id);

        if (find.isPresent()) {
            Aluno atlzImage = find.get();
            String urlAluno = atlzImage.getImagURL();

            // Verifica se a URL da imagem antiga não é nula ou vazia
            if (urlAluno != null && !urlAluno.isEmpty()) {
                try {
                    // Tentando excluir a imagem antiga do Cloudinary
                    String deleteSuccess = imageService.deleteImageByUrl(urlAluno);

                    // Aqui você pode verificar o resultado da exclusão
                    if (!"ok".equals(deleteSuccess)) {
                        return ResponseEntity.status(500).body(Map.of("error", "Failed to delete old image: " + deleteSuccess));
                    }
                } catch (Exception e) {
                    return ResponseEntity.status(500).body(Map.of("error", "Error deleting old image: " + e.getMessage()));
                }
            }

            try {
                // Faremos a mudança de imagem após o upload
                Map<String, Object> uploadResult = imageService.uploadImage(file);
                String imageUrl = (String) uploadResult.get("url");

                // Se o upload for bem-sucedido, atualizamos a URL da imagem
                atlzImage.setImagURL(imageUrl);
                repository.save(atlzImage);

                // Retornamos a URL da nova imagem
                return ResponseEntity.ok(Map.of("url", imageUrl));
            } catch (Exception e) {
                // Em caso de erro no upload, podemos restaurar a imagem antiga se necessário
                return ResponseEntity.status(500).body(Map.of("error", "Upload failed: " + e.getMessage()));
            }
        } else {
            // Caso o aluno não seja encontrado
            return ResponseEntity.status(404).body(Map.of("error", "Aluno não encontrado"));
        }

    }
}


