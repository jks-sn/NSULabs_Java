package client.view.uicomponents;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import client.Client;
import ctsmessages.LoginMessage;
import exceptions.ConnectionError;

import static constants.ClientGUIConstants.*;

public class MainMenu implements ActionListener {

    private final Button connectButton;
    private final Button exitButton;
    
    private final JFrame mainFrame;
    private Client client = null;

    private final Box sideBar;

    public MainMenu(JFrame mainFrame) {
        this.mainFrame = mainFrame;
        sideBar = Box.createVerticalBox();
        connectButton = defaultConnectButton(this);
        exitButton = defaultExitButton(this);
        Dimension fillerDim = defaultFillerDim(Toolkit.getDefaultToolkit().getScreenSize().height);
        sideBar.add(connectButton.getButton());
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
        if (e.getSource() == connectButton.getButton()) {
            if (this.client != null)
                try {
                    LoginDialog dialog = new LoginDialog(mainFrame);
                    dialog.setVisible(true);
                    if (dialog.isSucceeded()) {
                        client.connect();
                    }
                } catch (ConnectionError e1) {
                    JOptionPane.showMessageDialog(mainFrame, e1.getMessage());
                }
        }

        if (e.getSource() == exitButton.getButton()) {
            mainFrame.dispose();
            client.closeClient();
        }
    }

    private class LoginDialog extends JDialog {

        private final JTextField usernameField;
        private final JTextField hostField;
        private final JTextField portField;
        private boolean succeeded;
    
        public LoginDialog(JFrame parent) {
            super(parent, "Login", true);

            JPanel panel = new JPanel(new GridBagLayout());
            GridBagConstraints cs = new GridBagConstraints();
    
            cs.fill = GridBagConstraints.HORIZONTAL;

            JLabel lbHost = new JLabel("Set host: ");
            cs.gridx = 0;
            cs.gridy = 0;
            cs.gridwidth = 1;
            panel.add(lbHost, cs);
    
            hostField = new JTextField(20);
            cs.gridx = 1;
            cs.gridy = 0;
            cs.gridwidth = 2;
            panel.add(hostField, cs);

            JLabel lbPort = new JLabel("Set port: ");
            cs.gridx = 0;
            cs.gridy = 1;
            cs.gridwidth = 1;
            panel.add(lbPort, cs);
    
            portField = new JTextField(20);
            cs.gridx = 1;
            cs.gridy = 1;
            cs.gridwidth = 2;
            panel.add(portField, cs);

            JLabel lbUsername = new JLabel("Set username: ");
            cs.gridx = 0;
            cs.gridy = 2;
            cs.gridwidth = 1;
            panel.add(lbUsername, cs);
    
            usernameField = new JTextField(20);
            cs.gridx = 1;
            cs.gridy = 2;
            cs.gridwidth = 2;
            panel.add(usernameField, cs);

            panel.setBorder(new LineBorder(Color.GRAY));

            JButton btnLogin = new JButton("Confirm");
            cs.gridx = 0;
            cs.gridy = 3;
            cs.gridwidth = 3;
            panel.add(btnLogin, cs);

            JButton btnExit = new JButton("Exit");
            cs.gridx = 0;
            cs.gridy = 4;
            cs.gridwidth = 3;
            panel.add(btnExit, cs);

            getContentPane().add(panel, BorderLayout.CENTER);
    
            btnLogin.addActionListener(e -> {
                if (getHost().length() == 0 || getPort().length() == 0 || getUsername().length() == 0) {
                    succeeded = false;
                    return;
                }
                client.setHost(getHost());
                client.setPort(Integer.parseInt(getPort()));
                client.setUserInfo(new LoginMessage(getUsername()));
                succeeded = true;
                dispose();
            });

            btnExit.addActionListener(e -> {
                succeeded = false;
                dispose();
            });

            pack();
            setResizable(false);
            setLocationRelativeTo(parent);
        }
    
        private String getUsername() {
            return usernameField.getText();
        }
    
        private String getHost() {
            return hostField.getText();
        }

        private String getPort() {
            return portField.getText();
        }
    
        public boolean isSucceeded() {
            return succeeded;
        }
    } 
}