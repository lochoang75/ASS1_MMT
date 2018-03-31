package TCP_web;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
//  ww w.  j a v a 2 s  . c  om

public class TCP_web {
	  public static void main(String[] args) throws Exception {
		Integer counter=0;
		BufferedReader br = null;
		BufferedWriter wr = null;
        String textInALine = "\t";

	    while (true) {

	        try {   
	            br = new BufferedReader(new FileReader("C:\\Users\\Loc\\eclipse-workspace\\UDP_soc\\buf.txt"));  
	            wr = new BufferedWriter(new FileWriter("C:\\Users\\Loc\\eclipse-workspace\\UDP_soc\\buf.txt"));
	            String buf=null;
	            
	            while ((buf = br.readLine()) != null) {
	                System.out.println(buf+counter.toString());
	                if(counter==5) {
	                	continue;
	                }
	                else {
	                	counter++;
	                	textInALine+=buf;
	                	
	                }
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                br.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	    	if(counter==5) {
	    		System.out.println("Waiting for a  connection...");
	    		
	    		ServerSocket serverSocket = new ServerSocket(9885,0,InetAddress.getByName("10.0.128.167"));
	    	    System.out.println("Server started  at:  " + serverSocket);
	    		final Socket activeSocket = serverSocket.accept();

	    		System.out.println("Received a  connection from  " + activeSocket);
	    		handleClientRequest(activeSocket,textInALine.trim());
	    		textInALine="\t";
	    		serverSocket.close();
	    		counter=counter-5;
	    		//new Thread(runnable).start(); // start a new thread
	    	}
	    }
	  }

	  public static void handleClientRequest(Socket socket,String text) {
		
	    try{
	      BufferedWriter socketWriter = null;
	      socketWriter = new BufferedWriter(new OutputStreamWriter(
	          socket.getOutputStream()));

	      System.out.println("Send to client");
      	  socketWriter.write(text);
      	  text=null;
      	  socketWriter.write("\n");
      	  socketWriter.flush(); //clear buff
	      socket.close();
	    }catch(Exception e){
	      e.printStackTrace();
	    }

	  }
	}
