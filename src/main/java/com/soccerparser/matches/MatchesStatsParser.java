package com.soccerparser.matches;

import com.soccerparser.Parser;
import com.soccerparser.database.Database;
import com.soccerparser.database.MatchActionsDatabase;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class MatchesStatsParser extends Parser{
    private ArrayList<Match> matches = new ArrayList<>();
    private final String[] cupStages = {"group_stage", "1at8final", "1at4final", "1at2final", "3place_final", "final"};
    private String lang_code;

    public MatchesStatsParser(String url, String lang_code) {
        super(url);
        this.lang_code = lang_code;
    }

    @Override
    public void parse() {
        Elements matchesAtStageElements = document.getElementsByClass("match-list-round");
        System.out.println(matchesAtStageElements.size() + " <====================== SIZE");

        for (int i=0;i<matchesAtStageElements.size();i++) { // Цикл по стадиям турнира
            Elements matchesAtDateElements = matchesAtStageElements.get(i).getElementsByClass("match-list-date");
            for (Element matchDate: matchesAtDateElements) { // Цикл по датам каждой стадии турнира
                String userFormatDate = matchDate.getElementsByClass("h3-wrap").text();
                String formatDate = matchDate.id();
                Elements matchesAtConcreteDate = matchDate.getElementsByClass("col-xs-12");
                for (Element matchDOM: matchesAtConcreteDate) { // Цикл по каждому матчу
                    Match match = new Match();
                    match.setFormatUserDate(userFormatDate);
                    match.setFormatDate(formatDate);
                    match.setGroup(matchDOM.getElementsByClass("mu-i-group").text());
                    match.setMatchNumber(matchDOM.getElementsByClass("mu-i-matchnum").text());
                    match.setStadium(matchDOM.getElementsByClass("mu-i-stadium").text());
                    match.setVenue(matchDOM.getElementsByClass("mu-i-venue").text());
                    match.setMatchStage(cupStages[i]);

                    Element home = matchDOM.getElementsByClass("home").get(0);
                    match.setHomeTeam(home.getElementsByClass("t-nText").text());
                    match.setHomeTeamCode(home.getElementsByClass("t-nTri").text());

                    Element visitor = matchDOM.getElementsByClass("away").get(0);
                    match.setVisitorTeam(visitor.getElementsByClass("t-nText").text());
                    match.setVisitorTeamCode(visitor.getElementsByClass("t-nTri").text());

                    match.setMatchStat(matchDOM.getElementsByClass("s-scoreText").text());

                    matches.add(match);
                }
            }
        }
        System.out.println("THATSSSSSSSS ======== IT !");
        MatchActionsDatabase matchDB = new MatchActionsDatabase(
                "postgres", "password",
                "jdbc:postgresql://127.0.0.1:5432/soccerdata", lang_code);
        matchDB.addRowsToDatabase(matches);
    }
}
