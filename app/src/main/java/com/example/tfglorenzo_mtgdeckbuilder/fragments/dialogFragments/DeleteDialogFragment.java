package com.example.tfglorenzo_mtgdeckbuilder.fragments.dialogFragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tfglorenzo_mtgdeckbuilder.R;
import com.example.tfglorenzo_mtgdeckbuilder.api.ConexionRetrofitDeckbuilder;
import com.example.tfglorenzo_mtgdeckbuilder.api.DockerLampApi;
import com.example.tfglorenzo_mtgdeckbuilder.interfaces.InterfaceUpdateCards;
import com.example.tfglorenzo_mtgdeckbuilder.interfaces.InterfaceUpdateDecks;
import com.example.tfglorenzo_mtgdeckbuilder.models.dockerLamp.response.ResponseDeleteCard;
import com.example.tfglorenzo_mtgdeckbuilder.models.dockerLamp.response.ResponseDeleteDeck;
import com.example.tfglorenzo_mtgdeckbuilder.models.userData.Card;
import com.example.tfglorenzo_mtgdeckbuilder.models.userData.Deck;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeleteDialogFragment extends DialogFragment {

    private InterfaceUpdateCards listenerUpdateCards;
    private InterfaceUpdateDecks listenerUpdateDecks;
    private final RecyclerView.Adapter adapter;
    private Deck deck;
    private List<Card> cardList;
    private int i;
    private int deckId;
    private ConexionRetrofitDeckbuilder conexionRetrofitDeckbuilder;
    private String token;

    private DockerLampApi dockerLampApi;

    public DeleteDialogFragment(RecyclerView.Adapter adapter, Object object, int deckId, int i, String token) {
        this.adapter = adapter;
        this.i = i;
        this.deckId = deckId;
        this.token = token;

        if (adapter instanceof InterfaceUpdateCards) {
            listenerUpdateCards = (InterfaceUpdateCards) adapter;
            cardList = (List<Card>) object;
        } else if (adapter instanceof InterfaceUpdateDecks) {
            listenerUpdateDecks = (InterfaceUpdateDecks) adapter;
            deck = (Deck) object;
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_delete_dialog, null);
        conexionRetrofitDeckbuilder = new ConexionRetrofitDeckbuilder();
        dockerLampApi = conexionRetrofitDeckbuilder.getDockerLampApi();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        builder.setMessage("¿Seguro que quieres eliminar?")
                .setPositiveButton("Aceptar", (dialog, id) -> {
                    if (adapter instanceof InterfaceUpdateCards) {
                        deleteCard(cardList.get(i).getId());
                        Toast.makeText(getContext(), "Carta eliminada correctamente...", Toast.LENGTH_SHORT).show();
                    } else if (adapter instanceof InterfaceUpdateDecks) {
                        deleteDeck(deck.getId());
                        Toast.makeText(getContext(), "Mazo eliminado correctamente...", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(
                        "Cancelar",
                        (dialog, id) -> Toast.makeText(getContext(), "Cancelado", Toast.LENGTH_SHORT).show()
                );

        return builder.create();
    }

    public void deleteDeck(int deckId) {
        Call<ResponseDeleteDeck> call = dockerLampApi.deleteDeck(token, deckId);

        call.enqueue(new Callback<ResponseDeleteDeck>() {
            @Override
            public void onResponse(Call<ResponseDeleteDeck> call, Response<ResponseDeleteDeck> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getResult().equals("ok")) {
                        listenerUpdateDecks.updateDeleteDeckList(i);
                    } else {
                        Toast.makeText(getContext(), "Hay algun error con la eliminacion", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseDeleteDeck> call, Throwable t) {
                Toast.makeText(getContext(), "No hay respuesta del servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void deleteCard(String cardId) {
        Call<ResponseDeleteCard> call = dockerLampApi.deleteCard(cardId, token, deckId);
        call.enqueue(new Callback<ResponseDeleteCard>() {
            @Override
            public void onResponse(Call<ResponseDeleteCard> call, Response<ResponseDeleteCard> response) {
                if (response.body().getResult().equals("ok")) {
                    listenerUpdateCards.updateDeleteCardList(i);
                }
            }

            @Override
            public void onFailure(Call<ResponseDeleteCard> call, Throwable t) {
                Toast.makeText(getContext(), "No hay respuesta del servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }
}