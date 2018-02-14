package com.soccerparser.servlets;

import com.soccerparser.LanguagesCodesURLs;
import com.soccerparser.database.Database;
import com.soccerparser.database.MatchActionsDatabase;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GetMatchesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String lang_code = req.getParameter("lang_code");
        if (lang_code.isEmpty())
            return;

        MatchActionsDatabase queryToDB = new MatchActionsDatabase("postgres", "password",
                "jdbc:postgresql://127.0.0.1:5432/soccerdata", lang_code);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(queryToDB.getRowsFromDatabase());
    }
}
