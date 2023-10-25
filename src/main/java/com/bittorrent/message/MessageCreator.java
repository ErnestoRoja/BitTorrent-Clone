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
        
        messageLength = messageLengthBuffer.array();


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

        messageLength = messageLengthBuffer.array();


        System.arraycopy(messageLength, 0, message, 0, 4);
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
        
        messageLength = messageLengthBuffer.array();


        System.arraycopy(messageLength, 0, message, 0, 4);
        System.arraycopy(messageType, 0, message, 4, 1);

        
        return message;
    }

    // not interested type 3 
    public static byte[] notinterestedMessage() throws IOException {
        byte[] message = new byte[5];

        byte[] messageLength = new byte[4];
        byte[] messageType = new byte[1];

        messageType[0] = 3;

        // determining the length of message
        int lengthNum = messageType.length;
        ByteBuffer messageLengthBuffer = ByteBuffer.allocate(4);
        messageLengthBuffer.putInt(lengthNum);
        
        messageLength = messageLengthBuffer.array();



        System.arraycopy(messageLength, 0, message, 0, 4);
        System.arraycopy(messageType, 0, message, 4, 1);

        
        return message;
    }
    
    // have message type 4
    // implement after piece type is done
    public static byte[] haveMessage() throws IOException {
        
        return null;
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


    // type 6
    // payload consts of a 4 byte piece index field
    public static byte[] requestMessage(int index) throws IOException {
        // total 9 bytes
        byte[] message = new byte[9];
        byte[] messageLength = new byte[4];      
        byte[] messageType = new byte[1];

        messageType[0] = 6;

        int lengthNum = messageType.length;

        // getting the payload length amount 
        // and turning it into a byte array
        ByteBuffer indexBuffer = ByteBuffer.allocate(4);
        indexBuffer.putInt(index);
        byte[] payload = indexBuffer.array();
        lengthNum += payload.length;
        
        
        ByteBuffer messageLengthBuffer = ByteBuffer.allocate(4);
        messageLengthBuffer.putInt(lengthNum);

        messageLength = messageLengthBuffer.array();
        
        System.arraycopy(messageLength, 0, message, 0, 4);
        System.arraycopy(messageType, 0, message, 4, 1);
        System.arraycopy(payload, 0, message, 5, payload.length);



        return message;

    }

    // type 7
    // payload which consists of 4 byte piece index field and content
    public static byte[] pieceMessage(int index, byte[] content) throws IOException {
        // depending on the size of the content, the message length will vary
        //byte[] message = new byte[9]; 

        byte[] messageLength = new byte[4];      
        byte[] messageType = new byte[1];

        messageType[0] = 7;

        int lengthNum = messageType.length;

        // getting the payload length amount 
        // and turning it into a byte array
        ByteBuffer indexBuffer = ByteBuffer.allocate(4);
        indexBuffer.putInt(index);
        byte[] indexByteArray = indexBuffer.array();
        lengthNum += indexByteArray.length;
        lengthNum += content.length;
                


        ByteBuffer messageLengthBuffer = ByteBuffer.allocate(4);
        messageLengthBuffer.putInt(lengthNum);

        byte[] message = new byte[lengthNum + 4];


        messageLength = messageLengthBuffer.array();
        
        System.arraycopy(messageLength, 0, message, 0, 4);
        System.arraycopy(messageType, 0, message, 4, 1);
        System.arraycopy(indexByteArray, 0, message, 5, indexByteArray.length);
        System.arraycopy(content, 0, message, 9, content.length);


        return message;
    }

    public static void testMessageCreator() {
        MessageCreator messageCreator = new MessageCreator();
        try {
            // Test handshake message
            System.out.println("Handshake message test");
            System.out.println(new String(messageCreator.handshakeMessage(1001)));
    
            // Test bitfield message
            System.out.println("Bitfield message test");
            BitSet bitfield = new BitSet(8);
            System.out.println(new String(messageCreator.bitfieldMessage(bitfield)));
    
            // Test not interested message
            System.out.println("Not interested message test");
            byte[] notInterestedMessage = MessageCreator.notinterestedMessage();
            System.out.println(Arrays.toString(notInterestedMessage));
    
            // Test interested message
            System.out.println("Interested message test");
            byte[] interestedMessage = MessageCreator.interestedMessage();
            System.out.println(Arrays.toString(interestedMessage));
    
            // Test choke message
            System.out.println("Choke message test");
            byte[] chokeMessage = MessageCreator.chokeMessage();
            System.out.println(Arrays.toString(chokeMessage));
    
            // Test unchoke message
            System.out.println("Unchoke message test");
            byte[] unchokeMessage = MessageCreator.unchokeMessage();
            System.out.println(Arrays.toString(unchokeMessage));
    
            // Test request message
            System.out.println("Request message test");
            byte[] requestMessage = messageCreator.requestMessage(1);
            System.out.println(Arrays.toString(requestMessage));
    
            // Test piece message
            System.out.println("Piece message test");
            byte[] pieceMessage = messageCreator.pieceMessage(1, "hello".getBytes());
            System.out.println(Arrays.toString(pieceMessage));
        } catch (IOException e) {
            System.out.println("Error");
            e.printStackTrace();
        }
    }


    public static void main(String[] args){
        testMessageCreator();
    }
}
