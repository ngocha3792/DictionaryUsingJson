package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.Word;

public class DetailController {

    @FXML
    private Label wordLabel;

    @FXML
    private Label pronunciationLabel;

    @FXML
    private Label partOfSpeechLabel;

    @FXML
    private Label originLabel;

    @FXML
    private Label meaningsLabel;

    @FXML
    private Label synonymsLabel;

    @FXML
    private Label antonymsLabel;

    public void setWord(Word word) {
        wordLabel.setText(word.getWord());
        pronunciationLabel.setText(word.getPronunciation());
        partOfSpeechLabel.setText("Từ loại: " + word.getPartOfSpeech());
        originLabel.setText("Nguồn gốc: " + word.getOrigin());

        // Hiển thị nghĩa và ví dụ
        StringBuilder meanings = new StringBuilder();
        word.getMeanings().forEach(meaning -> {
            meanings.append(meaning.getDefination()).append("\n");
            meaning.getExample().forEach(example -> meanings.append(" - Ví dụ: ").append(example).append("\n"));
        });
        meaningsLabel.setText(meanings.toString());

        // Hiển thị từ đồng nghĩa
        synonymsLabel.setText(String.join(", ", word.getSynonyms()));

        // Hiển thị từ trái nghĩa
        antonymsLabel.setText(String.join(", ", word.getAntonyms()));
    }
}
