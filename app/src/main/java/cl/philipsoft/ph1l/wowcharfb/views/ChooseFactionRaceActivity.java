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

import java.util.List;

import cl.philipsoft.ph1l.wowcharfb.R;
import cl.philipsoft.ph1l.wowcharfb.models.Faction;
import cl.philipsoft.ph1l.wowcharfb.models.Race;

public class ChooseFactionRaceActivity extends AppCompatActivity {
    private Faction faction;
    private Race race;
    private String raceName;
    private Long factionID, raceID;

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
        final Button sendBtn = (Button) findViewById(R.id.sendBtn);
        sendBtn.setEnabled(false);
        allianceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hordeBtn.setEnabled(false);
                hordeBtn.setVisibility(View.GONE);
                hordeRg.setVisibility(View.GONE);
                allianceRg.setEnabled(true);
                List<Faction> factionList = Faction.find(Faction.class, "name = ?", "Alliance");
                sendBtn.setBackgroundColor(getResources().getColor(R.color.allianceBackground));
                sendBtn.setTextColor(getResources().getColor(R.color.allianceFront));
                faction = factionList.get(0);
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
                List<Faction> factionList = Faction.find(Faction.class, "name = ?", "Horde");
                sendBtn.setBackgroundColor(getResources().getColor(R.color.hordeBackground));
                sendBtn.setTextColor(getResources().getColor(R.color.hordeFront));
                faction = factionList.get(0);
                Log.d("WOWC", "onCheckedChanged: CHOSEN FACTION NAME: " + faction.getName());
                Log.d("WOWC", "onCheckedChanged: CHOSEN FACTION ID : " + faction.getId());
                factionID = faction.getId();
            }
        });

        allianceRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                raceName = getResources().getResourceEntryName(checkedId).replace("race", "");
                raceName = raceName.replace("Rb", "");
                Log.d("WOWC", "onCheckedChanged: raceName: " + raceName);
                List<Race> raceList = Race.find(Race.class, "race_name = ?", raceName);
                if (!raceList.isEmpty()) {
                    race = raceList.get(0);
                    Log.d("WOWC", "onCheckedChanged: CHOSEN RACE NAME: " + race.getRaceName());
                    Log.d("WOWC", "onCheckedChanged: CHOSEN RACE ID: " + race.getId());
                    raceID = race.getId();
                    sendBtn.setEnabled(true);
                } else {
                    Log.d("WOWC", "onCheckedChanged: raceList size: " + raceList.size());
                }
            }
        });

        hordeRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                raceName = getResources().getResourceEntryName(checkedId).replace("race", "");
                raceName = raceName.replace("Rb", "");
                Log.d("WOWC", "onCheckedChanged: raceName: " + raceName);
                List<Race> raceList = Race.find(Race.class, "race_name = ?", raceName);
                if (!raceList.isEmpty()) {
                    race = raceList.get(0);
                    Log.d("WOWC", "onCheckedChanged: CHOSEN RACE NAME: " + race.getRaceName());
                    Log.d("WOWC", "onCheckedChanged: CHOSEN RACE ID: " + race.getId());
                    raceID = race.getId();
                    sendBtn.setEnabled(true);
                } else {
                    Log.d("WOWC", "onCheckedChanged: raceList size: " + raceList.size());
                }
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
}
