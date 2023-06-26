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
import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tfglorenzo_mtgdeckbuilder.R;
import com.example.tfglorenzo_mtgdeckbuilder.api.ConexionRetrofitDeckbuilder;
import com.example.tfglorenzo_mtgdeckbuilder.api.DockerLampApi;
import com.example.tfglorenzo_mtgdeckbuilder.fragments.dialogFragments.DeleteDialogFragment;
import com.example.tfglorenzo_mtgdeckbuilder.interfaces.InterfaceDeckCardList;
import com.example.tfglorenzo_mtgdeckbuilder.interfaces.InterfaceOnCardClick;
import com.example.tfglorenzo_mtgdeckbuilder.interfaces.InterfaceUpdateCards;
import com.example.tfglorenzo_mtgdeckbuilder.models.dockerLamp.data.UpdateNumCardsData;
import com.example.tfglorenzo_mtgdeckbuilder.models.dockerLamp.response.ResponseUpdateNumCards;
import com.example.tfglorenzo_mtgdeckbuilder.models.userData.Card;
import com.example.tfglorenzo_mtgdeckbuilder.models.userData.Deck;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CardListAdapter extends RecyclerView.Adapter<CardListAdapter.ViewHolder> implements InterfaceUpdateCards {
    private final Context context;
    private Deck selectedDeck;
    private List<Card> cardList;
    private final InterfaceOnCardClick listenerCardClick;
    private InterfaceDeckCardList listenerCardList;
    private DeleteDialogFragment deleteCardDialogFragment;
    private FragmentManager parenFragmentManager;
    private SharedPreferences preferences;
    private String token;
    private int i;
    private int deckId;
    private ConexionRetrofitDeckbuilder conexionRetrofitDeckbuilder = new ConexionRetrofitDeckbuilder();
    private final DockerLampApi dockerLampApi = conexionRetrofitDeckbuilder.getDockerLampApi();

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView cardName, cardCost, txtNumberCopies;
        ImageView cardMini;
        ImageButton btnDelete, btnAddCopy, btnSubstractCopy;

        public ViewHolder(View itemView) {
            super(itemView);
            // Store the item subviews in member variables
            cardName = itemView.findViewById(R.id.txt_cardName);
            cardCost = itemView.findViewById(R.id.txt_cardCost);
            cardMini = itemView.findViewById(R.id.imageView);
            btnDelete = itemView.findViewById(R.id.btn_delete_card);
            btnAddCopy = itemView.findViewById(R.id.btn_add_copies);
            btnSubstractCopy = itemView.findViewById(R.id.btn_substract_copies);
            txtNumberCopies = itemView.findViewById(R.id.txt_num_copies);
        }
    }

    public CardListAdapter(Context context, List<Card> cardList, Deck selectedDeck, InterfaceOnCardClick listenerCardClick, FragmentManager parenFragmentManager) {
        this.listenerCardClick = listenerCardClick;
        this.context = context;
        this.cardList = cardList;
        this.selectedDeck = selectedDeck;
        this.parenFragmentManager = parenFragmentManager;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        deckId = selectedDeck.getId();
        preferences = context.getSharedPreferences(context.getString(R.string.userPreferences), Context.MODE_PRIVATE);
        token = preferences.getString(context.getString(R.string.preferences_apikey), null);

        ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.element_card_list, parent, false);
        viewHolder = new ViewHolder(view);
        listenerCardList = (InterfaceDeckCardList) context;

        viewHolder.btnDelete.setOnClickListener(view1 -> {
            i = viewHolder.getAdapterPosition();
            deleteCardDialogFragment = new DeleteDialogFragment(this, cardList, deckId, i, token);
            deleteCardDialogFragment.show(parenFragmentManager, "Eliminar carta");
        });

        viewHolder.btnAddCopy.setOnClickListener(view1 -> {
            i = viewHolder.getAdapterPosition();
            modifyNumCards("up", cardList.get(i).getId(), deckId, token);
            Card card = cardList.get(i);
            int numCards = card.getNumCards();
            if (numCards < 4) {
                numCards++;
                card.setNumCards(numCards);
            }
            cardList.set(i, card);
            notifyItemChanged(i);
        });

        viewHolder.btnSubstractCopy.setOnClickListener(view1 -> {
            i = viewHolder.getAdapterPosition();
            modifyNumCards("down", cardList.get(i).getId(), deckId, token);
            int numCards = cardList.get(i).getNumCards();
            Card card = cardList.get(i);
            if (numCards > 1) {
                numCards--;
                cardList.get(i).setNumCards(numCards);
            }
            notifyItemChanged(i);
        });

        view.setOnClickListener(v -> {
            i = viewHolder.getAdapterPosition();
            listenerCardClick.onClick(cardList.get(i));
            Navigation.findNavController(view).navigate(R.id.action_FragmentList_to_fragmentCardInfo);
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        holder.cardName.setText(cardList.get(i).getName());
        holder.cardCost.setText(cardList.get(i).getManacost());
        holder.txtNumberCopies.setText(String.valueOf(cardList.get(i).getNumCards()));
        if (cardList.get(i).getImageUri() != null) {
            Glide.with(context)
                    .load(cardList.get(i).getImageUri())
                    .error(R.drawable.magic_back)
                    .into(holder.cardMini);
        }
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    @Override
    public void updateDeleteCardList(int i) {
        cardList.remove(i);
        notifyItemRemoved(i);
    }

    public void modifyNumCards(String action, String cardId, int deckId, String token) {
        UpdateNumCardsData updateNumCardsData = new UpdateNumCardsData();
        updateNumCardsData.setCardId(cardId);
        updateNumCardsData.setAction(action);
        Call<ResponseUpdateNumCards> call = dockerLampApi.updateNumCards(deckId, token, updateNumCardsData);
        call.enqueue(new Callback<ResponseUpdateNumCards>() {
            @Override
            public void onResponse(Call<ResponseUpdateNumCards> call, Response<ResponseUpdateNumCards> response) {

                if(response.body().getResult().equals("error")){
                    Toast.makeText(context, "Solo puedes tener un maximo de 4 copias", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseUpdateNumCards> call, Throwable t) {
                Toast.makeText(context, "Error al modificar el numero de cartas", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
