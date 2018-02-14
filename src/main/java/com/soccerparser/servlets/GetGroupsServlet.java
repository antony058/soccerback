package com.soccerparser.servlets;

import com.soccerparser.database.GroupActionsDatabase;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class GetGroupsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String lang_code = req.getParameter("lang_code");
        if (lang_code.isEmpty())
            return;

        GroupActionsDatabase queryToDB = new GroupActionsDatabase("postgres", "password",
                "jdbc:postgresql://127.0.0.1:5432/soccerdata", lang_code);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(queryToDB.getRowsFromDatabase());
    }
}
