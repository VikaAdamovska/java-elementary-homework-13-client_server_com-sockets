package org.hillel.networking;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class Client implements IClient {

    private final int serverPort;

    private final String  host;

    public Client(int serverPort, String host) {
        this.serverPort = serverPort;
        this.host = host;
    }

    @Override
    public void clientChatting() {

        try (
                DatagramSocket serverSocket = new DatagramSocket();
        ) {
            Scanner scanner = new Scanner(System.in);
            InetAddress inetAddress = InetAddress.getByName(host);
            while (true) {
                byte[] sendBuffer = new byte[1024];
                byte[] receiveBuffer = new byte[1024];

                System.out.println("\r\nClient: ");
                String clientMessage = scanner.nextLine();
                sendBuffer = clientMessage.getBytes();

                DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, inetAddress, serverPort);
                serverSocket.send(sendPacket);

                if (clientMessage.equalsIgnoreCase(ApplicationConstants.EXIT_COMMAND)) {
                    System.out.println("Connection ended by client");
                    break;
                }

                DatagramPacket receivePocket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                serverSocket.receive(receivePocket);
                String serverMessage = new String(receivePocket.getData());
                System.out.println("\r\nServer: " + "\r\n" + serverMessage);
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
