package com.edu.ifpb.worldle.services;

import com.edu.ifpb.worldle.entities.Palavra;
import com.edu.ifpb.worldle.repositories.PalavraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class PalavraService {

    private static final Random RANDOM = new Random();

    @Autowired
    private PalavraRepository repository;

    public Palavra findPalavraByTamanho(Integer tamanho) throws Exception {
        List<Palavra> palavras = repository.findRandByTamanho(tamanho);
        if (palavras.isEmpty()) {
            throw new Exception(String.format("\nNão foram encontradas palavras com tamanho %d! Tente novamente!\n", tamanho));
        }
        return palavras.get(RANDOM.nextInt(palavras.size() - 1));
    }
}
