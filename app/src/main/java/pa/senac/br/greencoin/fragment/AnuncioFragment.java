package pa.senac.br.greencoin.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import pa.senac.br.greencoin.R;
import pa.senac.br.greencoin.adapter.MyAdapter;
import pa.senac.br.greencoin.model.Anuncio;

public class AnuncioFragment extends android.support.v4.app.Fragment {

    FirebaseDatabase database;
    DatabaseReference myRef;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    private List<Anuncio> mList;

    ProgressDialog progressDialog;

//    DatabaseReference databaseReference; //ok
//    ProgressDialog progressDialog; //ok INCLUÍ ELE DEPOIS
//    List<StudentDetails> list = new ArrayList<>(); //ok
//    RecyclerView recyclerView; //ok
//    RecyclerView.Adapter adapter ; //ok

    FloatingActionButton novoAnuncioButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.fragment_anuncio,null); //esse aqui é o padrão
        //View view = inflater.inflate(R.layout.fragment_anuncio,container,false); // esse aqui é o do thiego
        View view = inflater.inflate(R.layout.fragment_anuncio,null); // ficou esse aqui pra possibilitar de dar os view.findVi...

        novoAnuncioButton = view.findViewById(R.id.novoAnuncioId);

        //--RecyclerView marotagens começa aqui--
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_list);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        //----

//-------EXEMPLO
//        recyclerView = (RecyclerView) findViewById(R.id.recyclerView); //ok
//        recyclerView.setHasFixedSize(true);//ok
//        recyclerView.setLayoutManager(new LinearLayoutManager(ShowStudentDetailsActivity.this));//ok
//        progressDialog = new ProgressDialog(ShowStudentDetailsActivity.this); // progress dialog desnecessario
//        progressDialog.setMessage("Loading Data from Firebase Database"); // progress dialog desnecessario
//        progressDialog.show();// progress dialog desnecessario
//-------

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

        progressDialog = new ProgressDialog(getActivity());

        progressDialog.setMessage("Carregando Anúncios...");

        progressDialog.show();

        //Assign FirebaseDatabase.getInstance().getReference to databaseReference and call database path name from MainActivity file.
        //myRef =FirebaseDatabase.getInstance().getReference(MainActivity.Database_Path);
        myRef =FirebaseDatabase.getInstance().getReference();

        myRef.child("anuncio").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                mList = new ArrayList<Anuncio>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    Anuncio anuncio = dataSnapshot.getValue(Anuncio.class);

                    mList.add(anuncio);
                }

                mAdapter = new MyAdapter(getActivity(), mList);

                mRecyclerView.setAdapter(mAdapter);

                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                progressDialog.dismiss();

            }
        });


    }

}
