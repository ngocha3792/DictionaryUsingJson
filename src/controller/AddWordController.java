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

public class AddWordController {

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

    private final List<Meaning> meanings = new ArrayList<>();
    private final List<String> synonyms = new ArrayList<>();
    private final List<String> antonyms = new ArrayList<>();

    public void setDictionaryApp(DictionaryApp dictionaryApp) {
        this.dictionaryApp = dictionaryApp;
    }

    @FXML
    private void handleAddMeaning() {
        VBox meaningBox = new VBox(10);
        TextField definitionField = new TextField();
        definitionField.setPromptText("Nhập nghĩa...");
        
        VBox examplesContainer = new VBox(5);
        Button addExampleButton = new Button("Thêm ví dụ");
        addExampleButton.setStyle("-fx-background-color: #007bff; -fx-text-fill: white;");
        addExampleButton.setOnAction(event -> handleAddExample(examplesContainer));

        Button deleteMeaningButton = new Button("Xóa nghĩa");
        deleteMeaningButton.setStyle("-fx-background-color: #ff4d4d; -fx-text-fill: white;");
        deleteMeaningButton.setOnAction(event -> {
            meaningsContainer.getChildren().remove(meaningBox);
            meanings.remove(meaningsContainer.getChildren().indexOf(meaningBox));
        });

        meaningBox.getChildren().addAll(definitionField, examplesContainer, addExampleButton, deleteMeaningButton);
        meaningsContainer.getChildren().add(meaningsContainer.getChildren().size() - 1, meaningBox);

        meanings.add(new Meaning());
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

    @FXML
    private void handleAddSynonym() {
        HBox synonymBox = new HBox(10);
        TextField synonymField = new TextField();
        synonymField.setPromptText("Nhập từ đồng nghĩa...");
        Button deleteButton = new Button("Xóa");
        deleteButton.setStyle("-fx-background-color: #ff4d4d; -fx-text-fill: white;");
        deleteButton.setOnAction(event -> synonymsContainer.getChildren().remove(synonymBox));

        synonymBox.getChildren().addAll(synonymField, deleteButton);
        synonymsContainer.getChildren().add(synonymsContainer.getChildren().size() - 1, synonymBox);

        synonyms.add("");
    }

    @FXML
    private void handleAddAntonym() {
        HBox antonymBox = new HBox(10);
        TextField antonymField = new TextField();
        antonymField.setPromptText("Nhập từ trái nghĩa...");
        Button deleteButton = new Button("Xóa");
        deleteButton.setStyle("-fx-background-color: #ff4d4d; -fx-text-fill: white;");
        deleteButton.setOnAction(event -> antonymsContainer.getChildren().remove(antonymBox));

        antonymBox.getChildren().addAll(antonymField, deleteButton);
        antonymsContainer.getChildren().add(antonymsContainer.getChildren().size() - 1, antonymBox);

        antonyms.add("");
    }

    @FXML
    private void handleSaveWord() {
        String word = wordField.getText().trim();
        String pronunciation = pronunciationField.getText().trim();
        String partOfSpeech = partOfSpeechField.getText().trim();
        String origin = originField.getText().trim();

        // Lấy danh sách meanings
        for (int i = 0; i < meaningsContainer.getChildren().size() - 1; i++) {
            VBox meaningBox = (VBox) meaningsContainer.getChildren().get(i);
            TextField definitionField = (TextField) meaningBox.getChildren().get(0);

            VBox examplesContainer = (VBox) meaningBox.getChildren().get(1);
            List<String> examples = new ArrayList<>();
            for (int j = 0; j < examplesContainer.getChildren().size(); j++) {
                HBox exampleBox = (HBox) examplesContainer.getChildren().get(j);
                TextField exampleField = (TextField) exampleBox.getChildren().get(0);
                examples.add(exampleField.getText().trim());
            }

            meanings.set(i, new Meaning(definitionField.getText().trim(), examples));
        }

        // Lấy danh sách synonyms
        for (int i = 0; i < synonymsContainer.getChildren().size() - 1; i++) {
            HBox synonymBox = (HBox) synonymsContainer.getChildren().get(i);
            TextField synonymField = (TextField) synonymBox.getChildren().get(0);
            synonyms.set(i, synonymField.getText().trim());
        }

        // Lấy danh sách antonyms
        for (int i = 0; i < antonymsContainer.getChildren().size() - 1; i++) {
            HBox antonymBox = (HBox) antonymsContainer.getChildren().get(i);
            TextField antonymField = (TextField) antonymBox.getChildren().get(0);
            antonyms.set(i, antonymField.getText().trim());
        }

        if (word.isEmpty() || meanings.isEmpty()) {
            showAlert("Lỗi", "Vui lòng nhập đầy đủ thông tin!", Alert.AlertType.WARNING);
            return;
        }

        Word newWord = new Word(word, pronunciation, meanings, partOfSpeech, synonyms, antonyms, origin);
        dictionaryApp.addNewWord(newWord);

        showAlert("Thành công", "Từ mới đã được lưu!", Alert.AlertType.INFORMATION);
        resetForm();
    }

    private void resetForm() {
        wordField.clear();
        pronunciationField.clear();
        partOfSpeechField.clear();
        originField.clear();
        meaningsContainer.getChildren().clear();
        synonymsContainer.getChildren().clear();
        antonymsContainer.getChildren().clear();
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
