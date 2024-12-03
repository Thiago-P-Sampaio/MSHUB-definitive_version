package com.studenthub.api.dto;

import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.RequestParam;

public record AlunoDTO( // ADICIONAR ALGUMAS VALIDAÇÕES -> @Notnull
                        @NotNull
                        @RequestParam("nome")
                        String nome,
                        @NotNull
                        @RequestParam("email")
                        String email,
                        @NotNull
                        @RequestParam("telefone")
                        String telefone,
                        @NotNull
                        @RequestParam("matricula")
                        int matricula,
                        @NotNull
                        String responsavel) {
}
