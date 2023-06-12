package brickBreakerGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameScene extends JPanel implements ActionListener, KeyListener, Runnable {
    private final int bricksRow;
    private final int bricksColumn;
    private int score;
    private boolean play;
    private int numberOfBricks;
    private int delay;
    private int horizontalBallPosition;
    private int verticalBallPosition;
    private int horizontalBallSpeed;
    private int verticalBallSpeed;
    private int playerPositionX; // We don't need Y of the Player in Brick Breaker
    private int playerSpeed;
    private Timer timer;
    private Bricks bricks;
    private Graphics2D g;

    GameScene(int horizontalBallSpeed, int verticalBallSpeed, int playerSpeed, int bricksRow, int bricksColumn) {
        play = false;
        delay = 8;
        horizontalBallPosition = 120;
        verticalBallPosition = 350;
        this.horizontalBallSpeed = horizontalBallSpeed;
        this.verticalBallSpeed = verticalBallSpeed;
        playerPositionX = 275;
        this.playerSpeed = playerSpeed;
        this.bricksRow = bricksRow;
        this.bricksColumn = bricksColumn;
        numberOfBricks = bricksRow * bricksColumn;
        score = 0;
        bricks = new Bricks(bricksRow,bricksColumn);

        // I could've simply used Delay instead of calling Getter.
        timer = new Timer(getDelay(), this);
        timer.start();

        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(true);


    }

    // The method of Graphics to create Game Graphics Player, Ball etc.
    @Override
    public void paint(Graphics paint) {
        /*
        * Keep in mind that the Frame Size is 700 x 600, but we will
          subtract 8 from them because whole frame isn't the display.
        */

        // Setting Panel Color
        paint.setColor(Color.BLACK); // Setting the color to black
        paint.fillRect(0, 0, 692, 592); // Filling whole Panel with Black color

        // Player
        paint.setColor(Color.yellow);
        /* Although this method can directly use attributes of GameScene, I still want to keep the Protocol,
            that's why used getter.*/
        paint.fillRect(getPlayerPositionX(), 550, 100, 8);

        // Bricks
        bricks.draw((Graphics2D) paint);

        // Ball
        paint.setColor(Color.GREEN);
        paint.fillOval(horizontalBallPosition, verticalBallPosition, 20, 20);

        // many things share same color. Borders, Score board, and Try again screen
        paint.setColor(Color.ORANGE);

        // Top Border
        paint.fillRect(0, 0, 692, 5);

        // Left Border
        paint.fillRect(0, 0, 5, 592);

        // Right Border
        paint.fillRect(682, 0, 5, 592);

        // Score
        paint.setFont(new Font("times new roman", Font.BOLD, 20));
        paint.drawString("Score " + score, 550, 30);

        // Lost
        if(getVerticalBallPosition()>=570){
            play = false; // stop the game

            paint.setFont(new Font("times new roman", Font.BOLD, 30));
            paint.drawString("You Lost. Score: " + score, 225, 300);

            paint.setFont(new Font("times new roman", Font.BOLD, 25));
            paint.drawString("Press Enter to Try Again. ", 200, 350);
        }

        // If Won
        if(numberOfBricks<=0){
            play = false; // stop the game

            paint.setFont(new Font("times new roman", Font.BOLD, 30));
            paint.drawString("You Won. Score: "  + score,225, 300);
            paint.drawString("Press Enter to Try Again. ", 200, 350);
        }
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        // If play becomes true
        if(play){
            // Start this thread of Ball Movements
            Thread ballMovements = new Thread(this);
            ballMovements.start();

            // Player Rigid Body
            Rectangle playerRigidBody = new Rectangle(playerPositionX, 550, 100, 8);

            // Ball Rigid Body
            Rectangle ballRigidBody = new Rectangle(horizontalBallPosition, verticalBallPosition, 20, 20);

            // If ball intersected with Player
            if(ballRigidBody.intersects(playerRigidBody))
                setVerticalBallSpeed(-getVerticalBallSpeed());

            // Bricks Rigid Body and Pop
            for(int i=0; i<bricks.bricks.length; i++)     // length of the bricks array of the bricks class
                for(int j=0; j<bricks.bricks[0].length; j++)
                    if(bricks.bricks[i][j]>0){
                        int width = bricks.brickWidth;
                        int height = bricks.brickHeight;
                        int brickPositionX = j*width+80; // Simply finding the position of the particular brick
                        int brickPositionY = i*height+50; // So we can make rectangle there

                        Rectangle brickRigidBody = new Rectangle(brickPositionX, brickPositionY, width, height);
                        if(ballRigidBody.intersects(brickRigidBody)){
                            /*this method will make that particular brick 0,
                            since paint don't paint with 0 value*/
                            bricks.setBrick(0,i,j); // User defined method we made in Bricks class.
                            numberOfBricks--; // reduced one brick from total for the sake of final result.
                            score++; // Breaking brick = Score 1+
                            // Let's check from where the ball hit the brick, so it may reflect back
                            if(horizontalBallPosition+19<=brickPositionX // left
                                    || horizontalBallPosition+1>=brickPositionX+width) // right
                                setHorizontalBallSpeed(-getHorizontalBallSpeed());
                            else
                                setVerticalBallSpeed(-verticalBallSpeed);
                        }
                    }
        }
    }

    // Ball Movements being controlled by 2nd thread.
    @Override
    public void run() {
        // If ball touches Left border
        if(getHorizontalBallPosition() <= 0)
            setHorizontalBallSpeed(-getHorizontalBallSpeed());
            // If ball touches Right border
        else if(getHorizontalBallPosition() >= 660)
            setHorizontalBallSpeed(-getHorizontalBallSpeed());
        // if ball touches Top border
        if(getVerticalBallPosition() <= 0)
            setVerticalBallSpeed(-getVerticalBallSpeed());

        // Moving the ball
        setHorizontalBallPosition(getHorizontalBallPosition()+getHorizontalBallSpeed());
        setVerticalBallPosition(getVerticalBallPosition()+getVerticalBallSpeed());

        // Refresh the screen
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {

        // Move Left if Left key tapped and the player isn't touching the border
        if(e.getKeyCode()==KeyEvent.VK_LEFT){
            play = true;
            moveLeft();
        }

        // Move Right
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            play = true;
            moveRight();
        }

        if(e.getKeyCode()==KeyEvent.VK_ENTER)
            if(!play){
                score = 0;
                // hfhfhsdof
                numberOfBricks = Main.bricksRow * Main.bricksColumn;
                setHorizontalBallPosition(200);
                setVerticalBallPosition(300);
                playerPositionX = 320;

                // Directly asked the Main Class for the initial values,
                // since we already set them Static to reuse them easily
                bricks = new Bricks(Main.bricksRow, Main.bricksColumn);
            }

        // Refresh the painting
        repaint();
    }

    void moveLeft(){
        // Move left according to the speed
        setPlayerPositionX(getPlayerPositionX()-getPlayerSpeed());

        // Just throw back to the place before the border
        if(getPlayerPositionX() <= 5) setPlayerPositionX(6);
    }
    void moveRight(){
        // Move right according to the speed
        setPlayerPositionX(getPlayerPositionX()+getPlayerSpeed());

        // Just throw back to the place before the border
        if(getPlayerPositionX() >= 581) setPlayerPositionX(580);
    }

    // We don't need to use them
    @Override
    public void keyTyped(KeyEvent e) {

    }
    @Override
    public void keyReleased(KeyEvent e) {

    }


    // Getters and Setters
    public boolean isPlay() {
        return play;
    }

    public void setPlay(boolean play) {
        this.play = play;
    }

    public int getNumberOfBricks() {
        return numberOfBricks;
    }

    public void setNumberOfBricks(int numberOfBricks) {
        this.numberOfBricks = numberOfBricks;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public int getHorizontalBallPosition() {
        return horizontalBallPosition;
    }

    public void setHorizontalBallPosition(int horizontalBallPosition) {
        this.horizontalBallPosition = horizontalBallPosition;
    }

    public int getVerticalBallPosition() {
        return verticalBallPosition;
    }

    public void setVerticalBallPosition(int verticalBallPosition) {
        this.verticalBallPosition = verticalBallPosition;
    }

    public int getHorizontalBallSpeed() {
        return horizontalBallSpeed;
    }

    public void setHorizontalBallSpeed(int horizontalBallSpeed) {
        this.horizontalBallSpeed = horizontalBallSpeed;
    }

    public int getVerticalBallSpeed() {
        return verticalBallSpeed;
    }

    public void setVerticalBallSpeed(int verticalBallSpeed) {
        this.verticalBallSpeed = verticalBallSpeed;
    }

    public int getPlayerPositionX() {
        return playerPositionX;
    }

    public void setPlayerPositionX(int playerPositionX) {
        this.playerPositionX = playerPositionX;
    }

    public int getPlayerSpeed() {
        return playerSpeed;
    }

    public void setPlayerSpeed(int playerSpeed) {
        this.playerSpeed = playerSpeed;
    }


}
