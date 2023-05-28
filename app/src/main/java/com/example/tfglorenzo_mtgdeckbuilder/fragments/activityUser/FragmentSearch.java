package com.example.tfglorenzo_mtgdeckbuilder.fragments.activityUser;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tfglorenzo_mtgdeckbuilder.R;
import com.example.tfglorenzo_mtgdeckbuilder.adapters.SearchListAdapter;
import com.example.tfglorenzo_mtgdeckbuilder.api.Converter;
import com.example.tfglorenzo_mtgdeckbuilder.api.ScryfallApi;
import com.example.tfglorenzo_mtgdeckbuilder.interfaces.InterfaceDeckCardList;
import com.example.tfglorenzo_mtgdeckbuilder.models.api.ResponseFromApi;
import com.example.tfglorenzo_mtgdeckbuilder.models.singleCardInfo.SingleCardInfo;
import com.example.tfglorenzo_mtgdeckbuilder.models.userData.Card;
import com.example.tfglorenzo_mtgdeckbuilder.models.userData.Deck;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragmentSearch extends Fragment {
    Deck selectedDeck;
    private EditText txtSearch;
    private Button btnSearch;
    private InterfaceDeckCardList listenerCardList;
    private List<Card> cards;
    private SearchListAdapter searchListAdapter;
    private int deckId;

    public FragmentSearch() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        txtSearch = view.findViewById(R.id.edit_search);
        btnSearch = view.findViewById(R.id.btn_start_search);
        if (cards == null) {
            cards = new ArrayList<>();
        }
        selectedDeck = listenerCardList.getSelectedDeck();
        deckId = selectedDeck.getId();
        searchListAdapter = new SearchListAdapter(
                getContext(),
                cards,
                deckId,
                card -> listenerCardList.setSelectedCard(card)
        );

        RecyclerView cardList = view.findViewById(R.id.list_search);
        cardList.setHasFixedSize(true);
        cardList.setLayoutManager(new LinearLayoutManager(getContext()));
        cardList.setAdapter(searchListAdapter);

        btnSearch.setOnClickListener(button -> {
            String searchText = String.valueOf(txtSearch.getText());
            searchCardsOnApi(searchText);

        });
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof InterfaceDeckCardList) {
            listenerCardList = (InterfaceDeckCardList) context;
        } else {
            throw new RuntimeException("Implementa la interfaz por enesima vez");
        }
    }

    public void searchCardsOnApi(String searchText) {
        Gson gson = new GsonBuilder().setLenient().create();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl("https://api.scryfall.com/")
                .build();

        ScryfallApi scryfallApi = retrofit.create(ScryfallApi.class);
        Call<ResponseFromApi> call = scryfallApi.searchCards(searchText);

        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.isSuccessful() && response.body() != null) {
                    ResponseFromApi responseList = (ResponseFromApi) response.body();
                    Converter converter = new Converter();
                    List<SingleCardInfo> cardList = responseList.getData();
                    cards = converter.convertSingleCardIntoFormatedCard(cardList);
                    searchListAdapter.setListaCartas(cards);
                    Toast.makeText(getContext(), "Se han encontrado " + cards.size() + " cartas", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "No se encuentran cartas con ese texto", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                System.out.println(t.getLocalizedMessage());
                Toast.makeText(getContext(), "Error en la api", Toast.LENGTH_SHORT).show();
            }
        });
    }
}