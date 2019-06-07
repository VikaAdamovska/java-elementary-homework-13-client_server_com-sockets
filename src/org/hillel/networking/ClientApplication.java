package org.hillel.networking;

public class ClientApplication {
    public static void main(String[] args) {

        Client client = new Client(ApplicationConstants.APPLICATION_DEMO_PORT, "127.0.0.1");
        client.clientChatting();

    }
}
