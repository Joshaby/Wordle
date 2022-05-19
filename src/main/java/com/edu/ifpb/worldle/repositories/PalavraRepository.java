package com.edu.ifpb.worldle.repositories;

import com.edu.ifpb.worldle.entities.Palavra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PalavraRepository extends JpaRepository<Palavra, Long> {

    @Query("SELECT palavra FROM Palavra palavra " +
            "WHERE palavra.tamanho = :tamanho " +
            "ORDER BY function('RAND')")
    List<Palavra> findRandByTamanho(@Param("tamanho") Integer tamanho);
}
