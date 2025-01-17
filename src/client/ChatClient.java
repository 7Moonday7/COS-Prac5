package client;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatClient {
    private final String serverAddress;
    private final int port;

    public ChatClient(String serverAddress, int port) {
        this.serverAddress = serverAddress;
        this.port = port;
    }

    public void start() {
        try (Socket socket = new Socket(serverAddress, port)) {
            System.out.println("Подключено к серверу...");
            new Thread(new MessageListener(socket)).start();

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            Scanner scanner = new Scanner(System.in);

            while (true) {
                String message = scanner.nextLine();
                out.println(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
