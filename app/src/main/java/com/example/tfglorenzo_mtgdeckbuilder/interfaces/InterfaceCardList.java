package com.example.tfglorenzo_mtgdeckbuilder.interfaces;

import com.example.tfglorenzo_mtgdeckbuilder.models.userData.Card;

import java.util.List;

public interface InterfaceCardList {
    void onListCardLoaded(List<Card> newCardList);
    void onListCardFailed();
}
