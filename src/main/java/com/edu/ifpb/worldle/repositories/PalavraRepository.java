package com.edu.ifpb.worldle.repositories;

import com.edu.ifpb.worldle.entities.Palavra;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PalavraRepository extends JpaRepository<Palavra, Long> {

    List<Palavra> findByTamanho(Integer tamanho);
}
