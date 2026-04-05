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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String name = request.getParameter("name");
        String ageText = request.getParameter("age");

        if (name == null || ageText == null || name.trim().isEmpty() || ageText.trim().isEmpty()) {
            out.println("""
                <!DOCTYPE html>
                <html>
                <head>
                    <meta charset="UTF-8">
                    <title>Validation Error</title>
                    <link rel="stylesheet" href="style.css">
                </head>
                <body>
                    <div class="page">
                        <div class="card small-card">
                            <h1>Missing Data</h1>
                            <p>Please enter both name and age.</p>
                            <a class="btn" href="index.html">Back</a>
                        </div>
                    </div>
                </body>
                </html>
            """);
            return;
        }

        try {
            int age = Integer.parseInt(ageText.trim());

            try (
                Connection con = DBConnection.getConnection();
                PreparedStatement pst = con.prepareStatement(
                    "INSERT INTO students(name, age) VALUES (?, ?)"
                )
            ) {
                pst.setString(1, name.trim());
                pst.setInt(2, age);
                pst.executeUpdate();
            }

            out.println("""
                <!DOCTYPE html>
                <html>
                <head>
                    <meta charset="UTF-8">
                    <title>Student Added</title>
                    <link rel="stylesheet" href="style.css">
                </head>
                <body>
                    <div class="page">
                        <div class="card small-card">
                            <h1>Student Added</h1>
                            <p><b>Name:</b> """ + name + """
                            </p>
                            <p><b>Age:</b> """ + age + """
                            </p>
                            <div class="actions center">
                                <a class="btn" href="index.html">Home</a>
                                <a class="btn secondary" href="view-students">View Students</a>
                            </div>
                        </div>
                    </div>
                </body>
                </html>
            """);

        } catch (NumberFormatException e) {
            out.println("""
                <!DOCTYPE html>
                <html>
                <head>
                    <meta charset="UTF-8">
                    <title>Invalid Age</title>
                    <link rel="stylesheet" href="style.css">
                </head>
                <body>
                    <div class="page">
                        <div class="card small-card">
                            <h1>Invalid Age</h1>
                            <p>Age must be a valid number.</p>
                            <a class="btn" href="index.html">Back</a>
                        </div>
                    </div>
                </body>
                </html>
            """);
        } catch (Exception e) {
            out.println("""
                <!DOCTYPE html>
                <html>
                <head>
                    <meta charset="UTF-8">
                    <title>Error</title>
                    <link rel="stylesheet" href="style.css">
                </head>
                <body>
                    <div class="page">
                        <div class="card small-card">
                            <h1>Error</h1>
                            <p>""" + e.getMessage() + """
                            </p>
                            <a class="btn" href="index.html">Back</a>
                        </div>
                    </div>
                </body>
                </html>
            """);
        }
    }
}