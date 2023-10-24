package main.java.com.bittorrent.message;

import java.io.IOException;
import java.util.BitSet;

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
    // 4 byte message length, 1 byte message type, variable length message (payload)

    // type = 5 
    public static byte[] bitfieldMessage(BitSet bitfieldMessage) throws IOException {
        byte[] message = new byte[32]; // error message length to be determined later
        byte[] messageLength = new byte[4];      
        byte[] messageType = new byte[1];
        byte[] messagePayload = bitfieldMessage.toByteArray();



        System.arraycopy(messageLength, 0, message, 0, 4);
        System.arraycopy(messageType, 0, message, 4, 1);
        System.arraycopy(messagePayload, 0, message, message.length, 0);

        //byte[] messagePayload = new byte[bitfieldMessage.length()];
        return message;
    }

    // type = 6



    public static void main(String[] args){
        MessageCreator messageCreator = new MessageCreator();
        try {
            System.out.println("Handshake message test");
            System.out.println(new String(messageCreator.handshakeMessage(1001)));

            System.out.println("Bitfield message test");
            BitSet bitfield = new BitSet(8);
            System.out.println(new String(messageCreator.bitfieldMessage(bitfield)));

            
        }
        catch (IOException e){
            System.out.println("Error");
            e.printStackTrace();
        }
    }
    

}
