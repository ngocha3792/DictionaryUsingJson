package view;

import controller.WordController;
import model.Meaning;
import model.Word;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class WordApp extends Application {
    private WordController controller;
    private ObservableList<String> wordList;
    private ListView<String> listView;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        controller = new WordController();
        wordList = FXCollections.observableArrayList(controller.getAllWords().stream()
                .map(Word::getWord)
                .collect(Collectors.toList()));

        primaryStage.setTitle("Word Management");

        TextField searchField = new TextField();
        searchField.setPromptText("Search for a word...");

        listView = new ListView<>(wordList);
        listView.setPrefWidth(200);
        listView.setVisible(false);

        searchField.textProperty().addListener((obs, oldText, newText) -> {
            if (!newText.isEmpty()) {
                listView.setVisible(true);
                wordList.setAll(controller.getAllWords().stream()
                        .filter(word -> word.getWord().toLowerCase().contains(newText.toLowerCase()))
                        .map(Word::getWord)
                        .collect(Collectors.toList()));
            } else {
                listView.setVisible(false);
            }
        });

        VBox detailsBox = new VBox(10);
        detailsBox.setPadding(new Insets(10));

        Label wordLabel = new Label();
        Label pronunciationLabel = new Label();
        Label originLabel = new Label();
        Label partOfSpeechLabel = new Label();
        Label meaningsLabel = new Label();

        detailsBox.getChildren().addAll(wordLabel, pronunciationLabel, originLabel, partOfSpeechLabel, meaningsLabel);

        listView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                Word word = controller.getWord(newSelection);
                if (word != null) {
                    wordLabel.setText("Word: " + word.getWord());
                    pronunciationLabel.setText("Pronunciation: " + word.getPronunciation());
                    originLabel.setText("Origin: " + word.getOrigin());
                    partOfSpeechLabel.setText("Part of Speech: " + word.getPartOfSpeech());
                    meaningsLabel.setText("Meanings: " + word.getMeanings().stream()
                            .map(Meaning::getDefination)
                            .collect(Collectors.joining(", ")));
                }
            }
        });

        TextField wordField = new TextField();
        wordField.setPromptText("Word");

        TextField pronunciationField = new TextField();
        pronunciationField.setPromptText("Pronunciation");

        TextField originField = new TextField();
        originField.setPromptText("Origin");

        TextField partOfSpeechField = new TextField();
        partOfSpeechField.setPromptText("Part of Speech");

        TextArea meaningsField = new TextArea();
        meaningsField.setPromptText("Meanings (comma separated)");

        Button addButton = new Button("Add");
        addButton.setOnAction(e -> addWord(wordField, pronunciationField, originField, partOfSpeechField, meaningsField));

        Button updateButton = new Button("Update");
        updateButton.setOnAction(e -> updateWord(wordField, pronunciationField, originField, partOfSpeechField, meaningsField));

        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e -> deleteWord(wordField));

        HBox buttonBox = new HBox(10, addButton, updateButton, deleteButton);

        VBox root = new VBox(10, searchField, listView, detailsBox, wordField, pronunciationField, originField, partOfSpeechField, meaningsField, buttonBox);
        root.setPadding(new Insets(10));

        primaryStage.setScene(new Scene(root, 600, 600));
        primaryStage.show();
    }

    private void addWord(TextField wordField, TextField pronunciationField, TextField originField, TextField partOfSpeechField, TextArea meaningsField) {
        String word = wordField.getText();
        String pronunciation = pronunciationField.getText();
        String origin = originField.getText();
        String partOfSpeech = partOfSpeechField.getText();
        String[] meaningsArray = meaningsField.getText().split(",");
        ArrayList<Meaning> meanings = new ArrayList<>();
        for (String meaning : meaningsArray) {
            meanings.add(new Meaning(meaning.trim(), new ArrayList<>()));
        }

        Word newWord = new Word(word, pronunciation, meanings, partOfSpeech, new ArrayList<>(), new ArrayList<>(), origin);
        if (controller.addWord(newWord)) {
            wordList.add(word);
        } else {
            showAlert("Error", "Word already exists or input is invalid.");
        }
    }

    private void updateWord(TextField wordField, TextField pronunciationField, TextField originField, TextField partOfSpeechField, TextArea meaningsField) {
        String word = wordField.getText();
        String pronunciation = pronunciationField.getText();
        String origin = originField.getText();
        String partOfSpeech = partOfSpeechField.getText();
        String[] meaningsArray = meaningsField.getText().split(",");
        ArrayList<Meaning> meanings = new ArrayList<>();
        for (String meaning : meaningsArray) {
            meanings.add(new Meaning(meaning.trim(), new ArrayList<>()));
        }

        Word updatedWord = new Word(word, pronunciation, meanings, partOfSpeech, new ArrayList<>(), new ArrayList<>(), origin);
        if (!controller.updateWord(word, updatedWord)) {
            showAlert("Error", "Word not found.");
        }
    }

    private void deleteWord(TextField wordField) {
        String word = wordField.getText();
        if (controller.deleteWord(word)) {
            wordList.remove(word);
        } else {
            showAlert("Error", "Word not found.");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}