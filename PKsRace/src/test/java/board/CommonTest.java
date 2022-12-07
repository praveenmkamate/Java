package board;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static board.Dice.Directions.*;
import static org.junit.jupiter.api.Assertions.*;

class CommonTest {

    private Common commonTest;
    @BeforeEach
    public void setup(){
        commonTest = new Common();
    }

    @Test
    void getRowValue() {
        assert (commonTest.getRowValue(4, FORWARD) == 3);
        assert (commonTest.getRowValue(4, BACKWARD) == 5);
    }

    @Test
    void getColValue() {
        assert (commonTest.getColValue(4, LEFT) == 3);
        assert (commonTest.getColValue(4, RIGHT) == 5);
    }

}