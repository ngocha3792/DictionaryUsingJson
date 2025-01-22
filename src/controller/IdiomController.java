package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.Idiom;
import function.IdiomApp;

import java.util.List;
import java.util.stream.Collectors;

public class IdiomController {

    @FXML
    private TextField searchField;

    @FXML
    private ListView<String> idiomListView;

    @FXML
    private ListView<String> meaningsListView;

    private IdiomApp idiomApp;
    private ObservableList<String> idiomNames; // Danh sách gốc để tìm kiếm

    @FXML
    public void initialize() {
        idiomApp = new IdiomApp(); // Đọc dữ liệu từ file JSON
        loadIdioms();
        setupEventHandlers();
        setupSearchHandler();
    }

    private void loadIdioms() {
        List<Idiom> idioms = idiomApp.getAllIdioms();
        idiomNames = FXCollections.observableArrayList();

        for (Idiom idiom : idioms) {
            idiomNames.add(idiom.getName());
        }

        idiomListView.setItems(idiomNames);
    }

    private void setupEventHandlers() {
        idiomListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                showMeanings(newValue);
            }
        });
    }

    private void setupSearchHandler() {
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filterIdioms(newValue);
        });
    }

    private void filterIdioms(String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            idiomListView.setItems(idiomNames); // Hiển thị lại danh sách gốc
        } else {
            List<String> filteredIdioms = idiomNames.stream()
                .filter(name -> name.toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
            idiomListView.setItems(FXCollections.observableArrayList(filteredIdioms));
        }
    }

    private void showMeanings(String idiomName) {
        List<Idiom> idioms = idiomApp.getAllIdioms();

        for (Idiom idiom : idioms) {
            if (idiom.getName().equalsIgnoreCase(idiomName)) {
                ObservableList<String> meanings = FXCollections.observableArrayList();

                idiom.getMeanings().forEach(meaning -> meanings.add(meaning.getDefination()));
                meaningsListView.setItems(meanings);
                return;
            }
        }

        meaningsListView.setItems(FXCollections.observableArrayList()); // Nếu không có nghĩa, xóa danh sách
    }
}
