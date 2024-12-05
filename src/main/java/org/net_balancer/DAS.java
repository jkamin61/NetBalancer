package org.net_balancer;

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
        } catch (NumberFormatException e) {
            System.err.println("Invalid port or number format");
            System.exit(1);
        }
    }
}
