package com.asartech.udhamFX;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SettingsTab {

    private final TextField lowTempField = new TextField();
    private final TextField highTempField = new TextField();
    private final TextField noMovementField = new TextField();
    private final TextField freeFallField = new TextField();

    public Node getContent() {
        VBox root = new VBox(30);
        root.setPadding(new Insets(90));
        root.setAlignment(Pos.TOP_CENTER);

        // 🔹 Temperature Group
        VBox tempBox = new VBox(15);
        tempBox.setPadding(new Insets(10));
        tempBox.setStyle("-fx-border-color: #ccc; -fx-border-radius: 5; -fx-border-width: 1;");
        tempBox.getChildren().add(new Label("Temperature Data Settings:"));

        tempBox.getChildren().addAll(
                createSettingLine("Low Temp. Alarm Threshold :", "°C", lowTempField),
                createSettingLine("High Temp. Alarm Threshold :", "°C", highTempField)
        );

        // 🔹 Accelerometer Group
        VBox accelBox = new VBox(15);
        accelBox.setPadding(new Insets(10));
        accelBox.setStyle("-fx-border-color: #ccc; -fx-border-radius: 5; -fx-border-width: 1;");
        accelBox.getChildren().add(new Label("Accelerometer Data Settings:"));

        accelBox.getChildren().addAll(
                createSettingLine("No Movement Alarm Threshold :", "seconds", noMovementField),
                createSettingLine("Free Fall Alarm Threshold :", "m/s", freeFallField)
        );

        // 🔘 Apply Button
        Button applyButton = new Button("Uygula");
        applyButton.setPrefWidth(120);
        applyButton.setOnAction(e -> applySettings());

        HBox buttonBox = new HBox(applyButton);
        buttonBox.setAlignment(Pos.CENTER);

        root.getChildren().addAll(tempBox, accelBox, buttonBox);
        return root;
    }

    private HBox createSettingLine(String labelText, String unitText, TextField textField) {
        Label label = new Label(labelText);
        label.setMinWidth(220);
        label.setAlignment(Pos.CENTER_RIGHT);

        textField.setPrefWidth(100);

        Label unitLabel = new Label(unitText);
        unitLabel.setMinWidth(60);

        HBox hBox = new HBox(10, label, textField, unitLabel);
        hBox.setAlignment(Pos.CENTER);
        return hBox;
    }

    private void applySettings() {
        try {
            double lowTemp = Double.parseDouble(lowTempField.getText());
            double highTemp = Double.parseDouble(highTempField.getText());
            double noMovement = Double.parseDouble(noMovementField.getText());
            double freeFall = Double.parseDouble(freeFallField.getText());

            System.out.println("Ayarlar uygulandı:");
            System.out.println("  Low Temp Threshold: " + lowTemp + " °C");
            System.out.println("  High Temp Threshold: " + highTemp + " °C");
            System.out.println("  No Movement Threshold: " + noMovement + " seconds");
            System.out.println("  Free Fall Threshold: " + freeFall + " m/s");

            showAlert(Alert.AlertType.INFORMATION, "Başarılı", "Ayarlar başarıyla uygulandı.");

        } catch (NumberFormatException ex) {
            showAlert(Alert.AlertType.ERROR, "Hatalı Giriş", "Lütfen tüm alanlara geçerli sayısal değerler girin.");
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
