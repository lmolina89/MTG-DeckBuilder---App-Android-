package com.example.tfglorenzo_mtgdeckbuilder.interfaces;

import com.example.tfglorenzo_mtgdeckbuilder.models.userData.Card;
import com.example.tfglorenzo_mtgdeckbuilder.models.userData.Deck;

public interface InterfaceDeckCardList {

    Deck getSelectedDeck();

    void setSelectedDeck(Deck selectedDeck);

    void setSelectedCard(Card card);

    Card getSelectedCard();
}
