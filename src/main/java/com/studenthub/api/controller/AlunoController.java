package com.studenthub.api.controller;

import com.studenthub.api.Service.CloudnaryService;
import com.studenthub.api.domain.Aluno;
import com.studenthub.api.dto.AlunoDTO;
import com.studenthub.api.repository.AlunoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/mshub")
@CrossOrigin(origins = "*")
public class AlunoController {

    @Autowired
    AlunoRepository repository;

    @Autowired
    CloudnaryService imageService;

    @GetMapping("get")
    public ResponseEntity BuscarDados(){
        var getList = repository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(getList);
    }

    @GetMapping("get/{id}")
    public ResponseEntity BuscarDadosPorID(@PathVariable UUID id){
        Optional<Aluno> exists = repository.findById(id);
        if(exists.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(exists);
        } else return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }

    @PostMapping(value = "new")
    public  ResponseEntity CadastrarAluno(@RequestBody @Valid AlunoDTO dados){
        Aluno newAluno = new Aluno(dados);
        repository.save(newAluno);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("update/{id}")
    public ResponseEntity AtlzDadosPorId(@PathVariable @Valid UUID id, @RequestBody @Valid AlunoDTO dados){
        Optional<Aluno> exists = repository.findById(id);
        if(exists.isPresent()){
            Aluno atualizarCredencial = exists.get();
            if(dados.nome() != null){
                atualizarCredencial.setNome(dados.nome());
                repository.save(atualizarCredencial);
            }
            if(dados.email() != null){
                atualizarCredencial.setEmail(dados.email());
                repository.save(atualizarCredencial);
            }
            if(dados.matricula() != 0){
                atualizarCredencial.setMatricula((dados.matricula()));
                repository.save(atualizarCredencial);
            }
            if(dados.telefone() != null){
                atualizarCredencial.setTelefone(dados.telefone());
                repository.save(atualizarCredencial);
            }
            if(dados.responsavel() != null) {
                atualizarCredencial.setResponsavel(dados.responsavel());
                repository.save(atualizarCredencial);
            }


            return ResponseEntity.status(HttpStatus.CREATED).build();
        }else return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/delete/{id}")
    public  ResponseEntity DeletarAlunoPorID(@PathVariable @Valid UUID id){
        Optional<Aluno> exists = repository.findById(id);
        if(exists.isPresent()){
            repository.deleteById(id);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }else return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }




}
