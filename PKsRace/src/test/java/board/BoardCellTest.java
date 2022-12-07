package board;

import obstacle.Ice;
import org.junit.jupiter.api.Test;

import static board.Common.ObstacleType.ICE;
import static org.junit.jupiter.api.Assertions.*;
class BoardCellTest {

    @Test
    void createObstacleTest() {
        BoardCell boardCell = new BoardCell();
        assertTrue(boardCell.createObstacle(ICE) instanceof Ice);
    }
}