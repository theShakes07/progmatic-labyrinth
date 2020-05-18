package com.progmatic.labyrinthproject;

import com.progmatic.labyrinthproject.enums.Direction;
import com.progmatic.labyrinthproject.interfaces.Labyrinth;
import com.progmatic.labyrinthproject.interfaces.Player;

import java.util.Random;

public class RandomPlayerImp implements Player {

    @Override
    public Direction nextMove(Labyrinth l) {
        return l.possibleMoves().get(new Random().nextInt(l.possibleMoves().size()));
    }
}