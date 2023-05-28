package client.view.uicomponents;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.Box;

import client.Client;
import exceptions.NoActiveSocketException;

import static constants.ClientGUIConstants.*;

public class ChatMenu implements ActionListener {

    private final Button showParticipantsButton;
    private final Button exitButton;

    private Client client = null;

    private final Box sideBar;

    public ChatMenu() {
        sideBar = Box.createVerticalBox();
        showParticipantsButton = defaultShowParticipantsButton(this);
        exitButton = defaultExitButton(this);
        sideBar.add(showParticipantsButton.getButton());
        Dimension fillerDim = defaultFillerDim(Toolkit.getDefaultToolkit().getScreenSize().height);
        sideBar.add(new Box.Filler(fillerDim, fillerDim, fillerDim));
        sideBar.add(exitButton.getButton());
        sideBar.setVisible(false);
    }

    public void showMenu() {
        sideBar.setVisible(true);
    }

    public void hideMenu() {
        sideBar.setVisible(false);
    }

    public Box getMenu() {
        return this.sideBar;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == showParticipantsButton.getButton()) {
            try {
                client.getParticipantsTable();
            } catch (NoActiveSocketException | IOException e1) {
                client.processError(e1.getMessage());
            }
        }

        if (e.getSource() == exitButton.getButton()) {
            try {
                client.disconnect();
            } catch (IOException | NoActiveSocketException e1) {
                client.processError(e1.getMessage());
            }
        }
    }
}
