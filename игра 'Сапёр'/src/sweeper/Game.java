package sweeper;

public class Game {

    private Bomb bomb;
    private Flag flag;
    private GameState state;

    public GameState getState() { // возвращение состояния игры
        return state;
    }

    public Game(int cols, int rows, int bombs) { // инициализация объекта в зависимости от количества столбцов, строк и бомб
        Ranges.setSize(new Coord(cols, rows));
        bomb = new Bomb(bombs);
        flag = new Flag();
    }

    public void start() { // главная функция, запускаете игру
        bomb.start();
        flag.start();
        state = GameState.PLAYED;
    }

    public Box getBox(Coord coord) { // возвращение игровой клетки
        if (flag.get(coord) == Box.OPENED)
            return bomb.get(coord);
        else
            return flag.get(coord);
    }

    public void pressLeftButton(Coord coord) { // нажатие левой клавиши мышки
        if (gameOver())
            return;
        openBox(coord);
        checkWinner();
    }

    private void checkWinner() { // проверка на выигрыш
        if (state == GameState.PLAYED)
            if (flag.getCountOfClosedBoxes() == bomb.getTotalBombs())
                state = GameState.WINNER;
    }

    private void openBox(Coord coord) { // открытие клетки
        switch (flag.get(coord)) {
            case OPENED: setOpenedToClosedBoxesAroundNumber(coord); return;
            case FLAGED: return;
            case CLOSED:
                switch (bomb.get(coord)) {
                    case ZERO -> openBoxesAround(coord);
                    case BOMB -> openBombs(coord);
                    default -> flag.setOpenedToBox(coord);
                }
        }
    }

    void setOpenedToClosedBoxesAroundNumber(Coord coord) { // открытие закрытых клеток вокруг
        if (bomb.get(coord) != Box.BOMB)
            if (flag.getCountToFlagedBoxesAround(coord) == bomb.get(coord).getNumber())
                for (Coord around : Ranges.getCoordsAround(coord))
                    if (flag.get(around) == Box.CLOSED)
                        openBox(around);
    }

    private void openBombs(Coord bombed) { // открытие бомбы
        state = GameState.BOMBED;
        flag.setBombedToBox(bombed);
        for (Coord coord : Ranges.getAllCoords())
            if (bomb.get(coord) == Box.BOMB)
                flag.setOpenedToClosedBombBox(coord);
            else
                flag.setNobombToFlagedSafeBox(coord);
    }

    private void openBoxesAround(Coord coord) { // открытие клеток вокруг
        flag.setOpenedToBox(coord);
        for (Coord around : Ranges.getCoordsAround(coord))
            openBox(around);
    }

    public void pressRightButton(Coord coord) { // нажатие правой клавиши мышки
        if (gameOver())
            return;
        flag.toggleFlagedToBox(coord);
    }

    private boolean gameOver() { // конец игры
        if (state == GameState.PLAYED)
            return false;
        start();
        return true;
    }
}