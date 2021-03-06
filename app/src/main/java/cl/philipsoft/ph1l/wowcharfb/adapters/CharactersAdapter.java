package cl.philipsoft.ph1l.wowcharfb.adapters;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.QuickContactBadge;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;

import cl.philipsoft.ph1l.wowcharfb.R;
import cl.philipsoft.ph1l.wowcharfb.data.Nodes;
import cl.philipsoft.ph1l.wowcharfb.models.Character;
import cl.philipsoft.ph1l.wowcharfb.models.CharacterClass;
import cl.philipsoft.ph1l.wowcharfb.models.Faction;
import cl.philipsoft.ph1l.wowcharfb.models.Race;

import static cl.philipsoft.ph1l.wowcharfb.R.drawable.bg_alliance;
import static cl.philipsoft.ph1l.wowcharfb.R.drawable.bg_horde;

/**
 * Created by phil_ on 04-03-2017.
 */


public class CharactersAdapter extends FirebaseRecyclerAdapter<Character, CharactersAdapter.CharacterHolder> {

    private CharacterClickListener characterClickListener;

    public CharactersAdapter(String uid, CharacterClickListener characterClickListener) {
        super(Character.class, R.layout.list_character, CharacterHolder.class, new Nodes().userCharacters(uid));
        this.characterClickListener = characterClickListener;
    }

    @Override
    public CharacterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_character, parent, false);
        CharacterHolder characterHolder = new CharacterHolder(view);
        return characterHolder;
    }

    @Override
    protected void populateViewHolder(CharacterHolder viewHolder, final Character model, int position) {

        if (model != null) {
            Log.d("WOWC", "onBindViewHolder: FACTION: " + model.getCharacterFaction().getId().toString());

            if (model.getCharacterFaction().getId() == 2) {
                if (model.isFavorite()) {
                    viewHolder.factionBadge.setImageResource(R.mipmap.ic_skull_horde_48dp);
                } else {
                    viewHolder.factionBadge.setImageResource(R.mipmap.ic_wow_flag_horde_24dp);
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    viewHolder.itemView.setBackground(ContextCompat.getDrawable(viewHolder.itemView.getContext(), bg_horde));
                    viewHolder.charRace.setTextColor(ContextCompat.getColor(viewHolder.itemView.getContext(), R.color.hordeFront));
                    viewHolder.charClass.setTextColor(ContextCompat.getColor(viewHolder.itemView.getContext(), R.color.hordeFront));
                    viewHolder.charName.setTextColor(ContextCompat.getColor(viewHolder.itemView.getContext(), R.color.hordeFront));
                } else {
                    viewHolder.itemView.setBackgroundResource(bg_alliance);
                }
            } else if (model.getCharacterFaction().getId() == 1) {
                if (model.isFavorite()) {
                    viewHolder.factionBadge.setImageResource(R.mipmap.ic_skull_alliance_48dp);
                } else {
                    viewHolder.factionBadge.setImageResource(R.mipmap.ic_wow_flag_alliance_24dp);
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    viewHolder.itemView.setBackground(ContextCompat.getDrawable(viewHolder.itemView.getContext(), bg_alliance));
                    viewHolder.charRace.setTextColor(ContextCompat.getColor(viewHolder.itemView.getContext(), R.color.allianceFront));
                    viewHolder.charClass.setTextColor(ContextCompat.getColor(viewHolder.itemView.getContext(), R.color.allianceFront));
                    viewHolder.charName.setTextColor(ContextCompat.getColor(viewHolder.itemView.getContext(), R.color.allianceFront));
                } else {
                    viewHolder.itemView.setBackgroundResource(bg_alliance);
                }
            }

            viewHolder.charRace.setText(model.getCharacterRace().getRaceName());
            viewHolder.charClass.setText(model.getCharacterClass().getClassName());
            viewHolder.charName.setText(model.getCharacterName());

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String id = model.getId();
                    Log.d("WOWC", "CHARACTERSADAPTER Sent CharID: " + id);
                    characterClickListener.viewClickedCharId(id);
                }
            });

            viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    new AlertDialog.Builder(v.getContext())
                            .setIcon(R.drawable.com_facebook_close)
                            .setTitle("Eliminar personaje")
                            .setMessage("Eliminarás el personaje de forma permanente")
                            .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String id = model.getId();
                                    Log.d("WOWC:FBREMOVE", "onClick: REMOVE CHARACTER ID:" + id);
                                    characterClickListener.removeClickedCharId(id);
                                }
                            })
                            .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Log.d("WOWC:FBREMOVE", "onClick: REMOVE CHARACTER CANCELED");
                                }
                            });
                    return true;
                }
            });
        }
    }

    @Override
    protected void onDataChanged() {
        super.onDataChanged();
    }


// ====>>>>   Internal CharacterClass CharacterHolder

    static class CharacterHolder extends RecyclerView.ViewHolder {
        private final QuickContactBadge factionBadge;
        private final TextView charRace, charClass, charName;
        View row;

        public CharacterHolder(View itemView) {
            super(itemView);
            factionBadge = (QuickContactBadge) itemView.findViewById(R.id.charFactionQcb);
            charRace = (TextView) itemView.findViewById(R.id.charRaceTv);
            charClass = (TextView) itemView.findViewById(R.id.charClassTv);
            charName = (TextView) itemView.findViewById(R.id.charNameTv);
            row = itemView.findViewById(R.id.characterLl);


        }
    }
}
