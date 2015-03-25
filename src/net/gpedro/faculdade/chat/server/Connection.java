package net.gpedro.faculdade.chat.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

public class Connection extends Thread {
    DataInputStream in;
    DataOutputStream out;
    Socket clientSocket;

    public Connection(Socket aClientSocket) {
        try {
            clientSocket = aClientSocket;
            in = new DataInputStream(clientSocket.getInputStream());
            out = new DataOutputStream(clientSocket.getOutputStream());
            this.start();
        } catch (IOException e) {
            System.out.println("Connection:" + e.getMessage());
        }
    }

    public void sendMessage(String message) {
        try {
            out = new DataOutputStream(clientSocket.getOutputStream());
            out.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isConnected() {
        return clientSocket.isConnected();
    }

    public void close() {
        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try { // an echo server
            String data = in.readUTF(); // read a line of data from the stream
            // out.writeUTF(data);
            for (Connection con : ChatServer.connections) {
                if (con.isConnected()) {
                    con.sendMessage(data);
                } else {
                    ChatServer.connections.remove(con);
                }
            }
        } catch (EOFException e) {
            System.out.println("EOF:" + e.getMessage());
        } catch (IOException e) {
            ChatServer.connections.remove(this);
            System.out.println("readline:" + e.getMessage());
        }
    }
}