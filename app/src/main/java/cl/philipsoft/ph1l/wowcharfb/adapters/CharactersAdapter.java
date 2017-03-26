package cl.philipsoft.ph1l.wowcharfb.adapters;

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

import java.util.List;

import cl.philipsoft.ph1l.wowcharfb.R;
import cl.philipsoft.ph1l.wowcharfb.data.Queries;
import cl.philipsoft.ph1l.wowcharfb.models.Character;
import cl.philipsoft.ph1l.wowcharfb.models.Class;
import cl.philipsoft.ph1l.wowcharfb.models.Faction;
import cl.philipsoft.ph1l.wowcharfb.models.Race;

import static cl.philipsoft.ph1l.wowcharfb.R.drawable.bg_alliance;
import static cl.philipsoft.ph1l.wowcharfb.R.drawable.bg_horde;

/**
 * Created by phil_ on 04-03-2017.
 */

//public class CharactersAdapter extends RecyclerView.Adapter<CharactersAdapter.ViewHolder> {
public class CharactersAdapter extends FirebaseRecyclerAdapter<Character, CharactersAdapter.ViewHolder> {

    private List<Character> characters = new Queries().characters();
    private CharacterClickListener listener;

    public CharactersAdapter(CharacterClickListener listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_character, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        int textColor = R.color.textColorPrimary;
        final Character character = characters.get(position);
        Log.d("WOWC", "onBindViewHolder: Loaded Character id: " + character.getId().toString());
        Log.d("WOWC", "onBindViewHolder: Character Name: " + character.getCharacterName());
        Log.d("WOWC", "onBindViewHolder: Character Faction: " + character.getCharacterFaction().toString());
        final Faction characterFaction = character.getCharacterFaction();
        final Race characterRace = character.getCharacterRace();
        final Class characterClass = character.getCharacterClass();

        Log.d("WOWC", "onBindViewHolder: FACTION: " + characterFaction.getId().toString());

        // TODO: 05-03-2017 modificar modelos forzar id en la data preguardada. Evaluar facciones, razas y clases en base a ID especifico.
        if (characterFaction.getId() == 2) {
            holder.factionBadge.setImageResource(R.mipmap.ic_wow_flag_horde_24dp);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                holder.itemView.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), bg_horde));
                holder.charRace.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.hordeFront));
                holder.charClass.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.hordeFront));
                holder.charName.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.hordeFront));
            } else {
                holder.itemView.setBackgroundResource(bg_alliance);
            }
        } else if (characterFaction.getId() == 1) {
            holder.factionBadge.setImageResource(R.mipmap.ic_wow_flag_alliance_24dp);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                holder.itemView.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), bg_alliance));
                holder.charRace.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.allianceFront));
                holder.charClass.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.allianceFront));
                holder.charName.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.allianceFront));
            } else {
                holder.itemView.setBackgroundResource(bg_alliance);
            }
        }

        holder.charRace.setText(character.getCharacterRace().getRaceName());
        holder.charClass.setText(character.getCharacterClass().getClassName());
        holder.charName.setText(character.getCharacterName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Character aux = characters.get(holder.getAdapterPosition());
//                listener.clickedCharacter(aux);
                listener.clickedIds(characterFaction.getId(),characterRace.getId(),characterClass.getId(),character.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return characters.size();
    }

    public void addCharacter(Character character) {
        characters.add(character);
        notifyDataSetChanged();
    }

    public void forceReload() {
        characters = new Queries().characters();
        notifyDataSetChanged();
    }

// ====>>>>   Clase interna ViewHolder

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final QuickContactBadge factionBadge;
        private final TextView charRace, charClass, charName;
        View row;

        public ViewHolder(View itemView) {
            super(itemView);
            factionBadge = (QuickContactBadge) itemView.findViewById(R.id.charFactionQcb);
            charRace = (TextView) itemView.findViewById(R.id.charRaceTv);
            charClass = (TextView) itemView.findViewById(R.id.charClassTv);
            charName = (TextView) itemView.findViewById(R.id.charNameTv);
            row = itemView.findViewById(R.id.characterLl);
        }
    }
}
