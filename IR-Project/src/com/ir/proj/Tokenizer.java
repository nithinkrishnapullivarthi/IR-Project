package com.ir.proj;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.*;


public class Tokenizer {


     HashSet<String> stopWords = new HashSet<>();
     Porter stemmer = new Porter();
     Integer docId = 1;
     Integer wordId = 1;

    public void parseDocumentAndAddToDictionary(File file, HashMap<String, Integer> termDictionary, HashMap<String, Integer> documentDictionary, HashSet<String> stopWords) throws ParserConfigurationException, IOException, SAXException {

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(file);

        doc.getDocumentElement().normalize();
        NodeList nodeList = doc.getElementsByTagName("DOC");


        for (int docIndex = 0; docIndex < nodeList.getLength(); docIndex++)
        {
            Element document = (Element)nodeList.item(docIndex);
            String docName = document.getElementsByTagName("DOCNO").item(0).getTextContent();
            String docContent = document.getElementsByTagName("TEXT").item(0).getTextContent();

            addTodDocNameToIdMapper(docName,documentDictionary);

            //Split the document content based non word character(splitting based on punctuation marks, hyphens etc)
            String[] words = docContent.split("\\W+");
            for(String word : words){
                String lword = word.toLowerCase();
                //Exclude words containing numbers and that are present in stop words list
                if(word.equals("") || word.matches(".*\\d.*") || stopWords.contains(lword)) {
                    continue;
                }
                addToWordToIdMapper(stemmer.stripAffixes(lword).trim(),termDictionary);
            }
        }
    }

    public void addTodDocNameToIdMapper(String docName,HashMap<String, Integer> documentDictionary){
        documentDictionary.put(docName,docId++);
    }

    public void addToWordToIdMapper(String word, HashMap<String, Integer> termDictionary){
        if(!termDictionary.containsKey(word) && !word.equals("")){
            termDictionary.put(word,wordId++);
        }
    }


    public HashSet<String> populateStopWordsSet() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("src/resources/stopwordlist.txt"));
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            stopWords.add(line.trim());
        }
        return stopWords;
    }

    public void writeToOutputFile( HashMap<String, Integer> dictionary, String fileName) throws IOException {
        FileWriter fileWriter = new FileWriter(fileName,true);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        List<Map.Entry<String,Integer>> entries = new ArrayList<>();

        for(Map.Entry<String,Integer> entry: dictionary.entrySet()){
            entries.add(entry);
        }

        Comparator<Map.Entry<String,Integer>> valueComparator = new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o1.getKey().compareTo(o2.getKey());
            }
        };

        Collections.sort(entries,valueComparator);
        for(Map.Entry<String,Integer> entry: entries){
            printWriter.println(entry.getKey() +"  "+ entry.getValue());
        }
        printWriter.println();
        printWriter.println();
        printWriter.println();
        printWriter.close();
    }
}
