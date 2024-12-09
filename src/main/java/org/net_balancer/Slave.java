package org.net_balancer;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Slave {
    private final int masterPort;
    private final double number;

    public Slave(int masterPort, double number) {
        this.masterPort = masterPort;
        this.number = number;
    }
    public void run() {
        try (DatagramSocket socket = new DatagramSocket()) {

            InetAddress localHost = InetAddress.getByName("localhost");
            String message = String.valueOf(number);

            DatagramPacket packet = new DatagramPacket(
                    message.getBytes(),
                    message.length(),
                    localHost,
                    masterPort
            );
            socket.send(packet);
            System.out.println("Sent number " + number + " to master on port " + masterPort);
        } catch (Exception e) {
            System.err.println("Error in Slave: " + e.getMessage());
        }
    }
}
