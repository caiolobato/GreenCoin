package pa.senac.br.greencoin.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import pa.senac.br.greencoin.ApplicationActivity;
import pa.senac.br.greencoin.R;
import pa.senac.br.greencoin.helper.EditarContaHelper;
import pa.senac.br.greencoin.helper.IncluirAnuncioHelper;
import pa.senac.br.greencoin.model.Anuncio;
import pa.senac.br.greencoin.model.User;

public class IncluirAnuncioFragment extends android.support.v4.app.Fragment {

    ApplicationActivity activity;
    DatabaseReference myRef;
    FirebaseUser firebaseUser;
    User user;
    String userId;
    IncluirAnuncioHelper helper;
    Anuncio anuncio;

    Button publicarBtn;


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


        publicarBtn = view.findViewById(R.id.botao_publicar);



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

        //AQUI ROLA O CODIGO DA DO FRAGMENT (ACTIVITY)

    }

    private void incluirAnuncio() {
        String key = myRef.child("anuncio").push().getKey(); // é isso mesmo para pegar a key do novo child?
        anuncio.setUid(key);
        anuncio = helper.pegaCampos();
        anuncio.setUserUid(userId);
        anuncio.setData(activity.getDate());
        anuncio.setAtivo(true);

        myRef.child("anuncio").child(key).setValue(anuncio);

        // INCLUIR AQUI O DIALOG DE CONFIRMAÇÃO

        Toast.makeText(activity,"Anúncio publicado...",Toast.LENGTH_SHORT).show(); // PODE FAZER UM METODO PADRÃO PRA TOASTS NA BASE ACTIVITY

        activity.getSupportFragmentManager().beginTransaction().replace(R.id.screen_area,new AnuncioFragment()).commit();

    }


}
