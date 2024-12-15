
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class bot {
    public bot(int x, int y, int xspeed, int yspeed) {
        _pos[0] = x;
        _pos[1] = y;
        _move[0] = xspeed;
        _move[1] = yspeed;
    }

    private final int[] _testSpace = {11, 7};
    private final int[] _fullSpace = {101,103};
    private final int[] _pos = new int[2];
    private final int[] _move = new int[2];

    public void move(boolean test) {
        if (!test) {
            // Checking ot of bounds horizontally
            if ( (_pos[0] + _move[0]) > _testSpace[0]-1 ) {
                _pos[0] = _pos[0] + _move[0] - _testSpace[0];
            } else
            if ( (_pos[0] + _move[0]) < 0) {
                _pos[0] = _testSpace[0] - _pos[0] + _move[0];
            } else {
                _pos[0] += _move[0];
            }
            // Checking out of bounds vertically
            if ( (_pos[1] + _move[1]) > _testSpace[1]-1 ) {
                _pos[1] = _pos[1] + _move[1] - _testSpace[1];
            } else
            if ( (_pos[1] + _move[1]) < 0) {
                _pos[1] = _testSpace[1] + _pos[1] + _move[1];
            } else {
                _pos[1] += _move[1];
            }
        } else {
             // Checking ot of bounds horizontally
            if ( (_pos[0] + _move[0]) > _fullSpace[0]-1 ) {
                _pos[0] = _pos[0] + _move[0] - _fullSpace[0];
            } else
            if ( (_pos[0] + _move[0]) < 0) {
                _pos[0] = _fullSpace[0] + _pos[0] + _move[0];
            } else {
                _pos[0] += _move[0];
            }
            // Checking out of bounds vertically
            if ( (_pos[1] + _move[1]) > _fullSpace[1]-1 ) {
                _pos[1] = _pos[1] + _move[1] - _fullSpace[1];
            } else
            if ( (_pos[1] + _move[1]) < 0) {
                _pos[1] = _fullSpace[1] + _pos[1] + _move[1];
            } else {           
                _pos[1] += _move[1];
            }
        }
    }

    public int[] where() {
        return _pos;
    }

    public int quadrant(boolean test) {
        int limitx = 0;
        int limity = 0;

        if (!test) {
            limitx = _testSpace[0] / 2;
            limity = _testSpace[1] / 2;
        } else {
            limitx = _fullSpace[0] / 2;
            limity = _fullSpace[1] / 2;
        }
        if (_pos[0] < limitx ) {
            if (_pos[1] < limity) {
                // left upper
                return 0;
            } else if (_pos[1] > limity) {
                // left lower
                return 1;
            }
        } else if (_pos[0] > limitx) {
            if (_pos[1] < limity) {
                // right upper
                return 2;
            } else if (_pos[1] > limity) {
                // right lower
                return 3;
            }
        }
        
        System.out.println("In the middle");
        return -1;
    }

    public String asString() {
        return "bot pos= " + _pos[0] + "," + _pos[1] + " with direction: " + _move[0] + ":" + _move[1];
    }
}

public class main {

    public static void main(String[] args) {
        System.out.println("AoC Day 14 Part 1");

        boolean full = true;
        Scanner scanner = null;

        ArrayList<bot> bots = new ArrayList<>();
 
        try {
            if (full) {
                scanner = new Scanner(new File("input_full.txt"));
            } else {
                scanner = new Scanner(new File("input_test.txt"));
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
 
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            System.out.println(line);
            String splits[] = line.split("=");
            String pos[] = splits[1].split(",");
            String yy = pos[1].substring(0, pos[1].length()-2);
            int x = Integer.parseInt(pos[0]);
            int y = Integer.parseInt(yy);
            pos = splits[2].split(",");
            int xspeed = Integer.parseInt(pos[0]);
            int yspeed = Integer.parseInt(pos[1]);
            bot b = new bot(x,y,xspeed, yspeed);
            bots.add(b);
        }

        for (int i=0; i<100; i++) {
            for (bot b : bots) {
                b.move(full);

                //System.out.println(b.asString());
            }
        }
        long res = 0;
        int upLeft = 0;
        int upRight = 0;
        int lowLeft = 0;
        int lowRight = 0;

        for (bot b : bots) {
            int quadrant = b.quadrant(full);

            switch (quadrant) {
                case 0 -> upLeft++;
                case 1 -> lowLeft++;
                case 2 -> upRight++;
                case 3 -> lowRight++;
                default -> {
                }
            }
        }
        res = upLeft * upRight * lowLeft * lowRight;
        System.out.println("Number of safe bots: " + res);
    }
}