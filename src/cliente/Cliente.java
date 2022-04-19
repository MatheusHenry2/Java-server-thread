package cliente;

import javax.swing.*;
import java.net.*;
import java.io.*;

public class Cliente {
    private Socket socket;
    private FileInputStream fileInputStream;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private File fileToSend;

    public Cliente(String address, int port) {
        try {
            socket = new Socket(address, port);
            System.out.println("Connected");
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));

        } catch(UnknownHostException u) {
            u.printStackTrace();
        } catch(IOException i) {
            i.printStackTrace();
        }

    }
    private void chooseFile()  {
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.showOpenDialog(null);
        jFileChooser.setDialogTitle("Choose a file to send.");
        if(jFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            fileToSend = jFileChooser.getSelectedFile();
            System.out.println("File to send: " + fileToSend.getName());
        }

    }

    private void sendFile() {
        try {
            fileInputStream = new FileInputStream(fileToSend.getAbsolutePath());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());

            byte[] fileBytes = new byte[(int)fileToSend.length()];
            fileInputStream.read(fileBytes);

            dataOutputStream.writeInt(fileBytes.length);
            dataOutputStream.write(fileBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void sair() {
        try {
            fileInputStream.close();
            dataOutputStream.close();
            dataInputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void reciveAnswer() {
        try {
            System.out.println(dataInputStream.readUTF());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        Cliente client = new Cliente("localhost", 5000);
        client.chooseFile();
        client.sendFile();
        client.reciveAnswer();
        client.sair();
    }

}