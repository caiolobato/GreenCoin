package pa.senac.br.greencoin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import pa.senac.br.greencoin.adapter.MyAdapter;
import pa.senac.br.greencoin.model.Anuncio;

public class AnuncioFragment extends android.support.v4.app.Fragment {

    FirebaseDatabase database;
    DatabaseReference myRef;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    private List<Anuncio> mList;

    //FloatingActionButton novoAnuncioButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.fragment_anuncio,null); //esse aqui é o padrão
        //View view = inflater.inflate(R.layout.fragment_anuncio,container,false); // esse aqui é o do thiego
        View view = inflater.inflate(R.layout.fragment_anuncio,null); // ficou esse aqui pra possibilitar de dar os view.findVi...


        //----
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_list);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(mList);
        mRecyclerView.setAdapter(mAdapter);
        //----


        return view;
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
