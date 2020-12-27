package pers.walyex.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class SocketCommandLineRunner implements CommandLineRunner {

    @Value("${socket.port}")
    private Integer port;

    @Value("${socket.pool-keep}")
    private Integer poolKeep;

    @Value("${socket.pool-core}")
    private Integer poolCore;

    @Value("${socket.pool-max}")
    private Integer poolMax;

    @Value("${socket.pool-queue-init}")
    private Integer poolQueueInit;

    @Override
    public void run(String... args) throws Exception {
        ServerSocket server = null;
        Socket socket = null;
        server = new ServerSocket(this.port);
        log.info("设备服务器已经开启, 监听端口:{}", this.port);

        ThreadPoolExecutor pool = new ThreadPoolExecutor(
                this.poolCore,
                this.poolMax,
                this.poolKeep,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(this.poolQueueInit),
                new ThreadPoolExecutor.DiscardOldestPolicy()
        );

        while (true) {
            socket = server.accept();
            pool.execute(new SocketThread(socket));
        }

    }
}
