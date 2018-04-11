package udp;

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

class udp_esp8266
{
   public static void main(String args[]) throws Exception
      {
	   String textInALine="\t";
	   Integer counter=0;
	   // Create UDP socket
         DatagramSocket serverSocket = new DatagramSocket(9870, InetAddress.getByName("10.0.128.167"));
            
            while(true)
               {
            	byte[] receiveData = new byte[1024];
            	// Create receive package
                  DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                  
                  
                  // Receive package from socket
                  serverSocket.receive(receivePacket);
                  
                  // Get data from package
                  String sentence = new String( receivePacket.getData());
                  
                  // Print out data received
                  System.out.println("RECEIVED: " + sentence);
                  
                  // Write to file
                  // 1. opening the file for writing (creation of the file)
                  FileWriter b = new FileWriter("buf.txt",true);
                  PrintWriter buf= new PrintWriter(b);
                  
                  // 2. Add string to buffer
                  textInALine+=sentence.trim()+";";
                  counter++;
                  
                  // 3. Add string to file
               	  buf.println(sentence.trim()+";"+new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()));
                  buf.flush();
                  buf.close();
                  b.close();
                  
                  if(counter==10) {
                	System.out.println("Waiting for a  connection...");
      	    		
      	    		ServerSocket tcp_serverSocket = new ServerSocket(9885,0,InetAddress.getByName("10.0.128.167"));
      	    	    System.out.println("Server started  at:  " + tcp_serverSocket);
      	    		final Socket activeSocket = tcp_serverSocket.accept();

      	    		System.out.println("Received a  connection from  " + activeSocket);
      	    		handleClientRequest(activeSocket,textInALine.trim());
      	    		System.out.println(textInALine);
      	    		textInALine="\t";
      	    		tcp_serverSocket.close();
      	    		counter=counter-10;
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
     	  socketWriter.write("\n");
     	  socketWriter.flush(); //clear buff
	      socket.close();
	    }catch(Exception e){
	      e.printStackTrace();
	    }

	  }
}
