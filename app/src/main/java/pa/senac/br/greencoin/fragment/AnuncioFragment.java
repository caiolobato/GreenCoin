package pa.senac.br.greencoin.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pa.senac.br.greencoin.ApplicationActivity;
import pa.senac.br.greencoin.R;
import pa.senac.br.greencoin.adapter.MyAdapter;
import pa.senac.br.greencoin.model.Anuncio;

public class AnuncioFragment extends android.support.v4.app.Fragment {

    //private FirebaseDatabase database;
    private DatabaseReference myRef;
    private ApplicationActivity activity;

    private FloatingActionButton novoAnuncioButton;

    private RecyclerView mRecyclerView;

    private List<Anuncio> mList;

    View view;
    MyAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.fragment_anuncio,null); //esse aqui é o padrão
        //View view = inflater.inflate(R.layout.fragment_anuncio,container,false); // esse aqui é o do thiego
        View view = inflater.inflate(R.layout.fragment_anuncio,null); // ficou esse aqui pra possibilitar de dar os view.findVi...
        mList = new ArrayList<Anuncio>();
        activity = (ApplicationActivity) getActivity();
        myRef = activity.myRef;

        novoAnuncioButton = view.findViewById(R.id.novoAnuncioId);

        novoAnuncioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ir para o fragment de incluir anuncio
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.screen_area,new IncluirAnuncioFragment()).commit();
            }
        });


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view=view;
        initRecycleView();
        activity.showProgressDialog("Carregando...");
        carregarAnuncios();

    }


    private void initRecycleView() {

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_list);

        RecyclerView.LayoutManager layout = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(layout);
        adapter = new MyAdapter(mList,getContext());
        //divisoria dos items - INICIO
        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(
                mRecyclerView.getContext(),
                DividerItemDecoration.VERTICAL
        );
        mRecyclerView.addItemDecoration(mDividerItemDecoration);
        //divisoria dos items - FIM
        mRecyclerView.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }

    private void carregarAnuncios() {
        //myRef = FirebaseDatabase.getInstance().getReference("anuncio");
//        mList = new ArrayList<Anuncio>();
        myRef.child("anuncio").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mList.clear();
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    Anuncio anuncio = d.getValue(Anuncio.class);
                    mList.add(anuncio);
                }
                Collections.reverse(mList); // botar na ordem inversa
                adapter.notifyDataSetChanged();
                activity.hideProgressDialog();

                maceteDoBugDoido();

//                mRecyclerView.setAdapter(new MyAdapter(mList,getContext()));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void maceteDoBugDoido() {
        // depois que botou o glide isso parou de funfar...
        int p = mRecyclerView.getVerticalScrollbarPosition();

        mRecyclerView.smoothScrollToPosition(mList.size()-1); //scroola até o final da lista
        mRecyclerView.smoothScrollToPosition(0); //scroola até o inicio
        // assim ele carrega os items, era uma vez um bug, era uma vez uma gambiarra...

        mRecyclerView.smoothScrollToPosition(p); // volta pra posição que tava, se não ele vai ficar indo pra primeira
    }

}
