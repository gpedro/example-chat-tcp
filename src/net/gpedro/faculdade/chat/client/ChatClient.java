package net.gpedro.faculdade.chat.client;

import java.util.Scanner;

public class ChatClient {

    private static boolean firstConnection = true;

    public static void main(String args[]) {

        Scanner sc = new Scanner(System.in);
        ConnectionListener cl = new ConnectionListener();

        while (cl.isConnected()) {
            if (firstConnection) {
                cl.sendMessage(args[0] + " conectou-se");
                firstConnection = false;
            }

            String mensagem = sc.next();
            if (!mensagem.isEmpty()) {
                cl.sendMessage(args[0] + ": " + mensagem);
            } else {
                System.out.println("Mensagem não enviada!");
            }
        }
    }
}