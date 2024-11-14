package com.studenthub.api.repository;

import com.studenthub.api.domain.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
@EnableJpaRepositories
public interface AlunoRepository extends JpaRepository<Aluno, UUID> {

    @Query("SELECT a FROM Aluno a ORDER BY a.id DESC  LIMIT 1")
    Aluno findLastUser();
}
