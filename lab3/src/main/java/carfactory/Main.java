package carfactory;
import carfactory.carbuildings.CarFabric;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Timer;
import java.util.TimerTask;

class Main extends Application {
    private Text carCount;
    private Text carsInStorage;
    private Text accessoryInStorage;
    private Text enginesInStorage;
    private Text carBodiesInStorage;
    private CarFabric fabric;

    @Override
    public void start(Stage stage) {
        fabric = new CarFabric();
        carCount = new Text("Total cars produced: " + fabric.getProducedCarCount());
        carsInStorage = new Text("Cars in storage: " + fabric.getCarStorageSize());
        carBodiesInStorage = new Text("Cars bodies in storage: " + fabric.getCarBodyStorageSize());
        accessoryInStorage = new Text("Accessory in storage: " + fabric.getAccessoryStorageSize());
        enginesInStorage = new Text("Engines in storage: " + fabric.getEngineStorageSize());

        int topPadding = 50;
        int leftPadding = 40;
        int interval = 40;

        carCount.setX(leftPadding);
        carCount.setY(topPadding);

        carsInStorage.setX(leftPadding);
        carsInStorage.setY(topPadding + interval);

        accessoryInStorage.setX(leftPadding);
        accessoryInStorage.setY(topPadding + 2 * interval);

        enginesInStorage.setX(leftPadding);
        enginesInStorage.setY(topPadding + 3 * interval);

        carBodiesInStorage.setX(leftPadding);
        carBodiesInStorage.setY(topPadding + 4 * interval);

        VBox sliderBox = new VBox();
        sliderBox.setPadding(new Insets(leftPadding));
        sliderBox.setSpacing(20);
        sliderBox.setLayoutY(250);

        Label accessorySupplierDelay = new Label("Wheel supplier delay in milliseconds:");
        Label newAccessorySupplierDelay = new Label("-");
        newAccessorySupplierDelay.setTextFill(Color.DARKGREEN);
        Slider AccessorySupplierDelaySlider = new Slider();
        AccessorySupplierDelaySlider.setMin(0);
        AccessorySupplierDelaySlider.setMax(60000);
        AccessorySupplierDelaySlider.setValue(1000);
        AccessorySupplierDelaySlider.setShowTickMarks(true);
        AccessorySupplierDelaySlider.setBlockIncrement(10);

        AccessorySupplierDelaySlider.valueProperty().addListener((observable, oldValue, newValue) -> {
                    newAccessorySupplierDelay.setText("Current Accessory supplier delay: " + newValue.intValue());
                    fabric.setAccessorySupplierDelay(newValue.intValue());
                }
        );

        sliderBox.getChildren().addAll(accessorySupplierDelay, newAccessorySupplierDelay, AccessorySupplierDelaySlider);

        // create Engine Supplier Delay Slider
        Label engineSupplierDelay = new Label("Engine supplier delay in mills:");
        Label newEngineSupplierDelay = new Label("-");
        newEngineSupplierDelay.setTextFill(Color.DARKGREEN);
        Slider engineSupplierDelaySlider = new Slider();
        engineSupplierDelaySlider.setMin(0);
        engineSupplierDelaySlider.setMax(60000);
        engineSupplierDelaySlider.setValue(1000);
        engineSupplierDelaySlider.setShowTickMarks(true);
        engineSupplierDelaySlider.setBlockIncrement(10);

        engineSupplierDelaySlider.valueProperty().addListener((observable, oldValue, newValue) -> {
                    newEngineSupplierDelay.setText("Current engine supplier delay: " + newValue.intValue());
                    fabric.setEngineSupplierDelay(newValue.intValue());
                }
        );

        sliderBox.getChildren().addAll(engineSupplierDelay, newEngineSupplierDelay, engineSupplierDelaySlider);


        Label carBodySupplierDelay = new Label("Car body supplier delay in mills:");
        Label newCarBodySupplierDelay = new Label("-");
        newCarBodySupplierDelay.setTextFill(Color.DARKGREEN);
        Slider carBodySupplierDelaySlider = new Slider();
        carBodySupplierDelaySlider.setMin(0);
        carBodySupplierDelaySlider.setMax(60000);
        carBodySupplierDelaySlider.setValue(1000);
        carBodySupplierDelaySlider.setShowTickMarks(true);
        carBodySupplierDelaySlider.setBlockIncrement(10);

        carBodySupplierDelaySlider.valueProperty().addListener((observable, oldValue, newValue) -> {
                    newCarBodySupplierDelay.setText("Current car body supplier delay: " + newValue.intValue());
                    fabric.setCarBodySupplierDelay(newValue.intValue());
                }
        );

        sliderBox.getChildren().addAll(carBodySupplierDelay, newCarBodySupplierDelay, carBodySupplierDelaySlider);

        Label dealerDelay = new Label("Dealer delay in mills:");
        Label newDealerDelay = new Label("-");
        newDealerDelay.setTextFill(Color.DARKGREEN);
        Slider dealerDelaySlider = new Slider();
        dealerDelaySlider.setMin(0);
        dealerDelaySlider.setMax(60000);
        dealerDelaySlider.setValue(1000);
        dealerDelaySlider.setShowTickMarks(true);
        dealerDelaySlider.setBlockIncrement(10);

        dealerDelaySlider.valueProperty().addListener((observable, oldValue, newValue) -> {
                    newDealerDelay.setText("Current dealer delay: " + newValue.intValue());
                    fabric.setDealerDelay(newValue.intValue());
                }
        );
        sliderBox.getChildren().addAll(dealerDelay, newDealerDelay, dealerDelaySlider);


        Group counts = new Group(sliderBox, carCount, carsInStorage, accessoryInStorage, enginesInStorage, carBodiesInStorage);
        counts.setStyle("-fx-font: 17 arials;");

        Scene scene = new Scene(counts, 600, 900);
        scene.setFill(Color.PEACHPUFF);
        stage.setTitle("Car fabric");
        stage.setScene(scene);
        stage.show();

        Timer upd = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    carsInStorage.setText("Cars in storage: " + fabric.getCarStorageSize());
                    carBodiesInStorage.setText("Cars bodies in storage: " + fabric.getCarBodyStorageSize());
                    accessoryInStorage.setText("Accessory in storage: " + fabric.getAccessoryStorageSize());
                    enginesInStorage.setText("Engines in storage: " + fabric.getEngineStorageSize());
                    carCount.setText("Total cars produced: " + fabric.getProducedCarCount());
                });
            }
        };

        upd.schedule(task, 0, 300);
    }
    @Override
    public void stop() throws Exception{
        fabric.stopFabric();
        super.stop();
        System.exit(0);
    }
    public static void main(String[] args) {
        launch(args);
    }
}
