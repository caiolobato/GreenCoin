package pa.senac.br.greencoin.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.AppLaunchChecker;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import pa.senac.br.greencoin.ApplicationActivity;
import pa.senac.br.greencoin.R;
import pa.senac.br.greencoin.helper.EditarContaHelper;
import pa.senac.br.greencoin.model.User;

public class EditarContaFragment extends android.support.v4.app.Fragment {


    //private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    private FirebaseUser firebaseUser;
    private User user;
    private ApplicationActivity activity;
    private EditarContaHelper helper;
    private Button botaoSalvar;
    private String userId;
    private EditText campoEmail,campoNome,campoTelefone;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_editar_conta,null);

        botaoSalvar = view.findViewById(R.id.salvar_botao_id);

        activity = (ApplicationActivity) getActivity();
        myRef = activity.myRef;
        firebaseUser = activity.firebaseUser;
        user = activity.user;
        userId = firebaseUser.getUid();
        helper = new EditarContaHelper(view,user);

//        campoNome.setText(user.getUsername());
//        campoTelefone.setText(user.getTelefone());

        helper.carregaCampos(user);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //AQUI ROLA O CODIGO DA DO FRAGMENT (ACTIVITY)

        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                user = helper.pegaCampos();

                //salva no firebase aqui
                confirmaDialog();

            }
        });

    }

    public void confirmaDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        builder.setTitle("Confirmar")
                .setMessage("Tem certeza que deseja salvar as alterações?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //salva no firebase
                        myRef.child("users").child(userId).setValue(user);
                        Toast.makeText(getActivity(),"Alterações realizadas",Toast.LENGTH_SHORT).show();
                        //muda pra tela de mapa
                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.screen_area,new MapaFragment()).commit();
                    }
                })
                .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(activity,"Alterações canceladas",Toast.LENGTH_SHORT).show();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();

    }







}