package cl.philipsoft.ph1l.wowcharfb.views;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import cl.philipsoft.ph1l.wowcharfb.R;
import cl.philipsoft.ph1l.wowcharfb.data.CurrentUser;
import cl.philipsoft.ph1l.wowcharfb.data.Nodes;
import cl.philipsoft.ph1l.wowcharfb.models.Character;
import cl.philipsoft.ph1l.wowcharfb.models.Class;
import cl.philipsoft.ph1l.wowcharfb.models.Faction;
import cl.philipsoft.ph1l.wowcharfb.models.Race;

public class CharacterDetailsActivity extends AppCompatActivity {
    Character character;
    TextView nameTv;
    TextView factionTv;
    TextView raceTv;
    TextView classTv;
    TextView levelTextView;
    TextView staminaTextView;
    TextView strengthTextView;
    TextView agilityTextView;
    TextView intellectTextView;
    TextView spiritTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        Character character = (Character) getIntent().getSerializableExtra("Character");
//        final Character character = Character.findById(Character.class, getIntent().getLongExtra("characterID", 1));
//        Character character = new Nodes().userCharacters(new CurrentUser().userID()).getKey();
        new Nodes().userCharacters(new CurrentUser().userID()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                character = dataSnapshot.getValue(Character.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Log.d("WOWC", "onCreate: CHARACTER NAME : " + character.getCharacterName());
        Log.d("WOWC", "onCreate: CHARACTER ID : " + character.getId());
        final Faction characterFaction = Faction.findById(Faction.class, getIntent().getLongExtra("factionID", 1));
        Log.d("WOWC", "onCreate: FACTION NAME : " + characterFaction.getName());
        Log.d("WOWC", "onCreate: FACTION ID : " + characterFaction.getId());
        final Race characterRace = Race.findById(Race.class, getIntent().getLongExtra("raceID", 1));
        Log.d("WOWC", "onCreate: RACE NAME : " + characterRace.getRaceName());
        Log.d("WOWC", "onCreate: RACE ID : " + characterRace.getId());
        final Class characterClass = Class.findById(Class.class, getIntent().getLongExtra("classID", 1));
        Log.d("WOWC", "onCreate: CLASS NAME : " + characterClass.getClassName());
        Log.d("WOWC", "onCreate: CLASS ID : " + characterClass.getId());
        ImageView factionShield = (ImageView) findViewById(R.id.factionShield);
        Faction faction = character.getCharacterFaction();
//        switch can't manage long.... u.u
        if (characterFaction.getId() == 1) {
            factionShield.setImageResource(R.mipmap.ic_wow_alliance_48dp);
            factionShield.setBackgroundColor(getResources().getColor(R.color.allianceBackground));
            setTitleColor(getResources().getColor(R.color.allianceFront));
        } else if (faction.getId() == 2) {
            factionShield.setImageResource(R.mipmap.ic_wow_horde_48dp);
            factionShield.setBackgroundColor(getResources().getColor(R.color.hordeBackground));
            setTitleColor(getResources().getColor(R.color.hordeFront));
        } else {
            factionShield.setImageResource(R.mipmap.ic_wow_48dp);
        }
        setTitle(character.getCharacterName());


        nameTv = (TextView) findViewById(R.id.nameTv);
        factionTv = (TextView) findViewById(R.id.charFactionTv);
        raceTv = (TextView) findViewById(R.id.charRaceTv);
        classTv = (TextView) findViewById(R.id.charClassTv);
        levelTextView = (TextView) findViewById(R.id.levelTv);
        staminaTextView = (TextView) findViewById(R.id.staminaTv);
        strengthTextView = (TextView) findViewById(R.id.strengthTv);
        agilityTextView = (TextView) findViewById(R.id.agilityTv);
        intellectTextView = (TextView) findViewById(R.id.intellectTv);
        spiritTextView = (TextView) findViewById(R.id.spiritTv);

        nameTv.setText(String.valueOf(character.getCharacterName()));
        factionTv.setText(String.valueOf(character.getCharacterFaction().getName()));
        raceTv.setText(String.valueOf(character.getCharacterRace().getRaceName()));
        classTv.setText(String.valueOf(character.getCharacterClass().getClassName()));
        levelTextView.setText(String.valueOf(character.getCharacterLevel()));
        staminaTextView.setText(String.valueOf(character.getStamina()));
        strengthTextView.setText(String.valueOf(character.getStrength()));
        agilityTextView.setText(String.valueOf(character.getAgility()));
        intellectTextView.setText(String.valueOf(character.getIntellect()));
        spiritTextView.setText(String.valueOf(character.getSpirit()));


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.charDetailFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(CharacterDetailsActivity.this)
                        .setTitle("Compartir personaje: " + character.getCharacterName().toString())
                        .setMessage("¿Está seguro?")
                        .setIcon(android.R.drawable.ic_input_delete)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                character.setShared(true);
                                Toast.makeText(CharacterDetailsActivity.this, "", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton(android.R.string.no, null).show();
            }
        });
    }

}
