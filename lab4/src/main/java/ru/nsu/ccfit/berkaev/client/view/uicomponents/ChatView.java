package ru.nsu.ccfit.berkaev.client.view.uicomponents;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import ru.nsu.ccfit.berkaev.client.view.ClientGUI;
import ru.nsu.ccfit.berkaev.constants.ClientGUIConstants;

public class ChatView {

    private final JPanel panel;
    private final JTextArea textArea;
    private final JTextArea info;

    public ChatView(ClientGUI clientGUI) {
        JTextField input = ClientGUIConstants.defaultTextFieldInput(clientGUI);
        JButton textEnter = ClientGUIConstants.defaultSendButton(input, clientGUI);
        JPanel textInput = new JPanel(new FlowLayout());
        textInput.add(input);
        textInput.add(textEnter);
        panel = ClientGUIConstants.defaultPanel();
        panel.add(textInput, BorderLayout.SOUTH);
        textArea = ClientGUIConstants.defaultTextArea();
        info = ClientGUIConstants.defaultInfoTextArea();
        panel.add(textArea, BorderLayout.CENTER);
        panel.add(info, BorderLayout.NORTH);
    }

    public JPanel getTable() {
        return this.panel;
    }

    public void setUserName(String userName) {
        info.setText(ClientGUIConstants.yourNameIsText(userName));
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
