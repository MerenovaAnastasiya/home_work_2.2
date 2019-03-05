package servers15_16;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server16 {
    private static Socket socket;
    private static final int PORT = 8080;

    public Server16() {
        int summ  = 0;
        try(ServerSocket serverSocket  = new ServerSocket(Server16.PORT)) {
            while ((socket = serverSocket.accept()) != null) {
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String s = br.readLine();
                summ = summ(s);
                System.out.println(summ);

                PrintWriter pr = new PrintWriter(socket.getOutputStream());
                pr.println("HTTP/1.1 200 OK");
                pr.println("Content-type:text/plain;Charset:UTF-8");
                pr.println("Content-length:" + ((summ+"").length()));
                pr.println();
                pr.println(summ);
                pr.flush();
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        new Server16();
    }
    public static int summ(String string) {
        string = string.substring(10);
        int x = string.indexOf("&");
        int a = Integer.parseInt(string.substring(2, x));
        int y = string.indexOf("HTTP");
        int b = Integer.parseInt(string.substring(x+3, y-1));
        return a + b;

    }


}
