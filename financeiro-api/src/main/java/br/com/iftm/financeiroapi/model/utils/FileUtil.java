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

    public static void main(String[] args) throws BusinessException, IOException, URISyntaxException {
        writeLine("/src/main/resources/database/entries.txt", new String("{OK:OK}"));
    }

    public static void writeLine(String file, Object domain) throws IOException, BusinessException {
        String line = new ObjectMapper().writeValueAsString(domain);
        Path path = Paths.get(System.getProperty("user.dir"), file);

        if(Files.exists(path)) {
            Files.write(path, line.getBytes(Charset.forName("UTF-8")), StandardOpenOption.APPEND);
            Files.write(path, System.getProperty("line.separator").getBytes(), StandardOpenOption.APPEND);
        } else {
            throw new BusinessException("File " + path + " does not exists");
        }

    }

    public static byte[] readLine(String file) throws IOException, BusinessException {

        Path path = Paths.get(System.getProperty("user.dir"), file);
        if(Files.exists(path)) {
            return Files.readAllBytes(path);
        } else {
            throw new BusinessException("File " + path + " does not exists");
        }

    }

}
