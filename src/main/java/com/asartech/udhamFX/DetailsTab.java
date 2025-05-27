package com.asartech.udhamFX;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.chart.*;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.*;

public class DetailsTab {

    private final Map<String, XYChart.Series<Number, Number>> seriesXMap = new HashMap<>();
    private final Map<String, XYChart.Series<Number, Number>> seriesYMap = new HashMap<>();
    private final Map<String, XYChart.Series<Number, Number>> seriesZMap = new HashMap<>();
    private final Map<String, XYChart.Series<Number, Number>> tempSeriesMap = new HashMap<>();

    private String currentTag = "005";
    private int timeCounter = 0;
    private final Random random = new Random();

    public Node getContent() {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));

        // TAG# seçimi (sol üst)
        VBox tagBox = new VBox(10);
        tagBox.setAlignment(Pos.TOP_LEFT);

        Label tagLabel = new Label("TAG#");
        ComboBox<String> tagSelector = new ComboBox<>();
        tagSelector.getItems().addAll("005", "010", "124", "215", "330");
        tagSelector.setValue("005");

        tagBox.getChildren().addAll(tagLabel, tagSelector);
        root.setTop(tagBox);
        BorderPane.setAlignment(tagBox, Pos.TOP_LEFT);

        // Grafik kutusu

        //------AccData in HBox -----------//
        HBox hbox = new HBox(5);
        hbox.setPadding(new Insets(10));
        hbox.setPrefWidth(Region.USE_COMPUTED_SIZE);
        HBox.setHgrow(hbox, Priority.ALWAYS);

        //--------------------------------
        // Sağ tarafta grafik paneli
        VBox chartsBox = new VBox(20);
        chartsBox.setPadding(new Insets(10));
        chartsBox.setAlignment(Pos.CENTER);
        chartsBox.setPrefWidth(Region.USE_COMPUTED_SIZE);
        VBox.setVgrow(chartsBox, Priority.ALWAYS);

// Her grafik için esnek genişlik ve yükseklik
        LineChart<Number, Number> chartX = createAccelChart("Accelerometer - X");
        LineChart<Number, Number> chartY = createAccelChart("Accelerometer - Y");
        LineChart<Number, Number> chartZ = createAccelChart("Accelerometer - Z");
        LineChart<Number, Number> chartTemp = createTempChart("Temperature");

// Grafiklerin container'lara sarılması (esneklik için)
        for (LineChart<Number, Number> chart : List.of(chartX, chartY, chartZ, chartTemp)) {
            chart.setAnimated(false);
            chart.setMinHeight(150);
            chart.setPrefHeight(Region.USE_COMPUTED_SIZE);
            chart.setPrefWidth(Region.USE_COMPUTED_SIZE);
            chart.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE); //Eğer sekme büyüyüp küçüldükçe grafikler de yeniden boyutlansın istersen
            VBox.setVgrow(chart, Priority.ALWAYS);
            HBox.setHgrow(hbox, Priority.ALWAYS);

        }

        //--Ramis---//
        hbox.getChildren().addAll(chartX, chartY, chartZ);
        root.setCenter(hbox);

        //--end Ramis--//
        chartsBox.getChildren().addAll(hbox, chartTemp);
        root.setCenter(chartsBox);




        //---------------------------------
        /*
        VBox chartsBox = new VBox(20);
        chartsBox.setPadding(new Insets(10));
        chartsBox.setAlignment(Pos.CENTER);

        LineChart<Number, Number> chartX = createAccelChart("Accelerometer - X");
        LineChart<Number, Number> chartY = createAccelChart("Accelerometer - Y");
        LineChart<Number, Number> chartZ = createAccelChart("Accelerometer - Z");
        LineChart<Number, Number> chartTemp = createTempChart("Temperature");

        chartsBox.getChildren().addAll(chartX, chartY, chartZ, chartTemp);
        root.setCenter(chartsBox);
*/
        // TAG değişince grafik serilerini güncelle
        tagSelector.setOnAction(e -> {
            currentTag = tagSelector.getValue();
            chartX.getData().setAll(getOrCreateSeries(seriesXMap, currentTag));
            chartY.getData().setAll(getOrCreateSeries(seriesYMap, currentTag));
            chartZ.getData().setAll(getOrCreateSeries(seriesZMap, currentTag));
            chartTemp.getData().setAll(getOrCreateSeries(tempSeriesMap, currentTag));
        });

        // Başlangıç serilerini ayarla
        chartX.getData().add(getOrCreateSeries(seriesXMap, currentTag));
        chartY.getData().add(getOrCreateSeries(seriesYMap, currentTag));
        chartZ.getData().add(getOrCreateSeries(seriesZMap, currentTag));
        chartTemp.getData().add(getOrCreateSeries(tempSeriesMap, currentTag));

        // Saniyelik veri güncelleyici
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            timeCounter++;
            //addRandomAccel(seriesXMap.get(currentTag));
            //addRandomAccel(seriesYMap.get(currentTag));
            //addRandomAccel(seriesZMap.get(currentTag));
            //addRandomTemp(tempSeriesMap.get(currentTag));
        }));

        // Sol tarafta Durum Sergileme Kutusu
//-----------------------------------------------------
        // Sol: Durum paneli (etiket + durum etiketi)
        VBox statusBox = new VBox(15); // satırlar arası eşit boşluk
        statusBox.setPadding(new Insets(10));
        statusBox.setAlignment(Pos.TOP_LEFT);
        statusBox.setPrefWidth(350); // biraz geniş alan

// Satır verileri
        String[][] statusRows = {
                {"Connection Status :", "Connected"},         // ya da Not Connected
                {"Connection Type :", "Bluetooth"},           // ya da Sub1GHz
                {"Current Location :", "Meeting Rm#3"},       // seçenekli
                {"Previous Location :", "Loading Zone"},      // seçenekli
                {"Accelerometer Current Alarm :", "No Movement"}, // None, Free Fall
                {"Accelerometer Alarm Duration:", "10 sec"},
                {"Temperature Current Alarm :", "Low Temp"},  // None, Low Temp, High Temp
                {"Temperature Alarm Duration:", "10 sec"}
        };

        for (String[] row : statusRows) {
            Label labelLeft = new Label(row[0]);
            labelLeft.setMinWidth(170);
            Label labelRight = new Label(row[1]);
            labelRight.setStyle("-fx-font-weight: bold;");
            labelRight.setTextFill(Color.DARKBLUE);

            HBox hBox = new HBox(10, labelLeft, labelRight);
            hBox.setAlignment(Pos.CENTER_LEFT);
            statusBox.getChildren().add(hBox);
        }

        root.setLeft(statusBox);




        //----------------------------------

        // Durum Kutusu sonu

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        return root;
    }

    private LineChart<Number, Number> createAccelChart(String title) {
        NumberAxis xAxis = new NumberAxis("Time (s)", 0, 20, 1);
        NumberAxis yAxis = new NumberAxis("m/s²", -50, 50, 10);
        yAxis.setAutoRanging(false);

        //LineChart<Number, Number> chart = new LineChart<>(xAxis, yAxis);
        LineChart<Number, Number> chart = ChartFactoryHelper.createAccelerometerChart();
        chart.setTitle(title);
        /*
        chart.setAnimated(false);
        chart.setLegendVisible(false);
        chart.setMinHeight(200);
        chart.setMaxHeight(250);
         */

        return chart;
    }

    private LineChart<Number, Number> createTempChart(String title) {
        NumberAxis xAxis = new NumberAxis("Time (s)", 0, 20, 1);
        NumberAxis yAxis = new NumberAxis("°C", -10, 70, 10);
        yAxis.setAutoRanging(false);

        //LineChart<Number, Number> chart = new LineChart<>(xAxis, yAxis);
        LineChart<Number, Number> chart = ChartFactoryHelper.createTemperatureChart();
        chart.setTitle(title);
        /*
        chart.setAnimated(false);
        chart.setLegendVisible(false);
        chart.setMinHeight(200);
        chart.setMaxHeight(250);
         */

        return chart;
    }

    private XYChart.Series<Number, Number> getOrCreateSeries(Map<String, XYChart.Series<Number, Number>> map, String tag) {
        return map.computeIfAbsent(tag, t -> new XYChart.Series<>());
    }

    /*
    private void addRandomAccel(XYChart.Series<Number, Number> series) {
        double value = -50 + random.nextDouble() * 100;
        series.getData().add(new XYChart.Data<>(timeCounter, value));
        //if (series.getData().size() > 20) {
        //    series.getData().remove(0);
        //}
    }

    private void addRandomTemp(XYChart.Series<Number, Number> series) {
        double value = -10 + random.nextDouble() * 80;
        series.getData().add(new XYChart.Data<>(timeCounter, value));
        //if (series.getData().size() > 20) {
        //    series.getData().remove(0);
        //}
    }
    */
}
