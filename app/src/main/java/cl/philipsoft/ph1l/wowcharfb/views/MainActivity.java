package cl.philipsoft.ph1l.wowcharfb.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import cl.philipsoft.ph1l.wowcharfb.R;
import cl.philipsoft.ph1l.wowcharfb.data.DataSeeder;
import cl.philipsoft.ph1l.wowcharfb.data.Queries;
import cl.philipsoft.ph1l.wowcharfb.models.Character;
import cl.philipsoft.ph1l.wowcharfb.views.login.LoginActivity;
import cl.philipsoft.ph1l.wowcharfb.views.login.LogoutCallback;
import cl.philipsoft.ph1l.wowcharfb.views.login.LogoutValidation;

public class MainActivity extends AppCompatActivity implements CharacterCallback, LogoutCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dataLoader();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ChooseFactionRaceActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_signOut:
                Log.d("WOWC:LOGOUT", "onOptionsItemSelected: Salir seleccionado");
                new LogoutValidation(this).validate();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void created(Character character) {
        Log.d("WOWC", "created: Personaje creado: " + character.getCharacterName());
    }

    @Override
    public void noName() {
        Toast.makeText(this, "El personaje no tiene nombre.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void noFaction() {
        Toast.makeText(this, "El personaje no tiene Facción", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void noClass() {
        Toast.makeText(this, "No tiene clase", Toast.LENGTH_SHORT).show();
    }

    private void dataLoader() {
        Queries queries = new Queries();
        DataSeeder dataSeeder = new DataSeeder();
        if (queries.factions().size() < 1) {
            Log.d("WOWCDATA", "Queries().factions().size() : " + queries.factions().size());
            dataSeeder.createFactions();
            Log.d("WOWCDATA", "dataLoader: Facciones creadas");
        }
        if (queries.races().size() < 11) {
            Log.d("WOWCDATA", "Queries().races().size() : " + queries.races().size());
            dataSeeder.createRaces();
            Log.d("WOWCDATA", "dataLoader: Razas creadas");
        }
        if (queries.classes().size() < 8) {
            Log.d("WOWCDATA", "Queries().classes().size() : " + queries.classes().size());
            dataSeeder.createClasses();
            Log.d("WOWCDATA", "dataLoader: Clases creadas");
        }
    }

    @Override
    public void signOut() {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(MainActivity.this, "Haz cerrado tu sesión", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    }
                });
    }
}
