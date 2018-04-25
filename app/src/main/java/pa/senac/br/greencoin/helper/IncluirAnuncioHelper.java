package pa.senac.br.greencoin.helper;

import android.view.View;
import android.widget.EditText;

import pa.senac.br.greencoin.R;
import pa.senac.br.greencoin.model.Anuncio;
import pa.senac.br.greencoin.model.User;

public class IncluirAnuncioHelper {

    EditText campoTitulo,campoPeso,campoPreco;
    Anuncio a;

    public IncluirAnuncioHelper(View view, Anuncio a) {
        this.campoTitulo = view.findViewById(R.id.et_titulo);
        this.campoPeso = view.findViewById(R.id.et_peso);
        this.campoPreco = view.findViewById(R.id.et_preco);
        this.a = a;
    }

//    public void carregaCampos(User u) {
//        campoEmail.setText(u.getEmail());
//        campoUsername.setText(u.getUsername());
//        campoTelefone.setText(u.getTelefone());
//
//    }

    public Anuncio pegaCampos(){
        if(a == null) a = new Anuncio();

        a.setTitulo(campoTitulo.getText().toString());
        a.setPeso(campoPeso.getText().toString());
        a.setPreco(campoPreco.getText().toString());

        return a;
    }



}
