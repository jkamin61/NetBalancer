package org.net_balancer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class Master {
    private final DatagramSocket socket;
    private final double initialNumber;
    private final int port;
    private final List<Double> receivedNumbers;

    public Master(DatagramSocket socket, double initialNumber, int port) {
        this.socket = socket;
        this.initialNumber = initialNumber;
        this.port = port;
        this.receivedNumbers = new ArrayList<>();
        this.receivedNumbers.add(initialNumber);
    }

    public void run() {
        try {
            byte[] buffer = new byte[256];
            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

                String message = new String(packet.getData(), 0, packet.getLength());
                double receivedNumber = Double.parseDouble(message);

                if (receivedNumber == 0) {
                    broadcastAverage();
                } else if (receivedNumber == -1) {
                    broadcastExit();
                    break;
                } else {
                    System.out.println("Received: " + receivedNumber + " from Slave");
                    receivedNumbers.add(receivedNumber);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void broadcastAverage() throws IOException {
        double average = receivedNumbers.stream()
                .filter(num -> num != 0 && num != -1)
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);
        System.out.println("Average: " + average);
        String message = String.valueOf(average);
        broadcastMessage("Average: "+message);
    }

    private void broadcastExit() throws IOException {
        System.out.println("Exiting...");
        broadcastMessage("-1");
    }

    private void broadcastMessage(String message) throws IOException {
        InetAddress broadcastAddress = InetAddress.getByName("255.255.255.255");
        byte[] data = message.getBytes();
        DatagramPacket packet = new DatagramPacket(data, data.length, broadcastAddress, port);
        socket.send(packet);
    }
}
