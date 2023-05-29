package constants;

import client.view.ClientGUI;
import client.view.uicomponents.Button;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.*;
import java.awt.event.ActionListener;

public class ClientGUIConstants {
    public static Dimension defaultFillerDim(int height){
        return new Dimension(5, ((height - 400 - 25) / 3));
    }
    public static Button defaultShowParticipantsButton(ActionListener actionListener) {
        return new Button("All Participants", Color.black, new Dimension(300, 100), new Dimension(300, 100), new Dimension(300, 100), new Font("Arial", Font.BOLD, 20), actionListener);
        }
    public static Button defaultExitButton(ActionListener actionListener){
        return new Button("Exit", Color.black, new Dimension(300, 100), new Dimension(300, 100), new Dimension(300, 100), new Font("Arial", Font.BOLD, 20), actionListener);
    }
    public static Button defaultBackButton(ActionListener actionListener)
    {
        return new Button("Return", Color.black, new Dimension(300, 100), new Dimension(300, 100), new Dimension(300, 100), new Font("Arial", Font.BOLD, 20), actionListener);
    }
    public static Button defaultRefreshButton(ActionListener actionListener)
    {
        return new Button("Refresh", Color.black, new Dimension(300, 100), new Dimension(300, 100), new Dimension(300, 100), new Font("Arial", Font.BOLD, 20), actionListener);
    }
    public static Button defaultConnectButton(ActionListener actionListener)
    {
        return new Button("Connect", Color.black, new Dimension(300, 100), new Dimension(300, 100), new Dimension(300, 100), new Font("Arial", Font.BOLD, 20), actionListener);
    }

    public static JPanel defaultPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.GRAY);
        panel.setVisible(false);
        return panel;
    }
    public static JTextField defaultTextFieldInput(ClientGUI clientGUI)
    {
        JTextField input = new JTextField(50);
        input.setToolTipText("Type your message here");
        input.setFont(new Font("Dialog", Font.PLAIN, 14));
        input.addActionListener(e -> {
            if (input.getText().length() > 0) {
                clientGUI.sendMessage(input.getText());
                input.setText("");
            }
        });
        return input;
    }
    public static JButton defaultSendButton(JTextField input, ClientGUI clientGUI)
    {
        JButton sendButton = new JButton("Send");
        sendButton.addActionListener(e -> {
            if (input.getText().length() > 0) {
                clientGUI.sendMessage(input.getText());
                input.setText("");
            }
        });
        return sendButton;
    }
    public static JTextArea defaultTextArea()
    {
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setTabSize(10);
        textArea.setRows(10);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBackground(Color.GRAY);
        textArea.setFont(new Font("Dialog", Font.PLAIN, 16));
        textArea.setForeground(Color.BLACK);
        textArea.setText("SOME TEXT");
        return textArea;
    }
    public static JTextArea defaultInfoTextArea()
    {
        JTextArea info = new JTextArea();
        info.setAlignmentX(Component.CENTER_ALIGNMENT);
        info.setAlignmentY(Component.CENTER_ALIGNMENT);
        info.setEditable(false);
        info.setRows(1);
        info.setWrapStyleWord(true);
        info.setBackground(Color.WHITE);
        info.setFont(new Font("Dialog", Font.PLAIN, 20));
        info.setForeground(Color.BLACK);
        return info;
    }
    public static String loginDialogName = "Login";
    public static String yourNameIsText(String userName)
    {
        return "Your user name is : " + userName;
    }
    public static String hostLabelText = "Set host: ";
    public static String portLabelText = "Set port: ";
    public static String usernameLabelText = "Set username: ";
    public static String loginButtonText = "Confirm";
    public static String exitButtonText = "Exit";
    public static int defaultNumberColumnsForField = 20;
    public static Button defaultParticipantsButton(JPanel table, String newDatum)
    {
        Button button = new Button(newDatum, Color.black, new Dimension(table.getWidth() - 30, 100), new Dimension(table.getWidth() - 30, 100), new Dimension(table.getWidth() - 30, 100), new Font("Arial", Font.BOLD, 20), null);
        button.getButton().setVerticalAlignment(0);
        button.getButton().setBackground(Color.BLUE);
        button.getButton().setVisible(true);
        return button;
    }
    public static GridBagConstraints gridBagConstraintsForHostLabel()
    {
        GridBagConstraints cs = new GridBagConstraints();
        cs.fill = GridBagConstraints.HORIZONTAL;
        cs.gridx = 0;
        cs.gridy = 0;
        cs.gridwidth = 1;
        return cs;
    }
    public static GridBagConstraints gridBagConstraintsForHostField()
    {
        GridBagConstraints cs = new GridBagConstraints();
        cs.fill = GridBagConstraints.HORIZONTAL;
        cs.gridx = 1;
        cs.gridy = 0;
        cs.gridwidth = 2;
        return cs;
    }
    public static GridBagConstraints gridBagConstraintsForPortLabel()
    {
        GridBagConstraints cs = new GridBagConstraints();
        cs.fill = GridBagConstraints.HORIZONTAL;
        cs.gridx = 0;
        cs.gridy = 1;
        cs.gridwidth = 1;
        return cs;
    }
    public static GridBagConstraints gridBagConstraintsForPortField()
    {
        GridBagConstraints cs = new GridBagConstraints();
        cs.fill = GridBagConstraints.HORIZONTAL;
        cs.gridx = 1;
        cs.gridy = 1;
        cs.gridwidth = 2;
        return cs;
    }
    public static GridBagConstraints gridBagConstraintsForUsernameLabel()
    {
        GridBagConstraints cs = new GridBagConstraints();
        cs.fill = GridBagConstraints.HORIZONTAL;
        cs.gridx = 0;
        cs.gridy = 2;
        cs.gridwidth = 1;
        return cs;
    }
    public static GridBagConstraints gridBagConstraintsForUsernameField()
    {
        GridBagConstraints cs = new GridBagConstraints();
        cs.fill = GridBagConstraints.HORIZONTAL;
        cs.gridx = 1;
        cs.gridy = 2;
        cs.gridwidth = 2;
        return cs;
    }
    public static GridBagConstraints gridBagConstraintsForLoginButton()
    {
        GridBagConstraints cs = new GridBagConstraints();
        cs.fill = GridBagConstraints.HORIZONTAL;
        cs.gridx = 0;
        cs.gridy = 3;
        cs.gridwidth = 3;
        return cs;
    }
    public static GridBagConstraints gridBagConstraintsForExitButton()
    {
        GridBagConstraints cs = new GridBagConstraints();
        cs.fill = GridBagConstraints.HORIZONTAL;
        cs.gridx = 0;
        cs.gridy = 4;
        cs.gridwidth = 3;
        return cs;
    }
    public static int defaultGap = 5;
    public static boolean isInteger(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        if (length == 0) {
            return false;
        }
        int i = 0;
        if (str.charAt(0) == '-') {
            if (length == 1) {
                return false;
            }
            i = 1;
        }
        for (; i < length; i++) {
            char c = str.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }
}
