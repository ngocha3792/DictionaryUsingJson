package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import model.Word;
import model.Meaning;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MainController {

    @FXML
    private TextField searchField;

    @FXML
    private Button searchButton;

    @FXML
    private Button manageDictionaryButton;

    @FXML
    private Button idiomListButton;

    @FXML
    private VBox suggestionsContainer;

    // Danh sách từ điển dưới dạng đối tượng Word
    private List<Word> dictionary = new ArrayList<>();

    // Khởi tạo dữ liệu từ điển
    public MainController() {
        dictionary.add(new Word("apple", "ˈapəl", 
                List.of(new Meaning("A fruit", List.of("I ate an apple."))), 
                "noun", List.of("pome"), List.of("orange"), "Old English"));

        dictionary.add(new Word("application", "ˌapləˈkāSHən", 
                List.of(new Meaning("A formal request", List.of("I sent my job application."))), 
                "noun", List.of("request"), List.of("refusal"), "Middle English"));

        dictionary.add(new Word("apply", "əˈplī", 
                List.of(new Meaning("Make a formal application", List.of("She applied for the job."))), 
                "verb", List.of("use"), List.of("ignore"), "Middle English"));
    }

    @FXML
    public void initialize() {
        // Xử lý sự kiện tìm kiếm
        searchField.textProperty().addListener((observable, oldValue, newValue) -> handleSearch());
        suggestionsContainer.toFront(); // Đảm bảo khu vực gợi ý luôn ở trên
    }

    @FXML
    private void handleSearch() {
        String keyword = searchField.getText().toLowerCase();
        suggestionsContainer.getChildren().clear(); // Xóa các gợi ý cũ

        if (keyword.isEmpty()) {
            return; // Nếu từ khóa trống, không hiển thị gợi ý
        }

        List<Word> filteredWords = dictionary.stream()
                .filter(word -> word.getWord().toLowerCase().startsWith(keyword))
                .collect(Collectors.toList());

        for (Word word : filteredWords) {
            VBox wordBox = new VBox();
            wordBox.setSpacing(5);
            wordBox.setStyle("-fx-background-color: #ffffff; -fx-padding: 10; -fx-border-color: #cccccc; -fx-border-width: 1; -fx-border-radius: 5; -fx-background-radius: 5;");

            Label wordLabel = new Label(word.getWord());
            wordLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #0033cc;");

            Label pronunciationLabel = new Label(word.getPronunciation());
            pronunciationLabel.setStyle("-fx-font-size: 14px; -fx-font-style: italic; -fx-text-fill: #666;");

            Label definitionLabel = new Label(word.getMeanings().get(0).getDefination());
            definitionLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #000;");

            wordBox.getChildren().addAll(wordLabel, pronunciationLabel, definitionLabel);
            suggestionsContainer.getChildren().add(wordBox);
        }

        suggestionsContainer.toFront(); // Đảm bảo hiển thị trên các thành phần khác
    }

    // Xử lý sự kiện khi nhấn nút "Quản lý từ điển"
    @FXML
    private void handleManageDictionary(ActionEvent event) {
        System.out.println("Chuyển đến chức năng quản lý từ điển.");
        // Thực hiện chuyển scene hoặc xử lý logic quản lý từ điển ở đây
    }

    // Xử lý sự kiện khi nhấn nút "Danh sách thành ngữ"
    @FXML
    private void handleIdiomList(ActionEvent event) {
        System.out.println("Chuyển đến danh sách thành ngữ.");
        // Thực hiện chuyển scene hoặc xử lý logic danh sách thành ngữ ở đây
    }
}
