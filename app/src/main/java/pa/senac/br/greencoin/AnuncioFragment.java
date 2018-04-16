package pa.senac.br.greencoin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AnuncioFragment extends android.support.v4.app.Fragment {

    FirebaseDatabase database;
    DatabaseReference myRef;
    //FloatingActionButton novoAnuncioButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_anuncio,null);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //AQUI ROLA O CODIGO DO FRAGMENT (ACTIVITY)
        iniciaFirebase();

    }

    private void iniciaFirebase() {
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Anuncio");
    }
}
