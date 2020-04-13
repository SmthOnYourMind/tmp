package Server;

import java.io.*;
import java.util.*;
import java.net.*;

public class Server
{

    static Vector<ClientHandler> clients = new Vector<ClientHandler>();

    static int clientsCount = 0;

    public static void main(String[] args) throws IOException
    {
        ServerSocket serverSocket = new ServerSocket(1234);

        Socket socket;

        while (true)
        {
            socket = serverSocket.accept();

            System.out.println("New client request received : " + socket);

            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

            System.out.println("Creating a new handler for this client...");

            ClientHandler clientHandler = new ClientHandler(socket,"client " + clientsCount, inputStream, outputStream);

            Thread thread = new Thread(clientHandler);

            System.out.println("Adding this client to active client list");

            clients.add(clientHandler);

            thread.start();

            clientsCount++;

            if (clientsCount == 2)
            {
                int l = 1;
                for (ClientHandler mc : clients)
                {
                    mc.outputStream.writeUTF("Connection complete. Your mate is client " + l);
                    l--;
                }
            }
        }
    }
}
