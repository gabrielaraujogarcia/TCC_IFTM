package br.com.iftm.financeiroapi.model.utils;

import br.com.iftm.financeiroapi.model.exceptions.BusinessException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public abstract class FileUtil {

//    public static void main(String[] args) throws BusinessException, IOException, URISyntaxException {
//        System.out.println(ClassLoader.getSystemClassLoader().toString());
//        writeLine("financeiro-api/src/main/resources/database/entries.txt", new String("{OK:OK}"));
//    }

    public static void writeLine(String file, Object domain) throws IOException, BusinessException, URISyntaxException {
        String line = new ObjectMapper().writeValueAsString(domain);
        Path path = Paths.get(System.getProperty("user.dir"), file);
        writeLine(path, line);
    }

    private static void writeLine(Path path, String line) throws BusinessException, IOException {

        if(Files.exists(path)) {
            Files.write(path, line.getBytes(Charset.forName("UTF-8")), StandardOpenOption.APPEND);
            Files.write(path, System.getProperty("line.separator").getBytes(), StandardOpenOption.APPEND);
        } else {
            throw new BusinessException("File " + path + " does not exists");
        }

    }

}
