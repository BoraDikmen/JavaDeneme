package com.asartech.udhamFX;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FooterPane extends BorderPane {

    private final Label resolutionLabel = new Label();
    private final Label guiSizeLabel = new Label();
    private final Label dateTimeLabel = new Label();

    public FooterPane(Stage stage) {
        setPadding(new Insets(10));
        setStyle("-fx-background-color: #EAEAEA;");

        // Sol kısım: logo ve bilgiler
        HBox leftBox = new HBox(15);
        leftBox.setAlignment(Pos.CENTER_LEFT);

        ImageView logo = new ImageView(new Image("file:src/main/resources/udham.png"));
        logo.setFitHeight(40);
        logo.setPreserveRatio(true);

        VBox infoBox = new VBox(5);
        infoBox.setAlignment(Pos.CENTER_LEFT);

//        Label companyLabel = new Label("Firma Adı: ASARTECH ARGE TASARIM MÜHENDİSLİK A.Ş.");
//        Label projectLabel = new Label("Proje Adı: UDHAM Sensör Verileri Takip Sistemi");

        updateScreenResolution();
        updateWindowSize(stage);

//        infoBox.getChildren().addAll(companyLabel, projectLabel, resolutionLabel, guiSizeLabel);
        infoBox.getChildren().addAll(resolutionLabel, guiSizeLabel);
        leftBox.getChildren().addAll(logo, infoBox);
        setLeft(leftBox);

        // Sağ kısım: Tarih/Saat
/*        VBox rightBox = new VBox();
        rightBox.setAlignment(Pos.CENTER_RIGHT);
        dateTimeLabel.setStyle("-fx-font-size: 14px;");
        rightBox.getChildren().add(dateTimeLabel);
        setRight(rightBox);

        // Timeline ile tarih saat güncelle
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss - dd.MM.yyyy");
        Timeline clock = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            dateTimeLabel.setText("Tarih/Saat: " + LocalDateTime.now().format(formatter));
        }));
        clock.setCycleCount(Timeline.INDEFINITE);
        clock.play();
 */
        // Pencere boyutu değişince güncelle
        stage.widthProperty().addListener((obs, oldVal, newVal) -> updateWindowSize(stage));
        stage.heightProperty().addListener((obs, oldVal, newVal) -> updateWindowSize(stage));
    }

    private void updateScreenResolution() {
        javafx.geometry.Rectangle2D bounds = Screen.getPrimary().getBounds();
        String screenRes = (int) bounds.getWidth() + " x " + (int) bounds.getHeight();
        resolutionLabel.setText("Ekran Çözünürlüğü: " + screenRes);
    }

    private void updateWindowSize(Stage stage) {
        Platform.runLater(() -> {
            Scene scene = stage.getScene();
            if (scene != null) {
                int width = (int) scene.getWidth();
                int height = (int) scene.getHeight();
                guiSizeLabel.setText("GUI Boyutu: " + width + " x " + height);
            }
        });
    }
}

