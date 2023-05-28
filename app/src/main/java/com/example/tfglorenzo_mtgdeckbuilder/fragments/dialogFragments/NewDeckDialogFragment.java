package com.example.tfglorenzo_mtgdeckbuilder.fragments.dialogFragments;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.tfglorenzo_mtgdeckbuilder.R;
import com.example.tfglorenzo_mtgdeckbuilder.adapters.DeckListAdapter;
import com.example.tfglorenzo_mtgdeckbuilder.api.ConexionRetrofitDeckbuilder;
import com.example.tfglorenzo_mtgdeckbuilder.api.DockerLampApi;
import com.example.tfglorenzo_mtgdeckbuilder.interfaces.InterfaceDeckCardList;
import com.example.tfglorenzo_mtgdeckbuilder.interfaces.InterfaceUpdateDecks;
import com.example.tfglorenzo_mtgdeckbuilder.models.dockerLamp.data.DeckData;
import com.example.tfglorenzo_mtgdeckbuilder.models.dockerLamp.response.ResponseNewDeck;
import com.example.tfglorenzo_mtgdeckbuilder.models.userData.Deck;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewDeckDialogFragment extends DialogFragment {
    private EditText editNewDeck;
    private InterfaceDeckCardList listenerCardList;
    private InterfaceUpdateDecks listenerUpdateDecks;
    private ConexionRetrofitDeckbuilder conexionRetrofitDeckbuilder;
    private String token;
    private SharedPreferences preferences;

    public NewDeckDialogFragment(DeckListAdapter deckListAdapter) {
        listenerUpdateDecks = (InterfaceUpdateDecks) deckListAdapter;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        preferences = getActivity().getSharedPreferences(getString(R.string.userPreferences), MODE_PRIVATE);
        token = preferences.getString("preferences_apikey", "");
        conexionRetrofitDeckbuilder = new ConexionRetrofitDeckbuilder();
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_new_deck_dialog, null);
        editNewDeck = view.findViewById(R.id.edit_new_deck);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        builder.setMessage("Nombre del nuevo mazo")
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(getContext(), "Creado nuevo mazo " + editNewDeck.getText(), Toast.LENGTH_SHORT).show();
                        String newDeckName = editNewDeck.getText().toString();
                        Deck newDeck = new Deck();
                        newDeck.setName(newDeckName);
                        newDeck.setNumCards(0);
                        createNewDeck(newDeckName);
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(getContext(), "Cancelado", Toast.LENGTH_SHORT).show();
                    }
                });
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof InterfaceDeckCardList) {
            listenerCardList = (InterfaceDeckCardList) context;
        } else {
            throw new RuntimeException("Implementa la interfaz en el dialogo puto");
        }
    }

    public void createNewDeck(String newDeckName){
        DeckData deckData = new DeckData();
        deckData.setName(newDeckName);

        DockerLampApi dockerLampApi = conexionRetrofitDeckbuilder.getDockerLampApi();
        Call<ResponseNewDeck> callNewDeck = dockerLampApi.insertDeck(deckData,token);
        callNewDeck.enqueue(new Callback<ResponseNewDeck>() {
            @Override
            public void onResponse(Call<ResponseNewDeck> call, Response<ResponseNewDeck> response) {
//                Toast.makeText(getContext(), "Mazo creado correctamente", Toast.LENGTH_SHORT).show();
                Deck deck = new Deck();
                deck.setId(response.body().getInsertId());
                deck.setName(newDeckName);
                deck.setNumCards(0);

                listenerUpdateDecks.updateNewDeckList(deck);
            }

            @Override
            public void onFailure(Call<ResponseNewDeck> call, Throwable t) {

            }
        });
    }
}