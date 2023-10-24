package com.bittorrent.peer;

import java.io.*;
import java.util.Properties;

public class Peer {

    // Attributes from the 'Common.cfg' file
    public int numOfPreferredNeighbors;
    public int unchokingInterval;
    public int optimisticUnchokingInterval;
    public String fileName;
    public int fileSize;
    public int pieceSize;
    public int numPieces;

    // Attributes from the 'PeerInfo.cfg' file
    public int peerID;
    public String hostName;
    public int listeningPort;
    public boolean hasFile;
    public byte[] file;
    public Peer() {

    }

    public void parseCommonConfig() {
        Properties prop = new Properties();

        // Used to locate the config file within the 'resources' folder provided by Maven
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream input = classLoader.getResourceAsStream("Common.cfg");

        if (input == null) {
            throw new IllegalArgumentException("Common.cfg file not found!");
        }

        // Processes the config file and creates a list of all available properties found within the file.
        try {
            prop.load(input);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // Configures the peers with all the available configs.
        this.numOfPreferredNeighbors = Integer.parseInt(prop.getProperty("NumberOfPreferredNeighbors"));
        System.out.println("\nNumberOfPreferredNeighbors " + this.numOfPreferredNeighbors);
        this.unchokingInterval = Integer.parseInt(prop.getProperty("UnchokingInterval"));
        System.out.println("UnchokingInterval " + this.unchokingInterval);
        this.optimisticUnchokingInterval = Integer.parseInt(prop.getProperty("OptimisticUnchokingInterval"));
        System.out.println("OptimisticUnchokingInterval " + this.optimisticUnchokingInterval);
        this.fileName = prop.getProperty("FileName");
        System.out.println("FileName " + this.fileName);
        this.fileSize = Integer.parseInt(prop.getProperty("FileSize"));
        System.out.println("FileSize " + this.fileSize);
        this.pieceSize = Integer.parseInt(prop.getProperty("PieceSize"));
        System.out.println("PieceSize " + this.pieceSize);
        //Here we calculate the number of pieces in the file
        double result = (double)this.fileSize/this.pieceSize;
        this.numPieces = (int)Math.ceil(result);
    }

    public void parsePeerInfoConfig() {
        // Used to locate the config file within the 'resources' folder provided by Maven
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream input = classLoader.getResourceAsStream("PeerInfo.cfg");

        if (input == null) {
            throw new IllegalArgumentException("PeerInfo.cfg file not found!");
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            String line;
            while ((line = reader.readLine()) != null) {
                // Split the line into sections using the space in-between as a delimiter
                String[] tokens = line.split(" ");

                // Ensure that each line is formatted correctly
                if (tokens.length == 4) {
                    // First token
                    this.peerID = Integer.parseInt(tokens[0]);
                    // Second token
                    this.hostName = tokens[1];
                    // Third token
                    this.listeningPort = Integer.parseInt(tokens[2]);
                    // Fourth token, contains file is a boolean for true (1) or false (0)
                    int containsFile = Integer.parseInt(tokens[3]);
                    if (containsFile == 1)
                        this.hasFile = true;
                    else
                        this.hasFile = false;
                    System.out.println(this.peerID + " " + this.hostName + " " + this.listeningPort + " " + this.hasFile);
                }
                else {
                    throw new IllegalArgumentException("PeerInfo config file is not formatted properly!");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void peerDirectory() {
        FileOutputStream fileOutputStream = null;

        //creates a subDirectory for the peer with it's peerID
        String directoryPath = "/peer_" + peerID;
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            if (directory.mkdirs()) {
                System.out.println("Directory created: " + directoryPath);
            } else {
                System.out.println("Failed to create directory: " + directoryPath);
                return;
            }
        }
        try {
            File filePath = new File(directory, fileName);
            filePath.createNewFile();
            fileOutputStream = new FileOutputStream(filePath);

            for (int i = 0; i < numPieces; i++) {
                fileOutputStream.write(file[i]);
            }
        } catch (FileNotFoundException fileNotFound) {
            System.out.println("FileNotFoundException caught file is either a directory or does not exist.");
            fileNotFound.printStackTrace();
        } catch (IOException ioException) {
            System.out.println("IOException caught from function createNewFile.");
            ioException.printStackTrace();
        }

        if (fileOutputStream != null) {
            try {
                fileOutputStream.flush();
                fileOutputStream.close();
            } catch (IOException ioException) {
                System.out.println("IOException caught from function flush or close");
                ioException.printStackTrace();
            }
        }
    }
    public static void main (String[] args) {
        Peer test = new Peer();
        test.parseCommonConfig();
        System.out.println();
        test.parsePeerInfoConfig();
    }
}
