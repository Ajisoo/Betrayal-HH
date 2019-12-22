package updated.data;

import updated.data.card.Event;
import updated.data.card.Item;
import updated.data.card.Omen;
import updated.data.token.Token;

import java.util.ArrayList;

import static java.util.Collections.shuffle;

public class Board {

    private Character[] characters;
    private Room[][][] rooms; //[floor][x][y]

    private ArrayList<Event> eventStack;
    private ArrayList<Item> itemStack;
    private ArrayList<Omen> omenStack;

    private ArrayList<Token> tokens;

    public void initialize(int numPlayers){
        characters = new Character[numPlayers];
        rooms = new Room[3][101][101];
        eventStack = new ArrayList<Event>();
        for (int i = 0; i < Event.fullList.length; i++) eventStack.add(Event.fullList[i]);
        shuffle(eventStack);
        itemStack = new ArrayList<Item>();
        for (int i = 0; i < Item.fullList.length; i++) itemStack.add(Item.fullList[i]);
        shuffle(itemStack);
        omenStack = new ArrayList<Omen>();
        for (int i = 0; i < Omen.fullList.length; i++) omenStack.add(Omen.fullList[i]);
        shuffle(omenStack);
    }

}