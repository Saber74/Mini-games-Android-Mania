package com.mygdx.game;
//ICS4U FSE
//ANITA HU?/NIZAR ALRIFAI
//Client that only reads from server. this client is used in desktop launcer
import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
public class ClientRead {
   
    Socket sk;
    BufferedReader sin;
    PrintStream sout;
    public ClientRead() {
        try {
            Socket sk = new Socket("142.93.255.106", 8080);
            sin = new BufferedReader(new InputStreamReader(sk.getInputStream()));
            sk.close();

        }
        catch(IOException x){
            System.out.println("Ioexception");
        }
    }
    public String read() {
        String s="";
        try{
            s = sin.readLine();
        }
        catch(IOException y){
            System.out.println("can't read");
        }
        System.out.print("Server : " + s + "\n");
        return s;
    }
    public void close(){
        try{
            sin.close();
        }
        catch(IOException x){}
    }
}



