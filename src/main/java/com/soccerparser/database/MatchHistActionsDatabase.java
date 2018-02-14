package com.soccerparser.database;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.soccerparser.matches.Match;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MatchHistActionsDatabase extends Database implements MatchHistActionsDB {
    private ArrayList<Match> matches;
    private PreparedStatement statement;
    private String lang_code;

    public MatchHistActionsDatabase(String userName, String userPassword, String databaseURL, String lang_code) {
        super(userName, userPassword, databaseURL);
        this.lang_code = lang_code;
    }

    @Override
    public void addRowsToDatabase(ArrayList<Match> matches) {
        this.matches = matches;

        try {
            statement = connection.prepareStatement("INSERT INTO matches_hist_" + lang_code + " (match_reference," +
                    " format_date, group_name, match_num, stadium, venue, home_team, home_team_code, visitor_team," +
                    " visitor_team_code, match_stat) VALUES(?,?,?,?,?,?,?,?,?,?,?)");

            for (Match match: matches) {
                statement.setString(1, match.getReferenceToMatch());
                statement.setString(2, match.getFormatDate());
                statement.setString(3, match.getGroup());
                statement.setString(4, match.getMatchNumber());
                statement.setString(5, match.getStadium());
                statement.setString(6, match.getVenue());
                statement.setString(7, match.getHomeTeam());
                statement.setString(8, match.getHomeTeamCode());
                statement.setString(9, match.getVisitorTeam());
                statement.setString(10, match.getVisitorTeamCode());
                statement.setString(11, match.getMatchStat());

                statement.addBatch();
            }
            statement.executeBatch();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public String getRowsFromDatabase(String team_code) {
        String team = team_code.toUpperCase();
        String json = "";
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM matches_hist_" + lang_code +
            " WHERE home_team_code='" + team + "' OR visitor_team_code='" + team + "'");

            ArrayList<Match> matches = new ArrayList<>();
            while (rs.next()) {
                Match match = new Match();
                match.setReferenceToMatch(rs.getString("match_reference"));
                match.setFormatDate(rs.getString("format_date"));
                match.setGroup(rs.getString("group_name"));
                match.setMatchNumber(rs.getString("match_num"));
                match.setStadium(rs.getString("stadium"));
                match.setVenue(rs.getString("venue"));
                match.setHomeTeam(rs.getString("home_team"));
                match.setHomeTeamCode(rs.getString("home_team_code"));
                match.setVisitorTeam(rs.getString("visitor_team"));
                match.setVisitorTeamCode(rs.getString("visitor_team_code"));
                match.setMatchStat(rs.getString("match_stat"));

                matches.add(match);
            }
            Gson gson = new GsonBuilder().create();
            json = gson.toJson(matches);
            System.out.println(json);

            rs.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return json;
    }
}
