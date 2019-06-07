package org.hillel.networking;

public class ApplicationServer {
    public static void main(String[] args) {

        Server server = new Server(ApplicationConstants.APPLICATION_DEMO_PORT);
        server.serverChatting();
    }
}
