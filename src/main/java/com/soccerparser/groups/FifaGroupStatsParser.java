package com.soccerparser.groups;

import com.soccerparser.Parser;
import com.soccerparser.database.GroupActionsDatabase;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.ArrayList;

public class FifaGroupStatsParser extends Parser {
    private ArrayList<Group> groups = new ArrayList<>(8);
    private final String[] groups_code = {"A", "B", "C", "D", "E", "F", "G", "H"};
    private String lang_code;

    public FifaGroupStatsParser(String url, String lang_code) {
        super(url);
        this.lang_code = lang_code;
    }

    @Override
    public void parse() {
        groups.clear();
        Elements groupsElements = document.getElementsByClass("tbl-standings"); // 8 groups here
        int counter = 0;
        for (Element element: groupsElements) {

            Group group = new Group();
            group.setGroupName(element.getElementsByTag("caption").text()); // group name

            Elements teamsElements = element.getElementsByTag("tr");
            int teamPos = 1;
            for (int i=0;i<teamsElements.size();i++) {
                if (i % 2 != 0) {
                    group.addNewTeam(parseTeam(teamsElements.get(i).getElementsByTag("td"), teamPos));
                    teamPos++;
                }
            }
            group.setGroup_code(groups_code[counter++]);
            groups.add(group);
        }
        System.out.println("THATS IT !");
        GroupActionsDatabase groupDB = new GroupActionsDatabase("postgres", "password",
                "jdbc:postgresql://127.0.0.1:5432/soccerdata", lang_code);
        groupDB.addRowsToDatabase(groups);
    }

    private Team parseTeam(Elements teamStat, int pos) {
        Team team = new Team();
        team.setTeamName(teamStat.get(0).text());
        team.setPosition(String.valueOf(pos));
        team.setTeamCode(teamStat.get(2).text());
        team.setGames(teamStat.get(3).text());
        team.setWins(teamStat.get(4).text());
        team.setDraws(teamStat.get(5).text());
        team.setLoses(teamStat.get(6).text());
        team.setGoals(teamStat.get(7).text() + "-" + teamStat.get(8).text());
        team.setScores(teamStat.get(10).text());

        return team;
    }
}
