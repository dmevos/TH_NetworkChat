package ru.osipov;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

public class Client {
    public Client() {
        Logger.INSTANCE.log("Клинет запущен!");

        try (Socket socket = new Socket(Settings.SERVER_NAME, Settings.PORT); //Запрашиваем у сервера доступ на соединение
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader((new InputStreamReader(socket.getInputStream())));
             Scanner scanner = new Scanner(System.in)) {
            Logger.INSTANCE.log("К серверу подключен " + socket);

            System.out.println(" Введите свое имя: ");
            String userName = scanner.nextLine();

            Message message = new Message(userName, Settings.START_MESSAGE_NEW_CLIENT, new Date());
            Gson gson = new Gson();
            String jsonMessage = gson.toJson(message);
            out.println(jsonMessage);

            System.out.println("Наслаждайтесь общением, для выхода напишите \"" + Settings.EXIT_MESSAGE + "\"");

            ListenThread listenThread = new ListenThread(in, userName);
            listenThread.start();

            while (true) {
                String printingText = scanner.nextLine();

                message = new Message(userName, printingText, new Date());
                jsonMessage = gson.toJson(message);
                out.println((jsonMessage));

                if (Settings.EXIT_MESSAGE.equals(printingText)){
                    listenThread.interrupt();
                    break;
                }
            }
        }catch (IOException ex){
            Logger.INSTANCE.log(Arrays.toString(ex.getStackTrace())+" "+ex.getMessage());
        }
        System.out.println("Клиентское приложение остановлено!");
        Logger.INSTANCE.log("Клиентское приложение остановлено!");
    }
}
