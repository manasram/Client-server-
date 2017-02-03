//client program
import java.io.*;
import java.net.*;

public class Webclient {

	public static void main(String args[])
	{
			try
			{
				long start=System.currentTimeMillis();
			System.out.println("Establishing connection to "+"localhost" + " Using port number "+ "6789");
			Socket client=new Socket("localhost",6789);
			System.out.println("Connection successful established");
			String File_Name;
		    File_Name=args[0];
		    DataOutputStream dos=new DataOutputStream(client.getOutputStream());
		    BufferedReader is = new BufferedReader(new InputStreamReader(client.getInputStream()));
	     	dos.writeBytes("GET/"+" "+File_Name+" "+"/HTTP/1.1"+"\r\n");
	      	System.out.println("Server Response :");
	        try
	        {
	        	byte[] buffer=new byte[1024];
				String ln;
				while((ln=is.readLine())!=null)
				{
				System.out.println(ln);
				}
				long end=System.currentTimeMillis();
				long rtt=end-start;
				System.out.println("Time Taken in total : "+rtt+"ms");
				System.out.println("Server Host Name : localhost "+client.getRemoteSocketAddress()+"\r\n");//referred from http://docs.oracle.com
				System.out.println("Timeout Time: "+client.getSoTimeout()+"\r\n");//referred from http://docs.oracle.com
				System.out.println("SocketType : SOCK_STREAM\r\n");
				System.out.println("Protocol Used: TCP/IP");
	        }
	        catch(Exception e)
	        {
	        	System.out.println("exception encountered");
	        }
			}
			catch(UnknownHostException e)
			{
				System.out.println("Cannot establish connection...Host could not be identified");
			}
			catch(IOException e)
			{
				System.out.println("Cannot establish connection");
			}
			
}
}
