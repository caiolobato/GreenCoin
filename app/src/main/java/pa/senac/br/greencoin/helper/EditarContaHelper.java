package pa.senac.br.greencoin.helper;

import android.view.View;
import android.widget.EditText;

import pa.senac.br.greencoin.ApplicationActivity;
import pa.senac.br.greencoin.R;
import pa.senac.br.greencoin.fragment.EditarContaFragment;
import pa.senac.br.greencoin.model.User;

public class EditarContaHelper {

    private EditText campoEmail,campoUsername,campoTelefone;
    private User u;

    public EditarContaHelper(View view, User u) {
        this.campoEmail = view.findViewById(R.id.et_email);
        this.campoUsername = view.findViewById(R.id.et_username);
        this.campoTelefone = view.findViewById(R.id.et_telefone);
        this.u = u;
    }

    public void carregaCampos(User u) {
        campoEmail.setText(u.getEmail());
        campoUsername.setText(u.getUsername());
        campoTelefone.setText(u.getTelefone());

    }

    public User pegaCampos(){
        if(u == null) u = new User();

        u.setEmail(campoEmail.getText().toString());
        u.setUsername(campoUsername.getText().toString());
        u.setTelefone(campoTelefone.getText().toString());

        return u;
    }



}
