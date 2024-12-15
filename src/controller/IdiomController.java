package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import model.Idiom;
import function.IdiomApp;

import java.util.List;

public class IdiomController {

    @FXML
    private ListView<String> idiomListView;

    @FXML
    private ListView<String> meaningsListView;

    private IdiomApp idiomApp;

    @FXML
    public void initialize() {
        idiomApp = new IdiomApp(); // Đọc dữ liệu từ file JSON
        loadIdioms();
        setupEventHandlers();
    }

    private void loadIdioms() {
        List<Idiom> idioms = idiomApp.getAllIdioms();
        ObservableList<String> idiomNames = FXCollections.observableArrayList();

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
