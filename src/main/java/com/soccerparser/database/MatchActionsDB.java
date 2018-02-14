package com.soccerparser.database;

import com.soccerparser.matches.Match;
import java.util.ArrayList;

public interface MatchActionsDB {
    void addRowsToDatabase(ArrayList<Match> matches);
    String getRowsFromDatabase();
}
