<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="jakarta.servlet.http.Cookie"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Validasi Login</title>
        <style>
            body { font-family: Arial, sans-serif; background-color: #f0f0f0; }
            .container { width: 400px; margin: 100px auto; background: white; padding: 30px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
            h2 { text-align: center; }
            .success { color: green; text-align: center; }
            .error { color: red; text-align: center; }
            a { display: block; text-align: center; margin-top: 15px; }
        </style>
    </head>
    <body>
        <div class="container">
            <%
                String username = request.getParameter("user");
                String password = request.getParameter("password");

                if (username != null && password != null &&
                    username.equals("ADMIN") && password.equals("ADMIN")) {

                    // Set Session
                    session.setAttribute("userLogin", username);
                    session.setMaxInactiveInterval(60 * 60 * 24); // 1 hari

                    // Set Cookie
                    Cookie nmId = new Cookie("user", username);
                    nmId.setMaxAge(60 * 60 * 24); // 1 hari
                    response.addCookie(nmId);
            %>
                    <h2 class="success">Login Berhasil!</h2>
                    <p style="text-align:center">Selamat datang, <b><%= username %></b></p>
                    <p style="text-align:center">Session aktif: <%= session.getAttribute("userLogin") %></p>
            <%
                } else {
            %>
                    <h2 class="error">Login Gagal!</h2>
                    <p style="text-align:center">Username atau password salah.</p>
            <%
                }
            %>
            <a href="index.jsp">&larr; Kembali ke Login</a>
        </div>
    </body>
</html>