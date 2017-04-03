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

import cl.philipsoft.ph1l.wowcharfb.R;
import cl.philipsoft.ph1l.wowcharfb.models.Character;
import cl.philipsoft.ph1l.wowcharfb.models.CharacterClass;
import cl.philipsoft.ph1l.wowcharfb.models.Faction;
import cl.philipsoft.ph1l.wowcharfb.models.Race;

public class ChooseClassActivity extends AppCompatActivity implements CharacterCallback {

    private long factionID, raceID, classID;
    private Faction charFaction;
    private Race charRace;
    private CharacterClass charClass;
    private Button saveBtn;
    private TextView name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_class);

        factionID = getIntent().getLongExtra("factionID", 1);
        raceID = getIntent().getLongExtra("raceID", 1);


        charFaction = Faction.findById(Faction.class, factionID);
        charRace = Race.findById(Race.class, raceID);
        name = (TextView) findViewById(R.id.nameEt);
        saveBtn = (Button) findViewById(R.id.saveBtn);

        saveBtn.setEnabled(false);
        final RadioGroup classRg = (RadioGroup) findViewById(R.id.classesRg);
        classRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                ////

                String checkedRadioButton = getResources().getResourceEntryName(checkedId);
                String checked = checkedRadioButton.substring(checkedRadioButton.indexOf(".") + 1, checkedRadioButton.indexOf("_"));
                Log.d("WOWC", "onCheckedChanged: classID: " + checked);
                classID = Long.parseLong(checked);
                charClass = CharacterClass.findById(CharacterClass.class, classID);
                Log.d("WOWC", "onCheckedChanged: ClassName" + charClass.getClassName());
                saveBtn.setEnabled(true);
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Character character = new Character(charFaction, charRace, charClass, name.getText().toString());
                CreateCharacter createCharacter = new CreateCharacter(ChooseClassActivity.this);
                createCharacter.validation(character);

                Intent intent = new Intent(ChooseClassActivity.this, MainActivity.class);
                startActivity(intent);
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
