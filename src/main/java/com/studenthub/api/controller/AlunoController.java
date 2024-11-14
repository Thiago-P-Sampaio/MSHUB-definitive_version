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

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;
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
        Optional<Aluno> exists = repository.findById(id);
        if(exists.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(id);
        } else return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }

    @PostMapping("new")
    public ResponseEntity CadastrarAluno(@RequestBody @Valid AlunoDTO dados){
        Aluno newAluno = new Aluno(dados);
        repository.save(newAluno);


        return ResponseEntity.status(HttpStatus.CREATED).body("Hora:" + newAluno.getDateTime());
    }

    @PutMapping("update/{id}")
    public ResponseEntity AtlzDadosPorId(@PathVariable @Valid UUID id, @RequestBody @Valid AlunoDTO dados){
        Optional<Aluno> exists = repository.findById(id);
        if(exists.isPresent()){
            Aluno atualizarCredencial = exists.get();
            atualizarCredencial.setNome(dados.nome());
            atualizarCredencial.setEmail(dados.email());
            atualizarCredencial.setMatricula((dados.matricula()));
            atualizarCredencial.setTelefone(dados.telefone());
            atualizarCredencial.setResponsavel(dados.responsavel());
            repository.save(atualizarCredencial);

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
