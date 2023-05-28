package com.example.tfglorenzo_mtgdeckbuilder.fragments.dialogFragments;

import static android.app.Activity.RESULT_OK;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import android.Manifest;

import com.example.tfglorenzo_mtgdeckbuilder.R;
import com.example.tfglorenzo_mtgdeckbuilder.UserActivity;
import com.example.tfglorenzo_mtgdeckbuilder.adapters.DeckListAdapter;
import com.example.tfglorenzo_mtgdeckbuilder.api.ApiClient;
import com.example.tfglorenzo_mtgdeckbuilder.api.ConexionRetrofitDeckbuilder;
import com.example.tfglorenzo_mtgdeckbuilder.api.DockerLampApi;
import com.example.tfglorenzo_mtgdeckbuilder.interfaces.InterfaceDeckCardList;
import com.example.tfglorenzo_mtgdeckbuilder.interfaces.InterfaceLogin;
import com.example.tfglorenzo_mtgdeckbuilder.interfaces.InterfaceUpdateDecks;
import com.example.tfglorenzo_mtgdeckbuilder.models.dockerLamp.data.UpdateDeckData;
import com.example.tfglorenzo_mtgdeckbuilder.models.dockerLamp.response.ResponseUpdateDeck;
import com.example.tfglorenzo_mtgdeckbuilder.models.userData.Deck;
import com.example.tfglorenzo_mtgdeckbuilder.models.userData.User;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class EditDialogFragment extends DialogFragment {
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 2;
    Context context;
    private RecyclerView.Adapter adapter;
    private EditText editExistingDeckName, editImageUri;
    private ImageButton btnGallery, btnCamera;
    private InterfaceDeckCardList listenerCardList;
    private InterfaceLogin listenerLogin;
    private InterfaceUpdateDecks listenerUpdateDecks;
    private Deck deckToEdit;
    private int i;
    private Uri uri;
    private String base64Image;
    private int deckId;
    private String currentPhotoPath;
    private ConexionRetrofitDeckbuilder conexionRetrofitDeckbuilder;
    private Retrofit retrofit = ApiClient.getClient();
    private String token;
    private DockerLampApi dockerLampApi;

    private static final int RESPUESTA_PERMISO_CAMARA = 100;
    private static final int RESPUESTA_PERMISO_ALMACENAMIENTO = 200;
    private static final int RESPUESTA_PERMISO_GALERIA = 300;

    public EditDialogFragment(RecyclerView.Adapter adapter, Object object, int i, String token, Context context) {
        this.token = token;
        this.adapter = adapter;
        this.context = context;
        this.i = i;
        if (adapter instanceof DeckListAdapter) {//si el adaptador que lo llama es el de lista de mazos, hace cast al listenerUpdateDecks y al objeto Deck
            listenerUpdateDecks = (InterfaceUpdateDecks) adapter;
            deckToEdit = (Deck) object;
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        conexionRetrofitDeckbuilder = new ConexionRetrofitDeckbuilder();
//        dockerLampApi = conexionRetrofitDeckbuilder.getDockerLampApi();
        dockerLampApi = retrofit.create(DockerLampApi.class);
        if (adapter instanceof InterfaceUpdateDecks) {                                      //dialogo para editar mazos
            deckId = deckToEdit.getId();

            View view = inflater.inflate(R.layout.fragment_edit_dialog, null);
            editExistingDeckName = view.findViewById(R.id.edit_name_existing_deck);
            editImageUri = view.findViewById(R.id.edit_imageuri_existing_deck);
            btnGallery = view.findViewById(R.id.btn_gallery);
            btnCamera = view.findViewById(R.id.btn_camera);
            editExistingDeckName.setText(deckToEdit.getName());
            editImageUri.setText(deckToEdit.getDeckImage());

            btnCamera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    openCamera();


                }
            });

            btnGallery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, PICK_IMAGE_REQUEST);
                }
            });

            builder.setView(view);
            builder.setMessage("Editar el mazo")
                    .setPositiveButton("Aceptar", (dialog, id) -> {
                        updateDeck();
                        Toast.makeText(getContext(), "Editado correctamente...", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Cancelar", (dialog, id) -> {
                        Toast.makeText(getContext(), "Cancelado", Toast.LENGTH_SHORT).show();
                    });
        }
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof InterfaceDeckCardList) {
            listenerCardList = (InterfaceDeckCardList) context;
        } else if (context instanceof InterfaceLogin) {
            listenerLogin = (InterfaceLogin) context;
        } else {
            throw new RuntimeException("Implementa la interfaz en el dialogo puto");
        }
    }

    private void updateDeck() {
        String name = editExistingDeckName.getText().toString();
        String imgUri = editImageUri.getText().toString();
        UpdateDeckData updateDeckData = new UpdateDeckData();
        updateDeckData.setDeckImage(base64Image);

        updateDeckData.setName(name);

        Call<ResponseUpdateDeck> call = dockerLampApi.updateDeck(updateDeckData, token, deckId);

        call.enqueue(new Callback<ResponseUpdateDeck>() {
            @Override
            public void onResponse(Call<ResponseUpdateDeck> call, Response<ResponseUpdateDeck> response) {
                if (response.errorBody() != null) {
                    try {
                        System.out.println(response.errorBody().string());

                    } catch (IOException | NullPointerException e) {
                        throw new RuntimeException(e);
                    }
                }

                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getResult().equals("error")) {
                        System.out.println("Formato de imagen no permitido (solo jpg,jpeg,png)");
                    } else {
                        deckToEdit.setDeckImage(imgUri);
                        deckToEdit.setName(name);
                        listenerUpdateDecks.updateDeckList(i, deckToEdit);
                        System.out.println("Se ha editado correctamente el mazo");
                    }
                } else {
                    System.out.println("Ha ocurrido algun problema");
                }
            }

            @Override
            public void onFailure(Call<ResponseUpdateDeck> call, Throwable t) {
                System.out.println(t.getLocalizedMessage());
                System.out.println(t.getCause());
                System.out.println(call.request().toString());
                System.out.println("Ha habido algun error con la api");
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("requestcode -> " + requestCode);
        System.out.println("resultcode -> " + resultCode);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            System.out.println(data.getData().toString());
            uri = data.getData();
            base64Image = convertImageToBase64(uri);
            editImageUri.setText(uri.toString());
        } else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            saveFileInGallery();
        }
    }

    public String convertImageToBase64(Uri imageUri) {
        String base64Image = "";
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(imageUri);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }
            byte[] imageBytes = byteArrayOutputStream.toByteArray();
            String mimeType = context.getContentResolver().getType(imageUri);
            base64Image = "data:" + mimeType + ";base64," + Base64.encodeToString(imageBytes, Base64.DEFAULT);
            inputStream.close();
            byteArrayOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return base64Image;
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        System.out.println("DENTRO DE OPENCAMERA()0");
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if (photoFile != null) {
                System.out.println("DENTRO DE OPENCAMERA()2");
                System.out.println(photoFile.getAbsolutePath());
                Uri photoUri = FileProvider.getUriForFile(
                        context,
                        "com.example.android.fileprovider",
                        photoFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private void saveFileInGallery() {
        File photo = new File(currentPhotoPath);
        Uri imageUri = FileProvider.getUriForFile(context, "com.example.android.fileprovider", photo);
        base64Image = convertImageToBase64(imageUri);
        editImageUri.setText(imageUri.toString());
    }
}