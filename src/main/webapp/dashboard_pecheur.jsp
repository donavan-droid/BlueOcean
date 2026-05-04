<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="model.Utilisateur" %>
<%
    Utilisateur user = (Utilisateur) session.getAttribute("user");
    if (user == null) { response.sendRedirect("login.jsp"); return; }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Dashboard Pêcheur</title>
    <style>
        * { margin:0; padding:0; box-sizing:border-box; }
        body { font-family:Arial,sans-serif; background:#f0f4f8; }
        nav {
            background:#0077b6; color:white; padding:16px 24px;
            display:flex; justify-content:space-between; align-items:center;
        }
        nav h1 { font-size:20px; }
        nav a { color:white; text-decoration:none; margin-left:16px; font-size:14px; }
        .container { padding:30px; max-width:1100px; margin:0 auto; }
        h2 { color:#0077b6; margin-bottom:24px; }
        .cards { display:grid; grid-template-columns:repeat(3,1fr); gap:20px; }
        .card {
            background:white; border-radius:12px; padding:24px;
            text-align:center; box-shadow:0 2px 8px rgba(0,0,0,0.08);
            text-decoration:none; color:inherit; transition:transform .2s;
        }
        .card:hover { transform:translateY(-4px); }
        .card .icon { font-size:40px; margin-bottom:12px; }
        .card h3 { color:#0077b6; margin-bottom:6px; }
        .card p { color:#666; font-size:14px; }
    </style>
</head>
<body>
<nav>
    <h1>🌊 BlueOcean</h1>
    <div>
        <span>Bonjour, <%= user.getUsername() %></span>
        <a href="captures">🐟 Mes captures</a>
        <a href="gps">📍 GPS</a>
        <a href="meteo">🌦 Météo</a>
        <a href="signalement">⚠ Signaler</a>
        <a href="logout">Déconnexion</a>
    </div>
</nav>
<div class="container">
    <h2>Tableau de bord</h2>
    <div class="cards">
        <a href="captures" class="card">
            <div class="icon">🐟</div>
            <h3>Mes Captures</h3>
            <p>Enregistrer et consulter vos captures</p>
        </a>
        <a href="gps" class="card">
            <div class="icon">📍</div>
            <h3>Suivi GPS</h3>
            <p>Localiser votre bateau en temps réel</p>
        </a>
        <a href="meteo" class="card">
            <div class="icon">🌦</div>
            <h3>Météo</h3>
            <p>Consulter les prévisions et alertes</p>
        </a>
        <a href="signalement" class="card">
            <div class="icon">⚠️</div>
            <h3>Signalement</h3>
            <p>Signaler un problème en mer</p>
        </a>
        <a href="bateau" class="card">
            <div class="icon">⛵</div>
            <h3>Mes Bateaux</h3>
            <p>Gérer votre flotte</p>
        </a>
    </div>
</div>
</body>
</html>