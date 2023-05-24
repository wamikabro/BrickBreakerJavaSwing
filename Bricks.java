package brickBreakerGame;

import java.awt.*;

public class Bricks {
    public int bricks[][];
    public int brickWidth;
    public int brickHeight;

    public Bricks(int row, int column){
        bricks = new int[row][column];

        for(int i=0; i<row; i++)
            for(int j=0; j<column; j++)
                bricks[i][j] = 1;

        brickWidth = 540/column;
        brickHeight = 150/row;
    }

    public void setBrick(int value, int row, int column){
        bricks[row][column] = value;
    }

    public void draw(Graphics2D draw){
        for(int i=0; i<bricks.length; i++)
            for(int j=0; j<bricks[0].length; j++)
                if(bricks[i][j]>0){
                    draw.setColor(Color.PINK);
                    draw.fillRect(j*brickWidth+80, i*brickHeight+50, brickWidth, brickHeight);

                    draw.setColor(Color.BLACK);
                    draw.setStroke(new BasicStroke(2));
                    draw.drawRect(j*brickWidth+80, i*brickHeight+50, brickWidth, brickHeight);
                }
    }
}
