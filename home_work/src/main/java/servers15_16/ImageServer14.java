package servers15_16;

import javax.activation.MimetypesFileTypeMap;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;

public class ImageServer14 {

    private static Socket socket;

    private static final int PORT = 8080;

    public ImageServer14() {
        try ( ServerSocket serverSocket = new ServerSocket(ImageServer14.PORT) ) {
            while ((socket = serverSocket.accept()) != null) {
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String s = br.readLine();
                System.out.println(s);
                s = getPath(s);
                System.out.println(s);
                OutputStream out = socket.getOutputStream();
                String head;
                String path = "/Users/merenaas/Desktop";
                System.out.println(path + s);
                File file = new File(path + s);
                if(file.exists()) {
                    MimetypesFileTypeMap mimeTypesMap = new MimetypesFileTypeMap();
                    String mimeType = mimeTypesMap.getContentType(file);
                    System.out.println(mimeType);
                    head = "HTTP/1.1 200 OK\n" + "Content-type:" + mimeType + ";\n" +"\n";
                    System.out.println(head);
                    byte[] body = Files.readAllBytes(file.toPath());
                    out.write(head.getBytes());
                    out.write(body);
                }
                else {
                    head = "HTTP/1.1 404 Not Found\n" +"Status: 404 Not Found" + "Content-type:text/plain;Charset:UTF-8\n" +"\n";
                    String body = "File Not Found.Sorry!";
                    out.write(head.getBytes());
                    out.write(body.getBytes());
                }
                out.flush();
                socket.close();
            }
        } catch (Exception ex) {
            System.err.println("Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new ImageServer14();

    }
    public static String getPath(String string) {
        string = string.substring(4);
        int x = string.indexOf("HTTP");
        return string.substring(0, x-1);
    }
}