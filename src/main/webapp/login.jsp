<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>BlueOcean — Connexion</title>
    <style>
        * { margin:0; padding:0; box-sizing:border-box; }
        body {
            font-family: Arial, sans-serif;
            background: linear-gradient(135deg, #0077b6, #00b4d8);
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
        }
        .card {
            background: white;
            padding: 40px;
            border-radius: 16px;
            width: 380px;
            box-shadow: 0 8px 32px rgba(0,0,0,0.2);
        }
        h2 { color: #0077b6; text-align:center; margin-bottom:8px; }
        p.subtitle { text-align:center; color:#666; margin-bottom:24px; }
        label { display:block; margin-bottom:6px; color:#333; font-size:14px; }
        input {
            width:100%; padding:10px 14px; border:1px solid #ddd;
            border-radius:8px; margin-bottom:16px; font-size:14px;
        }
        input:focus { border-color:#0077b6; outline:none; }
        button {
            width:100%; padding:12px; background:#0077b6; color:white;
            border:none; border-radius:8px; font-size:16px; cursor:pointer;
        }
        button:hover { background:#005f99; }
        .erreur {
            background:#ffe0e0; color:#c00; padding:10px;
            border-radius:8px; margin-bottom:16px; font-size:14px;
        }
        .succes {
            background:#e0f7e9; color:#007a33; padding:10px;
            border-radius:8px; margin-bottom:16px; font-size:14px;
        }
        .lien { text-align:center; margin-top:16px; font-size:14px; }
        .lien a { color:#0077b6; }
    </style>
</head>
<body>
<div class="card">
    <h2>🌊 BlueOcean</h2>
    <p class="subtitle">Système de gestion de la pêche</p>

    <% if (request.getAttribute("erreur") != null) { %>
        <div class="erreur"><%= request.getAttribute("erreur") %></div>
    <% } %>
    <% if (request.getParameter("inscription") != null) { %>
        <div class="succes">Inscription réussie ! Connectez-vous.</div>
    <% } %>

    <form method="post" action="login">
        <label>Nom d'utilisateur</label>
        <input type="text" name="username" placeholder="Entrez votre username" required/>
        <label>Mot de passe</label>
        <input type="password" name="password" placeholder="••••••••" required/>
        <button type="submit">Se connecter</button>
    </form>
    <div class="lien">
        Pas encore inscrit ? <a href="inscription">S'inscrire</a>
    </div>
</div>
</body>
</html>