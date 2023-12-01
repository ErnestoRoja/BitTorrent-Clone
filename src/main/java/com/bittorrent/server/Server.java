package com.bittorrent.server;



public class Server implements Runnable {

    // creates message handler

    // wait for handshake message
    // starts handler on thread
    private Peer peer;
    private Socket socket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    public Server(Peer peer){
        this.peer = peer;
    }

    public void run(){
        ServerSocket listener = null;
        listener = new ServerSocket(peer.portNumber);
        while(true){
            socket = listener.accept();

            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();

            in = new ObjectInputStream(socket.getInputStream());

            //create message handler this will handle dealing with incoming messages as well as sending responses to messages
            MessageHandler handler = new MessageHandler(in, out, peer, socket); //(assuming we want peer and socket maybe not needed?)
//                peer.setOut(out);

            //start handler on thread
            Thread serverThread = new Thread(handler);
            serverThread.start();
        }

    }


}
