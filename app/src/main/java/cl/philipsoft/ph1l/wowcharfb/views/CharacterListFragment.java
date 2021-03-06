package cl.philipsoft.ph1l.wowcharfb.views;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import cl.philipsoft.ph1l.wowcharfb.R;
import cl.philipsoft.ph1l.wowcharfb.adapters.CharacterClickListener;
import cl.philipsoft.ph1l.wowcharfb.adapters.CharactersAdapter;
import cl.philipsoft.ph1l.wowcharfb.data.CurrentUser;
import cl.philipsoft.ph1l.wowcharfb.data.Nodes;

/**
 * A placeholder fragment containing a simple view.
 */
public class CharacterListFragment extends Fragment implements CharacterClickListener {

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

        CharactersAdapter charactersAdapter = new CharactersAdapter(new CurrentUser().userID(), this);
        recyclerView.setAdapter(charactersAdapter);

        final SwipeRefreshLayout reloadSr = (SwipeRefreshLayout) view.findViewById(R.id.reloadSr);
        reloadSr.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(), "I really should find something to do with this now...or not?", Toast.LENGTH_SHORT).show();
                        reloadSr.setRefreshing(false);
                    }
                }, 800);
            }
        });
    }

    @Override
    public void viewClickedCharId(String id) {
        Log.d("WOWC", "CharacterListFragment viewClickedCharId: characterID: " + id);
        Intent intent = new Intent(getActivity(), CharacterDetailsActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    @Override
    public void removeClickedCharId(String id) {
        new Nodes().userCharacters(new CurrentUser().userID()).child(id).removeValue();
    }
}
