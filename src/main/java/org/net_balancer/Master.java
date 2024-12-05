package org.net_balancer;

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
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
