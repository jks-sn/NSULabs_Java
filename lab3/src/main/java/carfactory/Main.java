package carfactory;

import carfactory.carbuildings.CarFabric;


import javax.swing.*;

import static carfactory.constants.Constants.*;
import static carfactory.constants.Constants.GUIConstants.*;
import static carfactory.constants.Constants.GUIConstants.FrameSize.height;
import static carfactory.constants.Constants.GUIConstants.FrameSize.width;
import static carfactory.constants.Constants.GUIConstants.JLabelTexts.*;
import static carfactory.constants.Constants.GUIConstants.PanelsConstants.Dimensions.*;
import static carfactory.constants.Constants.GUIConstants.PanelsConstants.FlowLayouts.*;
import static carfactory.constants.Constants.GUIConstants.Slider.SliderConstants.Names.*;
import static carfactory.constants.Constants.GUIConstants.Slider.SliderConstants.sliderBoxDimension;
import static carfactory.constants.Constants.GUIConstants.Slider.SliderConstants.sliderBoxFlowLayout;
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
        carsInStorage = new JLabel(labelcarsInStorageText + fabric.getCarStorageNumberCarItems());
        carBodiesInStorage = new JLabel(labelcarBodiesInStorageText + fabric.getCarBodyStorageNumberCarItems());
        accessoryInStorage = new JLabel(labelaccessoryInStorageText + fabric.getAccessoryStorageNumberCarItems());
        enginesInStorage = new JLabel(labelenginesInStorageText + fabric.getEngineStorageNumberCarItems());
        }

private void updateLabels() {
        carCount.setText(labelCarCountText + fabric.getProducedCarCount());
        carsInStorage.setText(labelcarsInStorageText + fabric.getCarStorageNumberCarItems());
        carBodiesInStorage.setText(labelcarBodiesInStorageText + fabric.getCarBodyStorageNumberCarItems());
        accessoryInStorage.setText(labelaccessoryInStorageText + fabric.getAccessoryStorageNumberCarItems());
        enginesInStorage.setText(labelenginesInStorageText + fabric.getEngineStorageNumberCarItems());
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

    JPanel sliders = new JPanel();
    sliders.setLayout(new BoxLayout(sliders, BoxLayout.Y_AXIS));
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