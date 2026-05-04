package com.example;

import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/AutoFillServlet")
public class AutoFillServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String name = "", email = "", city = "";

        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals("name")) name = c.getValue();
                if (c.getName().equals("email")) email = c.getValue();
                if (c.getName().equals("city")) city = c.getValue();
            }
        }

        out.println("<h2>Registration Form</h2>");
        out.println("<form action='AutoFillServlet' method='post'>");

        out.println("Name: <input type='text' name='name' value='" + name + "'><br><br>");
        out.println("Email: <input type='text' name='email' value='" + email + "'><br><br>");
        out.println("City: <input type='text' name='city' value='" + city + "'><br><br>");

        out.println("<input type='submit' value='Submit'>");
        out.println("</form>");

        if (!name.equals("")) {
            out.println("<p>Form auto-filled from your previous visit</p>");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String city = request.getParameter("city");

        Cookie c1 = new Cookie("name", name);
        Cookie c2 = new Cookie("email", email);
        Cookie c3 = new Cookie("city", city);

        c1.setMaxAge(7 * 24 * 60 * 60);
        c2.setMaxAge(7 * 24 * 60 * 60);
        c3.setMaxAge(7 * 24 * 60 * 60);

        response.addCookie(c1);
        response.addCookie(c2);
        response.addCookie(c3);

        response.sendRedirect("AutoFillServlet");
    }
}