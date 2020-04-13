package Client;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GameObject extends JPanel implements ActionListener
{
    JFrame frame;

    private static int startPositionX;
    private static int startPositionY;

    Image imgPlayer1 = new ImageIcon("sprites/player1.jpg").getImage();
    Image imgPlayer2 = new ImageIcon("sprites/player2.jpg").getImage();
    Image imgBackground = new ImageIcon("sprites/background.jpg").getImage();
    Image imgFloor = new ImageIcon("sprites/floor.jpg").getImage();

    Timer timer = new Timer(1, this);

    Player player1;
    Player player2;

    boolean in_playing = false;

    public Room room;

    public GameObject(JFrame Frame) {
        timer.start();
        this.frame = Frame;

        //room = new Room(frame.getHeight() - 50, frame.getWidth() - 50);
        room = new Room(1080, 1920);

        room.addObjects(new Platform(0, 850, 50, 400, Platform.Construction.FLOOR));
        room.addObjects(new Platform(400, 600, 50, 400, Platform.Construction.FLOOR));
        room.addObjects(new Platform(800, 400, 50, 200, Platform.Construction.FLOOR));
        room.addObjects(new Platform(0, 350, 50, 400, Platform.Construction.FLOOR));
        room.addObjects(new Platform(1500, 520, 50, 100, Platform.Construction.FLOOR));
        room.addObjects(new Platform(1700, 800, 50, 220, Platform.Construction.FLOOR));

        room.addObjects(new Platform(0, 1030, 50, 1920, Platform.Construction.FLOOR));

        startPositionX = 0;
        startPositionY = 1030;

        player1 = new Player(startPositionX, startPositionY, 20);
        player2 = new Player(startPositionX, startPositionY, 20);
        Frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                player1.keyPressed(e);
            }
            @Override
            public void keyReleased(KeyEvent e) {
                player1.keyReleased(e);
            }
        });
    }

    public void paint(Graphics g)
    {
        g.drawImage(imgBackground, 0, 0,frame.getWidth(), frame.getHeight(), null);
        if (in_playing) {
            for (Platform e: room.platforms)
            {
                g.drawImage(imgFloor, e.x, e.y, e.width, e.height, null);
            }
            g.drawImage(imgPlayer1, player1.getX(), player1.getY() - 50, 50, 50, null);
            g.drawImage(imgPlayer2, player2.getX(), player2.getY() - 50, 50, 50, null);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        repaint();
        player1.move(room);
    }

}