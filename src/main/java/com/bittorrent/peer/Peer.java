package main.java.com.bittorrent.peer;

public class Peer {

    // Attributes from the 'Common.cfg' file
    public int numOfPreferredNeighbors;
    public int unchokingInterval;
    public int optimisticUnchokingInterval;
    public String fileName;
    public int fileSize;
    public int pieceSize;

    // Attributes from the 'PeerInfo.cfg' file
    public int peerID;
    public String hostName;
    public int listeningPort;
    public boolean hasFile;


    public Peer() {

    }

    public void parseCommonConfig() {

    }

    public void parsePeerInfoConfig() {
        
    }
}
