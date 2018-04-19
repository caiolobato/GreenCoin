package pa.senac.br.greencoin;

import android.content.Intent;
import android.os.Bundle;
import android.os.UserHandle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
//import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import pa.senac.br.greencoin.Fragment.AnuncioFragment;
import pa.senac.br.greencoin.Fragment.FiqueSabendoFragment;
import pa.senac.br.greencoin.Fragment.MapaFragment;
import pa.senac.br.greencoin.model.User;

public class ApplicationActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    private FirebaseUser firebaseUser;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        mAuth = FirebaseAuth.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference();

        firebaseUser = mAuth.getCurrentUser();


        user = new User();
        // pega as infos do usuario atual e joga num User.class
        getUser(); // NO DEBUG  ISSO FUNCIONA.. VAI ENTENDER....


        //No inicio já abre no fragment de mapas
        getSupportFragmentManager().beginTransaction().replace(R.id.screen_area,new MapaFragment()).commit();

        }


    public void getUser() {
        final String userId = getUid();
        myRef.child("users").child(mAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(
                new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        user = dataSnapshot.getValue(User.class);

                        if (user == null) {
                            // User is null, error out
                            Log.e("getUserLogged", "User " + userId + " is unexpectedly null");
                        } else {
                            // Write new post
                            Log.d("getUserLogged", "User " + userId + " getted with sucess muchacho");
                        }


                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w("getUserLogged", "getUser:onCancelled", databaseError.toException());
                    }
                }
        );
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.application, menu);

        //Pegando o usuario e e-mail do Usuario e jogando pro NavDrawer Title/Subtitle
        //String username = myRef.child("users").child(user.toString()).child("username").getKey();

//        String username = user.getUsername();
//        String email = user.getEmail();

        TextView headerNavDrawer = findViewById(R.id.nav_user);
        TextView subNavDrawer = findViewById(R.id.nav_email);

        headerNavDrawer.setText(user.getUsername()); // SE NAO FOR NO DEBUG ISSO AQUI RETORNA NULL
        subNavDrawer.setText(user.getEmail());

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        Fragment fragment = null;

        int id = item.getItemId();

        if (id == R.id.nav_mapa) {
            fragment = new MapaFragment();
        } else if (id == R.id.nav_anuncio) {
            fragment = new AnuncioFragment();
        } else if (id == R.id.nav_fiquesabendo) {
            fragment = new FiqueSabendoFragment();
        } else if (id == R.id.nav_logout) {
            Toast.makeText(this,"Deslogou",Toast.LENGTH_LONG).show();
            mAuth.signOut();
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }

        if(fragment!=null){
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();

            ft.replace(R.id.screen_area,fragment);

            ft.commit();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
