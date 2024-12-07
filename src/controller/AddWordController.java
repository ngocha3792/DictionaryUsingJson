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
        deleteMeaningButton.setOnAction(event -> meaningsContainer.getChildren().remove(meaningBox));

        meaningBox.getChildren().addAll(definitionField, examplesContainer, addExampleButton, deleteMeaningButton);
        meaningsContainer.getChildren().add(meaningBox);
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
        addTextFieldWithDelete(synonymsContainer, "Nhập từ đồng nghĩa...");
    }

    @FXML
    private void handleAddAntonym() {
        addTextFieldWithDelete(antonymsContainer, "Nhập từ trái nghĩa...");
    }

    private void addTextFieldWithDelete(VBox container, String promptText) {
        HBox textBox = new HBox(10);
        TextField textField = new TextField();
        textField.setPromptText(promptText);
        Button deleteButton = new Button("Xóa");
        deleteButton.setStyle("-fx-background-color: #ff4d4d; -fx-text-fill: white;");
        deleteButton.setOnAction(event -> container.getChildren().remove(textBox));

        textBox.getChildren().addAll(textField, deleteButton);
        container.getChildren().add(textBox);
    }

    @FXML
    private void handleSaveWord() {
        String word = wordField.getText().trim();
        String pronunciation = pronunciationField.getText().trim();
        String partOfSpeech = partOfSpeechField.getText().trim();
        String origin = originField.getText().trim();

        if (word.isEmpty()) {
            showAlert("Lỗi", "Vui lòng nhập từ!", Alert.AlertType.ERROR);
            return;
        }

        List<Meaning> meanings = new ArrayList<>();
        for (var meaningBox : meaningsContainer.getChildren()) {
            if (meaningBox instanceof VBox vbox) {
                TextField definitionField = (TextField) vbox.getChildren().get(0);
                String definition = definitionField.getText().trim();

                VBox examplesContainer = (VBox) vbox.getChildren().get(1);
                List<String> examples = new ArrayList<>();
                for (var exampleBox : examplesContainer.getChildren()) {
                    if (exampleBox instanceof HBox hbox) {
                        TextField exampleField = (TextField) hbox.getChildren().get(0);
                        if (!exampleField.getText().trim().isEmpty()) {
                            examples.add(exampleField.getText().trim());
                        }
                    }
                }

                if (!definition.isEmpty()) {
                    meanings.add(new Meaning(definition, examples));
                }
            }
        }

        List<String> synonyms = collectTextFieldValues(synonymsContainer);
        List<String> antonyms = collectTextFieldValues(antonymsContainer);

        if (meanings.isEmpty()) {
            showAlert("Lỗi", "Vui lòng nhập ít nhất một nghĩa!", Alert.AlertType.ERROR);
            return;
        }

        Word newWord = new Word(word, pronunciation, meanings, partOfSpeech, synonyms, antonyms, origin);
        dictionaryApp.addNewWord(newWord);

        showAlert("Thành công", "Từ đã được thêm!", Alert.AlertType.INFORMATION);
        resetForm();
    }
    


    private List<String> collectTextFieldValues(VBox container) {
        List<String> values = new ArrayList<>();
        for (var box : container.getChildren()) {
            if (box instanceof HBox hbox) {
                TextField textField = (TextField) hbox.getChildren().get(0);
                if (!textField.getText().trim().isEmpty()) {
                    values.add(textField.getText().trim());
                }
            }
        }
        return values;
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