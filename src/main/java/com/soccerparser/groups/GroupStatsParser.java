package com.soccerparser.groups;

import com.soccerparser.Parser;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class GroupStatsParser extends Parser {
    private ArrayList<Group> groups = new ArrayList<>();

    public GroupStatsParser(String url) {
        super(url);
        System.out.println("GroupStatsParser constructor");
    }

    @Override
    public void parse() {
        groups.clear();
        Element tableElement = document.getElementById("fulltourtable");
        Elements theadElements = tableElement.getElementsByClass("thead");
        Elements oddElements = tableElement.getElementsByClass("odd");
        Elements evenElements = tableElement.getElementsByClass("even");

        for (int i=0, j=0;i<oddElements.size();i+=2, j++) {
            Group group = new Group();

            group.addNewTeam(createNewTeam(oddElements.get(i)));
            group.addNewTeam(createNewTeam(evenElements.get(i)));
            group.addNewTeam(createNewTeam(oddElements.get(i + 1)));
            group.addNewTeam(createNewTeam(evenElements.get(i + 1)));
            group.setGroupName(theadElements.get(j).getElementsByClass("team-name").text());

            groups.add(group);
        }
        System.out.println(groups.toString());
    }

    private Team createNewTeam(Element teamElement) {
        Team team = new Team();
        Elements elements = teamElement.getAllElements();
        team.setPosition(elements.get(2).text());
        team.setTeamName(elements.get(4).text());
        team.setGames(elements.get(6).text());
        team.setWins(elements.get(7).text());
        team.setDraws(elements.get(8).text());
        team.setLoses(elements.get(9).text());
        team.setGoals(elements.get(10).text());
        team.setScores(elements.get(11).text());

        return team;
    }
}
