package board;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void addScoreTest() {
        Player testPlayer = new Player("testPlayer");
        testPlayer.addScore(5);
        assert (testPlayer.getScore() == 5);

        testPlayer.addScore(-5);
        assert (testPlayer.getScore() == 5);
    }

    @Test
    void removeScoreTest() {
        Player testPlayer = new Player("testPlayer");
        testPlayer.setScore(5);
        testPlayer.removeScore(2);
        assert (testPlayer.getScore() == 3);

        testPlayer.removeScore(-5);
        assert (testPlayer.getScore() == 3);

        testPlayer.removeScore(5);
        assert (testPlayer.getScore() == 0);


    }
}