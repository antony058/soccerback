package com.soccerparser;

import com.soccerparser.matches.MatchesStatsParser;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class MatchesParserListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    for (String lang_code : LanguagesCodesURLs.urls) {
                        Parser parser = new MatchesStatsParser("http://" + lang_code +
                                ".fifa.com/worldcup/matches/index.html", lang_code);
                        parser.parse();
                    }

                    try {
                        Thread.sleep(20000000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
