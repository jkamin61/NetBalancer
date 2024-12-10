package org.net_balancer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class Master {
    private final DatagramSocket socket;
    private final int initialNumber;
    private final int port;
    private final List<Integer> receivedNumbers;

    public Master(DatagramSocket socket, int initialNumber, int port) {
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
                int receivedNumber = Integer.parseInt(message);

                if (receivedNumber == 0) {
                    broadcastAverage();
                } else if (receivedNumber == -1) {
                    broadcastExit();
                    break;
                } else {
                    System.out.println("Received: " + receivedNumber);
                    receivedNumbers.add(receivedNumber);
                    System.out.println(receivedNumbers);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void broadcastAverage() throws IOException {
        double sum = 0;
        int count = 0;
        for (int num : receivedNumbers) {
            if (num != 0 && num != -1) {
                sum += num;
                count++;
            }
        }
        double average = (count > 0) ? (sum / count) : 0.0;
        System.out.println("Average: " + average);
        String message = String.valueOf((int) average);
        broadcastMessage(message);
    }

    private void broadcastExit() throws IOException {
        System.out.println("Exiting...");
        broadcastMessage("-1");
    }

    private void broadcastMessage(String message) throws IOException {
        InetAddress broadcastAddress = InetAddress.getByName("255.255.255.255");
        byte[] data = message.getBytes();
        int targetPort = 60000;
        DatagramPacket packet = new DatagramPacket(data, data.length, broadcastAddress, targetPort);
        socket.send(packet);
    }
}
