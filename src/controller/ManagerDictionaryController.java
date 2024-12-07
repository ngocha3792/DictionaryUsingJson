package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import function.DictionaryApp;
import model.Word;

import java.io.IOException;
import java.util.Map;

public class ManagerDictionaryController {

    @FXML
    private ListView<Word> wordListView;

    @FXML
    private Button addWordButton;

    private DictionaryApp dictionaryApp;

    public ManagerDictionaryController() {
        // Constructor trống để controller được khởi tạo tự động bởi JavaFX
    }

    // Hàm setDictionaryApp để truyền đối tượng DictionaryApp từ bên ngoài
    public void setDictionaryApp(DictionaryApp dictionaryApp) {
        this.dictionaryApp = dictionaryApp;
        loadWords(); // Cập nhật danh sách từ khi đối tượng DictionaryApp được thiết lập
    }

    @FXML
    public void initialize() {
        setupWordListView();
    }

    private void loadWords() {
        if (dictionaryApp != null) {
            Map<String, Word> allWords = dictionaryApp.getAllWords();
            ObservableList<Word> wordObservableList = FXCollections.observableArrayList(allWords.values());
            wordListView.setItems(wordObservableList);
        }
    }

    private void setupWordListView() {
        wordListView.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(Word word, boolean empty) {
                super.updateItem(word, empty);
                if (empty || word == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    HBox container = new HBox(10);
                    Label wordLabel = new Label(word.getWord());
                    wordLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
                    HBox.setHgrow(wordLabel, Priority.ALWAYS);

                    Button editButton = new Button("Sửa");
                    editButton.setStyle("-fx-background-color: #007bff; -fx-text-fill: white;");
                    editButton.setOnAction(event -> handleEditWord(word));

                    Button deleteButton = new Button("Xóa");
                    deleteButton.setStyle("-fx-background-color: #ff4d4d; -fx-text-fill: white;");
                    deleteButton.setOnAction(event -> handleDeleteWord(word));

                    container.getChildren().addAll(wordLabel, editButton, deleteButton);
                    setGraphic(container);
                }
            }
        });
    }

    @FXML
    private void handleAddWord() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View.FXML/AddWordView.fxml"));
            Parent root = loader.load();

            // Hiển thị giao diện thêm từ
            AddWordController controller = loader.getController();
            controller.setDictionaryApp(dictionaryApp);

            Stage stage = new Stage();
            stage.setTitle("Thêm từ mới");
            stage.setScene(new Scene(root));
            stage.showAndWait(); // Chờ cửa sổ thêm từ đóng trước khi làm mới danh sách
            loadWords(); // Cập nhật danh sách từ
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleEditWord(Word word) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View.FXML/EditWordView.fxml"));
            Parent root = loader.load();

            // Hiển thị giao diện sửa từ
            EditWordController controller = loader.getController();
            controller.setEditingWord(word);
            controller.setDictionaryApp(dictionaryApp);

            Stage stage = new Stage();
            stage.setTitle("Sửa từ: " + word.getWord());
            stage.setScene(new Scene(root));
            stage.showAndWait(); // Chờ cửa sổ sửa từ đóng trước khi làm mới danh sách
            loadWords(); // Cập nhật danh sách từ
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleDeleteWord(Word word) {
        if (word == null) {
            showAlert("Lỗi", "Không có từ nào được chọn để xóa!", Alert.AlertType.ERROR);
            return;
        }

        boolean success = dictionaryApp.getAllWords().remove(word.getWord().toLowerCase()) != null;
        if (success) {
            dictionaryApp.deleteWord(word.getWord());
            showAlert("Thành công", "Đã xóa từ \"" + word.getWord() + "\" khỏi từ điển.", Alert.AlertType.INFORMATION);
            loadWords(); // Làm mới danh sách từ
        } else {
            showAlert("Lỗi", "Không thể xóa từ.", Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
