package pa.senac.br.greencoin.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import pa.senac.br.greencoin.ApplicationActivity;
import pa.senac.br.greencoin.R;
import pa.senac.br.greencoin.helper.EditarContaHelper;
import pa.senac.br.greencoin.helper.IncluirAnuncioHelper;
import pa.senac.br.greencoin.model.Anuncio;
import pa.senac.br.greencoin.model.User;

import static android.app.Activity.RESULT_OK;

public class IncluirAnuncioFragment extends android.support.v4.app.Fragment {

    ApplicationActivity activity;
    DatabaseReference myRef;
    FirebaseUser firebaseUser;
    User user;
    String userId;
    IncluirAnuncioHelper helper;
    Anuncio anuncio;

    private StorageReference mStorageRef;
    public static final int GALLERY_INTENT = 2;

    Button publicarBtn, cancelarBtn, insereImagemBtn;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_incluir_anuncio,null);

        activity = (ApplicationActivity) getActivity();
        myRef = activity.myRef;
        firebaseUser = activity.firebaseUser;
        user = activity.user;
        userId = firebaseUser.getUid();
        helper = new IncluirAnuncioHelper(view,anuncio);

        anuncio = new Anuncio();

        mStorageRef = FirebaseStorage.getInstance().getReference();

        publicarBtn = view.findViewById(R.id.botao_publicar);
        cancelarBtn = view.findViewById(R.id.botao_cancelar);
        insereImagemBtn = view.findViewById(R.id.botao_insere);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        publicarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incluirAnuncio();
            }
        });
        cancelarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelar();
            }
        });
        insereImagemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inserirImagem();
            }
        });
        //AQUI ROLA O CODIGO DA DO FRAGMENT (ACTIVITY)

    }





    private void incluirAnuncio() {
        String key = myRef.child("anuncio").push().getKey(); // é isso mesmo para pegar a key do novo child?
        anuncio.setUid(key);
        anuncio = helper.pegaCampos();
        anuncio.setUserUid(userId);
        anuncio.setData(activity.getDate());
        //anuncio.setAtivo(true);
        anuncio.setOwnerName(user.getUsername());

        // colocar a uid da imagem
        anuncio.setImagemUid(null);

        myRef.child("anuncio").child(key).setValue(anuncio);

        // INCLUIR AQUI O DIALOG DE CONFIRMAÇÃO

        Toast.makeText(activity,"Anúncio publicado...",Toast.LENGTH_SHORT).show(); // PODE FAZER UM METODO PADRÃO PRA TOASTS NA BASE ACTIVITY

        activity.getSupportFragmentManager().beginTransaction().replace(R.id.screen_area,new AnuncioFragment()).commit();

    }

    public void cancelar(){
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.screen_area,new AnuncioFragment()).commit();
    }

    private void inserirImagem() {
        Intent intent = new Intent(Intent.ACTION_PICK);

        intent.setType("image/*");

        startActivityForResult(intent,GALLERY_INTENT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_INTENT && resultCode == RESULT_OK) {

            activity.showProgressDialog("Enviando...");
            Uri uri = data.getData();

            StorageReference filepath = mStorageRef.child("Photos").child(uri.getLastPathSegment());

            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(getContext(),"Upload done",Toast.LENGTH_LONG).show();
                    activity.hideProgressDialog();
                }
            });

        }

    }
}
