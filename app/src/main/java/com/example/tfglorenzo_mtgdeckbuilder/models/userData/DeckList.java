package com.example.tfglorenzo_mtgdeckbuilder.models.userData;

import java.util.ArrayList;
import java.util.List;

public class DeckList {
    private int numDecks;
    private List<Deck> deckList;

    public DeckList() {
        this.deckList = new ArrayList<>();
        this.numDecks = deckList.size();
    }

    public List<Deck> getDeckList() {
        return deckList;
    }

    public void setDeckList(List<Deck> deckList) {
        this.deckList = deckList;
    }

    public int getNumDecks() {
        return deckList.size();
    }
}
