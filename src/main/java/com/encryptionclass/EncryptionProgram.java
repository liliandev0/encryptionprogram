package com.encryptionclass;

import javafx.application.Platform;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;


public class EncryptionProgram {

    private ArrayList<Character> list;
    private ArrayList<Character> shuffledList;
    private char aChar;
    private char[] letters;

    private String filePath;
    public EncryptionProgram() {
        list = new ArrayList<>();
        shuffledList = new ArrayList<>();
        aChar = ' ';

        newKey();
    }

    public void newKey() {
        aChar = ' ';
        list.clear();
        shuffledList.clear();

        for (int i = 32; i < 127; i++) {
            list.add(Character.valueOf(aChar));
            aChar++;
        }
        shuffledList = new ArrayList<>(list);
        Collections.shuffle(shuffledList);
        System.out.println("*A new key has been generated*");
    }

    public String getKey() {
        StringBuilder keyStringBuilder = new StringBuilder();
        for (Character x : list) {
            keyStringBuilder.append(x);
        }
        keyStringBuilder.append("\n");
        for (Character x : shuffledList) {
            keyStringBuilder.append(x);
        }
        return keyStringBuilder.toString();
    }

    public String encrypt(String message) {
        letters = message.toCharArray();

        for (int i = 0; i < letters.length; i++) {
            for (int j = 0; j < list.size(); j++) {
                if (letters[i] == list.get(j)) {
                    letters[i] = shuffledList.get(j);
                    break;
                }
            }
        }

        StringBuilder encryptedMessage = new StringBuilder("Encrypted: ");
        for (char x : letters) {
            encryptedMessage.append(x);
        }
        return encryptedMessage.toString();
    }

    public String decrypt(String message) {
        letters = message.toCharArray();

        for (int i = 0; i < letters.length; i++) {
            for (int j = 0; j < shuffledList.size(); j++) {
                if (letters[i] == shuffledList.get(j)) {
                    letters[i] = list.get(j);
                    break;
                }
            }
        }

        StringBuilder decryptedMessage = new StringBuilder("Decrypted: ");
        for (char x : letters) {
            decryptedMessage.append(x);
        }
        return decryptedMessage.toString();
    }

    public void setKey(String key) {
        shuffledList.clear();

        char[] keyArr = key.toCharArray();

        for (char x : keyArr) {
            shuffledList.add(x);
        }
    }

    public void saveKey(String fileName) throws Exception{
        File myObj = new File(fileName);

        if(myObj.createNewFile()){
            System.out.println("File created" + myObj.getName());
        }else{
            System.out.println("File already exists");
        }

        try {
            FileWriter fileWriter = new FileWriter(myObj);
            fileWriter.write(getKey());
            fileWriter.close();
            System.out.println("Key saved");
        } catch (IOException e){
            System.out.println(e.getMessage());
        }

        setFilePath(myObj.getAbsolutePath());
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void quit() {
        Platform.exit();
    }
}
