package com.bittorrent.peer;
import java.io.*;
import java.util.*;
import java.util.Properties;

public class peerProcess {
    public static void main(String arg[]) throws FileNotFoundException {

        Hashtable<Integer, Peer> peers = new Hashtable<Integer, Peer>();

        // Used to access the PeerInfo.cfg file from the project's 'Resource' folder
        ClassLoader classLoader = peerProcess.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("PeerInfo.cfg");

        if (inputStream != null) {
            Scanner scanner = new Scanner(inputStream);
            while (scanner.hasNextLine()) {
                String currLine = scanner.nextLine();
                String[] parameters = currLine.split(" ");
                Peer peer = new Peer();
                peer.parsePeerInfoConfig(Integer.parseInt(parameters[0]), parameters[1], Integer.parseInt(parameters[2]), Integer.parseInt(parameters[3]));
                peers.put(peer.peerID, peer);
            }
            scanner.close();

            int peerID = Integer.parseInt(arg[0]);

            WritingLog logger = new WritingLog(peers.get(peerID));

            logger.setVariables(peerID, peers.get(peerID).bitField, peers.get(peerID).hostName, peers.get(peerID).listeningPort, peers.get(peerID).hasFile, peers.get(peerID).numOfPreferredNeighbors, peers.get(peerID).unchokingInterval, peers.get(peerID).optimisticUnchokingInterval, peers.get(peerID).fileName, peers.get(peerID).fileSize, peers.get(peerID).pieceSize, peers.get(peerID).numPieces);

        }

        int peerID = Integer.parseInt(arg[0]);

        // making server for peer
        Server server = new Server(peers.get(peerID));
        Thread serverThread = new Thread(server);
        serverThread.start();
    }
}
