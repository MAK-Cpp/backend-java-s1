package edu.hw6.tasks.task6;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.Formatter;
import java.util.Map;

public final class PortScanner {
    private static final int[] PORTS =
        new int[] {135, 137, 138, 139, 445, 843, 1900, 3702, 5040, 5050, 5353, 5355, 5939, 6463, 6942, 17500, 17500,
            17600, 27017, 42420};
    private static final Map<Integer, String> KNOWN_PORTS = Map.ofEntries(
        Map.entry(80, "HTTP (HyperText Transfer Protocol)"),
        Map.entry(21, "FTP (File Transfer Protocol)"),
        Map.entry(25, "SMTP (Simple Mail Transfer Protocol)"),
        Map.entry(22, "SSH (Secure Shell)"),
        Map.entry(443, "HTTPS (HyperText Transfer Protocol Secure)"),
        Map.entry(53, "DNS (Domain Name System)"),
        Map.entry(3306, "MySQL Database"),
        Map.entry(5432, "PostgreSQL Database"),
        Map.entry(3389, "Remote Desktop Protocol (RDP)"),
        Map.entry(27017, "MongoDB Database"),
        Map.entry(1521, "Oracle Database"),
        Map.entry(23, "Telnet protocol to ensure effective communication along with the remote server."),
        Map.entry(110, "Post Office Protocol version 3 for email retrieval."),
        Map.entry(143, "Internet Message Access Protocol for email retrieval."),
        Map.entry(123, "Network Time Protocol for time synchronization."),
        Map.entry(445, "Server Message Block protocol for file sharing and printer sharing."),
        Map.entry(548, "Apple Filing Protocol for file sharing between Macs."),
        Map.entry(8080, "HTTP proxy server."),
        Map.entry(1080, "SOCKS proxy server."),
        Map.entry(1433, "Microsoft SQL Server database server."),
        Map.entry(5722, "SMB version 2 protocol."),
        Map.entry(500, "Internet Key Exchange protocol for VPN connections."),
        Map.entry(1701, "Layer 2 Tunneling Protocol for VPN connections."),
        Map.entry(1723, "Point-to-Point Tunneling Protocol for VPN connections."),
        Map.entry(3128, "HTTPS proxy server."),
        Map.entry(5900, "Virtual Network Computing for remote access."),
        Map.entry(5353, "Bonjour protocol for network discovery and communication."),
        Map.entry(2049, "Network File System for file sharing."),
        Map.entry(6379, "Redis key-value store."),
        Map.entry(11211, "Memcached distributed memory caching system."),
        Map.entry(873, "Remote synchronization for file transfers.")
    );

    private PortScanner() {
    }

    public static String centerString(int width, String s) {
        return String.format("%-" + width + "s", String.format("%" + (s.length() + (width - s.length()) / 2) + "s", s));
    }

    @SuppressWarnings("checkstyle:RegexpSinglelineJava") public static void scan() {
        Formatter formatter = new Formatter();
        formatter.format("%8s %-5s %6s\n", "Протокол", "Порт", "Сервис");
        final String tcp = centerString(8, "TCP");
        final String udp = centerString(8, "UDP");
        final String freePort = "%-8s %5s\n";
        final String notFreePort = "%-8s %5s %6s\n";
        for (int i : PORTS) {
            InetSocketAddress address;
            try {
                address = new InetSocketAddress(InetAddress.getByName(null), i);
            } catch (UnknownHostException e) {
                throw new RuntimeException(e);
            }
            try (ServerSocket serverSocket = new ServerSocket()) {
                serverSocket.bind(address);
                formatter.format(freePort, tcp, i);
            } catch (Exception e) {
                if (KNOWN_PORTS.containsKey(i)) {
                    formatter.format(notFreePort, tcp, i, KNOWN_PORTS.get(i));
                }
            }
            try (DatagramSocket datagramSocket = new DatagramSocket()) {
                datagramSocket.bind(address);
                formatter.format(freePort, udp, i);
            } catch (Exception e) {
                if (KNOWN_PORTS.containsKey(i)) {
                    formatter.format(notFreePort, udp, i, KNOWN_PORTS.get(i));
                }
            }
        }
        System.out.println(formatter);
    }
}
