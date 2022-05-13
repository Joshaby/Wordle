package com.edu.ifpb.worldle;

import com.edu.ifpb.worldle.entities.Palavra;
import com.edu.ifpb.worldle.repositories.PalavraRepository;
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

	private final static String WORDS_SMALL_TXT = "words-small.txt";
	private final static String WORDS_TXT = "words.txt";

	private final static int QTDE_PALAVRAS = 261799;

	private Random gerador = new Random();

	private Scanner input = new Scanner(System.in);

	public static void main(String[] args) {
		SpringApplication.run(WorldleApplication.class, args);
	}

	@Override
	public void run(String... args) throws IOException {
		loadWords();
		while (true) {
			System.out.print("Digite o tamanho da palavra: ");
			int tamanho = input.nextInt();
			System.out.printf("Tamanho selecionado: %d\n", tamanho);
			System.out.println("Buscando palavra...");
			System.out.println(repository.findRandByTamanho(tamanho).get(0));
			System.out.println();
		}
	}

	private void loadWords() throws IOException {
		File worlds = new File(WORDS_TXT);
		if (worlds.exists()) {
			List<String> worldsList = new ArrayList<>(Files.readAllLines(Path.of(WORDS_TXT)));
			for (String world : worldsList) {
				repository.save(new Palavra(null, world, world.length()));
			}
		}
	}
}
