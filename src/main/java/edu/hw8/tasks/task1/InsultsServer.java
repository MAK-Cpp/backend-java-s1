package edu.hw8.tasks.task1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class InsultsServer {
    private static final int DEFAULT_PORT = 49100;
    private static final int THREADS = 3;
    private static final List<String> PHRASES = Collections.synchronizedList(List.of(
        "a", "b", "c",
        "Не переходи на личности там, где их нет",
        "Если твои противники перешли на личные оскорбления, будь уверена — твоя победа не за горами",
        "А я тебе говорил, что ты глупый? Так вот, я забираю свои слова обратно... Ты просто бог идиотизма.",
        "Чем ниже интеллект, тем громче оскорбления"
    ));
    private final int port;
    private final AtomicBoolean isWork = new AtomicBoolean(true);

    public InsultsServer() {
        port = DEFAULT_PORT;
    }

    public InsultsServer(int port) {
        this.port = port;
    }

    @SuppressWarnings({"checkstyle:UncommentedMain", "checkstyle:RegexpSinglelineJava"})
    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port);
             ExecutorService executorService = Executors.newFixedThreadPool(THREADS)) {
            serverSocket.setReuseAddress(true);
            System.out.println("server started!");
            while (isWork.get()) {
                Socket client = serverSocket.accept();
                executorService.submit(new ClientThread(client));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            System.out.println("server terminated");
        }
    }

    public void stop() throws IOException {
        isWork.set(false);
        Socket closingSocket = new Socket(InetAddress.getLocalHost(), port);
        closingSocket.close();
    }

    private record ClientThread(Socket client) implements Runnable {
        @SuppressWarnings("checkstyle:RegexpSinglelineJava") @Override
        public void run() {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                 PrintWriter writer = new PrintWriter(client.getOutputStream(), true)) {
                System.out.println("connected new client: " + client);
                while (!client.isClosed()) {
                    final String word = reader.readLine();
                    final String result = PHRASES.stream().filter(x -> x.contains(word)).findAny().orElse("");
                    writer.println(result);
                    writer.flush();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                System.out.println("client socket " + client + " closed");
            }
        }
    }
}
