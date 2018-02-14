package com.soccerparser;

import com.soccerparser.groups.FifaGroupStatsParser;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class GroupsParserListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    //Parser parser = new GroupStatsParser("https://www.soccer0010.com/wcup/games");
                    for (String lang_code: LanguagesCodesURLs.urls) {
                        Parser parser = new FifaGroupStatsParser("http://" + lang_code +
                                ".fifa.com/worldcup/groups/index.html", lang_code);
                        parser.parse();
                    }

                    try {
                        Thread.sleep(10000);
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
