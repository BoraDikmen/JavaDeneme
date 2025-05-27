package com.asartech.udhamFX;

/* RUNNING FINE */
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.util.Duration;

import java.util.Random;

public class ChartFactoryHelper {

    private static final int MAX_DATA_POINTS = 20;

    public static LineChart<Number, Number> createAccelerometerChart() {
        LineChart<Number, Number> chart = createChart("Acc (m/s²)", -50, 50);
        startChartUpdate(chart, -50, 50);
        return chart;
    }

    public static LineChart<Number, Number> createTemperatureChart() {
        LineChart<Number, Number> chart = createChart("Temp (°C)", -10, 70);
        startChartUpdate(chart, -10, 70);
        return chart;
    }

    private static LineChart<Number, Number> createChart(String yLabel, int yMin, int yMax) {
        NumberAxis xAxis = new NumberAxis(0, MAX_DATA_POINTS, 5);
        xAxis.setLabel("Seconds");

        NumberAxis yAxis = new NumberAxis(yMin, yMax, 10);
        yAxis.setLabel(yLabel);

        LineChart<Number, Number> chart = new LineChart<>(xAxis, yAxis);
        chart.setLegendVisible(false);
        chart.setAnimated(false);
        chart.setCreateSymbols(false);
        chart.setMinWidth(200);

        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        chart.getData().add(series);

        return chart;
    }

    private static void startChartUpdate(LineChart<Number, Number> chart, int minY, int maxY) {
        XYChart.Series<Number, Number> series = chart.getData().get(0);
        Random random = new Random();

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            // veri noktası sayısı 20'yi geçtiyse ilkini sil
            if (series.getData().size() >= MAX_DATA_POINTS) {
                series.getData().remove(0);
            }

            int nextX = series.getData().isEmpty() ? 0 : series.getData().get(series.getData().size() - 1).getXValue().intValue() + 1;
            int nextY = minY + random.nextInt(maxY - minY + 1);

            series.getData().add(new XYChart.Data<>(nextX, nextY));

            // X eksenini güncelle
            NumberAxis xAxis = (NumberAxis) chart.getXAxis();
            xAxis.setLowerBound(nextX - MAX_DATA_POINTS + 1);
            xAxis.setUpperBound(nextX);
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
}
