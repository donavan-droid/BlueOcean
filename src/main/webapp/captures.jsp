<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="model.Utilisateur, model.Capture, java.util.List" %>
<%
    Utilisateur user = (Utilisateur) session.getAttribute("user");
    if (user == null) { response.sendRedirect("login.jsp"); return; }
    List<Capture> captures = (List<Capture>) request.getAttribute("captures");
    if (captures == null) captures = new java.util.ArrayList<>();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Mes Captures</title>
    <style>
        * { margin:0; padding:0; box-sizing:border-box; }
        body { font-family:Arial,sans-serif; background:#f0f4f8; }
        nav {
            background:#0077b6; color:white; padding:16px 24px;
            display:flex; justify-content:space-between;
        }
        nav a { color:white; text-decoration:none; margin-left:16px; }
        .container { padding:30px; max-width:900px; margin:0 auto; }
        h2 { color:#0077b6; margin-bottom:24px; }
        .form-card {
            background:white; border-radius:12px; padding:24px;
            margin-bottom:28px; box-shadow:0 2px 8px rgba(0,0,0,0.08);
        }
        .form-card h3 { color:#0077b6; margin-bottom:16px; }
        .form-row { display:flex; gap:16px; }
        input, select {
            flex:1; padding:10px; border:1px solid #ddd;
            border-radius:8px; font-size:14px;
        }
        button {
            padding:10px 24px; background:#0077b6; color:white;
            border:none; border-radius:8px; cursor:pointer; font-size:14px;
        }
        button:hover { background:#005f99; }
        table {
            width:100%; background:white; border-radius:12px;
            border-collapse:collapse;
            box-shadow:0 2px 8px rgba(0,0,0,0.08);
        }
        th {
            background:#0077b6; color:white; padding:12px 16px;
            text-align:left;
        }
        td { padding:12px 16px; border-bottom:1px solid #eee; }
        tr:hover td { background:#f5f9ff; }
        .empty { text-align:center; color:#999; padding:30px; }
    </style>
</head>
<body>
<%
    String role = (String) session.getAttribute("role");
    boolean isAdmin = "admin".equals(role);
%>
<nav>
    <strong>🌊 BlueOcean</strong>
    <div>
        <% if (isAdmin) { %>
            <a href="dashboard_admin.jsp">🏠 Accueil</a>
        <% } else { %>
            <a href="dashboard_pecheur.jsp">🏠 Accueil</a>
            <a href="bateau">⛵ Bateau</a>
            <a href="gps">📍 GPS</a>
            <a href="meteo">🌦 Météo</a>
            <a href="signalement"> ⚠ Signaler </a>
        <% } %>
        <a href="logout">Déconnexion</a>
    </div>
</nav>
<div class="container">
    <h2>🐟 Mes Captures</h2>

    <div class="form-card">
        <h3>Enregistrer une capture</h3>
        <form method="post" action="captures">
            <div class="form-row">
                <input type="text" name="typePoisson"
                       placeholder="Espèce (ex: Thon, Capitaine...)" required/>
                <input type="number" name="poids" step="0.1" min="0.1"
                       placeholder="Poids (kg)" required/>
                <button type="submit">Enregistrer</button>
            </div>
        </form>
    </div>

    <table>
        <tr>
            <th>#</th><th>Espèce</th>
            <th>Poids (kg)</th><th>Date</th>
        </tr>
        <% if (captures.isEmpty()) { %>
        <tr><td colspan="4" class="empty">Aucune capture enregistrée</td></tr>
        <% } else {
            int i = 1;
            for (Capture c : captures) { %>
        <tr>
            <td><%= i++ %></td>
            <td><%= c.getTypePoisson() %></td>
            <td><%= c.getPoids() %> kg</td>
            <td><%= c.getDateCapture() %></td>
        </tr>
        <% }} %>
    </table>
</div>
</body>
</html>