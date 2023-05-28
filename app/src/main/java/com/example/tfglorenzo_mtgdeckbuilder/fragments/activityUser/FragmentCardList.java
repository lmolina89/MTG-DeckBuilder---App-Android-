package com.example.tfglorenzo_mtgdeckbuilder.fragments.activityUser;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tfglorenzo_mtgdeckbuilder.R;
import com.example.tfglorenzo_mtgdeckbuilder.UserActivity;
import com.example.tfglorenzo_mtgdeckbuilder.adapters.CardListAdapter;
import com.example.tfglorenzo_mtgdeckbuilder.api.ConexionRetrofitDeckbuilder;
import com.example.tfglorenzo_mtgdeckbuilder.api.DockerLampApi;
import com.example.tfglorenzo_mtgdeckbuilder.databinding.ActivityUserBinding;
import com.example.tfglorenzo_mtgdeckbuilder.databinding.FragmentCardListBinding;
import com.example.tfglorenzo_mtgdeckbuilder.interfaces.InterfaceCardList;
import com.example.tfglorenzo_mtgdeckbuilder.interfaces.InterfaceDeckCardList;
import com.example.tfglorenzo_mtgdeckbuilder.models.dockerLamp.response.ResponseGetDeckCards;
import com.example.tfglorenzo_mtgdeckbuilder.models.userData.Card;
import com.example.tfglorenzo_mtgdeckbuilder.models.userData.Deck;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragmentCardList extends Fragment implements InterfaceCardList {
    private Deck selectedDeck;
    private List<Card> cardList;
    private FloatingActionButton btnSearch;
    private NavController navController;
    private InterfaceDeckCardList listenerCardList;
    private TextView txtList;
    private SharedPreferences preferences;
    private ConexionRetrofitDeckbuilder conexionRetrofitDeckbuilder;
    private String token;
    private DockerLampApi dockerLampApi;
    private RecyclerView recyclerCardList;

    public FragmentCardList() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        selectedDeck = listenerCardList.getSelectedDeck();
        conexionRetrofitDeckbuilder = new ConexionRetrofitDeckbuilder();
        dockerLampApi = conexionRetrofitDeckbuilder.getDockerLampApi();
        preferences = getActivity().getSharedPreferences(getString(R.string.userPreferences), MODE_PRIVATE);
        token = preferences.getString("preferences_apikey", "");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_card_list, container, false);


        btnSearch = view.findViewById(R.id.btn_search);
        txtList = view.findViewById(R.id.txt_list_name);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_FragmentList_to_FragmentSearch);
            }
        });
        getDeckCards(this);
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof InterfaceDeckCardList) {
            listenerCardList = (InterfaceDeckCardList) context;
        } else {
            throw new RuntimeException("Implementa la interfaz de cartas");
        }
    }

    public void getDeckCards(FragmentCardList listener) {
        Call<ResponseGetDeckCards> call = dockerLampApi.getDeckCards(token, selectedDeck.getId());
        call.enqueue(new Callback<ResponseGetDeckCards>() {
            @Override
            public void onResponse(Call<ResponseGetDeckCards> call, Response<ResponseGetDeckCards> response) {
//                if (response.errorBody() != null) {
//                    try {
//                        System.out.println(response.errorBody().string());
//                    } catch (IOException | NullPointerException e) {
//                        throw new RuntimeException(e);
//                    }
//                    System.out.println(call.request());
//                }

                if (response.isSuccessful() && response.body() != null) {
                    ResponseGetDeckCards responseGetDeckCards = response.body();
                    List<Card> newCardList = responseGetDeckCards.getCards();
                    listener.onListCardLoaded(newCardList);

                    txtList.setText(selectedDeck.getName());
                } else {
                    listener.onListCardFailed();
                }
            }

            @Override
            public void onFailure(Call<ResponseGetDeckCards> call, Throwable t) {
                listener.onListCardFailed();
                System.out.println(t.getLocalizedMessage());
                t.getCause();
            }
        });
    }

    @Override
    public void onListCardLoaded(List<Card> newCardList) {
        cardList = newCardList;
        CardListAdapter cardListAdapter = new CardListAdapter(
                getContext(),
                cardList,
                selectedDeck,
                card -> listenerCardList.setSelectedCard(card),
                getParentFragmentManager());

        recyclerCardList = getView().findViewById(R.id.list_card);
        recyclerCardList.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerCardList.setAdapter(cardListAdapter);
    }

    @Override
    public void onListCardFailed() {
        Toast.makeText(getContext(), "Error al cargar la lista de cartas", Toast.LENGTH_SHORT).show();
    }
}