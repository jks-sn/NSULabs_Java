package client.view.uicomponents;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import client.view.ClientGUI;

import static constants.ClientGUIConstants.*;

public class ChatView {

    private final JPanel panel;
    private final JTextArea textArea;
    private final JTextArea info;

    public ChatView(ClientGUI clientGUI) {
        JTextField input = defaultTextFieldInput(clientGUI);
        JButton textEnter = defaultSendButton(input, clientGUI);
        JPanel textInput = new JPanel(new FlowLayout());
        textInput.add(input);
        textInput.add(textEnter);
        panel = defaultPanel();
        panel.add(textInput, BorderLayout.SOUTH);
        textArea = defaultTextArea();
        info = defaultInfoTextArea();
        panel.add(textArea, BorderLayout.CENTER);
        panel.add(info, BorderLayout.NORTH);
    }

    public JPanel getTable() {
        return this.panel;
    }

    public void setUserName(String userName) {
        info.setText(yourNameIsText(userName));
    }

    public void repaintChat(ArrayList<Object> newChatList) {
        textArea.setText("");
        for (Object o : newChatList) {
            String s = (String) o;
            String[] arr = s.split(",");
            for (String value : arr) {
                textArea.append(value);
                textArea.append("       ");
            }
            textArea.append("\n");
        }
    }
}
