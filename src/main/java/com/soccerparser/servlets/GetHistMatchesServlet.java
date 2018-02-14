package com.soccerparser.servlets;

import com.soccerparser.database.MatchHistActionsDatabase;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GetHistMatchesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String lang_code = req.getParameter("lang_code");
        if (lang_code.isEmpty())
            return;

        String team_code = req.getParameter("team_code");
        if (team_code.isEmpty())
            return;

        MatchHistActionsDatabase queryToDB = new MatchHistActionsDatabase("postgres", "password",
                "jdbc:postgresql://127.0.0.1:5432/soccerdata", lang_code);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(queryToDB.getRowsFromDatabase(team_code));
    }
}
