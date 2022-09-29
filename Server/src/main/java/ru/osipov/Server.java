package ru.osipov;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
    private final Map<Integer, Client> clients = new ConcurrentHashMap<>();

    public Server() {

        int idClient = 1;

        try (ServerSocket serverSocket = new ServerSocket(Settings.PORT)) { // серверсокет прослушивает порт PORT
            Logger.INSTANCE.log("Сервер запущен!");
            System.out.println("Сервер запущен!");
            while (true) {
                try {
                    Socket socket = serverSocket.accept(); //accept() будет ждать пока кто-нибудь не захочет подключиться
                    ClientThread clientThread = new ClientThread(socket, this, idClient++);
                    clientThread.start(); //запустим текущий сеанс "общения" клиента в отдельном потоке
                } catch (IOException ex) {
                    Logger.INSTANCE.log(Arrays.toString(ex.getStackTrace()) + " " + ex.getMessage());
                }
            }
        } catch (IOException ex) {
            Logger.INSTANCE.log(Arrays.toString(ex.getStackTrace()) + " " + ex.getMessage());
        }

        Logger.INSTANCE.log("Сервер остановлен");
    }

    public Map<Integer, Client> getClients() {
        return clients;
    }

    public void sendMessageAll(Message msg) {
        clients.forEach((key, value) ->
                value.getClientThread().sendMessage(msg)
        );
    }
}
