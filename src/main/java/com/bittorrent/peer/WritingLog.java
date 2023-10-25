package com.bittorrent.peer;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class WritingLog {
    public WritingLog(Peer peer) {
        int peerID = peer.peerID;

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

    // Provides the current time of execution.
    public static String getCurrentTime(){
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd 'at' HH:mm:ss");
        return currentTime.format(formatter);
    }

    public static void main(String[] args) {
        String currentTimeString = getCurrentTime();
        System.out.println(currentTimeString);
    }
}
