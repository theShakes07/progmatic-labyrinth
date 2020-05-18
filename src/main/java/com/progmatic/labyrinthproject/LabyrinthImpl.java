package com.progmatic.labyrinthproject;

import com.progmatic.labyrinthproject.enums.CellType;
import com.progmatic.labyrinthproject.enums.Direction;
import com.progmatic.labyrinthproject.exceptions.CellException;
import com.progmatic.labyrinthproject.exceptions.InvalidMoveException;
import com.progmatic.labyrinthproject.interfaces.Labyrinth;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author pappgergely
 */
public class LabyrinthImpl implements Labyrinth {

    private CellType[][] labyrinth;
    private Coordinate PlayerPos;

    public LabyrinthImpl() {

    }

    @Override
    public void loadLabyrinthFile(String fileName) {
        try {
            Scanner sc = new Scanner(new File(fileName));
            int width = Integer.parseInt(sc.nextLine());
            int height = Integer.parseInt(sc.nextLine());
            labyrinth = new CellType[height][width];
            for (int hh = 0; hh < height; hh++) {
                String line = sc.nextLine();
                for (int ww = 0; ww < width; ww++) {
                    switch (line.charAt(ww)) {
                        case 'W':
                            labyrinth[hh][ww] = CellType.WALL;
                            break;
                        case 'E':
                            labyrinth[hh][ww] = CellType.END;
                            break;
                        case 'S':
                            labyrinth[hh][ww] = CellType.START;
                            PlayerPos = new Coordinate(ww, hh);
                            break;
                        case ' ':
                            labyrinth[hh][ww] = CellType.EMPTY;
                            break;
                    }
                }
            }
        } catch (FileNotFoundException | NumberFormatException ex) {
            System.out.println(ex.toString());
        }
    }

    @Override
    public int getWidth() {
        if (labyrinth == null) {
            return -1;
        }
        return labyrinth[0].length;
    }

    @Override
    public int getHeight() {
        if (labyrinth == null) {
            return -1;
        }
        return labyrinth.length;
    }

    @Override
    public CellType getCellType(Coordinate c) throws CellException {
        if (c.getRow() >= labyrinth.length || c.getRow() < 0) {
            throw new CellException(c.getRow(), c.getCol(), "Error: row");
        }
        if (c.getCol() >= labyrinth[0].length || c.getCol() < 0) {
            throw new CellException(c.getRow(), c.getCol(), "Error: col");
        }
        return labyrinth[c.getRow()][c.getCol()];
    }

    @Override
    public void setSize(int width, int height) {
        CellType[][] newLabyrinth = new CellType[height][width];
        for (int i = 0; i < newLabyrinth.length; i++) {
            for (int j = 0; j < newLabyrinth[i].length; j++) {
                newLabyrinth[i][j] = CellType.EMPTY;
            }
        }
        labyrinth = newLabyrinth;
    }

    @Override
    public void setCellType(Coordinate c, CellType type) throws CellException {
        if (c.getRow() > labyrinth.length || c.getRow() < 0) {
            throw new CellException(c.getRow(), c.getCol(), "Error: row");
        }
        if (c.getCol() > labyrinth[0].length || c.getCol() < 0) {
            throw new CellException(c.getRow(), c.getCol(), "Error: col");
        }
        if (type == CellType.START) {
            PlayerPos = c;
        }
        labyrinth[c.getRow()][c.getCol()] = type;
    }

    @Override
    public Coordinate getPlayerPosition() {
        return PlayerPos;
    }

    @Override
    public boolean hasPlayerFinished() {
        return labyrinth[PlayerPos.getRow()][PlayerPos.getCol()] == CellType.END;

    }

    @Override
    public List<Direction> possibleMoves() {
        List<Direction> directionList = new ArrayList<>(4);
        if (PlayerPos.getCol() - 1 > 0) {
            if (labyrinth[PlayerPos.getRow()][PlayerPos.getCol() - 1] != CellType.WALL) {
                directionList.add(Direction.WEST);
            }
        }
        if (PlayerPos.getCol() + 1 < getWidth()) {
            if (labyrinth[PlayerPos.getRow()][PlayerPos.getCol() + 1] != CellType.WALL) {
                directionList.add(Direction.EAST);
            }
        }
        if (PlayerPos.getRow() - 1 > 0) {
            if (labyrinth[PlayerPos.getRow() - 1][PlayerPos.getCol()] != CellType.WALL) {
                directionList.add(Direction.NORTH);
            }
        }
        if (PlayerPos.getRow() + 1 < getHeight()) {
            if (labyrinth[PlayerPos.getRow() + 1][PlayerPos.getCol()] != CellType.WALL) {
                directionList.add(Direction.SOUTH);
            }
        }
        return directionList;
    }

    @Override
    public void movePlayer(Direction direction) throws InvalidMoveException {
        switch (direction) {
            case EAST:
                if (PlayerPos.getCol() + 1 >= getWidth() || labyrinth[PlayerPos.getRow()][PlayerPos.getCol() + 1] == CellType.WALL ) {
                    throw new InvalidMoveException();
                } else {
                    PlayerPos = new Coordinate(PlayerPos.getCol() + 1, PlayerPos.getRow());
                }
                break;
            case NORTH:
                if (PlayerPos.getRow() - 1 < 0 || labyrinth[PlayerPos.getRow() - 1][PlayerPos.getCol()] == CellType.WALL) {
                    throw new InvalidMoveException();
                } else {
                    PlayerPos = new Coordinate(PlayerPos.getCol(), PlayerPos.getRow() - 1);
                }
                break;
            case SOUTH:
                if (PlayerPos.getRow() + 1 >= getHeight() || labyrinth[PlayerPos.getRow() + 1][PlayerPos.getCol()] == CellType.WALL) {
                    throw new InvalidMoveException();
                } else {
                    PlayerPos = new Coordinate(PlayerPos.getCol(), PlayerPos.getRow()+1);
                }
                break;
            case WEST:
                if (PlayerPos.getCol() - 1 < 0 || labyrinth[PlayerPos.getRow()][PlayerPos.getCol() - 1] == CellType.WALL) {
                    throw new InvalidMoveException();
                } else {
                    PlayerPos = new Coordinate(PlayerPos.getCol() - 1, PlayerPos.getRow());
                }
                break;
        }

    }

}