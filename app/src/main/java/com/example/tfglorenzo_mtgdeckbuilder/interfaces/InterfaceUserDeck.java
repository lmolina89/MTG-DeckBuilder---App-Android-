package com.example.tfglorenzo_mtgdeckbuilder.interfaces;

import com.example.tfglorenzo_mtgdeckbuilder.models.userData.Deck;

import java.util.List;

public interface InterfaceUserDeck {
    void onDeckListLoaded(List<Deck> deckList);
    void onDeckListLoadFailed();
}
