package Client;

import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client
{
    final static int ServerPort = 1234;

    public static void main(String args[]) throws UnknownHostException, IOException
    {
        JFrame frame = new JFrame("JustGame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(false);
        GameObject gameObject = new GameObject(frame);
        frame.add(gameObject);
        frame.setVisible(true);
        final boolean[] connection = {false, false};

        final Scanner scanner = new Scanner(System.in);

        final String[] mate = new String[2];
        mate[0] = "nothing"; //если никто не подключен
        mate[1] = "";

        InetAddress ip = InetAddress.getByName("localhost");

        Socket socket = new Socket(ip, ServerPort);

        final DataInputStream inputStream = new DataInputStream(socket.getInputStream());
        final DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

//        Thread sendMessage = new Thread(new Runnable()
//        {
//            @Override
//            public void run() {
//                while (true) {
//
//                    String msg = scanner.nextLine();
//
//                    try {
//                        outputStream.writeUTF(msg);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });

        Thread readMessage = new Thread(new Runnable()
        {
            @Override
            public void run() {

                while (true) {
                    try {
                        String msg = inputStream.readUTF();
                        if (msg.equals("Connection complete. Your mate is client 0"))
                        {
                            mate[0] = "client 0";
                            connection[0] = true;
                        }
                        else if (msg.equals("Connection complete. Your mate is client 1")) {
                            mate[0] = "client 1";
                            connection[0] = true;
                        }
                        else
                        {
                            mate[1] = msg;
                        }
                        System.out.println(msg);
                    } catch (IOException e) {

                        e.printStackTrace();
                    }
                }
            }
        });

//        sendMessage.start();
        readMessage.start();
        boolean run = true;
        while (run)
        {
            if (connection[0] && !connection[1])
            {
                gameObject.player1.changeX(0);
                gameObject.player1.changeY(frame.getHeight() - 100);
                gameObject.player2.changeX(0);
                gameObject.player2.changeY(frame.getHeight() - 100);
                gameObject.in_playing = true;
                connection[1] = true;
            }
            System.out.println(mate[1]);
            String currentMessage = mate[1];
            if (currentMessage.length() != 0 && !mate[0].equals("nothing"))
            {
                String[] ar = mate[1].split(" ");
                gameObject.player2.changeX(Integer.parseInt(ar[0]));
                gameObject.player2.changeY(Integer.parseInt(ar[1]));
            }


            //main game method

            if (!mate[0].equals("nothing"))
            {
                outputStream.writeUTF(gameObject.player1.getX() + " " + gameObject.player1.getY() + "#" + mate[0]);
            }
        }
    }
}