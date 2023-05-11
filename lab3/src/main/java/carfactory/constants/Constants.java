package carfactory.constants;

import javax.swing.JSlider;
import java.awt.Dimension;
import java.awt.FlowLayout;

public class Constants {
    public static final String guiName = "Car Factory";
    public static final int timerDelay = 1000;
    public static final String stopButtonText = "Stop Production";
    public static final String startButtonText = "Start Production";

    public static class frameSize {
        public static final int width = 500;
        public static final int height = 400;
    }

    public static class JLabelTexts {
        public static final String labelCarCountText = "Total cars sells: ";
        public static final String labelcarsInStorageText = "Cars in storage: ";
        public static final String labelcarBodiesInStorageText = "Cars bodies in storage: ";
        public static final String labelaccessoryInStorageText = "Accessory in storage: ";
        public static final String labelenginesInStorageText = "Engines in storage: ";
    }

    public static class PanelsConstants {
        public static class Dimensions {
            public static final Dimension countPanelDimension = new Dimension(350, 20);
            public static final Dimension carPanelDimension = new Dimension(350, 20);
            public static final Dimension accessoryPanelDimension = new Dimension(350, 20);
            public static final Dimension enginesPanelDimension = new Dimension(350, 20);
            public static final Dimension carBodiesPanelDimension = new Dimension(350, 20);
            public static final Dimension mainPanelDimension = new Dimension(400, 300);
        }

        public static class FlowLayouts {
            public static final FlowLayout countPanelFlowLayout = new FlowLayout(FlowLayout.LEFT, 0, 0);
            public static final FlowLayout carPanelFlowLayout = new FlowLayout(FlowLayout.LEFT, 0, 0);
            public static final FlowLayout accessoryPanelFlowLayout = new FlowLayout(FlowLayout.LEFT, 0, 0);
            public static final FlowLayout enginesPanelFlowLayout = new FlowLayout(FlowLayout.LEFT, 0, 0);
            public static final FlowLayout carBodiesPanelFlowLayout = new FlowLayout(FlowLayout.LEFT, 0, 0);
            public static final FlowLayout mainPanelFlowLayout = new FlowLayout(FlowLayout.LEFT, 20, 20);
        }
    }

    public static class Slider {
        public static class SliderConstants {
            public static class Names {
                public static final String accessorySupplierDelaySliderName = "Accessory";
                public static final String engineSupplierDelaySliderName = "Engine";
                public static final String carBodyDelaySliderName = "Body";
            }

            public static final FlowLayout sliderBoxFlowLayout = new FlowLayout(FlowLayout.LEFT, 20, 20);
            public static final Dimension sliderBoxDimension = new Dimension(400, 100);
            public static final JSlider sliderPreset = new JSlider(JSlider.HORIZONTAL, 0, 60000, 1000);
            public static final int sliderMajorTickSpacing = 20000;
            public static final int sliderMinorTickSpacing = 1000;
            public static final Dimension sliderDimension = new Dimension(350, 50);
        }

    }

}
