package servidor;

import java.net.*;
import java.io.*;

public class Servidor extends Thread {
    private Socket socket = null;
    private static ServerSocket server;
    private InputStream in;
    private InputStreamReader inr;
    private BufferedReader bfr;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    public Servidor(Socket socket) {
        this.socket = socket;
        try {
            in = socket.getInputStream();
            inr = new InputStreamReader(in);
            bfr = new BufferedReader(inr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            int fileContentLength = dataInputStream.readInt();
            byte[] fileContent = new byte[fileContentLength];
            dataInputStream.readFully(fileContent);
            String dados = new String(fileContent);
            String[] data = dados.split("\n");
            System.out.println("Corringo provas...");
            String notaFinal = Corretora.analisando(data);
            System.out.println(notaFinal);
            dataOutputStream.writeUTF((notaFinal));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {

        try {
            server = new ServerSocket(5000);
            System.out.println("Server has started in port 5000");
            while (true) {
                System.out.println("Waiting for connections");
                Socket con = server.accept();
                System.out.println("connected client");
                Thread t = new Servidor(con);
                t.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}