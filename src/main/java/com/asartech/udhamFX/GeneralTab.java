package com.asartech.udhamFX;

 /* RUNNING FINE */
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.chart.LineChart;

import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.Random;


public class GeneralTab {

    private final ObservableList<SensorData> sensorDataList = FXCollections.observableArrayList();

    public Node getContent() {
        TableView<SensorData> tableView = new TableView<>();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.setFixedCellSize(100);
        tableView.setPrefHeight(600);

        tableView.getColumns().addAll(
                createTextColumn("TAG#", "tag"),
                createTextColumn("Media", "media"),
                createTextColumn("Location", "location"),
                createChartColumn("Accelerometer", true),
                createAccAlarmColumn(),
                createChartColumn("Temperature", false),
                createTempAlarmColumn()
                //createButtonColumn()
        );

        // 5 adet veri oluştur
        for (int i = 0; i < 5; i++) {
            sensorDataList.add(createRandomSensorData(i));
        }

        tableView.setItems(sensorDataList);
        return new VBox(tableView);
    }

    private TableColumn<SensorData, String> createTextColumn(String title, String property) {
        TableColumn<SensorData, String> column = new TableColumn<>(title);
        column.setCellValueFactory(new PropertyValueFactory<>(property));
        column.setStyle("-fx-alignment: CENTER;");
        return column;
    }

    private TableColumn<SensorData, LineChart<Number, Number>> createChartColumn(String title, boolean isAccel) {
        TableColumn<SensorData, LineChart<Number, Number>> col = new TableColumn<>(title);
        col.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(LineChart<Number, Number> chart, boolean empty) {
                super.updateItem(chart, empty);
                if (empty || chart == null) {
                    setGraphic(null);
                } else {
                    setGraphic(chart);
                }
            }
        });
        col.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(
                isAccel ? cellData.getValue().getAccelerometerChart() : cellData.getValue().getTemperatureChart()
        ));
        return col;
    }

    private TableColumn<SensorData, Label> createAccAlarmColumn() {
        TableColumn<SensorData, Label> col = new TableColumn<>("AccAlarm");
        col.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getAccAlarmLabel()));
        col.setStyle("-fx-alignment: CENTER;");
        col.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(Label label, boolean empty) {
                super.updateItem(label, empty);
                if (empty || label == null) {
                    setGraphic(null);
                } else {
                    setGraphic(label);
                }
            }
        });
        return col;
    }

    private TableColumn<SensorData, Label> createTempAlarmColumn() {
        TableColumn<SensorData, Label> col = new TableColumn<>("TempAlarm");
        col.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getTempAlarmLabel()));
        col.setStyle("-fx-alignment: CENTER;");
        col.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(Label label, boolean empty) {
                super.updateItem(label, empty);
                if (empty || label == null) {
                    setGraphic(null);
                } else {
                    setGraphic(label);
                }
            }
        });
        return col;
    }

    /*
    private TableColumn<SensorData, Button> createButtonColumn() {
        TableColumn<SensorData, Button> col = new TableColumn<>("Action");
        col.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getResetButton()));
        col.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(Button btn, boolean empty) {
                super.updateItem(btn, empty);
                if (empty || btn == null) {
                    setGraphic(null);
                } else {
                    setGraphic(btn);
                }
            }
        });
        return col;
    }*/

    private SensorData createRandomSensorData(int index) {
        String tag = String.format("%03d", 100 + index);
        String[] mediaTypes = {"BLE", "Sub1GHz"};
        String[] locations = {"Meeting Rm#3", "Meeting Rm5", "Hall #5", "Loading Zone", "Office #9"};
        String media = mediaTypes[new Random().nextInt(mediaTypes.length)];
        String location = locations[new Random().nextInt(locations.length)];

        LineChart<Number, Number> accChart = ChartFactoryHelper.createAccelerometerChart();
        LineChart<Number, Number> tempChart = ChartFactoryHelper.createTemperatureChart();

        // Alarm içeriği
        String[] accAlarmStrings = {"None", "No Movement", "Free Fall"};
        Label accAlarm = new Label(accAlarmStrings[new Random().nextInt(accAlarmStrings.length)]);
        accAlarm.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        if (accAlarm.getText() == "None") {
            accAlarm.setTextFill(Color.GRAY);
        }
        else if (accAlarm.getText() == "No Movement") {
            accAlarm.setTextFill(Color.RED);
        }
        else if (accAlarm.getText() == "Free Fall") {
            accAlarm.setTextFill(Color.RED);
        }
        else {
            accAlarm.setTextFill(Color.WHITE);
        }


        String[] tempAlarmStrings = {"None", "Low Temp", "High Temp"};
        Label tempAlarm = new Label(tempAlarmStrings[new Random().nextInt(tempAlarmStrings.length)]);
        tempAlarm.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        if (tempAlarm.getText() == "None") {
            tempAlarm.setTextFill(Color.GRAY);
        }
        else if (tempAlarm.getText() == "Low Temp") {
            tempAlarm.setTextFill(Color.BLUE);
        }
        else if (tempAlarm.getText() == "High Temp") {
            tempAlarm.setTextFill(Color.RED);
        }
        else {
            tempAlarm.setTextFill(Color.WHITE);
        }

        return new SensorData(tag, media, location, accChart, accAlarm, tempChart, tempAlarm);
    }

}
