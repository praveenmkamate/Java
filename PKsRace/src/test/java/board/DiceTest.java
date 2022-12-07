package board;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiceTest {

    private Dice dice;
    @BeforeEach
    public void setup(){
        dice = new Dice();
    }

    @Test
    void generateCountTest() {
        int min = 1;
        int max = 4;
        int count = dice.generateCount();
        assertTrue(min <= count && count <= max);
    }

}