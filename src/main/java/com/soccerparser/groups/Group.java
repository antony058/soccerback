package com.soccerparser.groups;

import java.util.ArrayList;
import java.util.List;

public class Group {
    private String groupName;
    private ArrayList<Team> teams = new ArrayList<>(4);
    private String group_code;

    public String getGroup_code() {
        return group_code;
    }

    public void setGroup_code(String group_code) {
        this.group_code = group_code;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void addNewTeam(Team team) {
        teams.add(team);
    }

    public List<Team> getTeams() {
        return teams;
    }
}
