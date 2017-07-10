import java.net.*;
import java.io.*;           
import java.util.Random;

public class TCPServer {

static Random r;
static int min, max, determ;

public static void main(String[] args)
{
	TCPServer obj=new TCPServer();
	r = new Random();
	
	try{
		ServerSocket socket = new ServerSocket(8888);
		
		while (true)
		{
			  Socket client = socket.accept();
			
			  DataInputStream in = new DataInputStream(client.getInputStream());
			
			  min=Integer.parseInt(in.readLine());
			  max=Integer.parseInt(in.readLine());
			 
			  
			  if( (Math.abs(min-max)==1) || min==max) determ=max;
			  else
			  determ = (min+1) + (int)(Math.random() * (max - min-1));  
			      
			  PrintStream out = new PrintStream(client.getOutputStream());
			  out.println(determ);
			
			in.close(); client.close();
		} 
	}
	catch (Exception e) {System.out.println(e);} } 
  
}
