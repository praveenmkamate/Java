package board;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Score {
    public void writeScore(List<Player> playerList) throws IOException {
        FileOutputStream fileOutputStream;
        BufferedReader bufferedReader;
        try {
            fileOutputStream = new FileOutputStream("TopScores.txt");
        } catch (FileNotFoundException e){
            System.out.println("Score file not found, creating a new file!");
            fileOutputStream = new FileOutputStream(new File("TopScores.txt"));
        }

        bufferedReader = new BufferedReader(new FileReader("TopScores.txt"));
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

        if(bufferedReader.readLine() == null){
            writeFile(playerList,fileOutputStream,objectOutputStream);
        } else {

        }

    }

    public void updateFile(List<Player> playerList, FileOutputStream fileOutputStream, ObjectOutputStream objectOutputStream){

        List<Player> playersInFile = new ArrayList<>();

        while(objectOutputStream.)

    }

    public void writeFile(List<Player> playerList, FileOutputStream fileOutputStream, ObjectOutputStream objectOutputStream){
        try {
            for(int i=0; i<playerList.size();i++){
                objectOutputStream.writeObject(playerList.get(i));
            }

            objectOutputStream.close();
            fileOutputStream.close();

            /*FileInputStream fi = new FileInputStream(new File("myObjects.txt"));
            ObjectInputStream oi = new ObjectInputStream(fi);

            // Read objects
            Person pr1 = (Person) oi.readObject();
            Person pr2 = (Person) oi.readObject();

            System.out.println(pr1.toString());
            System.out.println(pr2.toString());

            oi.close();
            fi.close();
*/
        } catch (IOException e) {
            System.out.println("File not found");
        }
    }
}