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
import com.example.tfglorenzo_mtgdeckbuilder.fragments.dialogFragments.DeleteDialogFragment;
import com.example.tfglorenzo_mtgdeckbuilder.fragments.dialogFragments.EditDialogFragment;
import com.example.tfglorenzo_mtgdeckbuilder.interfaces.InterfaceDeckCardList;
import com.example.tfglorenzo_mtgdeckbuilder.interfaces.InterfaceOnDeckClick;
import com.example.tfglorenzo_mtgdeckbuilder.interfaces.InterfaceUpdateDecks;
import com.example.tfglorenzo_mtgdeckbuilder.models.userData.Deck;

import java.util.List;

public class DeckListAdapter extends RecyclerView.Adapter<DeckListAdapter.ViewHolder> implements InterfaceUpdateDecks {

    private Context context;
    private List<Deck> deckList;
    private InterfaceOnDeckClick listenerDeckClick;
    private InterfaceDeckCardList listenerCardList;
    private EditDialogFragment editDeckDialogFragment;
    private DeleteDialogFragment deleteDeckDialogFragment;
    private FragmentManager fragmentManager;
    private SharedPreferences preferences;
    private int i;
    private String token;
    private String selectedDeckName;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView deckName;
        TextView deckSize;
        ImageView deckImage;
        ImageButton btnDelete, btnEdit;

        public ViewHolder(View itemView) {
            super(itemView);
            deckName = itemView.findViewById(R.id.txt_deck_name);
            deckSize = itemView.findViewById(R.id.txt_num_cards);
            deckImage = itemView.findViewById(R.id.imageView);
            btnDelete = itemView.findViewById(R.id.btn_delete_deck);
            btnEdit = itemView.findViewById(R.id.btn_edit_deck);
        }
    }

    public DeckListAdapter(Context context, List<Deck> listaMazos, InterfaceOnDeckClick listenerDeckClick, FragmentManager fragmentManager) {
        this.context = context;
        this.deckList = listaMazos;
        this.listenerDeckClick = listenerDeckClick;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        preferences = context.getSharedPreferences(context.getString(R.string.userPreferences), Context.MODE_PRIVATE);
        token = preferences.getString(context.getString(R.string.preferences_apikey), null);
        ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.element_deck_list, parent, false);
        viewHolder = new ViewHolder(view);
        listenerCardList = (InterfaceDeckCardList) context;
        view.setOnClickListener(v -> {
            i = viewHolder.getAdapterPosition();
            Toast.makeText(context, "Mazo seleccionado: " + deckList.get(i).getName(), Toast.LENGTH_SHORT).show();
            listenerDeckClick.onClick(deckList.get(i));
//            selectedDeckName = deckList.get(i).getName();
            Navigation.findNavController(view).navigate(R.id.action_FragmentDecks_to_FragmentList);
        });

        viewHolder.btnDelete.setOnClickListener(view1 -> {
            i = viewHolder.getAdapterPosition();
            deleteDeckDialogFragment = new DeleteDialogFragment(this, deckList.get(i), deckList.get(i).getId(), i, token);
            deleteDeckDialogFragment.show(fragmentManager, "Delete deck dialog");

        });

        viewHolder.btnEdit.setOnClickListener(view12 -> {
            i = viewHolder.getAdapterPosition();
            editDeckDialogFragment = new EditDialogFragment(this, deckList.get(i), i, token, context);
            editDeckDialogFragment.show(fragmentManager, "Edit Deck");
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        holder.deckName.setText(deckList.get(i).getName());
        holder.deckSize.setText(String.valueOf(deckList.get(i).getNumCards()));

        if (deckList.get(i).getDeckImage() != null) {
            Glide.with(context)
                    .load(deckList.get(i).getDeckImage())
                    .error(R.drawable.magic_back)
                    .into(holder.deckImage);
        }
    }

    @Override
    public int getItemCount() {
        return deckList.size();
    }

    @Override
    public void updateDeckList(int i, Deck newDeck) {
        deckList.set(i, newDeck);
        notifyItemChanged(i);
    }

    @Override
    public void updateDeleteDeckList(int i) {
        deckList.remove(i);
        notifyItemRemoved(i);
    }

    @Override
    public void updateNewDeckList(Deck newDeck) {
        int i = deckList.size();
        deckList.add(newDeck);
        notifyItemInserted(i);
    }
}
