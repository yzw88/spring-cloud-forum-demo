package pers.walyex.demo.config;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;

@Slf4j
public class SocketThread extends Thread {

    private Socket socket;

    public SocketThread(Socket socket) {
        this.socket = socket;
    }

    private String handle(InputStream inputStream) throws IOException {
        byte[] bytes = new byte[1024];
        int len = inputStream.read(bytes);
        if (len != -1) {
            StringBuffer request = new StringBuffer();
            request.append(new String(bytes, 0, len, StandardCharsets.UTF_8));
            System.out.println("接受的数据: " + request);
            System.out.println("from client ... " + request + "当前线程" + Thread.currentThread().getName());
        } else {
            log.info("数据处理异常");
        }

        return "ok";
    }

    @Override
    public void run() {
        BufferedWriter writer = null;
        try {
            // 设置连接超时9秒
            socket.setSoTimeout(9000);
            System.out.println("客户 - " + socket.getRemoteSocketAddress() + " -> 机连接成功");
            InputStream inputStream = socket.getInputStream();
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            String result = null;
            try {
                result = handle(inputStream);
                writer.write(result);
                writer.newLine();
                writer.flush();
            } catch (IOException | IllegalArgumentException e) {
                writer.write("error");
                writer.newLine();
                writer.flush();
                System.out.println("发生异常");
                try {
                    System.out.println("再次接受!");
                    result = handle(inputStream);
                    writer.write(result);
                    writer.newLine();
                    writer.flush();
                } catch (SocketTimeoutException ex) {
                    System.out.println("再次接受, 发生异常,连接关闭");
                }
            }
        } catch (SocketException socketException) {
            socketException.printStackTrace();
            try {
                writer.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
