package ru.osipov;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;

public class ListenThread extends Thread {
    private final BufferedReader in;
    private final String userName;

    public ListenThread(BufferedReader in, String userName) {
        this.in = in;
        this.userName = userName;
    }

    @Override
    public void run() {
        try {
            while (!isInterrupted()) {
                String strMessage = in.readLine();

                Gson gson = new Gson();
                Message message = gson.fromJson(strMessage, Message.class);

                Logger.INSTANCE.log("{" + message.getName() + "} " + message.getText());
                if (!message.getName().equals(userName)) { //Если сообщение от самого себя - в консоль не выводим
                    System.out.println(message);
                }
            }
        } catch (IOException ex) {
            Logger.INSTANCE.log(Arrays.toString(ex.getStackTrace()) + " " + ex.getMessage());
        } finally {
            Logger.INSTANCE.log("Принятие входящих сообщений от сервера прекращено");
        }
    }
}