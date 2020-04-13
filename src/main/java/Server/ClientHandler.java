package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.StringTokenizer;

class ClientHandler implements Runnable
{
    Scanner scanner = new Scanner(System.in);
    private String name;
    final DataInputStream inputStream;
    final DataOutputStream outputStream;
    Socket socket;
    boolean isLoggedIn;

    public ClientHandler(Socket s, String name,
                         DataInputStream dis, DataOutputStream dos) {
        this.inputStream = dis;
        this.outputStream = dos;
        this.name = name;
        this.socket = s;
        this.isLoggedIn =true;
    }

    @Override
    public void run() {

        String received;
        while (true)
        {
            try
            {
                received = inputStream.readUTF();

                System.out.println(received);

                if(received.equals("logout")){
                    this.isLoggedIn =false;
                    this.socket.close();
                    break;
                }

                // тут идет расспознование сообщения клиента сервером и передача сообщения нужному клиенту
                StringTokenizer token = new StringTokenizer(received, "#");
                String MsgToSend = token.nextToken();
                String receiver = token.nextToken();

                for (ClientHandler mc : Server.clients)
                {
                    if (mc.name.equals(receiver) && mc.isLoggedIn)
                    {
//                        mc.dos.writeUTF(this.name+" : "+MsgToSend);
                        mc.outputStream.writeUTF(MsgToSend);
                        break;
                    }
                }
            } catch (IOException e) {

                e.printStackTrace();
            }

        }
        try
        {
            this.inputStream.close();
            this.outputStream.close();

        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
