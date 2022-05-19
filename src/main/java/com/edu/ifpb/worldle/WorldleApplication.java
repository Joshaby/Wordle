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
		Palavra palavraObj;
		int qtdeTentativas = 6;
		int tamanho;
		while (true) {
			try {
				System.out.print("Digite o tamanho da palavra(mínimo de 2 letras): ");
				tamanho = input.nextInt();
				System.out.println("Buscando palavra...");
				palavraObj = service.findPalavraByTamanho(tamanho);
				System.out.println(palavraObj);
				break;
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		System.out.print("Deseja alterar a quantidade de tentativas?(Digite s para sim e n para não) ");
		String reposta = input.next();
		switch (reposta.toLowerCase()) {
			case "s" :
				System.out.print("Digite a nova quantidade de tentativas: ");
				qtdeTentativas = input.nextInt();
				break;
			case "n" :
				System.out.print("Ok! Será mantido o padrão de 6 tentativas!");
				break;
			default:
				System.out.println("É o que kkkjj? Será mantido o padrão de 6 tentativas!");
				break;
		}
		while (qtdeTentativas != 0) {
			String palavra;
			System.out.print("Digite uma palavra: ");
			palavra = input.next();
			if (palavra.length() > palavraObj.getTamanho() || palavra.length() < palavraObj.getTamanho()) {
				System.out.printf("Você precisa digitar uma palavra de tamanho %d!\n", tamanho);
			} else {
				if (palavra.equals(palavraObj.getPalavra())) {
					System.out.println("PARABÉNS!!! Você acertou a palavra!!! Tome aqui 10 centavos de moral!!!");
					System.exit(0);
				}
				qtdeTentativas --;
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
