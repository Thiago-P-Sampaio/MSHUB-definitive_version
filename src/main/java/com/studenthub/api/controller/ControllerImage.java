package com.studenthub.api.controller;

import com.studenthub.api.Service.CloudnaryService;
import com.studenthub.api.domain.Aluno;
import com.studenthub.api.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("image")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class ControllerImage {

    @Autowired
     CloudnaryService imageService;

    @Autowired
    AlunoRepository repository;




    @PostMapping("/upload")
    public ResponseEntity<Map> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            Map uploadResult = imageService.uploadImage(file);
            String imageUrl = (String) uploadResult.get("url"); // Obtendo a URL da imagem
            Aluno exists = repository.findLastStudent();
            exists.getClass();
            exists.setImagURL(imageUrl);
            repository.save(exists);

            return ResponseEntity.ok(Map.of("url", imageUrl)); // Retornando a URL em um mapa
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Upload failed: " + e.getMessage()));
        }
    }
}