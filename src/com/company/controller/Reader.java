package com.company.controller;

import com.company.entity.Formula;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Reader {
    private String filePathString;
    private Formula readFormula;

    public Reader(String filePathString) {
        this.setFilePathString(filePathString);
    }

    public void readTextFileFormula() {
        Path filePath = Paths.get(getFilePathString());
        Pattern pattern = Pattern.compile("\\s+");
        try {
            List<String[]> collect = Files.lines(filePath, StandardCharsets.ISO_8859_1)
                    .map(line -> line.substring(0, line.indexOf('/')).replaceAll(",", ""))
                    .map(line -> line.split(" "))
                    .collect(Collectors.toList());

            collect.forEach(x -> System.out.println(Arrays.toString(x)));

//            int variablesCount = Integer.parseInt(collect.get(0));
//            int clausesCount = Integer.parseInt(collect.get(1));


//            collect.stream().skip(2).;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getFilePathString() {
        return filePathString;
    }

    public void setFilePathString(String filePathString) {
        this.filePathString = filePathString;
    }

    public Formula getReadFormula() {
        return readFormula;
    }

    public void setReadFormula(Formula readFormula) {
        this.readFormula = readFormula;
    }
}
