package com.bittorrent.peer;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.logging.Logger;

public class WritingLog {
    private Peer peer;
    private int peerID;
    private Logger messageLogger;

    public WritingLog(Peer peer) {
        this.peer = peer;
        this.peerID = peer.peerID;
        this.messageLogger = Logger.getLogger(Integer.toString(peerID));

        //Creating the log file directory for the peer
        String directoryPath = "/log_peer_" + Integer.toString(peerID) + ".log";
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            if (directory.mkdirs()) {
                System.out.println("Directory created: " + directoryPath);
            } else {
                System.out.println("Failed to create directory: " + directoryPath);
                return;
            }
        }
    }

    public static String getCurrentTime(){
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd 'at' HH:mm:ss");
        return currentTime.format(formatter);
    }

    public void tcpConnect(int peerID, int peerID_2){
        messageLogger.info(getCurrentTime() + "Peer " + peerID + " makes a connection to Peer " + peerID_2 + ".");
    }

    public void connect(int peerID, int peerID_2){
        messageLogger.info(getCurrentTime() + "Peer " + peerID + " is connected from Peer " + peerID_2 + ".");
    }

    public void preferredNeighborChange(int peerID, int[] neighborsID){
        messageLogger.info(getCurrentTime() + "Peer " + peerID + " has the preferred neighbors " + Arrays.toString(neighborsID) + ".");
    }

    public void optimisticallyUnchockedChange(int peerID, int peerID_2){
        messageLogger.info(getCurrentTime() + "Peer " + peerID + " has the optimistically unchoked neighbor " + peerID_2 + ".");
    }

    public void unchoking(int peerID, int peerID_2){
        messageLogger.info(getCurrentTime() + "Peer " + peerID + " is unchoked by " + peerID_2 + ".");
    }

    public void choking(int peerID, int peerID_2){
        messageLogger.info(getCurrentTime() + "Peer " + peerID + " is choked by " + peerID_2 + ".");
    }

    public void receivingHave(int peerID, int peerID_2, int pieceIndex){
        messageLogger.info(getCurrentTime() + "Peer " + peerID + " received the ‘have’ message from " + peerID_2 + "for the piece" + pieceIndex + ".");
    }

    public void interested(int peerID, int peerID_2){
        messageLogger.info(getCurrentTime() + "Peer " + peerID + " received the ‘interested’ message from " + peerID_2 + ".");
    }

    public void notInterested(int peerID, int peerID_2){
        messageLogger.info(getCurrentTime() + "Peer " + peerID + " received the ‘not interested’ message from " + peerID_2 + ".");
    }

    public void downloading(int peerID, int peerID_2, int pieceIndex, int numPieces){
        messageLogger.info(getCurrentTime() + "Peer " + peerID + " has downloaded the piece " + pieceIndex + "from" + peerID_2 + ". Now the number of pieces it has is" + numPieces + ".");
    }

    public void downloaded(int peerID){
        messageLogger.info(getCurrentTime() + "Peer " + peerID + " has downloaded the complete file.");
    }

    public static void main(String[] args) {
        String currentTimeString = getCurrentTime();
        System.out.println(currentTimeString);
    }
}
