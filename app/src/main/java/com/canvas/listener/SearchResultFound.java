package com.canvas.listener;

import com.canvas.model.Murals;

import io.realm.RealmResults;

/**
 * Created by akashyadav on 12/17/17.
 */

public interface SearchResultFound {
    public void onSearchResultFound(RealmResults<Murals> realmResults);
}
