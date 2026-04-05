package com.example;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/view-students")
public class ViewStudentsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("""
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset='UTF-8'>
                <title>Student List</title>
                <link rel='stylesheet' href='style.css'>
            </head>
            <body>
                <div class='page'>
                    <div class='card wide-card'>
                        <div class='header-row'>
                            <div>
                                <h1>Student Records</h1>
                                <p class='subtitle'>Modern servlet-based student management view</p>
                            </div>
                            <div class='actions'>
                                <a class='btn' href='index.html'>Home</a>
                            </div>
                        </div>
        """);

        try (
            Connection con = DBConnection.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM students ORDER BY id")
        ) {
            out.println("<div class='table-wrap'>");
            out.println("<table class='modern-table'>");
            out.println("<thead><tr><th>ID</th><th>Name</th><th>Age</th></tr></thead>");
            out.println("<tbody>");

            boolean hasData = false;
            while (rs.next()) {
                hasData = true;
                out.println("<tr>");
                out.println("<td>" + rs.getInt("id") + "</td>");
                out.println("<td>" + rs.getString("name") + "</td>");
                out.println("<td>" + rs.getInt("age") + "</td>");
                out.println("</tr>");
            }

            if (!hasData) {
                out.println("<tr><td colspan='3' class='empty-cell'>No students found</td></tr>");
            }

            out.println("</tbody></table></div>");
        } catch (Exception e) {
            out.println("<p class='error-text'>Error: " + e.getMessage() + "</p>");
        }

        out.println("""
                    </div>
                </div>
            </body>
            </html>
        """);
    }
}