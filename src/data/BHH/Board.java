package data.BHH;

import data.BHH.card.Event;
import data.BHH.card.Item;
import data.BHH.card.Omen;

import java.io.Serializable;
import java.util.ArrayList;

import static java.util.Collections.shuffle;

public class Board implements Serializable {

    private Character[] characters;
    private Room[][][] rooms; // [floor][x][y]
    private byte[][][] rotations; // [floor][x][y] 0 = none, 1 =

    private ArrayList<Event> eventStack;
    private ArrayList<Item> itemStack;
    private ArrayList<Omen> omenStack;

    private ArrayList<Token> tokens;

    public void initialize(int numPlayers){
        characters = new Character[numPlayers];
        rooms = new Room[3][101][101];
        rotations = new byte[3][101][101];
        eventStack = new ArrayList<Event>();
        for (int i = 0; i < Event.fullList.length; i++) eventStack.add(Event.fullList[i]);
        shuffle(eventStack);
        itemStack = new ArrayList<Item>();
        for (int i = 0; i < Item.fullList.length; i++) itemStack.add(Item.fullList[i]);
        shuffle(itemStack);
        omenStack = new ArrayList<Omen>();
        for (int i = 0; i < Omen.fullList.length; i++) omenStack.add(Omen.fullList[i]);
        shuffle(omenStack);
        tokens = new ArrayList<Token>();
    }

}
