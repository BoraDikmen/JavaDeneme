package com.asartech.udhamFX;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;


public class JavaFXDashboard extends Application {

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();

        //VBox topPanel = createTopPanel();
        TabPane tabPane = createTabPane();

        HeaderPane header = new HeaderPane(primaryStage);
        root.setTop(header);
        FooterPane footer = new FooterPane(primaryStage);
        root.setBottom(footer);
        root.setCenter(tabPane);

        //Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("C:/Users/USER/IdeaProjects/udhamFX/src/main/resources/udham.png")));
        Image icon = new Image("file:C:/Users/USER/IdeaProjects/udhamFX/src/main/resources/udham.png");
        primaryStage.getIcons().add(icon);

        Scene scene = new Scene(root, 1200, 800);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Sensor Monitor GUI");
        primaryStage.show();
    }



    /*
    private VBox createTopPanel() {
        Label companyLabel = new Label("Firma Adı: ACME Corp");
        Label projectLabel = new Label("Proje: IoT Sensor Monitor");
        Label dateTimeLabel = new Label();
        Label resolutionLabel = new Label();
        ImageView logo = new ImageView(new Image("file:logo.png")); // logo dosyasını ekleyin
        logo.setFitHeight(50);
        logo.setPreserveRatio(true);

        HBox infoRow = new HBox(20, companyLabel, projectLabel, dateTimeLabel, resolutionLabel, logo);
        infoRow.setAlignment(Pos.CENTER_LEFT);
        infoRow.setPadding(new Insets(10));

        // Tarih-Saat bilgisi güncellemesi
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            dateTimeLabel.setText("Tarih/Saat: " + now.format(formatter));
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        // Çözünürlük bilgisi
        resolutionLabel.setText("Çözünürlük: " + Screen.getPrimary().getBounds().getWidth() + "x" +
                Screen.getPrimary().getBounds().getHeight());

        return new VBox(infoRow);
    }
*/
    private TabPane createTabPane() {
        TabPane tabPane = new TabPane();

        Tab tabGeneral = new Tab("General");
        Tab tabDetails = new Tab("Details");
        Tab tabSettings = new Tab("Settings");

        tabGeneral.setContent(new GeneralTab().getContent());
        //tabDetails.setContent(new Label("Details içeriği gelecek."));
        tabDetails.setContent(new DetailsTab().getContent());
        //tabSettings.setContent(new Label("Settings içeriği gelecek."));
        tabSettings.setContent(new SettingsTab().getContent());

        tabPane.getTabs().addAll(tabGeneral, tabDetails, tabSettings);
        return tabPane;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
