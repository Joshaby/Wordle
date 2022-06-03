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
		int qtdeJogadas;
		int qtdeTentativas = 6;
		int count = 1;
		int tamanho;
		Palavra palavraObj;
		while (true) {
			try {
				System.out.print("\nDigite a quantidade de vezes que você quer esse jogo: ");
				qtdeJogadas = input.nextInt();
				if (qtdeJogadas <= 0) {
					System.out.println("Não digite 0 ou número negativo kkjj!!\n");
				} else {
					break;
				}
			} catch (Exception e) {
				System.out.println("Digite apenas números!!\n");
			}
		}
		for (int i = 0; i < qtdeJogadas; i ++) {
			while (true) {
				try {
					System.out.print("\nDigite o tamanho da palavra(mínimo de 2 letras): ");
					tamanho = input.nextInt();
					if (tamanho < 2) {
						System.out.println("Digite um número maior do que 2!!");
					} else {
						System.out.println("Buscando palavra...\n");
						palavraObj = service.findPalavraByTamanho(tamanho);
						break;
					}
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
			System.out.print("Deseja alterar a quantidade de tentativas?(Digite s para sim e n para não) ");
			String reposta = input.next();
			switch (reposta.toLowerCase()) {
				case "s" :
					while (true) {
						System.out.print("Digite a nova quantidade de tentativas: ");
						try {
							qtdeTentativas = input.nextInt();
							break;
						} catch (InputMismatchException e) {
							input.nextLine();
							System.out.println("Digite apenas números!!\n");
						}
					}
					break;
				case "n" :
					System.out.println("Ok! Será mantido o padrão de 6 tentativas!\n");
					break;
				default:
					System.out.println("É o que kkkjj? Enfim, Será mantido o padrão de 6 tentativas!\n");
					break;
			}
			while (qtdeTentativas != 0) {
				String palavra;
				System.out.printf("(Tentativa %d) Digite uma palavra: ", count);
				palavra = input.next();
				if (palavra.length() > palavraObj.getTamanho() || palavra.length() < palavraObj.getTamanho()) {
					System.out.printf("Você precisa digitar uma palavra de tamanho %d!\n", tamanho);
				}
				else if (checarNumeros(palavra)) {
					System.out.println("Você digitou uma palavra com número!! Tente novamente!!\n");
				} else {
					if (palavra.equals(palavraObj.getPalavra())) {
						System.out.println("PARABÉNS!!! Você acertou a palavra!!! Tome aqui 10 centavos de moral!!!\n");
					} else {
						verificarPalavraDigitada(palavra, palavraObj.getPalavra(), tamanho);
					}
					qtdeTentativas --;
					count ++;
				}
			}
			System.out.printf("Infelizmente, você não acertou a palavra ); !! E ela era \"%s\"\n\n", palavraObj.getPalavra());
			qtdeTentativas = 6;
			count = 1;
		}
		System.out.println("Obrigado por jogar!! Até um outro dia!!");
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

	private boolean checarNumeros(String palavra) {
		return palavra.matches(".*[0-9].*");
	}
}
