package com.soccerparser.database;

import com.soccerparser.groups.Group;

import java.util.ArrayList;

public interface GroupActionsDB {
    void addRowsToDatabase(ArrayList<Group> groups);
    String getRowsFromDatabase();
}
