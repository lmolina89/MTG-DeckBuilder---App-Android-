package com.example.tfglorenzo_mtgdeckbuilder.interfaces;

import com.example.tfglorenzo_mtgdeckbuilder.models.userData.Deck;

public interface InterfaceUpdateDecks {
    void updateDeckList(int i,Deck newDeck);
    void updateDeleteDeckList(int i);
    void updateNewDeckList(Deck newDeck);
}
