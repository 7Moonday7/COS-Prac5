package server;

import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
    private final int port;
    private final Set<ClientHandler> clients = Collections.synchronizedSet(new HashSet<>());
    private final File clientsFile = new File("server/clients.txt");

    public ChatServer(int port) {
        this.port = port;
    }

    public void start() {
        System.out.println("Сервер запущен...");
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket, this);
                clients.add(clientHandler);
                saveClient(clientSocket.getInetAddress().toString());
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void broadcast(String message, ClientHandler sender) {
        synchronized (clients) {
            for (ClientHandler client : clients) {
                if (client != sender) {
                    client.sendMessage(message);
                }
            }
        }
    }

    public void removeClient(ClientHandler clientHandler) {
        clients.remove(clientHandler);
    }

    private void saveClient(String clientInfo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(clientsFile, true))) {
            writer.write(clientInfo);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
