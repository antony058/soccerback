package com.soccerparser.database;

import com.soccerparser.matches.Match;

import java.util.ArrayList;

public interface MatchHistActionsDB {
    void addRowsToDatabase(ArrayList<Match> matches);
    String getRowsFromDatabase(String team_code);
}
