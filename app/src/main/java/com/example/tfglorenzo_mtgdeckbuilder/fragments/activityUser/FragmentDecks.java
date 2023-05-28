package com.example.tfglorenzo_mtgdeckbuilder.fragments.activityUser;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tfglorenzo_mtgdeckbuilder.R;
import com.example.tfglorenzo_mtgdeckbuilder.adapters.DeckListAdapter;
import com.example.tfglorenzo_mtgdeckbuilder.api.ConexionRetrofitDeckbuilder;
import com.example.tfglorenzo_mtgdeckbuilder.api.DockerLampApi;
import com.example.tfglorenzo_mtgdeckbuilder.fragments.dialogFragments.NewDeckDialogFragment;
import com.example.tfglorenzo_mtgdeckbuilder.interfaces.InterfaceDeckCardList;
import com.example.tfglorenzo_mtgdeckbuilder.interfaces.InterfaceUserDeck;
import com.example.tfglorenzo_mtgdeckbuilder.models.dockerLamp.response.ResponseGetDeck;
import com.example.tfglorenzo_mtgdeckbuilder.models.userData.Deck;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class FragmentDecks extends Fragment  implements InterfaceUserDeck {
    private FloatingActionButton btnAdd;
    private InterfaceDeckCardList listenerCardList;
    private NewDeckDialogFragment newDeckDialogFragment;
    private TextView txtDeckList;
    private SharedPreferences preferences;
    private ConexionRetrofitDeckbuilder conexionRetrofitDeckbuilder;
    private String token, nick;
    private DockerLampApi dockerLampApi;
    private List<Deck> userDeckList;
    private RecyclerView deckList;

    public FragmentDecks() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        conexionRetrofitDeckbuilder = new ConexionRetrofitDeckbuilder();
        preferences = getActivity().getSharedPreferences(getString(R.string.userPreferences), MODE_PRIVATE);
        token = preferences.getString("preferences_apikey", "");
        dockerLampApi = conexionRetrofitDeckbuilder.getDockerLampApi();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_decks, container, false);
        btnAdd = view.findViewById(R.id.btn_add);
        txtDeckList = view.findViewById(R.id.txt_deck_list);
        nick = preferences.getString(getString(R.string.preferences_nick), null);
        txtDeckList.setText(nick);
        loadUserDecks(this);
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof InterfaceDeckCardList) {
            listenerCardList = (InterfaceDeckCardList) context;
        } else {
            throw new RuntimeException("Error de cojones");
        }
    }

    public void loadUserDecks(FragmentDecks listener) {
        Call<ResponseGetDeck> callDeck = dockerLampApi.getUserDeckList(token);
        callDeck.enqueue(new Callback<ResponseGetDeck>() {
            @Override
            public void onResponse(Call<ResponseGetDeck> call, Response<ResponseGetDeck> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ResponseGetDeck responseDeck = response.body();
                    List<Deck> deckList = responseDeck.getDecks();
                    listener.onDeckListLoaded(deckList);
                } else {
                    listener.onDeckListLoadFailed();
                }
            }

            @Override
            public void onFailure(Call<ResponseGetDeck> call, Throwable t) {
                listener.onDeckListLoadFailed();
                System.out.println("dentro de onfailure");
                System.out.println(t.getLocalizedMessage());
                t.getCause();
            }
        });
    }


    @Override
    public void onDeckListLoaded(List<Deck> newUserDeckList) {
        userDeckList = newUserDeckList;
        DeckListAdapter deckListAdapter = new DeckListAdapter(
                getContext(),
                userDeckList,
                i -> listenerCardList.setSelectedDeck(i),
                getParentFragmentManager()
        );

        btnAdd.setOnClickListener(view1 -> {
            newDeckDialogFragment = new NewDeckDialogFragment(deckListAdapter);
            newDeckDialogFragment.show(getParentFragmentManager(), "Nuevo Mazo");
        });
        deckList = getView().findViewById(R.id.list_deck);
        deckList.setLayoutManager(new LinearLayoutManager(getContext()));
        deckList.setAdapter(deckListAdapter);
    }

    @Override
    public void onDeckListLoadFailed() {
        Toast.makeText(getContext(), "Error al cargar la lista de mazos", Toast.LENGTH_SHORT).show();
    }
}