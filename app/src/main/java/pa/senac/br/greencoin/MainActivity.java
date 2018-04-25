package pa.senac.br.greencoin;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.annotation.NonNull;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import pa.senac.br.greencoin.model.User;

public class MainActivity extends BaseActivity implements
        View.OnClickListener {

    private EditText mEmailField;
    private EditText mPasswordField;
    //private ProgressDialog progressDialog;


    private FirebaseAuth mAuth;
    DatabaseReference myRef;

    private VideoView mVideoView;


    // Root Database Name for Firebase Database.
    public static final String Database_Path = "greencoin-87179";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //progressDialog = new ProgressDialog(MainActivity.this);

        mEmailField = findViewById(R.id.field_email);
        mPasswordField = findViewById(R.id.field_password);

        findViewById(R.id.wechatLoginButton).setOnClickListener(this);
        findViewById(R.id.fbLoginButton).setOnClickListener(this);
        findViewById(R.id.otherAccButton).setOnClickListener(this);
        findViewById(R.id.createAccountButton).setOnClickListener(this);
        findViewById(R.id.loginButton).setOnClickListener(this);


        //In the onCreate() method, initialize the FirebaseAuth instance.
        mAuth = FirebaseAuth.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference();

        //mAuth.signOut();
        //mAuth.createUserWithEmailAndPassword("caiolobatogon@gmail.com","12345678");

        videoBG();

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser user) {
        hideProgressDialog();
        //progressDialog.hide();
        //verifica se usuario está logado
        if (user != null) {
            Intent intent = new Intent(MainActivity.this,ApplicationActivity.class);
            intent.putExtra("USER",user);
            startActivity(intent);
            Log.i("updateUI","Usuario está logado");
        } else {
            Log.i("updateUI","Usuario não está logado");
        }
    }





    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.wechatLoginButton) {
            Toast.makeText(this,"Google",Toast.LENGTH_LONG).show();
        } else if (i == R.id.fbLoginButton) {
            Toast.makeText(this,"Facebook",Toast.LENGTH_SHORT).show();
        } else if (i == R.id.otherAccButton) {
            criarContaUpdateUI(true);
            Toast.makeText(this,"Outras Contas",Toast.LENGTH_SHORT).show();
        } else if (i== R.id.createAccountButton){
            criarConta(mEmailField.getText().toString(), mPasswordField.getText().toString());
            Toast.makeText(this,"Criar conta",Toast.LENGTH_SHORT).show();
        } else if (i== R.id.loginButton){
            logar(mEmailField.getText().toString(), mPasswordField.getText().toString());
            Toast.makeText(this,"Logar",Toast.LENGTH_SHORT).show();
        }
    }

    private void criarConta(String email, String password) {
        Log.d("createUser", "createAccount:" + email);
        if (!validateForm()) {
            return;
        }

//        progressDialog.setMessage("Carregando");
//        progressDialog.show();
        showProgressDialog("Carregando");


        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("createUser", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            FirstAuthSuccess(user);
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("createUser", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // [START_EXCLUDE]
                        hideProgressDialog();
                        //progressDialog.hide();
                        // [END_EXCLUDE]
                    }
                });
        // [END create_user_with_email]
    }


    private void FirstAuthSuccess(FirebaseUser user) {
        String username = usernameFromEmail(user.getEmail());
        // Write new user
        writeNewUser(user.getUid(), username, user.getEmail());
    }

    private String usernameFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }

    // [START basic_write] // escreve o usuario no database
    private void writeNewUser(String userId, String name, String email) {
        User user = new User(userId, name, email);
        myRef.child("users").child(userId).setValue(user);
    }
    // [END basic_write]



    private void logar(String email, String password) {
        Log.d("signIn", "signIn:" + email);
        if (!validateForm()) {
            return;
        }

        showProgressDialog("Carregando");
//        progressDialog.setMessage("Carregando");
//        progressDialog.show();

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("signIn", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("signIn", "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // [START_EXCLUDE]
//                        if (!task.isSuccessful()) {
//                            mStatusTextView.setText(R.string.auth_failed);
//                        }
                        hideProgressDialog();
                        //progressDialog.hide();
                        // [END_EXCLUDE]
                    }
                });
        // [END sign_in_with_email]
    }




    private void criarContaUpdateUI(boolean b) {

        if(b==true) {
            findViewById(R.id.wechatLoginButton).setVisibility(View.INVISIBLE);
            findViewById(R.id.fbLoginButton).setVisibility(View.INVISIBLE);
            findViewById(R.id.otherAccButton).setVisibility(View.INVISIBLE);

            findViewById(R.id.field_email).setVisibility(View.VISIBLE);
            findViewById(R.id.field_password).setVisibility(View.VISIBLE);
            findViewById(R.id.loginButton).setVisibility(View.VISIBLE);
            findViewById(R.id.createAccountButton).setVisibility(View.VISIBLE);

        } else {
            findViewById(R.id.wechatLoginButton).setVisibility(View.VISIBLE);
            findViewById(R.id.fbLoginButton).setVisibility(View.VISIBLE);
            findViewById(R.id.otherAccButton).setVisibility(View.VISIBLE);

            findViewById(R.id.field_email).setVisibility(View.INVISIBLE);
            findViewById(R.id.field_password).setVisibility(View.INVISIBLE);
            findViewById(R.id.loginButton).setVisibility(View.INVISIBLE);
            findViewById(R.id.createAccountButton).setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        criarContaUpdateUI(false);
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = mEmailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmailField.setError("Campo necessário.");
            valid = false;
        } else {
            mEmailField.setError(null);
        }

        String password = mPasswordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPasswordField.setError("Campo necessário.");
            valid = false;
        } else {
            mPasswordField.setError(null);
        }

        return valid;
    }


    //---------video
    private void videoBG() {
        //Codigo do video começa aqui
        mVideoView = findViewById(R.id.bgVideoView);

        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.bg_video2);

        mVideoView.setVideoURI(uri);
        mVideoView.start();


        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setLooping(true);
            }
        });
        //Codigo do video termina aqui
    }


}
