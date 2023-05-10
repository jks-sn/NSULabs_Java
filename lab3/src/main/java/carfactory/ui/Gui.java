package carfactory.ui;

import carfactory.carbuildings.CarFabric;
import org.jetbrains.annotations.NotNull;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import java.awt.Dimension;
import java.awt.FlowLayout;

import static carfactory.constants.Constants.Slider.SliderConstants.*;
import static carfactory.constants.Constants.Slider.SliderConstants.Names.*;

public class Gui {
    public static @NotNull JSlider makeSlider(String nameSlider, CarFabric fabric) {
        JSlider supplierDelaySlider = sliderPreset;
        supplierDelaySlider.setMajorTickSpacing(sliderMajorTickSpacing);
        supplierDelaySlider.setMinorTickSpacing(sliderMinorTickSpacing);
        supplierDelaySlider.setPaintTicks(true);
        supplierDelaySlider.setPaintLabels(true);
        supplierDelaySlider.setPreferredSize(sliderDimension);
        supplierDelaySlider.addChangeListener(e -> {
            JSlider source = (JSlider) e.getSource();
            if (!source.getValueIsAdjusting()) {
                int delay = source.getValue();
                switch (nameSlider) {
                    case (engineSupplierDelaySliderName) -> fabric.setEngineSupplierDelay(delay);
                    case (accessorySupplierDelaySliderName) -> fabric.setAccessorySupplierDelay(delay);
                    case (carBodyDelaySliderName) -> fabric.setCarBodySupplierDelay(delay);
                }
            }
        });
        return supplierDelaySlider;
    }

    public static @NotNull JPanel createPanel(FlowLayout flowLayout, Dimension dimension, JLabel label)
    {
        JPanel panel = new JPanel(flowLayout);
        panel.setPreferredSize(dimension);
        panel.add(label);
        return panel;
    }
}
