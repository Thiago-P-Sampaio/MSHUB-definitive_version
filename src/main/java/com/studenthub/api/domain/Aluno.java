package com.studenthub.api.domain;

import com.studenthub.api.dto.AlunoDTO;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table (name="aluno")
public class Aluno {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String nome;
    private String email;
    private String telefone;
    private int matricula;
    private String responsavel;
    private String ImagURL;

    public Aluno() {
    }

    public Aluno(UUID id, String nome, String email, String telefone, int matricula, String responsavel, String ImagURL) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.matricula = matricula;
        this.responsavel = responsavel;
        this.ImagURL= ImagURL;
    }

    public Aluno(AlunoDTO dto){
        this.nome= dto.nome();
        this.email=dto.email();
        this.telefone= dto.telefone();
        this.matricula= dto.matricula();
        this.responsavel= dto.responsavel();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    public String getImagURL() {
        return ImagURL;
    }

    public void setImagURL(String imagURL) {
        ImagURL = imagURL;
    }
}
