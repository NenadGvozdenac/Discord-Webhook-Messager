package com.discordwebhookmessagesender;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import club.minnced.discord.webhook.WebhookClientBuilder;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;

public class MainFrame extends JPanel {
    private JTextArea webhookInput;
    private JMenuBar menuBar;
    private JButton sendButton;
    private JLabel jcomp4;
    private JTextField jcomp5;
    private JLabel jcomp6;

    public static File fileForSending;

    private String github = "https://github.com/NenadGvozdenac/Discord-Webhook-Messager";

    public MainFrame() {
        //construct preComponents
        JMenu exitMenu = new JMenu ("Exit");
        JMenu fileMenu = new JMenu ("File");
        JMenu informationMenu = new JMenu ("Information");

        JMenuItem githubItem = new JMenuItem ("Github");
        informationMenu.add (githubItem);
        githubItem.addActionListener(listener -> {
            Desktop desktop = Desktop.getDesktop();

            try {
                desktop.browse(new URI(github));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        });
        
        JMenuItem aboutItem = new JMenuItem ("About");
        informationMenu.add (aboutItem);
        aboutItem.addActionListener(listener -> {
            JOptionPane.showMessageDialog(null, "This tool was designed by Nenad Gvozdenac.", "Information", JOptionPane.INFORMATION_MESSAGE);
        });

        //construct components
        webhookInput = new JTextArea (5, 1);
        menuBar = new JMenuBar();
        menuBar.add (fileMenu);
        menuBar.add (informationMenu);
        menuBar.add (exitMenu);

        sendButton = new JButton ("SEND");
        jcomp4 = new JLabel ("Enter the text for sending...");
        jcomp5 = new JTextField (5);
        jcomp6 = new JLabel ("Enter name of the webhook...");

        sendButton.addActionListener(listener -> {
            String url = App.token.getToken();
            WebhookClientBuilder builder = new WebhookClientBuilder(url);

            WebhookMessageBuilder messageBuilder = new WebhookMessageBuilder();
            messageBuilder.setUsername(jcomp5.getText());
            messageBuilder.setAvatarUrl("https://www.compassscicomm.org/wp-content/uploads/2020/04/tools-and-resources@2x-1.png");
            messageBuilder.setContent(webhookInput.getText());

            try {
                messageBuilder.addFile(fileForSending);
                builder.build().send(messageBuilder.build());
            } catch(NullPointerException e) {
                builder.build().send(messageBuilder.build());
            }

            MainFrame.fileForSending = null;
        });

        fileMenu.addMenuListener(new FileChooserMenuListener());
        exitMenu.addMenuListener(new ExitMenuListener());

        //set components properties
        webhookInput.setLineWrap(true);
        webhookInput.setWrapStyleWord(true);
        webhookInput.setToolTipText ("Enter the message...");

        //adjust size and set layout
        setPreferredSize (new Dimension (414, 222));
        setLayout (null);

        //add components
        add (webhookInput);
        add (menuBar);
        add (sendButton);
        add (jcomp4);
        add (jcomp5);
        add (jcomp6);

        //set component bounds (only needed by Absolute Positioning)
        webhookInput.setBounds (5, 65, 215, 150);
        menuBar.setBounds (0, 0, 505, 25);
        sendButton.setBounds (225, 185, 185, 30);
        jcomp4.setBounds (5, 35, 215, 25);
        jcomp5.setBounds (225, 65, 185, 30);
        jcomp6.setBounds (225, 35, 185, 25);
    }
}

class FileChooserMenuListener implements MenuListener {

    @Override
    public void menuCanceled(MenuEvent arg0) {
        
    }

    @Override
    public void menuDeselected(MenuEvent arg0) {
        
    }

    @Override
    public void menuSelected(MenuEvent arg0) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.showOpenDialog(null);
        MainFrame.fileForSending = fileChooser.getSelectedFile();
    }
}

class ExitMenuListener implements MenuListener {

    @Override
    public void menuCanceled(MenuEvent arg0) {
        
    }

    @Override
    public void menuDeselected(MenuEvent arg0) {
        
    }

    @Override
    public void menuSelected(MenuEvent arg0) {
        JOptionPane.showMessageDialog(null, "Thank you for using this tool.", "See you next time!", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }
    
}