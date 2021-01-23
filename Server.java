package Server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

    public static void main(String[] args) {
        Server server = new Server();
    }

    public Server() {

        // розетка сервера
        ServerSocket server = null; // иницилизация локальной перемннной, так что пишем нолик
        // поток, который будет подключаться к серверу
        Socket socket = null;

        try {
            // порт, наше сервера
            server = new ServerSocket(8888);
            System.out.println("Сервер готов...");
            socket = server.accept();
            System.out.println("Клиент подключился...");
            // входящий поток
            Scanner in =  new Scanner(socket.getInputStream());
            // исходящий поток
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            // возможность ввода с консоли
            Scanner sc = new Scanner(System.in);

            // отправляем в этом потоке сообщение от сервера
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        System.out.println("Сервер, напиши сообщенеи");
                        String msg = sc.nextLine();
                        System.out.println("Сообщенеи отправлено");
                        out.println(msg);
                    }
                }
            }).start();
            // в главном потоке получаем сообщение
            while (true) {
                String msg = in.nextLine();
                if (msg.equals("/end")) break;
                System.out.println("Клиент: " + msg);

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close(); // закрываем поток клиента
                server.close(); // закрываем поток сервера
                System.out.println("Сервер закрыт");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
