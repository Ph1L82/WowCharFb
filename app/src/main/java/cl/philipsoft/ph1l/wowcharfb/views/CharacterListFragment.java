package cl.philipsoft.ph1l.wowcharfb.views;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cl.philipsoft.ph1l.wowcharfb.R;
import cl.philipsoft.ph1l.wowcharfb.adapters.CharacterClickListener;
import cl.philipsoft.ph1l.wowcharfb.adapters.CharactersAdapter;
import cl.philipsoft.ph1l.wowcharfb.models.Character;

/**
 * A placeholder fragment containing a simple view.
 */
public class CharacterListFragment extends Fragment implements CharacterClickListener {
    public static final String CHARACTER = "cl.philipsoft.ph1l.wowchar.views.CharacterListFragment.CHARACTER";
    private CharactersAdapter charactersAdapter;

    public CharacterListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.charactersRv);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(layoutManager);



//        charactersAdapter = new CharactersAdapter(this);
//        recyclerView.setAdapter(charactersAdapter);

        final SwipeRefreshLayout reloadSr = (SwipeRefreshLayout) view.findViewById(R.id.reloadSr);
        reloadSr.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        charactersAdapter.forceReload();
                        reloadSr.setRefreshing(false);
                    }
                }, 800);
            }
        });
    }

    public void addCharacter(Character character) {
        charactersAdapter.addCharacter(character);
    }

    @Override
    public void clickedId(Long id) {
        Intent intent = new Intent(getActivity(), CharacterDetailsActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    @Override
    public void clickedCharacter(Character character) {
        Intent intent = new Intent(getActivity(), CharacterDetailsActivity.class);
        intent.putExtra("Character", character);
        startActivity(intent);
    }

    @Override
    public void clickedIds(Long factionID, Long raceID, Long classID, Long characterID) {
        Intent intent = new Intent(getActivity(), CharacterDetailsActivity.class);
        intent.putExtra("factionID", factionID);
        intent.putExtra("raceID", raceID);
        intent.putExtra("classID", classID);
        intent.putExtra("characterID", characterID);
        startActivity(intent);
    }
}
