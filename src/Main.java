import server.ChatServer;
import client.ChatClient;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Выберите режим работы:");
        System.out.println("1. Сервер");
        System.out.println("2. Клиент");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Пропуск новой строки

        if (choice == 1) {
            new ChatServer(12345).start(); // Сервер запускается на порту 12345
        } else if (choice == 2) {
            System.out.print("Введите адрес сервера: "); //Для локальной машины 127.0.0.1
            String serverAddress = scanner.nextLine();
            new ChatClient(serverAddress, 12345).start();
        } else {
            System.out.println("Неверный выбор. Завершение работы.");
        }
    }
}
