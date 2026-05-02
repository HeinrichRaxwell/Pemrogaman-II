<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
        <style>
            body { font-family: Arial, sans-serif; background-color: #f0f0f0; }
            .container { width: 300px; margin: 100px auto; background: white; padding: 30px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
            h2 { text-align: center; color: #333; }
            input[type=text], input[type=password] { width: 100%; padding: 8px; margin: 8px 0; box-sizing: border-box; border: 1px solid #ccc; border-radius: 4px; }
            input[type=submit] { width: 100%; padding: 10px; background-color: #4CAF50; color: white; border: none; border-radius: 4px; cursor: pointer; }
            input[type=submit]:hover { background-color: #45a049; }
        </style>
    </head>
    <body>
        <div class="container">
            <h2>Form Login</h2>
            <form method="POST" action="Validasi.jsp">
                <label>Username:</label>
                <input type="text" name="user" placeholder="Masukkan username"/>
                <label>Password:</label>
                <input type="password" name="password" placeholder="Masukkan password"/>
                <br/><br/>
                <input type="submit" value="Login"/>
            </form>
        </div>
    </body>
</html>