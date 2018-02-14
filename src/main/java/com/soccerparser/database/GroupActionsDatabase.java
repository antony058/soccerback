package com.soccerparser.database;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.soccerparser.groups.Group;
import com.soccerparser.groups.Team;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class GroupActionsDatabase extends Database implements GroupActionsDB {
    private ArrayList<Group> groups;
    private PreparedStatement statement;
    private String lang_code;

    public GroupActionsDatabase(String userName, String userPassword, String databaseURL, String lang_code) {
        super(userName, userPassword, databaseURL);
        this.lang_code = lang_code;
    }

    @Override
    public void addRowsToDatabase(ArrayList<Group> groups) {
        this.groups = groups;

        try {
            statement = connection.prepareStatement("TRUNCATE groups_" + lang_code + " RESTART IDENTITY");
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            statement = connection.prepareStatement("INSERT INTO groups_" + lang_code + " (group_name," +
                    " group_code, team_name, team_position, games, wins, draws, loses, goals, scores)" +
                    " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

            for (Group group: groups) {
                for (Team team: group.getTeams()) {
                    statement.setString(1, group.getGroupName());
                    statement.setString(2, group.getGroup_code());
                    statement.setString(3, team.getTeamName());
                    statement.setString(4, team.getPosition());
                    statement.setString(5, team.getGames());
                    statement.setString(6, team.getWins());
                    statement.setString(7, team.getDraws());
                    statement.setString(8, team.getLoses());
                    statement.setString(9, team.getGoals());
                    statement.setString(10, team.getScores());

                    statement.addBatch();
                }
            }
            statement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getRowsFromDatabase() {
        String json = "";
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM groups_" + lang_code);

            ArrayList<Team> teams = new ArrayList<>();
            while (rs.next()) {
                Team team = new Team();
                team.setGroupName(rs.getString("group_name"));
                team.setGroupCode(rs.getString("group_code"));
                team.setTeamName(rs.getString("team_name"));
                team.setPosition(rs.getString("team_position"));
                team.setGames(rs.getString("games"));
                team.setWins(rs.getString("wins"));
                team.setDraws(rs.getString("draws"));
                team.setLoses(rs.getString("loses"));
                team.setGoals(rs.getString("goals"));
                team.setScores(rs.getString("scores"));

                teams.add(team);
            }
            Gson gson = new GsonBuilder().create();
            json = gson.toJson(teams);
            System.out.println(json);

            statement.close();
            rs.close();
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
