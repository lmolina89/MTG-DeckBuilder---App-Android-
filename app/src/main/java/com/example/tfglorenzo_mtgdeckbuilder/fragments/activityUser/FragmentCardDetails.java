package com.example.tfglorenzo_mtgdeckbuilder.fragments.activityUser;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tfglorenzo_mtgdeckbuilder.R;
import com.example.tfglorenzo_mtgdeckbuilder.interfaces.InterfaceDeckCardList;
import com.example.tfglorenzo_mtgdeckbuilder.models.userData.Card;

public class FragmentCardDetails extends Fragment {
    private Card selectedCard;
    private TextView txtName, txtCost, txtCmc, txtAttrib, txtText, txtExp, txtArtist;
    private ImageView imgCard;
    private InterfaceDeckCardList interfaceDeckCardList;

    public FragmentCardDetails() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_card_details, container, false);
        txtName = view.findViewById(R.id.txt_card_name);
        txtCost = view.findViewById(R.id.txt_card_cost);
        txtCmc = view.findViewById(R.id.txt_card_cmc);
        txtAttrib = view.findViewById(R.id.txt_card_atributes);
        txtText = view.findViewById(R.id.txt_card_text);
        txtExp = view.findViewById(R.id.txt_card_expansion);
        txtArtist = view.findViewById(R.id.txt_card_artist);
        imgCard = view.findViewById(R.id.img_card_details);

        selectedCard = interfaceDeckCardList.getSelectedCard();

        txtName.setText(selectedCard.getName());
        txtCost.setText(selectedCard.getManacost());
        txtCmc.setText(selectedCard.getCmc());
        txtAttrib.setText(selectedCard.getAtributes());
        txtText.setText(selectedCard.getText());
        txtExp.setText(selectedCard.getExpansion());
        txtArtist.setText(selectedCard.getArtist());
        Glide.with(getContext())
                .load(selectedCard.getImageUri())
                .into(imgCard);

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof InterfaceDeckCardList) {
            interfaceDeckCardList = (InterfaceDeckCardList) context;
        } else {
            throw new RuntimeException("Implementa la interfaz");
        }
    }
}