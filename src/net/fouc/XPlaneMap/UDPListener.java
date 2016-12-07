package net.fouc.XPlaneMap;

import java.net.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

public class UDPListener implements Runnable {
  private PlanesList list;

  public UDPListener(PlanesList list_) {
    this.list = list_;
  }

  public void run() {
    try {
      @SuppressWarnings("resource")
      DatagramSocket serverSocket = new DatagramSocket(49003);
      byte[] receiveData = new byte[32];

      System.out.printf("Listening on udp: %s:%d%n",
      InetAddress.getLocalHost().getHostAddress(), 49003);
      DatagramPacket receivePacket = new DatagramPacket(receiveData,
      receiveData.length);

      while(true)
      {
        serverSocket.receive(receivePacket);
        //String sentence = new String( receivePacket.getData(), 0, receivePacket.getLength() );
        //System.out.println("Received : " + sentence);
        int ident = receivePacket.getData()[5];
        if (ident == 20) {
          Float lat = ByteBuffer.wrap(Arrays.copyOfRange(receivePacket.getData(), 9, 13)).order(ByteOrder.LITTLE_ENDIAN).getFloat();
          Float lon = ByteBuffer.wrap(Arrays.copyOfRange(receivePacket.getData(), 13, 17)).order(ByteOrder.LITTLE_ENDIAN).getFloat();
          Float alt = ByteBuffer.wrap(Arrays.copyOfRange(receivePacket.getData(), 17, 21)).order(ByteOrder.LITTLE_ENDIAN).getFloat();
          InetAddress IPAddress = receivePacket.getAddress();
          //System.out.println(IPAddress.toString() + " is at latitude " + lat.toString() + ", longitude " + lon.toString() + ".");

          list.setPlaneLat(IPAddress, lat);
          list.setPlaneLon(IPAddress, lon);
          list.setPlaneAlt(IPAddress, alt);
        }

      }
    }
    catch (Exception e){
      e.printStackTrace();
    }

  }
}
