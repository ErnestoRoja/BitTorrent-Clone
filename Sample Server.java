package com.bittorrent.server;

import com.bittorrent.peer.Peer;
import java.net.*;
import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.util.*;

public class Server {

    private static final int sPort = 8000;   //The server will be listening on this port number
    private Peer peer;

    public Server(Peer peer){
        this.peer = peer;
    }
    public static void main(String[] args) throws Exception {
        System.out.println("The server is running.");
        ServerSocket listener = null;
        try{
            listener = new ServerSocket(sPort);
        }catch (IOException e) {
            System.err.println("Couldn't start Server");
            e.printStackTrace();
        }

        int clientNum = 1;
        try {
            while (true) {
                new Handler(listener.accept(),clientNum).start();
                System.out.println("Client "  + clientNum + " is connected!");
                clientNum++;
            }
        } finally {
            listener.close();
        }

    }

    /**
     * A handler thread class.  Handlers are spawned from the listening
     * loop and are responsible for dealing with a single client's requests.
     */
    private static class Handler extends Thread {
        private String message;    //message received from the client
        private String MESSAGE;    //uppercase message send to the client
        private Socket connection;
        private ObjectInputStream in;	//stream read from the socket
        private ObjectOutputStream out;    //stream write to the socket
        private int indexNum;		//The index number of the client

        public Handler(Socket connection, int no) {
            this.connection = connection;
            this.indexNum = no;
        }

        public void run() {
            try {
                //initialize Input and Output streams
                out = new ObjectOutputStream(connection.getOutputStream());
                out.flush();
                in = new ObjectInputStream(connection.getInputStream());
                try {
                    while (true) {
                        //receive the message sent from the client
                        message = (String)in.readObject();
                        //show the message to the user
                        System.out.println("Receive message: " + message + " from client " + indexNum);
                        //Capitalize all letters in the message
                        MESSAGE = message.toUpperCase();
                        //send MESSAGE back to the client
                        sendMessage(MESSAGE);
                    }
                }
                catch(ClassNotFoundException classnot) {
                    System.err.println("Data received in unknown format");
                }
            }
            catch(IOException ioException) {
                System.out.println("Disconnect with Client " + indexNum);
            }
            finally {
                //Close connections
                try {
                    in.close();
                    out.close();
                    connection.close();
                }
                catch(IOException ioException) {
                    System.out.println("Disconnect with Client " + indexNum);
                }
            }
        }

        //send a message to the output stream
        public void sendMessage(String msg) {
            try {
                out.writeObject(msg);
                out.flush();
                System.out.println("Send message: " + msg + " to Client " + indexNum);
            }
            catch(IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
}
