<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="model.Utilisateur, model.Alerte, java.util.List" %>
<%
    Utilisateur user = (Utilisateur) session.getAttribute("user");
    if (user == null) { response.sendRedirect("login.jsp"); return; }
    String jsonMeteo = (String) request.getAttribute("jsonMeteo");
    List<Alerte> alertes =
        (List<Alerte>) request.getAttribute("alertes");
    if (alertes == null) alertes = new java.util.ArrayList<>();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Météo</title>
    <style>
        * { margin:0; padding:0; box-sizing:border-box; }
        body { font-family:Arial,sans-serif; background:#f0f4f8; }
        nav {
            background:#0077b6; color:white; padding:16px 24px;
            display:flex; justify-content:space-between;
        }
        nav a { color:white; text-decoration:none; margin-left:16px; }
        .container { padding:30px; max-width:900px; margin:0 auto; }
        h2 { color:#0077b6; margin-bottom:20px; }
        .meteo-card {
            background:linear-gradient(135deg,#0077b6,#00b4d8);
            color:white; border-radius:16px; padding:30px;
            margin-bottom:24px; box-shadow:0 4px 16px rgba(0,119,182,0.3);
        }
        .meteo-card h3 { font-size:22px; margin-bottom:8px; }
        .meteo-card .temp { font-size:60px; font-weight:bold; }
        .meteo-card .desc { font-size:18px; opacity:.9; margin-top:4px; }
        .meteo-card .details {
            display:flex; gap:24px; margin-top:16px; font-size:14px;
        }
        table {
            width:100%; background:white; border-radius:12px;
            border-collapse:collapse;
            box-shadow:0 2px 8px rgba(0,0,0,0.08);
        }
        th {
            background:#e63946; color:white;
            padding:12px 16px; text-align:left;
        }
        td { padding:12px 16px; border-bottom:1px solid #eee; }
        .badge {
            padding:4px 10px; border-radius:20px;
            font-size:12px; font-weight:bold;
        }
        .ELEVE { background:#e63946; color:white; }
        .MODERE { background:#f4a261; color:white; }
        .FAIBLE { background:#2a9d8f; color:white; }
        .empty { text-align:center; color:#999; padding:20px; }
    </style>
</head>
<body>
<%
    String role = (String) request.getAttribute("role");
    if (role == null) role = (String) session.getAttribute("role");
    boolean isAdmin = "admin".equals(role);
%>
<nav>
    <strong>🌊 BlueOcean</strong>
    <div>
        <% if (isAdmin) { %>
            <a href="dashboard_admin.jsp">🏠 Accueil </a>
            <a href="bateau">⛵ Bateau</a>
            <a href="gps">📍 Carte GPS </a>
            <a href="signalement"> ⚠ Signalement </a>
        <% } else { %>
            <a href="dashboard_pecheur.jsp">🏠 Accueil</a>
            <a href="captures">🐟 Captures</a>
            <a href="gps">📍 GPS</a>
            <a href="signalement"> ⚠ Signaler </a>
        <% } %>
        <a href="logout">Déconnexion</a>
    </div>
</nav>
<div class="container">
    <h2>🌦 Météo & Alertes</h2>

    <div class="meteo-card" id="meteo-widget">
        <h3>Chargement des données météo...</h3>
    </div>

    <h3 style="color:#e63946;margin-bottom:12px;">⚠️ Alertes actives</h3>
    <table>
        <tr>
            <th>Type</th><th>Niveau</th>
            <th>Message</th><th>Date</th>
        </tr>
        <% if (alertes.isEmpty()) { %>
        <tr><td colspan="4" class="empty">Aucune alerte active</td></tr>
        <% } else { for (Alerte a : alertes) { %>
        <tr>
            <td><%= a.getType() %></td>
            <td>
                <span class="badge <%= a.getNiveau()
                    .replace("É","E").replace("é","e") %>">
                    <%= a.getNiveau() %>
                </span>
            </td>
            <td><%= a.getMessage() %></td>
            <td><%= a.getDateAlerte() %></td>
        </tr>
        <% }} %>
    </table>
</div>

<script>
// Parse et affiche les données météo JSON
var json = <%= jsonMeteo != null ? jsonMeteo : "{}" %>;
var w = document.getElementById('meteo-widget');
if (json && json.main) {
    w.innerHTML =
        '<h3>📍 ' + json.name + ', Madagascar</h3>' +
        '<div class="temp">' +
            Math.round(json.main.temp) + '°C' +
        '</div>' +
        '<div class="desc">' +
            json.weather[0].description + '</div>' +
        '<div class="details">' +
            '<span>💧 Humidité: ' + json.main.humidity + '%</span>' +
            '<span>🌬 Vent: ' +
                Math.round(json.wind.speed * 3.6) + ' km/h</span>' +
            '<span>🌡 Ressenti: ' +
                Math.round(json.main.feels_like) + '°C</span>' +
        '</div>';
} else {
    w.innerHTML =
        '<h3>⚠️ Données météo indisponibles</h3>' +
        '<p>Vérifiez votre clé API OpenWeatherMap</p>';
}
</script>
</body>
</html>