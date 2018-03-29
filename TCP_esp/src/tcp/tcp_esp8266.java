package tcp;

import java.io.*;
import java.net.*;

public class tcp_esp8266 {
	  public static void main(String[] args) throws Exception {
		//	Create server socket
	    ServerSocket serverSocket = new ServerSocket(9882, 0, InetAddress.getByName("10.0.128.167"));
	    
	    //	Create ID list
	    int[] list= {0,0,0,0,0,0,0,0,0,0};
	    
	    
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
	      Runnable runnable = () -> handleClientRequest(activeSocket,list);
	      
	      // Create new thread for new client
	      new Thread(runnable).start(); // start a new thread
	    }
	  }

	  public static void handleClientRequest(Socket socket,int[] list) {
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
	        
	        String outMsg = null;
	        
	        if(Integer.parseInt(inMsg)==-1) {
	        	int max=find_max(list);
	        	outMsg=String.valueOf(Provide_id(max,list));
		        System.out.println("Sending to client: "+outMsg);
		        socketWriter.write(outMsg);
	        }
	        else {
	        	list[Integer.parseInt(inMsg)]++;
	        }
	        
	        // Send data back to client
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
	  
	  //	Find max value in arr
	  public static int find_max(int[] arr) {
		  int size= arr.length;
		  int max=arr[0];
		  for(int i=0;i<size;i++) {
			  if(max<arr[i]) max=arr[i];
		  }
		  return max;
	  }
	  
	  //	Replace or New ID for client
	  public static int Provide_id(int max,int[]arr) {
		  int size=arr.length;
		  for(int i=0;i<size;i++) {
			  if(arr[i]<(max-3)&&arr[i]!=0) {
				  for(int j=0;j<size;j++) {
					  arr[j]=0;
				  }
				  return i;
			  }
			  if(arr[i]==0) {
				  for(int j=0;j<size;j++) {
					  arr[j]=0;
				  }
				  return i;
			  }
		  }
		  return 0;
	  }
	}
