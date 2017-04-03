package cl.philipsoft.ph1l.wowcharfb.views;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import cl.philipsoft.ph1l.wowcharfb.models.CharacterClass;
import cl.philipsoft.ph1l.wowcharfb.models.Faction;
import cl.philipsoft.ph1l.wowcharfb.models.Race;

public class CharacterDetailsActivity extends AppCompatActivity {
    private Character character;
    private Faction characterFaction;
    private Race characterRace;
    private CharacterClass characterClass;
    private String uid, charID;
    private TextView charNameTv, charFactionTv, charRaceTv, charClassTv, levelTv, staminaTv, strengthTv, agilityTv, intellectTv, spiritTv;
    private ImageView factionShield;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        uid = new CurrentUser().userID();
        Intent intent = getIntent();
        charID = intent.getStringExtra("id");
        Log.d("WOWC", "CharacterDetailsActivity onCreate: received charID:" + String.valueOf(charID));

        new Nodes().userCharacters(new CurrentUser().userID()).child(charID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                character = dataSnapshot.getValue(Character.class);

                Log.d("WOWC", "onCreate: CHARACTER NAME : " + character.getCharacterName());
                Log.d("WOWC", "onCreate: CHARACTER ID : " + character.getId());
                characterFaction = character.getCharacterFaction();
                Log.d("WOWC", "onCreate: FACTION NAME : " + characterFaction.getName());
                Log.d("WOWC", "onCreate: FACTION ID : " + characterFaction.getId());
                characterRace = character.getCharacterRace();
                Log.d("WOWC", "onCreate: RACE NAME : " + characterRace.getRaceName());
                Log.d("WOWC", "onCreate: RACE ID : " + characterRace.getId());
                characterClass = character.getCharacterClass();
                Log.d("WOWC", "onCreate: CLASS NAME : " + characterClass.getClassName());
                Log.d("WOWC", "onCreate: CLASS ID : " + characterClass.getId());
                factionShield = (ImageView) findViewById(R.id.factionShield);

                if (characterFaction.getId() == 1) {
                    factionShield.setImageResource(R.mipmap.ic_wow_alliance_48dp);
                    factionShield.setBackgroundColor(getResources().getColor(R.color.allianceBackground));
                    setTitleColor(getResources().getColor(R.color.allianceFront));
                } else if (characterFaction.getId() == 2) {
                    factionShield.setImageResource(R.mipmap.ic_wow_horde_48dp);
                    factionShield.setBackgroundColor(getResources().getColor(R.color.hordeBackground));
                    setTitleColor(getResources().getColor(R.color.hordeFront));
                } else {
                    factionShield.setImageResource(R.mipmap.ic_wow_48dp);
                }
                setTitle(character.getCharacterName());

                charNameTv = (TextView) findViewById(R.id.charNameTv);
                charFactionTv = (TextView) findViewById(R.id.charFactionTv);
                charRaceTv = (TextView) findViewById(R.id.charRaceTv);
                charClassTv = (TextView) findViewById(R.id.charClassTv);
                levelTv = (TextView) findViewById(R.id.levelTv);
                staminaTv = (TextView) findViewById(R.id.staminaTv);
                strengthTv = (TextView) findViewById(R.id.strengthTv);
                agilityTv = (TextView) findViewById(R.id.agilityTv);
                intellectTv = (TextView) findViewById(R.id.intellectTv);
                spiritTv = (TextView) findViewById(R.id.spiritTv);

                charNameTv.setText("Nombre: " + character.getCharacterName());
                charFactionTv.setText("Facción: " + character.getCharacterFaction().getName());
                charRaceTv.setText("Raza: " + character.getCharacterRace().getRaceName());
                charClassTv.setText("Clase: " + character.getCharacterClass().getClassName());
                levelTv.setText(String.valueOf("Nivel: " + character.getCharacterLevel()));
                staminaTv.setText(String.valueOf("Resistencia: " + character.getStamina()));
                strengthTv.setText(String.valueOf("Fuerza: " + character.getStrength()));
                agilityTv.setText(String.valueOf("Agilidad: " + character.getAgility()));
                intellectTv.setText(String.valueOf("Intelecto: " + character.getIntellect()));
                spiritTv.setText(String.valueOf("Espiritu: " + character.getSpirit()));

                FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.charDetailFab);
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new AlertDialog.Builder(CharacterDetailsActivity.this)
                                .setTitle("¿Quieres marcar tu personaje: " + character.getCharacterName() + " como favorito?")
                                .setMessage("¿Estás seguro?")
                                .setIcon(android.R.drawable.ic_input_delete)
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        character.setFavorite(true);
                                        new Nodes().userCharacters(uid).child(charID).setValue(character);
                                        Toast.makeText(CharacterDetailsActivity.this, character.getCharacterName() + " marcado como favorito", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .setNegativeButton(android.R.string.no, null).show();
                    }
                });

                Button deleteBtn = (Button) findViewById(R.id.deleteBtn);
                deleteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new AlertDialog.Builder(CharacterDetailsActivity.this)
                                .setTitle("Eliminar personaje")
                                .setMessage("Eliminarás el personaje de forma permanente")
                                .setIcon(android.R.drawable.ic_input_delete)
                                .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        String id = character.getId();
                                        Log.d("WOWC:FBREMOVE", "onClick: REMOVE CHARACTER ID:" + id);
                                        new Nodes().userCharacters(uid).child(id).removeValue();
                                    }
                                })
                                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Log.d("WOWC:FBREMOVE", "onClick: REMOVE CHARACTER CANCELED");
                                    }
                                });
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("WOWC", "onCancelled: Se canceló la descarga del personaje");
            }
        });


    }

}
