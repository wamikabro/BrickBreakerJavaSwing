# brickBreakerGame
The game Brick Breaker is made using Java Swing together with the help of AWT.
It is based on 4 Classes and 1 Icon Image.

1st Class is Main class, holding the Static basic values like Player Speed, Ball Speed and the Number of bricks. Main class will simply create object of GameFrame class.

GameFrame is taking care of the Frame only, while it adds object of GameScene to the frame to start the actual Game. 

GameScene is the class to take care of Functionality of the game, that's why it implements ActionListener and KeyListener.
GameScene is extending JPanel, so it is basically a panel to be added in the frame (as it is added in GameFrame's frame already).
GameScene is also using multithreading to moniter the movements of Ball a little bit. That's why it implements Runnable.
GameScene uses the class Bricks to print the Bricks.

Bricks class has only one thing and that is bricks[][] 2D Array (Map of the Bricks). It Paints that Map. (Then it is called by GameScene Paint method afterwards).

The 5th thing is iconImage. It is the icon of BrickBreakerGame. But in the code it's address is set as "brickBreakerGame/iconImage.png", so it must be kept inside.
The attribute of the icon: https://www.flaticon.com/free-icon/brick-breaker_3069124?term=brick+breaker&page=1&position=2&origin=search&related_id=3069124
