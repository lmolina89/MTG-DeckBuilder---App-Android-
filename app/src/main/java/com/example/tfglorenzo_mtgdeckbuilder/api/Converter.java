package com.example.tfglorenzo_mtgdeckbuilder.api;

import com.example.tfglorenzo_mtgdeckbuilder.models.singleCardInfo.ImageUris;
import com.example.tfglorenzo_mtgdeckbuilder.models.singleCardInfo.SingleCardInfo;
import com.example.tfglorenzo_mtgdeckbuilder.models.userData.Card;

import java.util.ArrayList;
import java.util.List;

//convierte el modelo de datos de la api Scryfall al modelo de datos de la APP
public class Converter {
    private static final String DEFAULT_IMAGE = "https://static.wikia.nocookie.net/mtgsalvation_gamepedia/images/f/f8/Magic_card_back.jpg/revision/latest/scale-to-width-down/250?cb=20140813141013";

    public List<Card> convertSingleCardIntoFormatedCard(List<SingleCardInfo> singleCardInfoList) {
        List<Card> formatedCardList = new ArrayList<>();

        singleCardInfoList.forEach(c -> {
            String atributes;
            if (c.getPower() == null || c.getToughness() == null) {
                atributes = "";
            } else {
                atributes = c.getPower() + "/" + c.getToughness();
            }

            ImageUris uris = c.getImageUris();
            String image = uris == null ? DEFAULT_IMAGE : uris.getNormal();

            formatedCardList.add(
                    new Card(
                            c.getName(),
                            c.getManaCost(),
                            String.valueOf(Math.round(c.getCmc())),
                            atributes,
                            c.getOracleText(),
                            c.getArtist(),
                            c.getSetName(),
                            image,
                            c.getId()
                    ));
        });
        return formatedCardList;
    }
}
