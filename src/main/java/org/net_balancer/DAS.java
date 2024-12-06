package org.net_balancer;

import java.net.DatagramSocket;
import java.net.SocketException;

public class DAS {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Usage: java DAS <port> <number>");
            System.exit(1);
        }
        int port;
        int number;
        try {
            port = Integer.parseInt(args[0]);
            number = Integer.parseInt(args[1]);

            try (DatagramSocket socket = new DatagramSocket(port)) {
                System.out.println("Entering Master mode...");
                Master master = new Master(socket, number, port);
                master.run();
            } catch (SocketException e) {
                System.out.println("Entering Slave mode...");
                Slave slave = new Slave(port, number);
                slave.run();
            }
        } catch (NumberFormatException e) {
            System.err.println("Invalid port or number format");
            System.exit(1);
        }
    }
}
