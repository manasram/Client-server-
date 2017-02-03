import java.io.*;
import java.net.*;
import java.util.StringTokenizer;
import javax.activation.MimetypesFileTypeMap;

public final class WebServer
{
	public static void main(String argv[]) throws Exception
	{
	 int port = 6789;
	 ServerSocket Listner = new ServerSocket(port);
	 
	 try{
		 System.out.println("Server socket READY! ..... Listening to connections")
		 
		}
		catch(Exception e)
		{
				System.out.println("**ErroR**");
		}
	 
	while (true)
	{
	 Socket clientSocket = Listner.accept();
	 System.out.println("socket creation successfull");
	 HttpRequest request = new HttpRequest(clientSocket);
	 Thread thread = new Thread(request);
	 thread.start();
	}
	}
}


final class HttpRequest implements Runnable
{
	final static String CRLF = "\r\n";	
	Socket socket;
	public HttpRequest(Socket socket) throws Exception
	{
	  this.socket = socket;
	}

	public void run()
	{
	try {
			long start=System.currentTimeMillis();
			BufferedReader is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			DataOutputStream os = new DataOutputStream(socket.getOutputStream());
			os.writeBytes("Welcome to FILE SERVER developed by VANISHA CRASTA");
		  	String requestLine = is.readLine()+"\r\n";
			os.writeBytes(requestLine);
			StringTokenizer tokens=new StringTokenizer(requestLine);
			tokens.nextToken();
	        String Filename=tokens.nextToken();
	    	Filename="."+Filename;
			FileInputStream fis=null;
			boolean fileexists=true;
			String statusLine=null;
			String contentTypeLine=null;
			String entityBody=null;
			try
			{
			fis=new FileInputStream(Filename);
			}
			catch(FileNotFoundException e)
			{
				fileexists=false;
				os.writeBytes("\r\n-----*File not Found*-------\r\n");
			}
			if(fileexists)
			{   
			     os.writeBytes("\r\n-----File found-----\r\n");
				 statusLine="HTTP/1.1 200 OK"+"\r\n";
				 os.writeBytes(statusLine);
				 MimetypesFileTypeMap mime=new MimetypesFileTypeMap();
			     contentTypeLine="content Type:"+mime.getContentType(Filename)+"\r\n";
				 os.writeBytes(contentTypeLine);
				 os.writeBytes("File Contents:");
				 byte[] buffer=new byte[1024];
				 int bytes=0;
				 while((bytes=fis.read(buffer))!=-1)
				 {
					os.write(buffer,0,bytes);
			     }
			}
			else
			{   os.writeBytes("-----*File not Found*-------\r\n");
				statusLine="HTTP/1.1 404 not found"+"\r\n";
				MimetypesFileTypeMap mime=new MimetypesFileTypeMap();
				contentTypeLine="content Type:"+mime.getContentType(Filename)+"\r\n";
				entityBody="<HTML>"+
				           "<HEAD><TITLE>Not Found</TITLE></HEAD>"+
						   "<BODY>Not Found</BODY><HTML>";
				os.writeBytes(statusLine);
				os.writeBytes(contentTypeLine);
				os.writeBytes(entityBody);		
			}
			long end=System.currentTimeMillis();
			long rtt=end-start;
			System.out.println("Total Routuing Time : "+rtt+"ms\r\n");
			System.out.println("Server Host Name  "+socket.getRemoteSocketAddress()+"\r\n");//referred from http://docs.oracle.com
			System.out.println("Timeout : "+socket.getSoTimeout()+"\r\n");//referred from http://docs.oracle.com
			System.out.println("SocketType : SOCK_STREAM\r\n");
			System.out.println("Protocol : TCP/IP");
			os.close();
			is.close();

		}
	catch (IOException e) 
	{
			System.out.println("Exception Encountered");
	}
		
	}
}

