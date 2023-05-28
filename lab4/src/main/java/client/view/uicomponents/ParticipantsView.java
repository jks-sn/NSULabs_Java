package client.view.uicomponents;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.LayoutManager;
import java.util.ArrayList;

import javax.swing.JPanel;

public class ParticipantsView {

    private final JPanel table;

    public ParticipantsView() {
        table = new JPanel();
        table.setLayout(new VerticalLayout());
        table.setBackground(Color.GRAY);
        table.setVisible(false);
    }

    private static class VerticalLayout implements LayoutManager{

        private final int GAP = 5;
        
        @Override
        public void addLayoutComponent(String name, Component comp) {
        }

        @Override
        public void removeLayoutComponent(Component comp) {
        }

        @Override
        public Dimension preferredLayoutSize(Container c) {
            return calculateBestSize(c);
        }

        @Override
        public Dimension minimumLayoutSize(Container c) {
            return calculateBestSize(c);
        }

        private final Dimension size = new Dimension();

        private Dimension calculateBestSize(Container c) {
            Component[] comps = c.getComponents();
            int maxWidth = 0;
            for (Component comp : comps) {
                int width = comp.getWidth();
                if (width > maxWidth) maxWidth = width;
            }
            size.width = maxWidth + GAP;
            int height = 0;
            for (Component comp : comps) {
                height += GAP;
                height += comp.getHeight();
            }
            size.height = height;
            return size;
        }

        @Override
        public void layoutContainer(Container c) {
            Component[] comps = c.getComponents();
            int currentY = GAP;
            for (Component comp : comps) {
                Dimension pref = comp.getPreferredSize();
                comp.setBounds(GAP, currentY, pref.width, pref.height);
                currentY += GAP;
                currentY += pref.height;
            }
        }

    }

    public JPanel getTable() {
        return this.table;
    }

    public void printTable(ArrayList<Object> newData) {
        table.removeAll();
        
        Color defaultColor = Color.black;
        Font defaultFont = new Font("Arial", Font.BOLD, 20);
        Dimension minSize = new Dimension(table.getWidth() - 30, 100);
        Dimension prefSize = new Dimension(table.getWidth() - 30, 100);
        Dimension maxSize = new Dimension(table.getWidth() - 30, 100);

        for (Object newDatum : newData) {
            Button button = new Button((String) newDatum, defaultColor, prefSize, minSize, maxSize, defaultFont, null);
            button.getButton().setVerticalAlignment(0);
            button.getButton().setBackground(Color.BLUE);
            button.getButton().setVisible(true);
            table.add(button.getButton());
        }

        table.setVisible(true);
        table.revalidate();
        table.repaint();
    }
}
