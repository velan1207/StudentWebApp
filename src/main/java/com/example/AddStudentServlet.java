package com.example;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/add-student")
public class AddStudentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String name = request.getParameter("name");
        String ageText = request.getParameter("age");

        if (name == null || ageText == null || name.isEmpty() || ageText.isEmpty()) {
            out.println("<h2>Please provide name and age in URL</h2>");
            out.println("<p>Example: /add-student?name=Velan&age=19</p>");
            return;
        }

        try {
            int age = Integer.parseInt(ageText);

            try (
                Connection con = DBConnection.getConnection();
                PreparedStatement pst = con.prepareStatement(
                    "INSERT INTO students(name, age) VALUES(?, ?)"
                )
            ) {
                pst.setString(1, name);
                pst.setInt(2, age);
                pst.executeUpdate();
            }

            out.println("<h2>Student added successfully</h2>");
            out.println("<p>Name: " + name + "</p>");
            out.println("<p>Age: " + age + "</p>");

        } catch (Exception e) {
            out.println("<h2>Error: " + e.getMessage() + "</h2>");
        }
    }
}