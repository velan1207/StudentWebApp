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
                         "name VARCHAR(100), " +
                         "age INT)";

            stmt.executeUpdate(sql);

            out.println("<h2>students table created successfully</h2>");
        } catch (Exception e) {
            out.println("<h2>Error: " + e.getMessage() + "</h2>");
        }
    }
}