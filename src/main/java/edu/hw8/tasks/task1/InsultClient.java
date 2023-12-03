package edu.hw8.tasks.task1;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class InsultClient implements Closeable {
    private final Socket server;
    private final PrintWriter out;
    private final BufferedReader in;

    public InsultClient(InetAddress address, int port) throws IOException {
        this.server = new Socket(address, port);
        this.in = new BufferedReader(new InputStreamReader(server.getInputStream()));
        this.out = new PrintWriter(server.getOutputStream(), true);
    }

    public String get(final String word) throws IOException {
        out.println(word);
        out.flush();
        return in.readLine();
    }

    @Override
    public void close() throws IOException {
        server.close();
        in.close();
        out.close();
    }
}
