package sweeper;

class Flag {
    private Matrix flagMap;
    private int countOfClosedBoxes;

    void start() {
        flagMap = new Matrix(Box.CLOSED);
        countOfClosedBoxes = Ranges.getSize().x * Ranges.getSize().y;
    }

    Box get(Coord coord) {
        return flagMap.get(coord);
    }

    void setOpenedToBox(Coord coord) { // установка открытой клетки
        flagMap.set(coord, Box.OPENED);
        countOfClosedBoxes--;
    }

    void toggleFlagedToBox(Coord coord) {
        switch (flagMap.get(coord)){
            case FLAGED -> setClosedToBox(coord);
            case CLOSED -> setFlagedToBox(coord);
        }
    }

    private void setClosedToBox(Coord coord) { // установка закрытой клетки
        flagMap.set(coord, Box.CLOSED);
    }

    private void setFlagedToBox(Coord coord) { // установка флага
        flagMap.set(coord, Box.FLAGED);
    }


    int getCountOfClosedBoxes() { // получаем количества закрытых клеток
        return countOfClosedBoxes;
    }

    public void setBombedToBox(Coord coord) {
        flagMap.set(coord, Box.BOMBED);
    }

    public void setOpenedToClosedBombBox(Coord coord) { // установка открытой клетки заместь закрытой
        if (flagMap.get(coord) == Box.CLOSED)
            flagMap.set(coord, Box.OPENED);
    }

    public void setNobombToFlagedSafeBox(Coord coord) { // установка NOBOMB клетки заместь флага
        if (flagMap.get(coord) == Box.FLAGED)
            flagMap.set(coord, Box.NOBOMB);
    }

    int getCountToFlagedBoxesAround(Coord coord) {
        int count = 0;
        for (Coord around : Ranges.getCoordsAround(coord))
            if (flagMap.get(around) == Box.FLAGED)
                count++;
        return count;
    }
}