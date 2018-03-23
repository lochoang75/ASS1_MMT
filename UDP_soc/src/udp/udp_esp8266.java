package udp;

import java.io.*;
import java.net.*;

class udp_esp8266
{
   public static void main(String args[]) throws Exception
      {
	   // Create UDP socket
         DatagramSocket serverSocket = new DatagramSocket(9870, InetAddress.getByName("10.0.128.164"));
         	// Set size of data receive and send
            
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
               }
      }
}