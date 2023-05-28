package com.example.tfglorenzo_mtgdeckbuilder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.tfglorenzo_mtgdeckbuilder.databinding.ActivityUserBinding;
import com.example.tfglorenzo_mtgdeckbuilder.interfaces.InterfaceDeckCardList;
import com.example.tfglorenzo_mtgdeckbuilder.models.userData.Card;
import com.example.tfglorenzo_mtgdeckbuilder.models.userData.Deck;

public class UserActivity extends AppCompatActivity implements InterfaceDeckCardList {
    private AppBarConfiguration appBarConfiguration;
    private ActivityUserBinding binding;
    private Card selectedCard;
    private Deck selectedDeck;
    private SharedPreferences preferences;

    private static final int REQUEST_CAMERA_PERMISSION = 1;
    private static final int REQUEST_STORAGE_PERMISSION = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        requestCameraPermission();
        requestStoragePermission();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.save_data) {
            Toast.makeText(this, "Guardando datos...", Toast.LENGTH_LONG).show();
            return true;
        }

        if (id == R.id.sign_out) {
            preferences = getSharedPreferences(getString(R.string.userPreferences), MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(getString(R.string.preferences_pass), "");
            editor.putString(getString(R.string.preferences_nick), "");
            editor.putBoolean(getString(R.string.preferences_is_loged), false);
            editor.commit();
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
    }


    @Override
    public Deck getSelectedDeck() {
        return selectedDeck;
    }

    @Override
    public void setSelectedDeck(Deck selectedDeck) {
        this.selectedDeck = selectedDeck;
    }


    @Override
    public void setSelectedCard(Card card) {
        selectedCard = card;
    }

    @Override
    public Card getSelectedCard() {
        return selectedCard;
    }

    private void requestCameraPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        }
    }

    private void requestStoragePermission(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_STORAGE_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            //El usuario ha pulsado una opción de "aceptar permiso" o "Cancelar"
            if (permissions.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Si ha aceptado pulsado el botón de aceptar.
            } else {
                Toast.makeText(this, "No se han aceptado los permisos de la camara", Toast.LENGTH_SHORT).show();
            }
        }
        //Comprobamos permiso de Almacenamiento.
        if (requestCode == REQUEST_STORAGE_PERMISSION) {
            if (permissions.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Si ha aceptado pulsado el botón de aceptar.
            } else {
                Toast.makeText(this, "No se han aceptado los permisos de escritura", Toast.LENGTH_SHORT).show();
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}