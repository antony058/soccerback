package com.soccerparser;

import com.soccerparser.matches.MatchesHistoryParser;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class MatchesHistoryParserListener implements ServletContextListener {
    private final static String[] continents = {
            "europe", "nccamerica", "southamerica", "asia", "africa"
    };
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (final String lang_code: LanguagesCodesURLs.urls) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            for (String continent: continents) {
                                Parser parser = new MatchesHistoryParser("http://" + lang_code +
                                        ".fifa.com/worldcup/preliminaries/" + continent + "/all-matches.html", lang_code);
                                parser.parse();
                            }
                        }
                    }).start();
                }
            }
        }).start();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
