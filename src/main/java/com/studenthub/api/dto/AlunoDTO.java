package com.studenthub.api.dto;

public record AlunoDTO(
        String nome,
        String email,
        String telefone,
        int matricula,
        String responsavel) {
}
