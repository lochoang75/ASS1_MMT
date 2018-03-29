package udp;

import java.io.*;
import java.net.*;

class udp_esp8266
{
   public static void main(String args[]) throws Exception
      {
	   // Create UDP socket
         DatagramSocket serverSocket = new DatagramSocket(9870, InetAddress.getByName("10.0.128.167"));
         	
         // Set data receive and send
         String s=null;
         int counter=0;
            
            while(true)
               {
            	byte[] receiveData = new byte[1024];
                byte[] sendData = new byte[1024];
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
                  FileWriter f = new FileWriter("test.txt",true);
                  FileWriter b = new FileWriter("buf.txt");
                  PrintWriter out = new PrintWriter(f);
                  PrintWriter buf= new PrintWriter(b);
                  // 2. writing text on the file
                  out.println(sentence.trim()+";");
                  
                  // 3. Add string to buffer
                  buf.println(sentence.trim()+";");
                  // 3. closing the output channel and the file
                  out.close();
                  buf.close();
                  b.close();
                  f.close();
               }
      }
}
