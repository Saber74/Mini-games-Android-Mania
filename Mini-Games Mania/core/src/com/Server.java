//ICS4U FSE
//ANITA HU? / NIZAR ALRIFAI
//Server code. Server code is hosted on server this is only to showcase the code.
package com;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
public class Server {
    ServerSocket server;
    int port; //port number
    Socket client = null; //adding clients
    ExecutorService pool = null; //clients are added to this pool and are managed there
    int clientcount = 0; //counting how many clients
    static ArrayList<Socket> clients= new ArrayList<Socket>();
    public static void main(String[] args) {
        try {

            Server serverobj = new Server(8080);
            serverobj.startServer();
        } catch (IOException x) {
        }
    }

    Server(int port) { //creating server object
        this.port = port;
        pool = Executors.newFixedThreadPool(5);
    }

    public void startServer() throws IOException { //starting the server and connectig to port and ip adress
        server = new ServerSocket(8080,1, InetAddress.getByName("142.93.255.106"));
        System.out.println("Server Booted");
        System.out.println("Any client can stop the server by sending -1");
        while (true) { //accepting any clients that try to join up to a max of 5 running at same time
            client = server.accept();
            clients.add(client);
            clientcount++;
            ServerThread runnable = new ServerThread(client, clientcount, this); //creates a thread to deal with every client seperatley
            pool.execute(runnable);
        }

    }

    private static class ServerThread implements Runnable {
        Server server = null;
        Socket client = null;
        BufferedReader cin; //reading what is sent by client
        PrintStream cout;  //sending to client
        Scanner sc = new Scanner(System.in); //for user to manually type to client for testing
        int id;
        String s;

        ServerThread(Socket client, int count, Server server) {
            try {
                this.client = client;
                this.server = server;
                this.id = count;
                System.out.println("Connection " + id + "established with client " + client);
                if(id==1){
                    cin = new BufferedReader(new InputStreamReader(client.getInputStream())); //setting up input and ouput for client
                }
                cout = new PrintStream(client.getOutputStream());
            }
            catch(IOException x){}
        }

        @Override
        public void run() { //running and getting and displaying input and output
            //terminate if right text is sent
            int x = 1;
            try {
                while (true) {
                    s = cin.readLine();
                    System.out.print("Client(" + id + ") :" + s + "\n");
                    System.out.print("Server : ");
                    s = sc.nextLine();
                    if (s.equalsIgnoreCase("bye")) {
                        cout.println("BYE");
                        x = 0;
                        System.out.println("Connection ended by server");
                        break;
                    }
                    int remmember=id-1;
                    for(int i=0;i<clients.size();i++){
                        client=clients.get(i);
                        cout = new PrintStream(client.getOutputStream());
                        cout.println(s);
                    }
                    client=clients.get(remmember);
                }

                cin.close();
                client.close();
                cout.close();
                if (x == 0) {
                    System.out.println("Server cleaning up.");
                    System.exit(0);
                }
            } catch (IOException ex) {
                System.out.println("Error : " + ex);
            }


        }
    }
}
// scp filelocation root@ipdress:~/Hello/ // to upload java file to server