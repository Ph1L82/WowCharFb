package cl.philipsoft.ph1l.wowcharfb.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cl.philipsoft.ph1l.wowcharfb.R;
import cl.philipsoft.ph1l.wowcharfb.data.CurrentUser;
import cl.philipsoft.ph1l.wowcharfb.data.Nodes;
import cl.philipsoft.ph1l.wowcharfb.models.Character;
import cl.philipsoft.ph1l.wowcharfb.models.Class;
import cl.philipsoft.ph1l.wowcharfb.models.Faction;
import cl.philipsoft.ph1l.wowcharfb.models.Race;

public class ChooseClassActivity extends AppCompatActivity implements CharacterCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_class);
//        final Faction charFaction = (Faction) getIntent().getSerializableExtra("Faction");
//        final Race charRace = (Race) getIntent().getSerializableExtra("Race");
        final long factionID = getIntent().getLongExtra("factionID", 1);
        final long raceID = getIntent().getLongExtra("raceID", 1);
        final Faction charFaction = Faction.findById(Faction.class, factionID);
        final Race charRace = Race.findById(Race.class, raceID);
        final TextView name = (TextView) findViewById(R.id.nameEt);
        final Button saveBtn = (Button) findViewById(R.id.saveBtn);
        saveBtn.setEnabled(false);
        final RadioGroup classRg = (RadioGroup) findViewById(R.id.classesRg);
        classRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                Log.d("WOWC", "onCheckedChanged: " + getResources().getResourceEntryName(classRg.getCheckedRadioButtonId()).replace("class", ""));
                saveBtn.setEnabled(true);
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String className = getResources().getResourceEntryName(classRg.getCheckedRadioButtonId()).replace("class", "");
                List<Class> charClassList = Class.find(Class.class, "class_name = ?", className);
                if (!charClassList.isEmpty()) {
                    Class charClass = charClassList.get(0);
                    Log.d("WOWC", "onClick: CHOSEN CLASS NAME: " + charClass.getClassName());
                    Log.d("WOWC", "onClick: CHOSEN CLASS ID: " + charClass.getId());
                    long classID = charClass.getId();
                    CreateCharacter createCharacter = new CreateCharacter(ChooseClassActivity.this);
                    Character character = new Character(charFaction, charRace, charClass, name.getText().toString());
                    createCharacter.validation(character, factionID, raceID, classID);

                    new Nodes().userCharacters(new CurrentUser().userID()).push().setValue(character);

                    Intent intent = new Intent(ChooseClassActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Log.d("WOWC", "onClick: Clase no encontrada: " + className);
                }
            }
        });
    }

    @Override
    public void created(Character character) {
        Log.d("WOWC", "created: Personaje creado: " + character.getCharacterName());
    }

    @Override
    public void noName() {
        Log.d("WOWC", "noName: ");
        Toast.makeText(this, "Debes darle un nombre a tu personaje", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void noFaction() {
        Log.d("WOWC", "noFaction: ");
        Toast.makeText(this, "Tu personaje debe pertenecer a una facción, no seas rebelde!.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void noClass() {
        Log.d("WOWC", "noClass: ");
        Toast.makeText(this, "Tu personaje debe ser de una clase determinada.", Toast.LENGTH_SHORT).show();
    }
}
