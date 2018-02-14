package com.soccerparser.matches;

import com.soccerparser.Parser;
import com.soccerparser.database.MatchHistActionsDatabase;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class MatchesHistoryParser extends Parser{
    private ArrayList<Match> matches = new ArrayList<>(300);
    private String lang_code;

    public MatchesHistoryParser(String url, String lang_code) {
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
                Elements matchesAtConcreteDate = matchDate.getElementsByClass("col-xs-12");
                for (Element matchDOM: matchesAtConcreteDate) { // Цикл по каждому матчу
                    Match match = new Match();

                    match.setReferenceToMatch(matchDOM.getElementsByClass("mu-m-link").attr("href"));
                    match.setFormatDate(matchDOM.getElementsByClass("mu-i-date").text());
                    match.setGroup(matchDOM.getElementsByClass("mu-i-group").text());
                    match.setMatchNumber(matchDOM.getElementsByClass("mu-i-matchnum").text());
                    match.setStadium(matchDOM.getElementsByClass("mu-i-stadium").text());
                    match.setVenue(matchDOM.getElementsByClass("mu-i-venue").text());

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
        System.out.println("END MATCHES HISTORY PARSE EU !");
        MatchHistActionsDatabase matchDB = new MatchHistActionsDatabase("postgres", "password",
                "jdbc:postgresql://127.0.0.1:5432/soccerdata", lang_code);
        matchDB.addRowsToDatabase(matches);
    }
}
