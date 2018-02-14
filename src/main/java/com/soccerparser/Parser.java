package com.soccerparser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public abstract class Parser {
    protected Document document;
    private String urlSiteParse;

    public Parser(String url) {
        urlSiteParse = url;
        System.out.println("Parser constructor");
        loadHtmlDocument();
    }

    private void loadHtmlDocument() {
        try {
            System.out.println(urlSiteParse);
            document = Jsoup.connect(urlSiteParse).get();
            System.out.println("Document loaded");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Document is not loaded");
        }
    }

    abstract public void parse();
}
