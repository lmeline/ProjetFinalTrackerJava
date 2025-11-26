package fr.esgi.tracker.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Duration;

public class TableauController {

    private ObservableList<String> data;
    private Timeline timeline;
    private int currentRow = 0;

    public void initTableau(TableView<String> table, TableColumn<String, String> column) {

        // Colonnes
        column.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue())
        );

        // 64 lignes
        data = FXCollections.observableArrayList();
        for (int i = 0; i < 64; i++) {
            data.add(String.format("%02d ---", i));
        }

        table.setItems(data);

        // Animation
        timeline = new Timeline(
                new KeyFrame(Duration.millis(200), e -> {
                    currentRow = (currentRow + 1) % data.size();
                    table.getSelectionModel().select(currentRow);
                    table.scrollTo(currentRow);
                })
        );
        timeline.setCycleCount(Animation.INDEFINITE);
    }
}
