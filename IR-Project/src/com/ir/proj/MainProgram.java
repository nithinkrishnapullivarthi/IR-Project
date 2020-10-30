package com.ir.proj;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

public class MainProgram {

    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {
        Tokenizer tokenizer = new Tokenizer();

        HashSet<String> stopWords = tokenizer.populateStopWordsSet();
        HashMap<String, Integer> termDictionary = new HashMap<String, Integer>();
        HashMap<String, Integer> documentDictionary = new HashMap<String, Integer>();

        //Parse each file
        File folder = new File("src/documents/");
        File[] listOfFiles = folder.listFiles();

        for(File file: listOfFiles){
            tokenizer.parseDocumentAndAddToDictionary(file,termDictionary,documentDictionary,stopWords);
        }

        tokenizer.writeToOutputFile(termDictionary, "src/output/parser_output.txt");
        tokenizer.writeToOutputFile(documentDictionary, "src/output/parser_output.txt");
    }
}
