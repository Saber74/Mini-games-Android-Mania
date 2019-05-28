import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
public class Client {

    public static void main(String args[]) throws Exception
    {
        Socket sk=new Socket("127.0.0.1",5014);
        PrintStream sout=new PrintStream(sk.getOutputStream());
        BufferedReader stdin=new BufferedReader(new InputStreamReader(System.in));
        String s;
        int w=0;
        while (  true )
        {
            s="move";
            sout.println(s);
            w++;
            if ( s.equalsIgnoreCase("BYE") )
            {
                System.out.println("Connection ended by client");
                break;
            }
        }
        sk.close();
        System.out.println("hi");
        sout.close();
        stdin.close();
    }

}
