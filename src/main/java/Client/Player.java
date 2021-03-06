package Client;

import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;

public class Player
{
    private int x;
    private int y;
    private int speedX;
    private int speedY = 0;
    private int accelerationY = 0;

    enum Direction
    {
        UP,
        DOWN,
        RIGHT,
        LEFT,
        NONE
    }

    Direction playerDirection = Direction.NONE;

    public Player(int X, int Y, int speedX)
    {
        this.x = X;
        this.y = Y;
        this.speedX = speedX;
    }

    public void changeX(int newX)
    {
        x = newX;
    }

    public void changeY(int newY)
    {
        y = newY;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSpeedX() {
        return speedX;
    }

    public int getSpeedY() {
        return speedY;
    }


    public boolean isJumping = false;
    public boolean isFalling = false;

// С ускорением

    public void jump(final Room room)
    {
        isJumping = true;
        speedY = 50;
        accelerationY = -5;
        TimerTask task = new TimerTask() {
            @Override
            public void run()
            {
                changeY(y- speedY);

                speedY += accelerationY;

                if (speedY <= 0 && onFloor(room))
                {
                    changeY(y);
                    speedY = 0;
                    accelerationY = 0;
                    isJumping = false;
                    cancel();
                }
            }
        };

        Timer timer = new Timer();
        int delay = 1;
        int period = 35;
        timer.scheduleAtFixedRate(task, delay, period);
    }

// Падение с платформы

    public void fall(final Room room)
    {
        isFalling = true;
        accelerationY = -2;
        TimerTask task = new TimerTask() {
            @Override
            public void run()
            {
                speedY += accelerationY;

                changeY(y- speedY);

                if (onFloor(room))
                {
                    speedY = 0;
                    accelerationY = 0;
                    isFalling = false;
                    cancel();
                }
            }
        };

        Timer timer = new Timer();
        int delay = 1;
        int period = 50;
        timer.scheduleAtFixedRate(task, delay, period);
    }


    public boolean onFloor(Room room)
    {
        if (y >= room.height)
            return true;

        for (Platform e: room.platforms)
        {
            if (y <= e.y + 30 && y >= e.y - 30 && x + 50 >= e.x && x <= e.x + e.width)
            {
                y = room.getPlatformUnderPlayer(this).y;
                return true;
            }
        }

       return false;
    }

    public boolean outOfPlatform(Room room)
    {
        return room.getPlatformUnderPlayer(this) == null;
    }

    public boolean nearEdge(Room room, Direction dir)
    {
       if (dir == Direction.LEFT && x == 0)
           return true;
       else
           return dir == Direction.RIGHT && x == room.width - 50;
    }

    public void move(Room room)
    {
        switch(playerDirection) {
            case UP:
                if (isJumping || isFalling)
                    break;
                this.jump(room);
                break;
            //case DOWN:
            //    y+=speed;
            //    break;
            case LEFT:
                if (nearEdge(room, Direction.LEFT))
                    break;
                if (outOfPlatform(room) && !isJumping)
                    this.fall(room);
                x-= speedX;
                break;
            case RIGHT:
                if (nearEdge(room, Direction.RIGHT))
                    break;
                if (outOfPlatform(room) && !isJumping)
                    this.fall(room);
                x+= speedX;
                break;
            default:
                break;
        }
        playerDirection = Direction.NONE;
    }

    public void keyPressed(KeyEvent e)
    {
        System.out.println(e.getKeyCode());
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_W) {
            playerDirection = Direction.UP;
        }
        //if (key == KeyEvent.VK_S) {
        //    playerDirection = Direction.DOWN;
        //}
        if (key == KeyEvent.VK_A) {
            playerDirection = Direction.LEFT;
        }
        if (key == KeyEvent.VK_D) {
            playerDirection = Direction.RIGHT;
        }

    }

    public void keyReleased(KeyEvent e)
    {

    }
}
