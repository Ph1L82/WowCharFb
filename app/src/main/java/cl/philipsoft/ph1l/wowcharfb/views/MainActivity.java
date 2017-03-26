package cl.philipsoft.ph1l.wowcharfb.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import cl.philipsoft.ph1l.wowcharfb.R;
import cl.philipsoft.ph1l.wowcharfb.data.DataSeeder;
import cl.philipsoft.ph1l.wowcharfb.data.Queries;
import cl.philipsoft.ph1l.wowcharfb.models.Character;

public class MainActivity extends AppCompatActivity implements CharacterCallback {

    private CharacterListFragment characterListFragment;

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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void created(Character character) {
        Log.d("WOWC", "created: Personaje creado: " + character.getCharacterName());
        characterListFragment.addCharacter(character);
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
}
