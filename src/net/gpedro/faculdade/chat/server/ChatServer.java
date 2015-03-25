package net.gpedro.faculdade.chat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {

    private static int maxConnections = 5;

    public static List<Connection> connections;

    public static void main(String args[]) {
        try {
            connections = new ArrayList<Connection>();

            int serverPort = 7896; // the server port
            ServerSocket listenSocket = new ServerSocket(serverPort,
                    maxConnections);
            while (true) {
                Socket clientSocket = listenSocket.accept();
                clientSocket.setSoTimeout(1000);
                Connection c = new Connection(clientSocket);

                if (!connections.contains(c)) {
                    connections.add(c);
                } else {
                    c.sendMessage("Restaurando conexão.");
                }
            }
        } catch (IOException e) {
            System.out.println("Listen socket:" + e.getMessage());
        }
    }
}
