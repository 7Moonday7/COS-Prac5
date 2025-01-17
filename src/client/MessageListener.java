package client;

import java.io.*;
import java.net.*;

public class MessageListener implements Runnable {
    private final Socket socket;

    public MessageListener(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input))
        ) {
            String message;
            while ((message = reader.readLine()) != null) {
                System.out.println("Новое сообщение: " + message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
