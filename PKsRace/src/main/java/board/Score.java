package board;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * This class is used to create or update the Score Board text file.
 */
public class Score {

    /**
     * This contains the final list of players to be updated on file.
     */
    public static List<Player> finalPlayerList = new ArrayList<>();

    /**
     * playerDictionary is used to store the location and player name of the player.
     */
    public static Map<String, Integer> playerDictionary = new HashMap<>();

    /**
     * This method is used to update the player dictionary.
     * @param playerList The list of players for which the dictionary has to be updated.
     */
    public void updatePlayerDictionary(List<Player> playerList) {
        playerDictionary.clear();
        String name = "";
        for (int i = 0; i < playerList.size(); i++) {
            name = playerList.get(i).getName();
            playerDictionary.put(name, i);
        }
    }

    /**
     * This method is used to read the score.
     * @param player            The PlayerScore object for this current game.
     * @param fileInputStream   The file input stream to read the files.
     * @param objectInputStream The object input stream to read the objects.
     */
    public void readScore(PlayerScore player, FileInputStream fileInputStream, ObjectInputStream objectInputStream) {

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

            if (playerDictionary.containsKey(currentPlayerCompare.getName())) {
                int loc = playerDictionary.get(currentPlayerCompare.getName());

                Player playerOnFileCompare = playerOnFile.get(loc);

                if (playerOnFileCompare.getScore() < currentPlayerCompare.getScore()) {
                    playerOnFile.set(loc, currentPlayerCompare);
                }

            } else {
                playerOnFile.add(currentPlayerCompare);
                playerDictionary.put(currentPlayerCompare.getName(), playerOnFile.indexOf(currentPlayerCompare));
            }

        }
        playerScore.setPlayers(playerOnFile);
        writeFile(playerScore);
    }


    /**
     * This method is called when player wants to display the top scores.
     * @param playerList The list of players for the current games.
     * @return returns the final list of updated players.
     */
    public List<Player> writeScore(List<Player> playerList) {

        PlayerScore playerScore = new PlayerScore(playerList);
        File topScores = new File("TopScores.txt");

        if (topScores.exists()) {
            FileInputStream fileInputStream = null;
            ObjectInputStream objectInputStream = null;
            try {
                fileInputStream = new FileInputStream(topScores);
                objectInputStream = new ObjectInputStream(fileInputStream);
                readScore(playerScore, fileInputStream, objectInputStream);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        } else {
            try {
                topScores.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            writeFile(playerScore);
        }

        return finalPlayerList;

    }

    /**
     * This method is used to write the PlayerScore in the Score file.
     * @param playerScore The list of players to be stored into file as Player Score object.
     */
    public void writeFile(PlayerScore playerScore) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("TopScores.txt");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(playerScore);
            objectOutputStream.close();
            fileOutputStream.close();

            finalPlayerList = playerScore.getPlayers();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
