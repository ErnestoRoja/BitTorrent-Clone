package main.java.com.bittorrent.message;

import java.io.IOException;
import java.util.Arrays;

public class MessageCreator {
    

    public static byte[] handshakeMessage(int peerId) throws IOException{

        byte[] handshakeMessage = new byte[32];
        byte[] header = "P2PFILESHARINGPROJ00000000".getBytes();
        System.arraycopy(header, 0, handshakeMessage, 0, header.length);
        byte[] peerIdBytes = String.format("%04d", peerId).getBytes();
        System.arraycopy(peerIdBytes, 0, handshakeMessage, header.length, 4);
        
        //System.out.println("Header: " + new String(header));
        //System.out.println("Handshake message after header: " + new String(handshakeMessage));
        //System.out.println("Peer ID bytes: " + new String(peerIdBytes));
        //System.out.println("Handshake message after peer ID: " + new String(handshakeMessage));

        return handshakeMessage; // should be output stream
        // or byte[] 


    }

    

    // making the other messages 

    public static byte[] bitfieldMessage() throws IOException {



        return null;
    }



    public class Main { 
        public static void main(String[] args){

            MessageCreator messageCreator = new MessageCreator();

            try {
                System.out.println("Handshake message test");
                System.out.println(new String(messageCreator.handshakeMessage(1001)));
            }
            catch (IOException e){
                System.out.println("Error");
                e.printStackTrace();
            }
        }
    }

}
