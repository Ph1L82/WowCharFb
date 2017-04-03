package cl.philipsoft.ph1l.wowcharfb.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.List;

import cl.philipsoft.ph1l.wowcharfb.R;
import cl.philipsoft.ph1l.wowcharfb.models.Faction;
import cl.philipsoft.ph1l.wowcharfb.models.Race;

public class ChooseFactionRaceActivity extends AppCompatActivity {
    private Faction faction;
    private Race race;
    private Long factionID, raceID;
    private Button sendBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_faction_race);
        final ImageButton allianceBtn = (ImageButton) findViewById(R.id.AllianceIbtn);
        final ImageButton hordeBtn = (ImageButton) findViewById(R.id.HordeIbtn);
        final RadioGroup allianceRg = (RadioGroup) findViewById(R.id.racesAllianceRg);
        final RadioGroup hordeRg = (RadioGroup) findViewById(R.id.racesHordeRg);
        allianceRg.setEnabled(false);
        hordeRg.setEnabled(false);
        sendBtn = (Button) findViewById(R.id.sendBtn);
        sendBtn.setEnabled(false);
        allianceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hordeBtn.setEnabled(false);
                hordeBtn.setVisibility(View.GONE);
                hordeRg.setVisibility(View.GONE);
                allianceRg.setEnabled(true);
                faction = Faction.findById(Faction.class, 1);
                sendBtn.setBackgroundColor(getResources().getColor(R.color.allianceBackground));
                sendBtn.setTextColor(getResources().getColor(R.color.allianceFront));
                Log.d("WOWC", "onCheckedChanged: CHOSEN FACTION NAME: " + faction.getName());
                Log.d("WOWC", "onCheckedChanged: CHOSEN FACTION ID : " + faction.getId());
                factionID = faction.getId();
            }
        });

        hordeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allianceBtn.setEnabled(false);
                allianceBtn.setVisibility(View.GONE);
                allianceRg.setVisibility(View.GONE);
                hordeRg.setEnabled(true);
                sendBtn.setBackgroundColor(getResources().getColor(R.color.hordeBackground));
                sendBtn.setTextColor(getResources().getColor(R.color.hordeFront));
                faction = Faction.findById(Faction.class, 2);
                Log.d("WOWC", "onCheckedChanged: CHOSEN FACTION NAME: " + faction.getName());
                Log.d("WOWC", "onCheckedChanged: CHOSEN FACTION ID : " + faction.getId());
                factionID = faction.getId();
            }
        });

        allianceRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                selectRace(checkedId);
            }
        });

        hordeRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                selectRace(checkedId);
            }
        });


        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseFactionRaceActivity.this, ChooseClassActivity.class);
                intent.putExtra("factionID", factionID);
                intent.putExtra("raceID", raceID);
                startActivity(intent);
            }
        });
    }

    private void selectRace(int checkedId) {
        String checkedRadioButton = getResources().getResourceEntryName(checkedId);
        String checked = checkedRadioButton.substring(checkedRadioButton.indexOf(".") + 1, checkedRadioButton.indexOf("_"));
        Log.d("WOWC", "onCheckedChanged: raceID: " + checked);
        raceID = Long.parseLong(checked);
        race = Race.findById(Race.class, raceID);
        Log.d("WOWC", "onCheckedChanged: RaceName" + race.getRaceName());
        sendBtn.setEnabled(true);
    }
}
