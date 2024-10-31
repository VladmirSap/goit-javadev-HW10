package org.example;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.DateTimeException;

@WebServlet("/time")
public class TimeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String timezoneParam = request.getParameter("timezone");
        ZoneId zoneId;

        if (timezoneParam == null || timezoneParam.isEmpty()) {
            zoneId = ZoneId.of("UTC");
        } else {
            try {
                zoneId = ZoneId.of(timezoneParam);
            } catch (DateTimeException e) {
                zoneId = ZoneId.of("UTC"); // Використання UTC, якщо таймзона некоректна
            }
        }

        Instant now = Instant.now();
        String formattedTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z")
                .withZone(zoneId)
                .format(now);

        try (PrintWriter out = response.getWriter()) {
            out.println("<html>");
            out.println("<head><title>Current Time</title></head>");
            out.println("<body>");
            out.println("<h1>Current Time</h1>");
            out.println("<p>" + formattedTime + "</p>");
            out.println("</body>");
            out.println("</html>");
        }
    }
}
