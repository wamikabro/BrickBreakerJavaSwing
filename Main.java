package brickBreakerGame;

public class Main {
    public static int horizontalBallSpeed, verticalBallSpeed, playerSpeed, bricksRow, bricksColumn;
    public static void main(String[] args) {

        // We can directly manipulate the speed of Ball and Player from here
        Main.horizontalBallSpeed = -2;
        Main.verticalBallSpeed = -4;
        Main.playerSpeed = 20;
        Main.bricksRow = 3;
        Main.bricksColumn = 7;
        // Constructing GameFrame class.
        new GameFrame(horizontalBallSpeed, verticalBallSpeed, playerSpeed, bricksRow, bricksColumn);

    }
}
