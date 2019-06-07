package org.hillel.networking;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Scanner;

public class Server implements IServer {

    public final int serverPort;

    public Server(int serverPort) {
        this.serverPort = serverPort;
    }

    @Override
    public void serverChatting() {
        try (
                DatagramSocket serverSocked = new DatagramSocket(serverPort);
        ) {
            System.out.println("Server started on port " + serverPort);
            Scanner scanner = new Scanner(System.in);
            while (true) {
                byte[] sendBuffer = new byte[1024];
                byte[] receiveBuffer = new byte[1024];

                DatagramPacket receivePocket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                serverSocked.receive(receivePocket);
                InetAddress inetAddress = receivePocket.getAddress();
                int port = receivePocket.getPort();
                String clientMessage = new String(receivePocket.getData());
                System.out.println("\r\nMessage from client: " + clientMessage + "\r\nclient address - " + inetAddress + ", " + " port - " + port);
                System.out.println("\r\nServer: ");

                String serverMessage = scanner.nextLine();
                sendBuffer = serverMessage.getBytes();
                DatagramPacket sendPocket = new DatagramPacket(sendBuffer, sendBuffer.length, inetAddress, port);
                serverSocked.send(sendPocket);

                if (serverMessage.equalsIgnoreCase(ApplicationConstants.EXIT_COMMAND)) {
                    System.out.println("Connection ended by server");
                    break;
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}