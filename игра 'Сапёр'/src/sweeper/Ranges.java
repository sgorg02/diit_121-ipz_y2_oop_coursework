package sweeper;

import java.util.ArrayList;
import java.util.Random;

public class Ranges { // хранение поля
    private static Coord size;
    private static ArrayList<Coord> allCoords;
    private static Random random = new Random();

    public static void setSize(Coord _size) { // устанавливаем розмер поля
       size = _size;
       allCoords = new ArrayList<>();
       for (int y = 0; y < size.y; y++) // перебор всех координат
           for (int x = 0; x < size.x; x++)
               allCoords.add(new Coord(x, y));
    }

    public static Coord getSize() { // возращаем розмер
        return size;
    }

    public static ArrayList<Coord> getAllCoords() { // возращаем все координаты
        return allCoords;
    }

    static boolean inRange(Coord coord) { // проверка на переполнение масива
        return coord.x >= 0 && coord.x < size.x &&
               coord.y >=0 && coord.y < size.y;
    }
    
    static Coord getRandomCoord() { // возращаем рандомние координаты бомбы
        return new Coord(random.nextInt(size.x),
                         random.nextInt(size.y));
    }
    
    static ArrayList<Coord> getCoordsAround(Coord coord) { // возращаем координаты вокруг заданой координаты
        Coord around;
        ArrayList<Coord> list = new ArrayList<Coord>();

        for (int x = coord.x - 1; x <= coord.x + 1; x++)
            for (int y = coord.y - 1; y <= coord.y + 1; y++)
                if (inRange(around = new Coord(x, y)))
                    if (!around.equals(coord))
                        list.add(around);
        return list;
    }
}