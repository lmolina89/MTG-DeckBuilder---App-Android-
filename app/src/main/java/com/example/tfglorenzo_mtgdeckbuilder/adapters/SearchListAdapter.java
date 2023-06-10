package com.example.tfglorenzo_mtgdeckbuilder.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tfglorenzo_mtgdeckbuilder.R;
import com.example.tfglorenzo_mtgdeckbuilder.api.ApiClient;
import com.example.tfglorenzo_mtgdeckbuilder.api.ConexionRetrofitDeckbuilder;
import com.example.tfglorenzo_mtgdeckbuilder.api.DockerLampApi;
import com.example.tfglorenzo_mtgdeckbuilder.api.ScryfallApi;
import com.example.tfglorenzo_mtgdeckbuilder.interfaces.InterfaceDeckCardList;
import com.example.tfglorenzo_mtgdeckbuilder.interfaces.InterfaceOnCardClick;
import com.example.tfglorenzo_mtgdeckbuilder.models.dockerLamp.data.InsertCardData;
import com.example.tfglorenzo_mtgdeckbuilder.models.dockerLamp.response.ResponseInsertCard;
import com.example.tfglorenzo_mtgdeckbuilder.models.userData.Card;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SearchListAdapter extends RecyclerView.Adapter<SearchListAdapter.ViewHolder> {
    private Context context;
    private List<Card> searchedCardList;
    private InterfaceOnCardClick listenerClick;
    private int i;
    private SharedPreferences preferences;
    private String token;
    private int deckId;
    private ConexionRetrofitDeckbuilder conexionRetrofitDeckbuilder = new ConexionRetrofitDeckbuilder();
    private final DockerLampApi dockerLampApi = conexionRetrofitDeckbuilder.getDockerLampApi();

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView cardName;
        TextView cardCost;
        ImageView cardMini;
        ImageButton btnAddCard;

        public ViewHolder(View itemView) {
            super(itemView);
            cardName = itemView.findViewById(R.id.txt_cardName);
            cardCost = itemView.findViewById(R.id.txt_cardCost);
            cardMini = itemView.findViewById(R.id.imageView);
            btnAddCard = itemView.findViewById(R.id.btn_add_search_deck);
        }
    }

    public SearchListAdapter(Context context, List<Card> listaCartas, int deckId, InterfaceOnCardClick listenerClick) {
        this.deckId = deckId;
        this.context = context;
        this.searchedCardList = listaCartas;
        this.listenerClick = listenerClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder viewHolder;
        preferences = context.getSharedPreferences(context.getString(R.string.userPreferences), Context.MODE_PRIVATE);
        token = preferences.getString(context.getString(R.string.preferences_apikey), null);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.element_search_list, parent, false);
        viewHolder = new ViewHolder(view);

        viewHolder.btnAddCard.setOnClickListener(view1 -> {
            i = viewHolder.getAdapterPosition();
            insertCardOnDeck(i, token, deckId);
        });

        view.setOnClickListener(v -> {
            i = viewHolder.getAdapterPosition();
            TextView rowName = v.findViewById(R.id.txt_cardName);
            Toast.makeText(context, "Seleccionado: " + rowName.getText().toString(), Toast.LENGTH_SHORT).show();
            listenerClick.onClick(searchedCardList.get(i));
            Navigation.findNavController(view).navigate(R.id.action_FragmentSearch_to_fragmentCardInfo);
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        holder.cardName.setText(searchedCardList.get(i).getName());
        holder.cardCost.setText(searchedCardList.get(i).getManacost());

        if (searchedCardList.get(i).getImageUri() != null) {
            Glide.with(context)
                    .load(searchedCardList.get(i).getImageUri())
                    .error(R.drawable.magic_back)
                    .into(holder.cardMini);
        }
    }

    @Override
    public int getItemCount() {
        return searchedCardList.size();
    }

    public void setListaCartas(List<Card> listaCartas) {
        this.searchedCardList = listaCartas;
        notifyDataSetChanged();
    }

    public void insertCardOnDeck(int i, String token, int deckId) {
        Card card = searchedCardList.get(i);
        InsertCardData insertCardData = new InsertCardData(
                card.getId(),
                card.getName(),
                card.getManacost(),
                card.getCmc(),
                card.getAtributes(),
                card.getText(),
                card.getArtist(),
                card.getExpansion(),
                card.getImageUri());

        Call<ResponseInsertCard> call = dockerLampApi.insertCardOnDeck(deckId, token, insertCardData);
        call.enqueue(new Callback<ResponseInsertCard>() {
            @Override
            public void onResponse(Call<ResponseInsertCard> call, Response<ResponseInsertCard> response) {
                ResponseInsertCard responseInsertCard = response.body();
                if (response.errorBody() != null) {
                    try {
                        System.out.println(response.errorBody().string());
                    } catch (IOException | NullPointerException e) {
                        throw new RuntimeException(e);
                    }
                }
                System.out.println(response);
                if (response.isSuccessful() && response.body() != null) {
                    if (responseInsertCard.getResult().equals("ok")) {
                        Toast.makeText(context, "Carta añadida correctamente", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Ha ocurrido algun error al insertar", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(context, "La carta ya existe en el mazo", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseInsertCard> call, Throwable t) {
                Toast.makeText(context, "Hay algun problema con la api", Toast.LENGTH_SHORT).show();
                System.out.println(t.getLocalizedMessage());
                t.getCause();
            }
        });
    }
}
