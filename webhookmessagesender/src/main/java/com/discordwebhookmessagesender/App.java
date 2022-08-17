package com.discordwebhookmessagesender;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class App extends JFrame {

    public static WebhookToken token;

    public App() throws MalformedURLException {
        try (FileReader webhookReader = new FileReader(new File("webhookToken.json"))) {

            Gson gson = new GsonBuilder()
                     .disableHtmlEscaping()
                     .setFieldNamingStrategy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                     .setPrettyPrinting()
                     .serializeNulls()
                     .create();

            java.lang.reflect.Type founderTypeSet = new TypeToken<WebhookToken>(){}.getType();
            token = gson.fromJson(webhookReader, founderTypeSet);

            System.out.println("Read token: " + token);

            webhookReader.close();
        } catch(IOException e) {

            String inputMessage = JOptionPane.showInputDialog(null, "Hello and thank you for using this program! Since this is your first time here, please input your webhook URL.", "Webhook URL", JOptionPane.INFORMATION_MESSAGE);

            try (FileWriter webhookWriter = new FileWriter(new File("webhookToken.json"))) {
                
                token = new WebhookToken(inputMessage);

                Gson gson = new GsonBuilder()
                     .disableHtmlEscaping()
                     .setFieldNamingStrategy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                     .setPrettyPrinting()
                     .serializeNulls()
                     .create();
                
                String jsonInputString = gson.toJson(token, WebhookToken.class);

                webhookWriter.write(jsonInputString);

                webhookWriter.close();
            } catch(IOException e1) {
                JOptionPane.showConfirmDialog(null, "Unfortunately. We couldn't start the program.", "ERROR", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
        }

        this.setTitle("Discord Webhook Message Sender");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().add(new MainFrame());
        this.setIconImage(new ImageIcon(new URL("https://www.compassscicomm.org/wp-content/uploads/2020/04/tools-and-resources@2x-1.png")).getImage());
        this.pack();
        this.setLocationRelativeTo(null);
        this.setAlwaysOnTop(false);
        this.setVisible(true);
    }

    public static void main( String[] args ) throws MalformedURLException {
        new App();
    }
}
