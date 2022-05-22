package com.edu.ifpb.worldle.services;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class GenerateSqlService {

    private final static String WORDS_SMALL_TXT = "words-small.txt";
    private final static String WORDS_TXT = "words.txt";

    private void loadWords() throws IOException {
        File worlds = new File(WORDS_TXT);
        String sqlFile = "src/main/resources/import.sql";
        if (worlds.exists()) {
            List<String> worldsList = new ArrayList<>(Files.readAllLines(Path.of(WORDS_TXT)));
            List<String> sqls = new ArrayList<>();
            for (String world : worldsList) {
                String newWorld = removeAccentsAfterJava7(world.toLowerCase());
                String sql = String.format("INSERT INTO tb_palavra(palavra, tamanho) VALUES ('%s', %d);", newWorld, newWorld.length());
                System.out.println(sql);
                sqls.add(sql);
            }
            Files.write(Path.of(sqlFile), sqls, Charset.defaultCharset(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING,
                    StandardOpenOption.WRITE);
        }
    }

    private String removeAccentsAfterJava7(String value) {
        String normalizer = Normalizer.normalize(value, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(normalizer).replaceAll("");
    }
}
