package com.example.encryptionprogram;

import com.encryptionclass.EncryptionProgram;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.LinkedHashSet;


public class HelloApplication extends Application {
    private EncryptionProgram encryptionProgram;
    private TextField inputTextField;
    private TextArea resultTextArea;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        encryptionProgram = new EncryptionProgram();

        primaryStage.setTitle("Encryption Program");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        Label instructions = new Label("Choose an action:");
        GridPane.setConstraints(instructions, 0, 0);

        inputTextField = new TextField();
        inputTextField.setPromptText("Enter text here");
        GridPane.setConstraints(inputTextField, 0, 1);

        Button encryptButton = new Button("Encrypt");
        encryptButton.setOnAction(e -> encrypt());
        GridPane.setConstraints(encryptButton, 1, 1);

        Button decryptButton = new Button("Decrypt");
        decryptButton.setOnAction(e -> decrypt());
        GridPane.setConstraints(decryptButton, 2, 1);

        Button getKeyButton = new Button("Get Key");
        getKeyButton.setOnAction(e -> getKey());
        GridPane.setConstraints(getKeyButton, 3, 1);

        Button setKeyButton = new Button("Set Key");
        setKeyButton.setOnAction(e -> setKey());
        GridPane.setConstraints(setKeyButton, 4, 1);

        Button saveKeyButton = new Button("Save Key");
        saveKeyButton.setOnAction(e -> saveKey());
        GridPane.setConstraints(saveKeyButton, 5, 1);



        resultTextArea = new TextArea();
        resultTextArea.setEditable(false);
        resultTextArea.setWrapText(true);
        GridPane.setConstraints(resultTextArea, 0, 3, 6,1);

        Button quitButton = new Button("Quit");
        quitButton.setOnAction(e -> {
            encryptionProgram.quit();
            primaryStage.close();
        });
        GridPane.setConstraints(quitButton, 1, 4);

        grid.getChildren().addAll(instructions, getKeyButton, inputTextField, encryptButton, decryptButton,
                setKeyButton, resultTextArea, saveKeyButton,quitButton);

        Scene scene = new Scene(grid, 400, 300);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private void encrypt() {
        String message = inputTextField.getText();
        String encryptedResult = encryptionProgram.encrypt(message);
        resultTextArea.setText(encryptedResult);
        inputTextField.clear();
    }

    private void decrypt() {
        String message = inputTextField.getText();
        String decryptedResult = encryptionProgram.decrypt(message);
        resultTextArea.setText(decryptedResult);
        inputTextField.clear();
    }
    private void setKey(){
        String key = inputTextField.getText();
        encryptionProgram.setKey(key);
        resultTextArea.setText("Key Set" +  key);
        inputTextField.clear();
    }

    private void getKey(){
        String key = encryptionProgram.getKey();
        resultTextArea.setText("Key: " + key);
    }

    private void saveKey(){
        launchSaveKeyWindow();
    }

    private void launchSaveKeyWindow(){
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(8);
        gridPane.setHgap(10);

        TextField fileTextArea = new TextField();
        fileTextArea.setPromptText("Entere file name");
        GridPane.setConstraints(fileTextArea, 0, 0);

        TextArea textArea = new TextArea();
        textArea.setEditable(false);
        textArea.setWrapText(true);
        GridPane.setConstraints(textArea, 0, 3, 6,1);

        Button confirmButton = new Button("Confirm");
        confirmButton.setOnAction(e -> {
            try {
                encryptionProgram.saveKey(fileTextArea.getText());
                textArea.setText(encryptionProgram.getFilePath());
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error into saving the key");
                alert.setContentText("Error");
                alert.showAndWait();
            }
        });

        GridPane.setConstraints(confirmButton,1, 0);
        
        gridPane.getChildren().addAll(fileTextArea, confirmButton, textArea);

        Scene scene = new Scene(gridPane, 300, 200);
        stage.setScene(scene);

        stage.showAndWait();

    }

}
