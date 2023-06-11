package ru.nsu.ccfit.berkaev.client.view;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import ru.nsu.ccfit.berkaev.client.Client;
import ru.nsu.ccfit.berkaev.client.view.uicomponents.ChatMenu;
import ru.nsu.ccfit.berkaev.client.view.uicomponents.ChatView;
import ru.nsu.ccfit.berkaev.client.view.uicomponents.DefaultMenu;
import ru.nsu.ccfit.berkaev.client.view.uicomponents.MainMenu;
import ru.nsu.ccfit.berkaev.client.view.uicomponents.ParticipantsView;

import java.awt.*;
import java.util.ArrayList;

import static ru.nsu.ccfit.berkaev.constants.ClientConstants.CLIENT_GUI_NAME;

public class ClientGUI {

    private final JFrame mainFrame;
    private final JPanel mainWindow;

    private final JPanel actionWindow;
    private final JPanel choiceWindow;

    private final MainMenu mainMenu;
    private final ChatMenu chatMenu;

    private final ChatView chatView;
    private final DefaultMenu defaultMenu;
    private final ParticipantsView participantsView;

    private final Client client;

    public ClientGUI(Client client) {

        this.client = client;
        mainFrame = getJFrame();
        mainWindow = new JPanel();
        mainFrame.add(mainWindow);
        mainWindow.setLayout(new BorderLayout());

        actionWindow = new JPanel();
        choiceWindow = new JPanel();
        
        mainMenu = new MainMenu(mainFrame);
        mainMenu.setClient(client);
        mainMenu.showMenu();

        chatMenu = new ChatMenu();
        chatMenu.setClient(client);

        actionWindow.setBackground(Color.GRAY);
        actionWindow.setLayout(new BorderLayout());
        
        choiceWindow.setBackground(Color.YELLOW);
        choiceWindow.setLayout(new BorderLayout());
        choiceWindow.add(mainMenu.getMenu(), BorderLayout.CENTER);

        mainWindow.add(actionWindow, BorderLayout.CENTER);
        mainWindow.add(choiceWindow, BorderLayout.WEST);

        chatView = new ChatView(this);
        defaultMenu = new DefaultMenu();
        defaultMenu.setGUI(this);
        defaultMenu.setClient(client);
        participantsView = new ParticipantsView();

        mainWindow.revalidate();
        mainFrame.revalidate();
        actionWindow.revalidate();
        choiceWindow.revalidate();
    }
    
    private JFrame getJFrame() {
        JFrame jframe = new JFrame() {};
        jframe.setVisible(true);
        jframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dm = toolkit.getScreenSize();

        jframe.setLocation(0, 0);
        jframe.setSize(dm.width, dm.height);
        jframe.setTitle(CLIENT_GUI_NAME);
        return  jframe;
    }

    public void openChat() {
        choiceWindow.remove(mainMenu.getMenu());
        choiceWindow.add(chatMenu.getMenu());
        chatMenu.showMenu();

        actionWindow.removeAll();
        actionWindow.add(chatView.getTable());
        chatView.setUserName(client.getUserName());
        chatView.getTable().setVisible(true);

        JScrollPane scrollPane = new JScrollPane(chatView.getTable(),   ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(actionWindow.getWidth(), actionWindow.getHeight()));
        actionWindow.add(scrollPane, BorderLayout.EAST);
        
        actionWindow.revalidate();
        actionWindow.repaint();
    }

    public void closeChat(){
        if (this.chatMenu.getMenu().isVisible()){
            chatMenu.hideMenu();
        }
        choiceWindow.removeAll();
        choiceWindow.add(mainMenu.getMenu());
        choiceWindow.revalidate();
        choiceWindow.repaint();

        actionWindow.removeAll();
        actionWindow.setBackground(Color.GRAY);
        actionWindow.revalidate();
        actionWindow.repaint();
        
        mainWindow.revalidate();
        mainWindow.repaint();

    }

    public void showParticipants(ArrayList<Object> newData) {
        choiceWindow.remove(chatMenu.getMenu());
        choiceWindow.add(defaultMenu.getMenu());
        defaultMenu.getMenu().setVisible(true);
        choiceWindow.revalidate();
        choiceWindow.repaint();

        actionWindow.removeAll();
        participantsView.getTable().setSize(actionWindow.getWidth(), actionWindow.getHeight());
        actionWindow.add(participantsView.getTable(), BorderLayout.CENTER);
        participantsView.printTable(newData);
        actionWindow.revalidate();
        actionWindow.repaint();
        
        mainWindow.revalidate();
        mainWindow.repaint();
    }

    public void returnToPrevView(){

        choiceWindow.removeAll();
        choiceWindow.add(chatMenu.getMenu());
        choiceWindow.revalidate();
        choiceWindow.repaint();

        actionWindow.removeAll();
        actionWindow.add(chatView.getTable());
        actionWindow.revalidate();
        actionWindow.repaint();
        
        mainWindow.revalidate();
        mainWindow.repaint();

    }

    public void sendMessage(String message) {
        client.sendChatMessageToServer(message);
    }

    public void displayMessages(ArrayList<Object> messages) {
        chatView.repaintChat(messages);
    }

    public void displayError(String err) {
        JOptionPane.showMessageDialog(mainFrame, err);
    }
}
