package Client;

import javax.swing.*;

public class Display extends JFrame {

    public static void main(String[] args) {
        JFrame frame = new JFrame("JustGame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        GameObject game = new GameObject(frame);
        game.in_playing = true;
        //game.player1.changeX(0);
        //game.player1.changeY(1080);
        //game.player1.changeY(frame.getHeight() - 100);
        //game.player2.changeX(0);
        //game.player2.changeY(1080);
        //game.player2.changeY(frame.getHeight() - 100);
        frame.add(game);
        frame.setVisible(true);
    }
}
