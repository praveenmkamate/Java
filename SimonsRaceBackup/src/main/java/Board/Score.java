package board;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Score {

    public static List<Player> finalPlayerList = new ArrayList<>();

    public static Map<String, Integer> playerDictionary = new HashMap<>();

    public void updatePlayerDictionary(List<Player> playerList) {
        playerDictionary.clear();
        String name = "";
        for (int i = 0; i < playerList.size(); i++) {
            name = playerList.get(i).getName();
            playerDictionary.put(name, i);
        }
    }

    public void readScore(PlayerScore player, FileInputStream fileInputStream, ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {

        List<Player> playerOnFile = null;
        List<Player> currentPlayer = player.getPlayers();
        PlayerScore playerScore = null;

        try {
            playerScore = (PlayerScore) objectInputStream.readObject();
            playerOnFile = playerScore.getPlayers();
        } catch (Exception e) {
            System.out.println("Error in getting Score from file");
        }

        updatePlayerDictionary(playerOnFile);

        for (int i = 0; i < currentPlayer.size(); i++) {
            Player currentPlayerCompare = currentPlayer.get(i);

            if(playerDictionary.containsKey(currentPlayerCompare.getName())) {
                int loc = playerDictionary.get(currentPlayerCompare.getName());

                Player playerOnFileCompare = playerOnFile.get(loc);

                if(playerOnFileCompare.getScore() < currentPlayerCompare.getScore()) {
                    playerOnFile.set(loc,currentPlayerCompare);
                }

            } else {
                playerOnFile.add(currentPlayerCompare);
                playerDictionary.put(currentPlayerCompare.getName(),playerOnFile.indexOf(currentPlayerCompare));
            }

        }
        playerScore.setPlayers(playerOnFile);
        writeFile(playerScore);
    }



    public List<Player> writeScore(List<Player> playerList) throws IOException, ClassNotFoundException {

        PlayerScore playerScore = new PlayerScore(playerList);
        File topScores = new File("TopScores.txt");

        if (topScores.exists()) {
            FileInputStream fileInputStream = new FileInputStream(topScores);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            readScore(playerScore, fileInputStream, objectInputStream);
        } else {
            topScores.createNewFile();
            writeFile(playerScore);
        }

        return finalPlayerList;

    }

    public void writeFile(PlayerScore playerScore) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("TopScores.txt");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(playerScore);
            objectOutputStream.close();
            fileOutputStream.close();

            finalPlayerList =  playerScore.getPlayers();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
