//package controller;
//
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.Alert;
//import javafx.scene.control.Button;
//import javafx.scene.control.ListCell;
//import javafx.scene.control.ListView;
//import javafx.scene.layout.HBox;
//import javafx.scene.layout.Priority;
//import javafx.stage.Stage;
//import javafx.scene.control.Label;
//import function.DictionaryApp;
//import model.Word;
//
//import java.io.IOException;
//import java.util.Map;
//
//public class ManagerDictionaryController {
//
//    @FXML
//    private ListView<Word> wordListView;
//
//    @FXML
//    private Button addWordButton;
//
//    private DictionaryApp dictionaryApp;
//
//    public ManagerDictionaryController() {
//        this.dictionaryApp = new DictionaryApp();
//    }
//
//    @FXML
//    public void initialize() {
//        loadWords();
//        setupWordListView();
//    }
//
//    private void loadWords() {
//        Map<String, Word> allWords = dictionaryApp.getAllWords();
//        ObservableList<Word> wordObservableList = FXCollections.observableArrayList(allWords.values());
//        wordListView.setItems(wordObservableList);
//    }
//
//    private void setupWordListView() {
//        wordListView.setCellFactory(listView -> new ListCell<>() {
//            @Override
//            protected void updateItem(Word word, boolean empty) {
//                super.updateItem(word, empty);
//                if (empty || word == null) {
//                    setText(null);
//                    setGraphic(null);
//                } else {
//                    HBox container = new HBox(10);
//                    Label wordLabel = new Label(word.getWord());
//                    wordLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
//                    HBox.setHgrow(wordLabel, Priority.ALWAYS);
//
//                    Button editButton = new Button("Sửa");
//                    editButton.setStyle("-fx-background-color: #007bff; -fx-text-fill: white;");
//                    editButton.setOnAction(event -> handleEditWord(word));
//
//                    Button deleteButton = new Button("Xóa");
//                    deleteButton.setStyle("-fx-background-color: #ff4d4d; -fx-text-fill: white;");
//                    deleteButton.setOnAction(event -> handleDeleteWord(word));
//
//                    container.getChildren().addAll(wordLabel, editButton, deleteButton);
//                    setGraphic(container);
//                }
//            }
//        });
//    }
//
//    @FXML
//    private void handleAddWord() {
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/path/to/AddWordView.fxml"));
//            Parent root = loader.load();
//
//            // Hiển thị giao diện thêm từ
//            AddWordController controller = loader.getController();
//            controller.setDictionaryApp(dictionaryApp);
//            controller.setManageDictionaryController(this);
//
//            Stage stage = new Stage();
//            stage.setTitle("Thêm từ mới");
//            stage.setScene(new Scene(root));
//            stage.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void handleEditWord(Word word) {
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/path/to/EditWordView.fxml"));
//            Parent root = loader.load();
//
//            // Hiển thị giao diện sửa từ
//            EditWordController controller = loader.getController();
//            controller.setWord(word);
//            controller.setDictionaryApp(dictionaryApp);
//            controller.setManageDictionaryController(this);
//
//            Stage stage = new Stage();
//            stage.setTitle("Sửa từ: " + word.getWord());
//            stage.setScene(new Scene(root));
//            stage.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void handleDeleteWord(Word word) {
//        boolean success = dictionaryApp.deleteWord(word.getWord());
//        if (success) {
//            showAlert("Thành công", "Đã xóa từ \"" + word.getWord() + "\" khỏi từ điển.", Alert.AlertType.INFORMATION);
//            loadWords();
//        } else {
//            showAlert("Lỗi", "Không thể xóa từ.", Alert.AlertType.ERROR);
//        }
//    }
//
//    private void showAlert(String title, String message, Alert.AlertType alertType) {
//        Alert alert = new Alert(alertType);
//        alert.setTitle(title);
//        alert.setHeaderText(null);
//        alert.setContentText(message);
//        alert.showAndWait();
//    }
//}
