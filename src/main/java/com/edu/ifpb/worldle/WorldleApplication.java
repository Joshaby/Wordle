package com.edu.ifpb.worldle;

import com.edu.ifpb.worldle.entities.Palavra;
import com.edu.ifpb.worldle.services.PalavraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.*;

@SpringBootApplication
public class WorldleApplication implements CommandLineRunner {

	@Autowired
	public PalavraService service;

	private Scanner input = new Scanner(System.in);

	public static void main(String[] args) {
		SpringApplication.run(WorldleApplication.class, args);
	}

	@Override
	public void run(String... args) throws IOException {
		Palavra palavraObj;
		int qtdeTentativas = 600;
		int count = 1;
		int tamanho;
		while (true) {
			try {
				System.out.print("Digite o tamanho da palavra(mínimo de 2 letras): ");
				tamanho = input.nextInt();
				System.out.println("Buscando palavra...");
				palavraObj = service.findPalavraByTamanho(tamanho);
				//palavraObj = new Palavra(null, "arara", 5);
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
				System.out.println("Ok! Será mantido o padrão de 6 tentativas!");
				break;
			default:
				System.out.println("É o que kkkjj? Enfim, Será mantido o padrão de 6 tentativas!");
				break;
		}
		while (qtdeTentativas != 0) {
			String palavra;
			System.out.printf("(Tentativa %d) Digite uma palavra: ", count);
			palavra = input.next();
			if (palavra.length() > palavraObj.getTamanho() || palavra.length() < palavraObj.getTamanho()) {
				System.out.printf("Você precisa digitar uma palavra de tamanho %d!\n", tamanho);
			} else {
				if (palavra.equals(palavraObj.getPalavra())) {
					System.out.println("PARABÉNS!!! Você acertou a palavra!!! Tome aqui 10 centavos de moral!!!");
					System.exit(0);
				} else {
					verificarPalavraDigitada(palavra, palavraObj.getPalavra(), tamanho);
				}
				qtdeTentativas --;
				count ++;
			}
		}
		System.out.printf("Infelizmente, você não acertou a palavra ); !! E ela era \"%s\"\n", palavraObj.getPalavra());
		System.exit(0);
	}

	private void verificarPalavraDigitada(String palavra, String palavra1, int tamanho) {
		ArrayList<String> letras = new ArrayList<>();
		for (int i = 0; i < tamanho; i ++) {
			if (palavra.charAt(i) == palavra1.charAt(i)) {
				letras.add(String.valueOf(palavra.charAt(i)).toUpperCase());
			} else {
				letras.add("_");
			}
		}
		for (int i = 0; i < tamanho; i ++) {
			if (letras.get(i).equals("_")) {
				String letra = String.valueOf(palavra.charAt(i));
				for (int j = 0; j < tamanho; j ++) {
					if (letra.equalsIgnoreCase(String.valueOf(palavra1.charAt(j))) &&
							checarQuantidadeLetras(letra, letras, palavra1)) {

						letras.set(i, letra);
					}
				}
			}
		}
		System.out.println(letras.stream().reduce("", String::concat));
	}

	private boolean checarQuantidadeLetras(String letra, ArrayList<String> letras, String palavra) {
		int qtdeInLetras = 0;
		int qtdeInPalavra = 0;
		for (int i = 0; i < palavra.length(); i ++) {
			if (String.valueOf(palavra.charAt(i)).equalsIgnoreCase(letra)) {
				qtdeInPalavra += 1;
			}
			if (letras.get(i).equalsIgnoreCase(letra)) {
				qtdeInLetras += 1;
			}
		}
		return qtdeInLetras < qtdeInPalavra;
	}
}
