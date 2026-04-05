package com.example;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Statement;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/create-table")
public class CreateTableServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try (
            Connection con = DBConnection.getConnection();
            Statement stmt = con.createStatement()
        ) {
            String sql = "CREATE TABLE IF NOT EXISTS students (" +
                         "id SERIAL PRIMARY KEY, " +
                         "name VARCHAR(100) NOT NULL, " +
                         "age INT NOT NULL)";

            stmt.executeUpdate(sql);

            out.println("""
                <!DOCTYPE html>
                <html>
                <head>
                    <meta charset="UTF-8">
                    <title>Create Table</title>
                    <link rel="stylesheet" href="style.css">
                </head>
                <body>
                    <div class="page">
                        <div class="card small-card">
                            <h1>Table Ready</h1>
                            <p>The <b>students</b> table was created successfully.</p>
                            <a class="btn" href="index.html">Back to Home</a>
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
                            <a class="btn" href="index.html">Back to Home</a>
                        </div>
                    </div>
                </body>
                </html>
            """);
        }
    }
}