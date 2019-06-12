//package com.example.firstswingtest;
//import java.io.*;
//import java.net.*;
//public class Client {
//    public Socket sk;
//    public PrintStream sout;
//    public BufferedReader stdin;
//    public Client(){
//        try {
//            sk = new Socket("127.0.0.1", 5014);
//            sout = new PrintStream(sk.getOutputStream());
//            stdin = new BufferedReader(new InputStreamReader(System.in));
//        }
//        catch (java.net.UnknownHostException x) {
//            System.out.println("Unkown host exception");
//        }
//        catch(java.io.IOException y){
//            System.out.println("IO exception");
//        }
//    }
//}
package com;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
public class Client {
    public Socket sk;
    public PrintStream sout;
    public Client() {
        try {
            sk = new Socket("127.0.0.1", 5014);
            sout = new PrintStream(sk.getOutputStream());
            System.out.println("it works");
        } catch (java.io.IOException x) {
            System.out.println("IOEXCEPTION");
        }
    }
    public void send(String se) {
        System.out.print("Client : ");
        if(sout==null) {
            try {
                sout = new PrintStream(sk.getOutputStream());
            } catch (java.io.IOException x) {
                System.out.println("idk");
            }
        }
        else {
            sout.println(se);
        }
        try{
            sk.close();
        }
        catch (java.io.IOException x){
            System.out.println("error");
        }
        System.out.println("hi");
        sout.close();
    }
}

