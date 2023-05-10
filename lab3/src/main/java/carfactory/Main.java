package carfactory;

import carfactory.carbuildings.CarFabric;


import javax.swing.*;

import static carfactory.constants.Constants.*;
import static carfactory.constants.Constants.JLabelTexts.*;
import static carfactory.constants.Constants.PanelsConstants.Dimensions.*;
import static carfactory.constants.Constants.PanelsConstants.FlowLayouts.*;
import static carfactory.constants.Constants.Slider.SliderConstants.Names.*;
import static carfactory.constants.Constants.Slider.SliderConstants.sliderBoxDimension;
import static carfactory.constants.Constants.Slider.SliderConstants.sliderBoxFlowLayout;
import static carfactory.constants.Constants.frameSize.height;
import static carfactory.constants.Constants.frameSize.width;
import static carfactory.ui.Gui.createPanel;
import static carfactory.ui.Gui.makeSlider;


public class Main {
    private final JLabel carCount;
    private final JLabel carsInStorage;
    private final JLabel accessoryInStorage;
    private final JLabel enginesInStorage;
    private final JLabel carBodiesInStorage;
    private final CarFabric fabric;
public Main() {
        fabric = new CarFabric();
        carCount = new JLabel(labelCarCountText + fabric.getProducedCarCount());
        carsInStorage = new JLabel(labelcarsInStorageText + fabric.getCarStorageSize());
        carBodiesInStorage = new JLabel(labelcarBodiesInStorageText + fabric.getCarBodyStorageSize());
        accessoryInStorage = new JLabel(labelaccessoryInStorageText + fabric.getAccessoryStorageSize());
        enginesInStorage = new JLabel(labelenginesInStorageText + fabric.getEngineStorageSize());
        }

private void updateLabels() {
        carCount.setText(labelCarCountText + fabric.getProducedCarCount());
        carsInStorage.setText(labelcarsInStorageText + fabric.getCarStorageSize());
        carBodiesInStorage.setText(labelcarBodiesInStorageText + fabric.getCarBodyStorageSize());
        accessoryInStorage.setText(labelaccessoryInStorageText + fabric.getAccessoryStorageSize());
        enginesInStorage.setText(labelenginesInStorageText + fabric.getEngineStorageSize());
        }

public void createAndShowGUI() {
    JFrame frame = new JFrame(guiName);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(width, height);

    JPanel countPanel = createPanel(countPanelFlowLayout,countPanelDimension,carCount);

    JPanel carPanel = createPanel(carPanelFlowLayout,carPanelDimension,carsInStorage);

    JPanel accessoryPanel = createPanel(accessoryPanelFlowLayout,accessoryPanelDimension,accessoryInStorage);

    JPanel enginesPanel = createPanel(enginesPanelFlowLayout,enginesPanelDimension,enginesInStorage);

    JPanel carBodiesPanel = createPanel(carBodiesPanelFlowLayout,carBodiesPanelDimension,carBodiesInStorage);

    JPanel mainPanel = new JPanel(mainPanelFlowLayout);
    mainPanel.setPreferredSize(mainPanelDimension);
    mainPanel.add(countPanel);
    mainPanel.add(carPanel);
    mainPanel.add(accessoryPanel);
    mainPanel.add(enginesPanel);
    mainPanel.add(carBodiesPanel);

    frame.add(mainPanel);

    JSlider accessorySupplierDelaySlider = makeSlider(accessorySupplierDelaySliderName,fabric);

    JSlider engineSupplierDelaySlider = makeSlider(engineSupplierDelaySliderName, fabric);

    JSlider carBodyDelaySlider = makeSlider(carBodyDelaySliderName,fabric);

    JPanel sliders = new JPanel(sliderBoxFlowLayout);
    sliders.setPreferredSize(sliderBoxDimension);
    sliders.add(accessorySupplierDelaySlider);
    sliders.add(engineSupplierDelaySlider);
    sliders.add(carBodyDelaySlider);
    mainPanel.add(sliders);

    JButton startButton = new JButton(startButtonText);
    startButton.addActionListener(e -> fabric.startFabric());

    JButton stopButton = new JButton(stopButtonText);
    stopButton.addActionListener(e -> fabric.stopFabric());

    mainPanel.add(startButton);
    mainPanel.add(stopButton);

    Timer timer = new Timer(timerDelay, e -> updateLabels());
    timer.start();

    frame.pack();
    frame.setVisible(true);
}
    public static void main(String[] args) {
        Main app = new Main();
        app.createAndShowGUI();
    }
}