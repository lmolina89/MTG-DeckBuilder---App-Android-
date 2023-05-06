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
import com.example.tfglorenzo_mtgdeckbuilder.api.DockerLampApi;
import com.example.tfglorenzo_mtgdeckbuilder.models.userData.Card;
import com.example.tfglorenzo_mtgdeckbuilder.models.userData.Deck;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CardListAdapter extends RecyclerView.Adapter<CardListAdapter.ViewHolder> implements InterfaceUpdateCards {
    // Declare variables to store data from the constructor
    private final Context context;
    private Deck selectedDeck;
    private List<Card> cardList;
    private final InterfaceOnCardClick listenerCardClick;
    private InterfaceDeckCardList listenerCardList;
    private DeleteDialogFragment deleteCardDialogFragment;
    private FragmentManager parenFragmentManager;
    private SharedPreferences preferences;
    private String token;

    private final String URL = "http://10.0.2.2:80/api-users/endp/";
    private int i;
    private int deckId;
    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private DockerLampApi dockerLampApi = retrofit.create(DockerLampApi.class);


    // Create a static inner class and provide references to all the Views for each data item.
    // This is particularly useful for caching the Views within the item layout for fast access.
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Declare member variables for all the Views in a row
        TextView cardName, cardCost, txtNumberCopies;
        ImageView cardMini;
        ImageButton btnDelete, btnAddCopy, btnSubstracCopy;

        // Create a constructor that accepts the entire row and search the View hierarchy to find each subview
        public ViewHolder(View itemView) {
            super(itemView);
            // Store the item subviews in member variables
            cardName = itemView.findViewById(R.id.txt_cardName);
            cardCost = itemView.findViewById(R.id.txt_cardCost);
            cardMini = itemView.findViewById(R.id.imageView);
            btnDelete = itemView.findViewById(R.id.btn_delete_card);
            btnAddCopy = itemView.findViewById(R.id.btn_add_copies);
            btnSubstracCopy = itemView.findViewById(R.id.btn_substract_copies);
            txtNumberCopies = itemView.findViewById(R.id.txt_num_copies);
        }
    }

    // Provide a suitable constructor
    public CardListAdapter(Context context, List<Card> cardList, Deck selectedDeck, InterfaceOnCardClick listenerCardClick, FragmentManager parenFragmentManager) {
        // Initialize the class scope variables with values received from constructor
        this.listenerCardClick = listenerCardClick;
        this.context = context;
        this.cardList = cardList;
        this.selectedDeck = selectedDeck;
        this.parenFragmentManager = parenFragmentManager;
    }

    // Create new views to be invoked by the layout manager
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        deckId = selectedDeck.getId();
        preferences = context.getSharedPreferences(context.getString(R.string.userPreferences), Context.MODE_PRIVATE);
        token = preferences.getString(context.getString(R.string.preferences_apikey), null);

        ViewHolder viewHolder;
        // Create a LayoutInflater object
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate the custom layout
        View view = inflater.inflate(R.layout.custom_element_card_list, parent, false);
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
            cardList.set(i,card);
            notifyItemChanged(i);
        });

        viewHolder.btnSubstracCopy.setOnClickListener(view1 -> {
            i = viewHolder.getAdapterPosition();
            modifyNumCards("down", cardList.get(i).getId(), deckId, token);
            int numCards = cardList.get(i).getNumCards();
            Card card = cardList.get(i);
            if (numCards > 1) {
                numCards--;
                cardList.get(i).setNumCards(numCards);
            }
            notifyItemChanged(i);
            System.out.println(cardList.get(i).getNumCards());
//            if (selectedDeck.getUserDeck().get(i).getNumCopies() == 1) {
//                viewHolder.btnSubstracCopy.setClickable(false);
//                Toast.makeText(context, "Como minimo tienes que tener una carta!!", Toast.LENGTH_SHORT).show();
//            } else {
//                listenerCardList.substractCopyFromDeck(i);
//                notifyItemChanged(i);
//            }
        });

        view.setOnClickListener(v -> {
            i = viewHolder.getAdapterPosition();
            listenerCardClick.onClick(cardList.get(i));
            Navigation.findNavController(view).navigate(R.id.action_FragmentList_to_fragmentCardInfo);
        });
        // Return a new holder instance
        return viewHolder;
    }

    // Replace the contents of a view to be invoked by the layout manager
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        // Get element from your dataset at this position and replace the contents of the View with that element
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

    // Return the size of your dataset
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
                if (response.body().getResult().equals("ok")) {
                    Toast.makeText(context, "Actualizado el numero de cartas", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "No se ha modificado el numero de cartas", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseUpdateNumCards> call, Throwable t) {
                Toast.makeText(context, "Error al modificar el numero de cartas", Toast.LENGTH_SHORT).show();
            }
        });
    }



}
