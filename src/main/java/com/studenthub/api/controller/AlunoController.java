package com.studenthub.api.controller;

import com.studenthub.api.domain.Aluno;
import com.studenthub.api.dto.AlunoDTO;
import com.studenthub.api.repository.AlunoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/mshub")
public class AlunoController {

    @Autowired
    AlunoRepository repository;

    @GetMapping("get")
    public ResponseEntity BuscarDados(){
        var getList = repository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(getList);
    }

    @GetMapping("get/{id}")
    public ResponseEntity BuscarDadosPorID(@PathVariable UUID id){
        var getId = repository.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(getId);
    }

    @PostMapping("new")
    public ResponseEntity CadastrarAluno(@RequestBody @Valid AlunoDTO dados){
        Aluno newAluno = new Aluno(dados);
        repository.save(newAluno);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
