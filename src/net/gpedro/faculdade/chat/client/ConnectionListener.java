package net.gpedro.faculdade.chat.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ConnectionListener extends Thread {

    private static Socket socket;

    private String ip = "localhost";
    private final int serverPort = 7896;

    private DataOutputStream out;
    private DataInputStream in;

    public List<String> queue;

    public ConnectionListener() {

        queue = new ArrayList<String>();

        try {
            socket = new Socket(ip, serverPort);
            socket.setKeepAlive(true);

        } catch (IOException e) {
            e.printStackTrace();
        }

        this.start();
    }

    @Override
    public void run() {
        while (socket != null && socket.isConnected()) {
            try {
                for (String message : queue) {
                    out = new DataOutputStream(socket.getOutputStream());
                    out.writeUTF(message);
                }
                queue.clear();
                
                in = new DataInputStream(socket.getInputStream());
                while (in.available() > 0) {
                    System.out.println("[Recebida] " + in.readUTF());
                }


                sleep(1000); // meio segundo: 0.5
            } catch (InterruptedException e) {
                System.out.println("Thread foi interrompida.");
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessage(String mensagem) {

        if (!mensagem.isEmpty()) {
            queue.add(mensagem);
        }

    }

    public boolean isConnected() {
        return socket.isConnected();
    }

}
