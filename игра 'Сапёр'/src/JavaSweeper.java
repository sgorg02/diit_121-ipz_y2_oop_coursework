import javax.swing.*;
import java.awt.*;
import sweeper.Box;
import sweeper.Coord;
import sweeper.Game;
import sweeper.Ranges;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class JavaSweeper extends JFrame {

    private Game game;

    private JPanel panel;
    private JLabel label;
    private final int COLS = 9;
    private final int ROWS = 9;
    private final int BOMBS = 10;
    private final int IMAGE_SIZE = 50;


    public static void main(String[] args) {
        new JavaSweeper();
    }

    private JavaSweeper() {
        game = new Game(COLS, ROWS, BOMBS);
        game.start();
        setImages();
        initLabel();
        initPanel();
        initFrame();
    }

    private void initLabel() { // инициализация надписи
        label = new JLabel("Welcome");
        add(label, BorderLayout.NORTH);
    }

    private void initPanel() { // инициализация панели
        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for (Coord coord : Ranges.getAllCoords()) {
                    g.drawImage((Image) game.getBox(coord).image,
                            coord.x * IMAGE_SIZE, coord.y * IMAGE_SIZE, this);
                }
            }
        };

        panel.addMouseListener(new MouseAdapter() { // добавление мышечного адаптера
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX() / IMAGE_SIZE;
                int y = e.getY() / IMAGE_SIZE;
                Coord coord = new Coord(x, y);
                if (e.getButton() == MouseEvent.BUTTON1)
                    game.pressLeftButton(coord);
                if (e.getButton() == MouseEvent.BUTTON2)
                    game.start();
                if (e.getButton() == MouseEvent.BUTTON3)
                    game.pressRightButton(coord);
                label.setText(getMessage());
                panel.repaint();
            }
        });
        panel.setPreferredSize(new Dimension(
                Ranges.getSize().x * IMAGE_SIZE,
                Ranges.getSize().y * IMAGE_SIZE)); // устанавливаем розмер панели
        add(panel);
    }

    private String getMessage() {
        return switch (game.getState()) {
            case PLAYED -> "Будь внимательным!";
            case BOMBED -> "Ты проиграл!!!";
            case WINNER -> "Ты выиграл!!!";
        };
    }

    private void initFrame() { // инициализация формы
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // полное закрите программы
        setTitle("Java Sweeper");
        setResizable(false); // запрет на изменение розмера окна
        setVisible(true); // видно форму
        setIconImage(getImage("icon")); // устанавливаем иконку
        pack(); // устанавливаем минимальный розмер контейнера
        setLocationRelativeTo(null); // устанавливаем окно по центру
    }

    private void setImages() { // загрузка всех .png
        for (Box box : Box.values())
            box.image = getImage(box.name().toLowerCase());
    }

    private Image getImage(String name) { // получение всех .png из папки rec
        String filename = "img/" + name.toLowerCase() + ".png";
        ImageIcon icon = new ImageIcon(getClass().getResource(filename));
        return  icon.getImage();
    }
}