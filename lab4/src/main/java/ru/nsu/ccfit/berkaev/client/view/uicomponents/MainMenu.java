package ru.nsu.ccfit.berkaev.client.view.uicomponents;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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

import ru.nsu.ccfit.berkaev.client.Client;
import ru.nsu.ccfit.berkaev.ctsmessages.LoginMessage;
import ru.nsu.ccfit.berkaev.exceptions.ConnectionError;

import static ru.nsu.ccfit.berkaev.constants.ClientGUIConstants.*;

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
            super(parent, loginDialogName, true);
            JPanel panel = new JPanel(new GridBagLayout());

            JLabel lbHost = new JLabel(hostLabelText);
            GridBagConstraints cs = gridBagConstraintsForHostLabel();
            panel.add(lbHost, cs);
    
            hostField = new JTextField(defaultNumberColumnsForField);
            cs = gridBagConstraintsForHostField();
            panel.add(hostField, cs);

            JLabel lbPort = new JLabel(portLabelText);
            cs = gridBagConstraintsForPortLabel();
            panel.add(lbPort, cs);
    
            portField = new JTextField(defaultNumberColumnsForField);
            cs = gridBagConstraintsForPortField();
            panel.add(portField, cs);

            JLabel lbUsername = new JLabel(usernameLabelText);
            cs = gridBagConstraintsForUsernameLabel();
            panel.add(lbUsername, cs);
    
            usernameField = new JTextField(defaultNumberColumnsForField);
            cs = gridBagConstraintsForUsernameField();
            panel.add(usernameField, cs);

            panel.setBorder(new LineBorder(Color.GRAY));

            JButton btnLogin = new JButton(loginButtonText);
            cs = gridBagConstraintsForLoginButton();
            panel.add(btnLogin, cs);

            JButton btnExit = new JButton(exitButtonText);
            cs = gridBagConstraintsForExitButton();
            panel.add(btnExit, cs);

            getContentPane().add(panel, BorderLayout.CENTER);
    
            btnLogin.addActionListener(e -> {
                if (!isInteger(getHost()) || !isInteger(getPort()) || getUsername().isEmpty()) {
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
