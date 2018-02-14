package com.soccerparser.database;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.soccerparser.matches.Match;

import java.sql.*;
import java.util.ArrayList;

public class MatchActionsDatabase extends Database implements MatchActionsDB {
    private ArrayList<Match> matches;
    private String lang_code;

    public MatchActionsDatabase(String userName, String userPassword, String databaseURL, String lang_code) {
        super(userName, userPassword, databaseURL);
        this.lang_code = lang_code;
    }

    @Override
    public void addRowsToDatabase(ArrayList<Match> matches) {
        PreparedStatement statement = null;
        this.matches = matches;

        try {
            statement = connection.prepareStatement("TRUNCATE matches_" + lang_code + " RESTART IDENTITY");
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            statement = connection.prepareStatement("INSERT INTO matches_" + lang_code + " (user_date," +
                    " format_date, group_name, match_num, stadium, venue, stage, home_team, home_team_code, visitor_team," +
                    " visitor_team_code, match_stat) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");

            for (Match match: matches) {
                statement.setString(1, match.getFormatUserDate());

                String formatDate = match.getFormatDate();
                formatDate = formatDate.substring(0,4) + "." + formatDate.substring(4,6) + "." + formatDate.substring(6);
                statement.setString(2, formatDate);
                statement.setString(3, match.getGroup());
                statement.setString(4, match.getMatchNumber());
                statement.setString(5, match.getStadium());
                statement.setString(6, match.getVenue());
                statement.setString(7, match.getMatchStage());
                statement.setString(8, match.getHomeTeam());
                statement.setString(9, match.getHomeTeamCode());
                statement.setString(10, match.getVisitorTeam());
                statement.setString(11, match.getVisitorTeamCode());
                statement.setString(12, match.getMatchStat());

                statement.addBatch();
            }
            statement.executeBatch();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null)
                    statement.close();
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public String getRowsFromDatabase() {
        String json = "";
        try {
            //DBPoolCache poolCache = DBPoolCache.getInstance("127.0.0.1:5432", "soccerdata", "postgres", "password");
            //Connection conn = poolCache.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM matches_" + lang_code);
            //poolCache.putConnection(conn);

            ArrayList<Match> matches = new ArrayList<>();

            while (rs.next()) {
                Match match = new Match();
                match.setFormatUserDate(rs.getString("user_date"));
                match.setFormatDate(rs.getString("format_date"));
                match.setGroup(rs.getString("group_name"));
                match.setMatchNumber(rs.getString("match_num"));
                match.setStadium(rs.getString("stadium"));
                match.setVenue(rs.getString("venue"));
                match.setMatchStage(rs.getString("stage"));
                match.setHomeTeam(rs.getString("home_team"));
                match.setHomeTeamCode(rs.getString("home_team_code"));
                match.setVisitorTeam(rs.getString("visitor_team"));
                match.setVisitorTeamCode(rs.getString("visitor_team_code"));
                match.setMatchStat(rs.getString("match_stat"));

                matches.add(match);
            }
            rs.close();
            statement.close();

            Gson gson = new GsonBuilder().create();
            json = gson.toJson(matches);
            System.out.println(json);
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
