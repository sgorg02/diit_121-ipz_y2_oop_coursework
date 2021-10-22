package sweeper;

class Bomb {
    private Matrix bombMap;
    private int totalBombs;

    Bomb(int totalBombs) { // инициализация объекта в зависимости от количества бомб
        this.totalBombs = totalBombs;
        fixBombsCount();
    }

    void start() { // установление бомб на поле игры
        bombMap = new Matrix(Box.ZERO);
        for (int j = 0; j < totalBombs; j++)
            placeBomb(); // устанавливаем бомбы
    }

    Box get(Coord coord) { // получение бомбы по заданой координате
        return bombMap.get(coord);
    }

    private void fixBombsCount() { // устанавливает максимально фиксированное количество бомб
        int maxBombs = Ranges.getSize().x * Ranges.getSize().y / 2; // бомб не больше половины клеток
        if (totalBombs > maxBombs)
            totalBombs = maxBombs;
    }

    private void placeBomb() { // установка бомбы
        while (true) { // проверка бомбы на бомбу
            Coord coord = Ranges.getRandomCoord();
            if (Box.BOMB == bombMap.get(coord))
                continue;
            bombMap.set(coord, Box.BOMB);
            incNumberAroundBomb(coord);
            break;
        }
    }

    private void incNumberAroundBomb(Coord coord) { // устанавливаем номера вокруг бомбы
        for (Coord around : Ranges.getCoordsAround(coord))
            if (Box.BOMB != bombMap.get(around))
                bombMap.set(around, bombMap.get(around).getNextNumberBox());
    }

    int getTotalBombs() { // получаем все бомбы
        return totalBombs;
    }
}