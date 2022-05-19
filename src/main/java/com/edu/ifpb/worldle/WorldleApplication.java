package com.edu.ifpb.worldle;

import com.edu.ifpb.worldle.entities.Palavra;
import com.edu.ifpb.worldle.repositories.PalavraRepository;
import com.edu.ifpb.worldle.services.PalavraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

@SpringBootApplication
public class WorldleApplication implements CommandLineRunner {

	@Autowired
	public PalavraRepository repository;

	@Autowired
	public PalavraService service;

	private final static String WORDS_SMALL_TXT = "words-small.txt";
	private final static String WORDS_TXT = "words.txt";

	private Scanner input = new Scanner(System.in);

	public static void main(String[] args) {
		SpringApplication.run(WorldleApplication.class, args);
	}

	@Override
	public void run(String... args) throws IOException {
		loadWords();
		while (true) {
			try {
				System.out.print("Digite o tamanho da palavra: ");
				int tamanho = input.nextInt();
				System.out.printf("Tamanho selecionado: %d\n", tamanho);
				System.out.println("Buscando palavra...");
				Palavra palavra = service.findPalavraByTamanho(tamanho);
				System.out.println(palavra);
				System.out.println();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

	private void loadWords() throws IOException {
		File worlds = new File(WORDS_TXT);
		if (worlds.exists()) {
			List<String> worldsList = new ArrayList<>(Files.readAllLines(Path.of(WORDS_SMALL_TXT)));
			for (String world : worldsList) {
				repository.save(new Palavra(null, world, world.length()));
			}
		}
	}
}
