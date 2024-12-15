package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import function.DictionaryApp;
import model.Word;

import java.io.IOException;
import java.util.List;
import java.util.Map;
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

    private DictionaryApp dictionaryApp;

    public MainController() {
        this.dictionaryApp = new DictionaryApp();
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

        Map<String, Word> allWords = dictionaryApp.getAllWords();
        List<Word> filteredWords = allWords.values().stream()
                .filter(word -> word.getWord().toLowerCase().startsWith(keyword))
                .collect(Collectors.toList());

        for (Word word : filteredWords) {
            VBox wordBox = new VBox();
            wordBox.setSpacing(5);
            wordBox.setStyle("-fx-background-color: #ffffff; -fx-padding: 10; -fx-border-color: #cccccc; -fx-border-width: 1; -fx-border-radius: 5; -fx-background-radius: 5;");

            Label wordLabel = new Label(word.getWord());
            wordLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #0033cc;");
            wordLabel.setOnMouseClicked(event -> handleWordClick(word)); // Thêm sự kiện click để hiển thị chi tiết từ

            Label pronunciationLabel = new Label(word.getPronunciation());
            pronunciationLabel.setStyle("-fx-font-size: 14px; -fx-font-style: italic; -fx-text-fill: #666;");

            Label definitionLabel = new Label(word.getMeanings().get(0).getDefination());
            definitionLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #000;");

            wordBox.getChildren().addAll(wordLabel, pronunciationLabel, definitionLabel);
            suggestionsContainer.getChildren().add(wordBox);
        }

        suggestionsContainer.toFront(); 
    }


    @FXML
    private void handleManageDictionary(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View.FXML/ManagerDictionaryView.fxml"));
            Parent root = loader.load();

            ManagerDictionaryController controller = loader.getController();
            controller.setDictionaryApp(dictionaryApp);

            
            Stage stage = new Stage();
            stage.setTitle("Quản lý từ điển");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void handleIdiomList() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View.FXML/IdiomView.fxml"));
            Parent root = loader.load();
            
            Stage stage = new Stage();
            stage.setTitle("Danh sách Thành ngữ");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	@FXML
	private void handleWordClick(Word selectedWord) {
	    try {
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View.FXML/DetailView.fxml"));
	        Parent root = loader.load();
	
	        // Lấy controller của DetailView
	        DetailController detailController = loader.getController();
	        detailController.setWord(selectedWord);
	
	        // Tạo Stage mới để hiển thị chi tiết từ
	        Stage stage = new Stage();
	        stage.setTitle("Chi tiết từ: " + selectedWord.getWord());
	        stage.setScene(new Scene(root));
	        stage.show();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
}