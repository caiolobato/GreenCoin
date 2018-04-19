package pa.senac.br.greencoin.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import pa.senac.br.greencoin.R;

public class MapaFragment extends android.support.v4.app.Fragment {

    TextView texto;
    Switch switchB;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mapa,null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //USAR O QUE VAI ROLAR NA FRAGMENT AQUI!!!
        texto = view.findViewById(R.id.textViewMapa);
        switchB = view.findViewById(R.id.switchMapa);

        switchB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(switchB.isChecked()) texto.setText("Mapa do Mestre Orlando");
                else texto.setText("Tela de Mapas");
            }
        });



    }
}
