package com.example;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/hello")
public class HelloServlet extends HttpServlet {

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
                <title>Hello Servlet</title>
                <link rel='stylesheet' href='style.css'>
            </head>
            <body>
                <div class='page'>
                    <div class='card small-card'>
                        <h1>Hello from Servlet</h1>
                        <p>Your first servlet is working perfectly.</p>
                        <a class='btn' href='index.html'>Back to Home</a>
                    </div>
                </div>
            </body>
            </html>
        """);
    }
}