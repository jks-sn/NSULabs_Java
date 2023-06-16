package ru.nsu.ccfit.berkaev.client.view.uicomponents;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.Box;

import ru.nsu.ccfit.berkaev.client.Client;
import ru.nsu.ccfit.berkaev.client.view.ClientGUI;
import ru.nsu.ccfit.berkaev.exceptions.NoActiveSocketException;
import ru.nsu.ccfit.berkaev.constants.ClientGUIConstants;

public class DefaultMenu implements ActionListener {

    private final Button backButton;
    private final Button refreshButton;
    
    private ClientGUI clientGUI;
    private Client client;

    private final Box sideBar;

    public DefaultMenu(){
        sideBar = Box.createVerticalBox();
        backButton = ClientGUIConstants.defaultBackButton(this);
        refreshButton = ClientGUIConstants.defaultRefreshButton(this);
        Dimension fillerDim = ClientGUIConstants.defaultFillerDim(Toolkit.getDefaultToolkit().getScreenSize().height);
        sideBar.add(new Box.Filler(fillerDim, fillerDim, fillerDim));
        sideBar.add(refreshButton.getButton());
        sideBar.add(backButton.getButton());
        sideBar.setVisible(false);
    }
    
    public void showMenu(){
        sideBar.setVisible(true);
    }

    public void hideMenu(){
        sideBar.setVisible(false);
    }

    public Box getMenu(){
        return this.sideBar;
    }

    public void setGUI(ClientGUI gui) {
        this.clientGUI = gui;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton.getButton()){
            clientGUI.returnToPrevView();
        }
        if (e.getSource() == refreshButton.getButton()){
            try {
                client.getParticipantsTable();
            } catch (NoActiveSocketException | IOException e1) {
                client.processError(e1.getMessage());
                e1.printStackTrace();
            }
        }
    }
}