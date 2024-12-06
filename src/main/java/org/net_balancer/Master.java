package org.net_balancer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.List;

public class Master {
    private final DatagramSocket socket;
    private final int initialNumber;
    private final List<Integer> receivedNumbers;

    public Master(DatagramSocket socket, int initialNumber) {
        this.socket = socket;
        this.initialNumber = initialNumber;
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
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void broadcastAverage() throws IOException {
        double average = receivedNumbers.stream()
                .filter(num -> num != 0 && num != -1)
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0.0);
        System.out.println("Average: " + average);
    }
}
