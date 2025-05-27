package com.asartech.udhamFX;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class SensorData {
    private final SimpleStringProperty tag;
    private final SimpleStringProperty media;
    private final SimpleStringProperty location;
    private final LineChart<Number, Number> accelerometerChart;
    private final Label accAlarmLabel;
    private final LineChart<Number, Number> temperatureChart;
    private final Label tempAlarmLabel;
    //private final Button resetButton;

    public SensorData(String tag, String media, String location,
                      LineChart<Number, Number> accChart,
                      Label accAlarmLabel,
                      LineChart<Number, Number> tempChart,
                      Label tempAlarmLabel) {
        this.tag = new SimpleStringProperty(tag);
        this.media = new SimpleStringProperty(media);
        this.location = new SimpleStringProperty(location);
        this.accelerometerChart = accChart;
        this.accAlarmLabel = accAlarmLabel;
        this.temperatureChart = tempChart;
        this.tempAlarmLabel = tempAlarmLabel;
        //this.resetButton = new Button("Reset");
    }

    public String getTag() { return tag.get(); }
    public String getMedia() { return media.get(); }
    public String getLocation() { return location.get(); }
    public LineChart<Number, Number> getAccelerometerChart() { return accelerometerChart; }
    public Label getAccAlarmLabel() { return accAlarmLabel; }
    public LineChart<Number, Number> getTemperatureChart() { return temperatureChart; }
    public Label getTempAlarmLabel() { return tempAlarmLabel; }
    //public Button getResetButton() { return resetButton; }
}
