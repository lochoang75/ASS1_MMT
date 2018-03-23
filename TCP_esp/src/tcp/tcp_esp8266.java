package tcp;

import java.io.*;
import java.net.*;

public class tcp_esp8266 {
	  public static void main(String[] args) throws Exception {
		//	Create server socket
	    ServerSocket serverSocket = new ServerSocket(9882, 0, InetAddress.getByName("10.0.128.164"));
	    
	    
	    //	Get server info
	    System.out.println("Server started  at:  " + serverSocket);
	    
	    //	Wait for client connect
	    while (true) {
	      System.out.println("Waiting for a  connection...");
	      
	      // Create socket to client
	      final Socket activeSocket = serverSocket.accept();
	      
	      // Print client info
	      System.out.println("Received a  connection from  " + activeSocket);
	      
	      // Handle request from client
	      Runnable runnable = () -> handleClientRequest(activeSocket);
	      
	      // Create new thread for new client
	      new Thread(runnable).start(); // start a new thread
	    }
	  }

	  public static void handleClientRequest(Socket socket) {
	    try{
	    // Create buffer
	      BufferedReader socketReader = null;
	      BufferedWriter socketWriter = null;
	      
	      // Assign Reader and Writer buffer
	      socketReader = new BufferedReader(new InputStreamReader(
	          socket.getInputStream()));
	      socketWriter = new BufferedWriter(new OutputStreamWriter(
	          socket.getOutputStream()));
	      
	      String inMsg = null;
	      
	      // Wait until receive data from client
	      while ((inMsg = socketReader.readLine()) != null) {
	        System.out.println("Received from  client: " + inMsg);
	        String outMsg = inMsg;
	        
	        // Send data back to client
	        System.out.println("Sending to client: "+outMsg);
	        socketWriter.write(outMsg);
	        socketWriter.write("\n");
	        
	        // Clean buffer
	        socketWriter.flush();
	        
	      }
	      
	      //close thread socket
	      socket.close();
	    }catch(Exception e){
	      e.printStackTrace();
	    }

	  }
	}
