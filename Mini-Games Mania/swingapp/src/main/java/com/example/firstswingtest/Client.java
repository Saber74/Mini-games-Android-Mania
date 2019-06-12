//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.io.PrintStream;
//import java.net.ServerSocket;
//import java.net.Socket;
//public class Client {
//
//    public static void main(String args[]) throws Exception
//    {
//        Socket sk=new Socket("127.0.0.1",5013);
//        BufferedReader sin=new BufferedReader(new InputStreamReader(sk.getInputStream()));
//        PrintStream sout=new PrintStream(sk.getOutputStream());
//        BufferedReader stdin=new BufferedReader(new InputStreamReader(System.in));
//        String s;
//        while (  true )
//        {
//            System.out.print("Client : ");
//            s=stdin.readLine();
//            sout.println(s);
//            if ( s.equalsIgnoreCase("BYE") )
//            {
//                System.out.println("Connection ended by client");
//                break;
//            }
//            s=sin.readLine();
//            System.out.print("Server : "+s+"\n");
//
//        }
//        sk.close();
//        System.out.println("hi");
//        sin.close();
//        sout.close();
//        stdin.close();
//    }
//
//}
package com.example.firstswingtest;
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

