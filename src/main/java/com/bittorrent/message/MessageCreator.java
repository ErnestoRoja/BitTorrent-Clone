package com.bittorrent.message;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
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

    // maybe make a message determiner ??

    // making the other messages 
    // 4 byte message length, 1 byte message type, variable length message (payload)

    // choke 0, unchoke 1, interested 2, not interested 3, they all follow the same structure
    // have no payload data 
    // only 5 bytes
    public static byte[] chokeMessage() throws IOException {
        byte[] message = new byte[5];

        byte[] messageLength = new byte[4];
        byte[] messageType = new byte[1];

        messageType[0] = 0;

        // determining the length of message
        int lengthNum = messageType.length;
        ByteBuffer messageLengthBuffer = ByteBuffer.allocate(4);
        messageLengthBuffer.putInt(lengthNum);
        
        System.arraycopy(messageLengthBuffer.array(), 0, message, 0, 4);
        System.arraycopy(messageType, 0, message, 4, 1);

        
        return message;
    }

    public static byte[] unchokeMessage() throws IOException {
        byte[] message = new byte[5];

        byte[] messageLength = new byte[4];
        byte[] messageType = new byte[1];

        messageType[0] = 1;

        // determining the length of message
        int lengthNum = messageType.length;
        ByteBuffer messageLengthBuffer = ByteBuffer.allocate(4);
        messageLengthBuffer.putInt(lengthNum);
        
        System.arraycopy(messageLengthBuffer.array(), 0, message, 0, 4);
        System.arraycopy(messageType, 0, message, 4, 1);

        
        return message;
    }

    public static byte[] interestedMessage() throws IOException {
        byte[] message = new byte[5];

        byte[] messageLength = new byte[4];
        byte[] messageType = new byte[1];

        messageType[0] = 2;

        // determining the length of message
        int lengthNum = messageType.length;
        ByteBuffer messageLengthBuffer = ByteBuffer.allocate(4);
        messageLengthBuffer.putInt(lengthNum);
        
        System.arraycopy(messageLengthBuffer.array(), 0, message, 0, 4);
        System.arraycopy(messageType, 0, message, 4, 1);

        
        return message;
    }

    public static byte[] notinterestedMessage() throws IOException {
        byte[] message = new byte[5];

        byte[] messageLength = new byte[4];
        byte[] messageType = new byte[1];

        messageType[0] = 4;

        // determining the length of message
        int lengthNum = messageType.length;
        ByteBuffer messageLengthBuffer = ByteBuffer.allocate(4);
        messageLengthBuffer.putInt(lengthNum);
        
        System.arraycopy(messageLengthBuffer.array(), 0, message, 0, 4);
        System.arraycopy(messageType, 0, message, 4, 1);

        
        return message;
    }
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

    // have message type 6
    // implement after piece type is done
    public static byte[] haveMessage() throws IOException {

        return null;
    }

    // type 7
    // payload which consists of 4 byte piece index field and content
    public static byte[] pieceMessage() throws IOException {

        return null;
    }




    public static void main(String[] args){
        MessageCreator messageCreator = new MessageCreator();
        try {
            System.out.println("Handshake message test");
            System.out.println(new String(messageCreator.handshakeMessage(1001)));

            System.out.println("Bitfield message test");
            BitSet bitfield = new BitSet(8);
            System.out.println(new String(messageCreator.bitfieldMessage(bitfield)));
           
            // type 0, 1,  2,3
            System.out.println("Not interested message test");
            byte[] notInterestedMessage = MessageCreator.notinterestedMessage();
            System.out.println(Arrays.toString(notInterestedMessage));

            System.out.println("Interested message test");
            byte[] interestedMessage = MessageCreator.interestedMessage();
            System.out.println(Arrays.toString(interestedMessage));

            System.out.println("Choke message test");
            byte[] chokeMessage = MessageCreator.chokeMessage();
            System.out.println(Arrays.toString(chokeMessage));

            System.out.println("Unchoke message test");
            byte[] unchokeMessage = MessageCreator.unchokeMessage();
            System.out.println(Arrays.toString(unchokeMessage));
        }
        catch (IOException e){
            System.out.println("Error");
            e.printStackTrace();
        }
    }
}
