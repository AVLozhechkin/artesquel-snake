package artesquelsnake.snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Game extends JPanel implements KeyListener, ActionListener {
    // Координаты змеи
    private int[] xPosition = new int[800];
    private int[] yPosition = new int[600];
    // Возможные координаты еды
    private int[] foodXPosition = {0, 25, 50, 75, 100, 125, 150, 175, 200, 225, 250, 275, 300, 325, 350, 375, 400, 425,
            450, 475, 500, 525, 550, 575, 600, 625, 650, 675, 700, 725, 750};
    private int[] foodYPosition = {50, 75, 100, 125, 150, 175, 200, 225, 250, 275, 300, 325, 350, 375, 400, 425, 450,
            475, 500, 525};
    private Random rand = new Random();

    private int xpos = rand.nextInt(31);
    private int ypos = rand.nextInt(20);

    // Число для определения направления движения 0 - нет движения, 1 - Вправо, 2 - Вниз, 3 - Влево, 4 - Вверх, 5 - конец движения (Замена булевым переменным,
    // так как код упрощается при использовании switch
    private int moveDirection = 0;
    //Значение для выбора цвета 0 - Зелёный; 1 - Красный; 2 - Синий
    private int currentChoice = 0;
    private Timer timer;
    private int length = 3;

    private boolean isMoving = false;
    private boolean isStarted = false;

    private ImageIcon upArrow;
    private ImageIcon choosingColor;
    private ImageIcon downArrow;
    private ImageIcon chooseRed;
    private ImageIcon chooseGreen;
    private ImageIcon chooseBlue;

    private ImageIcon mouthUp;
    private ImageIcon mouthDown;
    private ImageIcon mouthRight;
    private ImageIcon mouthLeft;

    private ImageIcon body;
    private ImageIcon score;
    private ImageIcon food;
    private Font myFont;

    public Game() {
        try {
            myFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/assets/myFont.otf"));
        } catch (FontFormatException e) {
            System.out.println("FontFormatException при загрузке шрифта");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("IOException при загрузке шрифта");
            e.printStackTrace();
        }
        myFont = myFont.deriveFont(56f);

        body = new ImageIcon("src/assets/bodyGreen.png");
        food = new ImageIcon("src/assets/food.png");
        choosingColor = new ImageIcon("src/assets/chooseColor.png");
        upArrow = new ImageIcon("src/assets/upArrow.png");
        downArrow = new ImageIcon("src/assets/downArrow.png");
        chooseRed = new ImageIcon("src/assets/chooseRed.png");
        chooseBlue = new ImageIcon("src/assets/chooseBlue.png");
        chooseGreen = new ImageIcon("src/assets/chooseGreen.png");
        score = new ImageIcon("src/assets/score.png");

        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(100, this);
        timer.start();

    }

    public void paint(Graphics g) {
        if (isStarted) {
            if (moveDirection < 5) {
                g.setColor(Color.black);
                g.fillRect(0, 0, 800, 600);
                if (!isMoving) {
                    xPosition[3] = 50;
                    xPosition[2] = 75;
                    xPosition[1] = 100;
                    xPosition[0] = 125;
                    yPosition[3] = 100;
                    yPosition[2] = 100;
                    yPosition[1] = 100;
                    yPosition[0] = 100;
                }
                g.setColor(Color.yellow);
                g.drawLine(0, 50, 800, 50);
                score.paintIcon(this, g, 0, 0);
                g.setFont(myFont);
                g.drawString(Integer.toString(length - 3), 250, 48);

                mouthRight.paintIcon(this, g, xPosition[0], yPosition[0]);
                food.paintIcon(this, g, foodXPosition[xpos], foodYPosition[ypos]);

                for (int i = 0; i < length; i++) {
                    if (i == 0 && moveDirection == 1) {
                        mouthRight.paintIcon(this, g, xPosition[i], yPosition[i]);
                    }
                    if (i == 0 && moveDirection == 3) {
                        mouthLeft.paintIcon(this, g, xPosition[i], yPosition[i]);
                    }
                    if (i == 0 && moveDirection == 4) {
                        mouthUp.paintIcon(this, g, xPosition[i], yPosition[i]);
                    }
                    if (i == 0 && moveDirection == 2) {
                        mouthDown.paintIcon(this, g, xPosition[i], yPosition[i]);
                    }
                    if (i != 0) {
                        body.paintIcon(this, g, xPosition[i], yPosition[i]);
                    }
                }
                for (int i = 3; i < length; i++) {
                    if (xPosition[i] == xPosition[0] && yPosition[i] == yPosition[0]) {
                        switch (moveDirection) {
                            case 1:
                                mouthRight.paintIcon(this, g, xPosition[0], yPosition[0]);
                                break;
                            case 2:
                                mouthDown.paintIcon(this, g, xPosition[0], yPosition[0]);
                                break;
                            case 3:
                                mouthLeft.paintIcon(this, g, xPosition[0], yPosition[0]);
                                break;
                            case 4:
                                mouthUp.paintIcon(this, g, xPosition[0], yPosition[0]);
                                break;
                        }
                        moveDirection = 5;
                    }
                }
                if (foodXPosition[xpos] == xPosition[0] && foodYPosition[ypos] == yPosition[0]) {
                    length++;
                    xpos = rand.nextInt(31);
                    ypos = rand.nextInt(20);
                }
            }
        } else {
            choosingColor.paintIcon(this, g, 100, 100);
            upArrow.paintIcon(this, g, 375, 250);
            switch (currentChoice) {
                case 0:
                    chooseGreen.paintIcon(this, g, 250, 300);
                    break;
                case 1:
                    chooseRed.paintIcon(this, g, 250, 300);
                    break;
                case 2:
                    chooseBlue.paintIcon(this, g, 250, 300);
                    break;
            }
            downArrow.paintIcon(this, g, 375, 400);
        }
        g.dispose();

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        // Флаги для определения направления движения 1 - Вправо, 2 - Вниз, 3 - Влево, 4 - Вверх
        switch (moveDirection) {
            case 1:
                for (int i = length - 1; i >= 0; i--) {
                    yPosition[i + 1] = yPosition[i];
                }
                for (int i = length; i >= 0; i--) {
                    if (i == 0) {
                        xPosition[i] = xPosition[i] + 25;
                    } else {
                        xPosition[i] = xPosition[i - 1];
                    }
                    if (xPosition[i] > 750) {
                        xPosition[i] = 0;
                    }
                }
                repaint();
                break;
            case 2:
                for (int i = length - 1; i >= 0; i--) {
                    xPosition[i + 1] = xPosition[i];
                }
                for (int i = length; i >= 0; i--) {
                    if (i == 0) {
                        yPosition[i] = yPosition[i] + 25;
                    } else {
                        yPosition[i] = yPosition[i - 1];
                    }
                    if (yPosition[i] > 525) {
                        yPosition[i] = 50;
                    }
                }
                repaint();
                break;
            case 3:
                for (int i = length - 1; i >= 0; i--) {
                    yPosition[i + 1] = yPosition[i];
                }
                for (int i = length; i >= 0; i--) {
                    if (i == 0) {
                        xPosition[i] = xPosition[i] - 25;
                    } else {
                        xPosition[i] = xPosition[i - 1];
                    }
                    if (xPosition[i] < 0) {
                        xPosition[i] = 750;
                    }
                }
                repaint();
                break;
            case 4:
                for (int i = length - 1; i >= 0; i--) {
                    xPosition[i + 1] = xPosition[i];
                }
                for (int i = length; i >= 0; i--) {
                    if (i == 0) {
                        yPosition[i] = yPosition[i] - 25;
                    } else {
                        yPosition[i] = yPosition[i - 1];
                    }
                    if (yPosition[i] < 50) {
                        yPosition[i] = 525;
                    }
                }
                repaint();
                break;
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!isStarted) {
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                if (currentChoice < 2) currentChoice++;
                else currentChoice = 0;
            }
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                if (currentChoice > 0) currentChoice--;
                else currentChoice = 2;
            }
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                isStarted = true;
                String color;
                switch (currentChoice){
                    case 0:
                        color="Green";
                        break;
                    case 1:
                        color="Red";
                        break;
                    case 2:
                        color="Blue";
                        break;
                    default:
                        color = "Green";
                }
                mouthUp = new ImageIcon("src/assets/upMouth" + color + ".png");
                mouthDown = new ImageIcon("src/assets/downMouth" + color + ".png");
                mouthLeft = new ImageIcon("src/assets/leftMouth" + color + ".png");
                mouthRight = new ImageIcon("src/assets/rightMouth" + color + ".png");
                body = new ImageIcon("src/assets/body" + color + ".png");
            }
        }
        //1 - Вправо, 2 - Вниз, 3 - Влево, 4 - Вверх
        else {
            isMoving = true;
            if (moveDirection != 5)
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_RIGHT:
                        if (moveDirection != 3) moveDirection = 1;
                        break;
                    case KeyEvent.VK_DOWN:
                        if (moveDirection != 4) moveDirection = 2;
                        break;
                    case KeyEvent.VK_LEFT:
                        if (moveDirection != 1) moveDirection = 3;
                        break;
                    case KeyEvent.VK_UP:
                        if (moveDirection != 2) moveDirection = 4;
                        break;
                }
            else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                moveDirection = 0;
                isMoving = false;
                length = 3;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
