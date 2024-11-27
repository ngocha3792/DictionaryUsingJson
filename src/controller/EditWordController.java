package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import function.DictionaryApp;
import model.Meaning;
import model.Word;

import java.util.ArrayList;
import java.util.List;

public class EditWordController {

    @FXML
    private TextField wordField;

    @FXML
    private TextField pronunciationField;

    @FXML
    private TextField partOfSpeechField;

    @FXML
    private TextField originField;

    @FXML
    private VBox meaningsContainer;

    @FXML
    private VBox synonymsContainer;

    @FXML
    private VBox antonymsContainer;

    private DictionaryApp dictionaryApp;

    private Word editingWord;

    public void setDictionaryApp(DictionaryApp dictionaryApp) {
        this.dictionaryApp = dictionaryApp;
    }

    public void setEditingWord(Word word) {
        this.editingWord = word;
        loadWordDetails();
    }

    private void loadWordDetails() {
        wordField.setText(editingWord.getWord());
        pronunciationField.setText(editingWord.getPronunciation());
        partOfSpeechField.setText(editingWord.getPartOfSpeech());
        originField.setText(editingWord.getOrigin());

        for (Meaning meaning : editingWord.getMeanings()) {
            VBox meaningBox = new VBox(10);
            TextField definitionField = new TextField(meaning.getDefination());
            VBox examplesContainer = new VBox(5);

            for (String example : meaning.getExample()) {
                HBox exampleBox = new HBox(10);
                TextField exampleField = new TextField(example);
                Button deleteExampleButton = new Button("Xóa");
                deleteExampleButton.setStyle("-fx-background-color: #ff4d4d; -fx-text-fill: white;");
                deleteExampleButton.setOnAction(event -> examplesContainer.getChildren().remove(exampleBox));

                exampleBox.getChildren().addAll(exampleField, deleteExampleButton);
                examplesContainer.getChildren().add(exampleBox);
            }

            Button addExampleButton = new Button("Thêm ví dụ");
            addExampleButton.setStyle("-fx-background-color: #007bff; -fx-text-fill: white;");
            addExampleButton.setOnAction(event -> handleAddExample(examplesContainer));

            Button deleteMeaningButton = new Button("Xóa nghĩa");
            deleteMeaningButton.setStyle("-fx-background-color: #ff4d4d; -fx-text-fill: white;");
            deleteMeaningButton.setOnAction(event -> meaningsContainer.getChildren().remove(meaningBox));

            meaningBox.getChildren().addAll(definitionField, examplesContainer, addExampleButton, deleteMeaningButton);
            meaningsContainer.getChildren().add(meaningsContainer.getChildren().size() - 1, meaningBox);
        }

        for (String synonym : editingWord.getSynonyms()) {
            addSynonymField(synonym);
        }

        for (String antonym : editingWord.getAntonyms()) {
            addAntonymField(antonym);
        }
    }

    private void handleAddExample(VBox examplesContainer) {
        HBox exampleBox = new HBox(10);
        TextField exampleField = new TextField();
        exampleField.setPromptText("Nhập ví dụ...");
        Button deleteExampleButton = new Button("Xóa");
        deleteExampleButton.setStyle("-fx-background-color: #ff4d4d; -fx-text-fill: white;");
        deleteExampleButton.setOnAction(event -> examplesContainer.getChildren().remove(exampleBox));

        exampleBox.getChildren().addAll(exampleField, deleteExampleButton);
        examplesContainer.getChildren().add(exampleBox);
    }

    private void addSynonymField(String synonym) {
        HBox synonymBox = new HBox(10);
        TextField synonymField = new TextField(synonym);
        Button deleteButton = new Button("Xóa");
        deleteButton.setStyle("-fx-background-color: #ff4d4d; -fx-text-fill: white;");
        deleteButton.setOnAction(event -> synonymsContainer.getChildren().remove(synonymBox));

        synonymBox.getChildren().addAll(synonymField, deleteButton);
        synonymsContainer.getChildren().add(synonymsContainer.getChildren().size() - 1, synonymBox);
    }

    private void addAntonymField(String antonym) {
        HBox antonymBox = new HBox(10);
        TextField antonymField = new TextField(antonym);
        Button deleteButton = new Button("Xóa");
        deleteButton.setStyle("-fx-background-color: #ff4d4d; -fx-text-fill: white;");
        deleteButton.setOnAction(event -> antonymsContainer.getChildren().remove(antonymBox));

        antonymBox.getChildren().addAll(antonymField, deleteButton);
        antonymsContainer.getChildren().add(antonymsContainer.getChildren().size() - 1, antonymBox);
    }

    @FXML
    private void handleSaveWord() {
        String pronunciation = pronunciationField.getText().trim();
        String partOfSpeech = partOfSpeechField.getText().trim();
        String origin = originField.getText().trim();

        // Cập nhật dữ liệu và lưu
        editingWord.setPronunciation(pronunciation);
        editingWord.setPartOfSpeech(partOfSpeech);
        editingWord.setOrigin(origin);
        dictionaryApp.editWord(editingWord.getWord(), editingWord);

        showAlert("Thành công", "Từ đã được cập nhật!", Alert.AlertType.INFORMATION);
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
