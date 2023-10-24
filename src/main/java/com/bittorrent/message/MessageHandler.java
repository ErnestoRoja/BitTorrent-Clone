package main.java.com.bittorrent.message;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import java.io.ObjectInputStream;

public class MessageHandler {
    
    public static byte[] readMessage(ObjectInputStream inputStream) throws IOException {
        byte[] lengthBytes = new byte[4];
        
        //byte [] message = (byte[]) inputStream.readObject();
        
        //inputStream.read(lengthBytes);
        int messageLength = ByteBuffer.wrap(lengthBytes).getInt();
    
        byte messageType = (byte) inputStream.read();
    
        byte[] payload = new byte[messageLength - 5];
        inputStream.read(payload);

        

        // depending on the payload 
        // for example, if it is a bitfield message, then the payload will be the bitfield
        // send the rest of the message to the bitfield handler
        // if it is a request message, then the payload will be the index of the piece requested
    
        
        // This tests the content of the message 
        byte[] message = new byte[messageLength];
        System.arraycopy(lengthBytes, 0, message, 0, 4);
        message[4] = messageType;
        System.arraycopy(payload, 0, message, 5, payload.length);
    
        return message;
    }


    


    public static void main(String[] args){

        MessageCreator messageCreator = new MessageCreator();

     
    }
}
