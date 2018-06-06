package artesquelsnake.snake;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame();
        Game game = new Game();

        window.setTitle("Artesquel Snake");
        window.setBounds(0, 0, 800, 600);
        window.setBackground(Color.BLACK);
        window.setResizable(false);
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.add(game);

    }
}
