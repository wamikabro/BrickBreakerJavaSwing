package brickBreakerGame;

import javax.swing.*;

public class GameFrame{
    JFrame gameFrame;
    ImageIcon imageIcon;
    GameScene gameScene;
    GameFrame(int horizontalBallSpeed, int verticalBallSpeed, int playerSpeed, int bricksRow, int bricksColumn){
        gameFrame = new JFrame("Brick Breaker");
        gameFrame.setSize(700, 600);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setLocationRelativeTo(null); // Centering the frame
        gameFrame.setResizable(false);
        imageIcon = new ImageIcon("brickBreakerGame/iconImage.png");
        gameFrame.setIconImage(imageIcon.getImage());
        gameFrame.add(new GameScene(horizontalBallSpeed, verticalBallSpeed, playerSpeed, bricksRow, bricksColumn)); // Adding the Game Panel, Actual game in the Frame
        gameFrame.setVisible(true);
    }


}
