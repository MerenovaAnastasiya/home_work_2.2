package servers15_16;

import java.io.*;
import java.net.Socket;

public class Main {

    public static void main(String[] args) throws IOException {

        try(Socket socket = new Socket("study.istamendil.info", 80)) {
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            out.println("GET / HTTP/1.1");
            out.println("Host: study.istamendil.info");
            out.println("");
            out.flush();
            String string;
            LineNumberReader lineNumberReader = new LineNumberReader(new BufferedReader(new InputStreamReader(socket.getInputStream())));
            while ((string = lineNumberReader.readLine()) != null) {
                System.out.println(lineNumberReader.readLine());

            }

//


        }
    }
}