package carfactory;

import carfactory.carbuildings.CarFabric;

import javax.swing.*;
import java.awt.Dimension;
import java.awt.FlowLayout;


public class Main {
    private final JLabel carCount;
    private final JLabel carsInStorage;
    private final JLabel accessoryInStorage;
    private final JLabel enginesInStorage;
    private final JLabel carBodiesInStorage;
    private CarFabric fabric;
public Main() {
        fabric = new CarFabric();
        carCount = new JLabel("Total cars produced: " + fabric.getProducedCarCount());
        carsInStorage = new JLabel("Cars in storage: " + fabric.getCarStorageSize());
        carBodiesInStorage = new JLabel("Cars bodies in storage: " + fabric.getCarBodyStorageSize());
        accessoryInStorage = new JLabel("Accessory in storage: " + fabric.getAccessoryStorageSize());
        enginesInStorage = new JLabel("Engines in storage: " + fabric.getEngineStorageSize());
        }

private void updateLabels() {
        carCount.setText("Total cars produced: " + fabric.getProducedCarCount());
        carsInStorage.setText("Cars in storage: " + fabric.getCarStorageSize());
        carBodiesInStorage.setText("Cars bodies in storage: " + fabric.getCarBodyStorageSize());
        accessoryInStorage.setText("Accessory in storage: " + fabric.getAccessoryStorageSize());
        enginesInStorage.setText("Engines in storage: " + fabric.getEngineStorageSize());
        }

public void createAndShowGUI() {
    JFrame frame = new JFrame("Car Factory");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(500, 400);
    JPanel mainPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
    mainPanel.setPreferredSize(new Dimension(400, 300));

    JPanel countPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
    countPanel.setPreferredSize(new Dimension(350, 20));
    countPanel.add(carCount);

    JPanel carPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
    carPanel.setPreferredSize(new Dimension(350, 20));
    carPanel.add(carsInStorage);

    JPanel accessoryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
    accessoryPanel.setPreferredSize(new Dimension(350, 20));
    accessoryPanel.add(accessoryInStorage);

    JPanel enginesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
    enginesPanel.setPreferredSize(new Dimension(350, 20));
    enginesPanel.add(enginesInStorage);

    JPanel carBodiesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
    carBodiesPanel.setPreferredSize(new Dimension(350, 20));
    carBodiesPanel.add(carBodiesInStorage);

    mainPanel.add(countPanel);
    mainPanel.add(carPanel);
    mainPanel.add(accessoryPanel);
    mainPanel.add(enginesPanel);
    mainPanel.add(carBodiesPanel);

    frame.add(mainPanel);

    JPanel sliderBox = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
    sliderBox.setPreferredSize(new Dimension(400, 100));

    JSlider accessorySupplierDelaySlider = new JSlider(JSlider.HORIZONTAL, 0, 60000, 1000);
    accessorySupplierDelaySlider.setMajorTickSpacing(20000);
    accessorySupplierDelaySlider.setMinorTickSpacing(1000);
    accessorySupplierDelaySlider.setPaintTicks(true);
    accessorySupplierDelaySlider.setPaintLabels(true);
    JSlider engineSupplierDelaySlider = new JSlider(JSlider.HORIZONTAL, 0, 60000, 1000);
    engineSupplierDelaySlider.setMajorTickSpacing(20000);
    engineSupplierDelaySlider.setMinorTickSpacing(1000);
    engineSupplierDelaySlider.setPaintTicks(true);
    engineSupplierDelaySlider.setPaintLabels(true);
    engineSupplierDelaySlider.setPreferredSize(new Dimension(350, 50));
    engineSupplierDelaySlider.addChangeListener(e -> {
        JSlider source = (JSlider) e.getSource();
        if (!source.getValueIsAdjusting()) {
            int delay = (int) source.getValue();
            fabric.setEngineSupplierDelay(delay);
        }
    });

    sliderBox.add(accessorySupplierDelaySlider);
    sliderBox.add(engineSupplierDelaySlider);

    mainPanel.add(sliderBox);

    JButton startButton = new JButton("Start Production");
    startButton.addActionListener(e -> fabric.startFabric());

    JButton stopButton = new JButton("Stop Production");
    stopButton.addActionListener(e -> fabric.stopFabric());

    mainPanel.add(startButton);
    mainPanel.add(stopButton);

    Timer timer = new Timer(1000, e -> updateLabels());
    timer.start();

    frame.pack();
    frame.setVisible(true);
}
    public static void main(String[] args) {
        Main app = new Main();
        app.createAndShowGUI();
    }
}