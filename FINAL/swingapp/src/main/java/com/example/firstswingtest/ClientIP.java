package com.example.firstswingtest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class ClientIP {
//    public static void main(String args[]) { //for testing purposes
//        ClientIP x = new ClientIP();
//        x.send("hi");
//        x.send("w");
//    }

    private Socket sk; //socket to connect to server
    public PrintStream sout; //output stream to send to server

    public ClientIP() { //creating new Client object
        try {
            sk = new Socket("142.93.255.106", 8080); //connecting to my server
            sout = new PrintStream(sk.getOutputStream()); //getting a the output stream onto socket
//            System.out.println("workkkkuuung"); //trying if client is set up
        } catch (IOException x) {
            System.out.println("did not work");
        }
    }

    public void send(String s) {
        System.out.print("Client : ");
        System.out.println(s);
        sout.println(s); //sends string given to server
    }

    public void close() { //closes all sockets and streams
        try {
            sk.close();
            System.out.println("hi");
            sout.close();
        } catch (IOException x) {
        }
    }
}
