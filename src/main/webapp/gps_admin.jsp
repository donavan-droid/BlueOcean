<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="model.Utilisateur, model.Bateau, model.PositionGPS,
                 java.util.List, java.util.Map" %>
<%
    Utilisateur user = (Utilisateur) session.getAttribute("user");
    if (user == null || !"admin".equals(user.getRole())) {
        response.sendRedirect("login.jsp"); return;
    }
    List<Map<String, Object>> bateauxAvecPosition =
        (List<Map<String, Object>>) request.getAttribute("bateauxAvecPosition");
    if (bateauxAvecPosition == null)
        bateauxAvecPosition = new java.util.ArrayList<>();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>GPS Admin — Tous les bateaux</title>
    <link rel="stylesheet"
          href="https://unpkg.com/leaflet@1.9.4/dist/leaflet.css"/>
    <style>
        * { margin:0; padding:0; box-sizing:border-box; }
        body { font-family:Arial,sans-serif; background:#f0f4f8; }
        nav {
            background:#003f5c; color:white; padding:16px 24px;
            display:flex; justify-content:space-between; align-items:center;
        }
        nav a { color:white; text-decoration:none; margin-left:16px; }
        .container { padding:24px; max-width:1200px; margin:0 auto; }
        h2 { color:#003f5c; margin-bottom:16px; }
        #map {
            height:500px; border-radius:12px;
            box-shadow:0 2px 8px rgba(0,0,0,0.15);
            margin-bottom:24px;
        }
        table {
            width:100%; background:white; border-radius:12px;
            border-collapse:collapse;
            box-shadow:0 2px 8px rgba(0,0,0,0.08);
        }
        th {
            background:#003f5c; color:white;
            padding:12px 16px; text-align:left;
        }
        td { padding:12px 16px; border-bottom:1px solid #eee; font-size:14px; }
        tr:hover td { background:#f5f9ff; cursor:pointer; }
        .badge-pos {
            padding:4px 10px; border-radius:20px;
            font-size:12px; font-weight:bold;
        }
        .en-mer { background:#00b4d8; color:white; }
        .inconnu { background:#aaa; color:white; }
    </style>
</head>
<body>
<nav>
    <strong>🌊 BlueOcean — Administration</strong>
    <div>
        <a href="dashboard_admin.jsp">🏠 Accueil</a>
        <a href="meteo">🌦 Météo</a>
        <a href="signalement">⚠ Signalement</a>
        <a href="logout">Déconnexion</a>
    </div>
</nav>

<div class="container">
    <h2>📍 Positions GPS de tous les bateaux</h2>

    <div id="map"></div>

    <!-- Tableau des bateaux -->
    <h3 style="color:#003f5c; margin-bottom:12px;">
        Liste des bateaux (<%= bateauxAvecPosition.size() %>)
    </h3>
    <table>
        <tr>
            <th>ID Bateau</th>
            <th>Type</th>
            <th>Dernière position</th>
            <th>Latitude</th>
            <th>Longitude</th>
            <th>Date</th>
            <th>Statut</th>
        </tr>
        <% for (Map<String, Object> entry : bateauxAvecPosition) {
            Bateau b = (Bateau) entry.get("bateau");
            PositionGPS pos = (PositionGPS) entry.get("position");
        %>
        <tr onclick="zoomBateau(<%= b.getIdBateau() %>)"
            title="Cliquez pour zoomer sur la carte">
            <td>#<%= b.getIdBateau() %></td>
            <td>⛵ <%= b.getTypeBateau() %></td>
            <td>
                <% if (pos != null) { %>
                    <%= pos.getLatitude() %>, <%= pos.getLongitude() %>
                <% } else { %>
                    —
                <% } %>
            </td>
            <td><%= pos != null ? pos.getLatitude() : "—" %></td>
            <td><%= pos != null ? pos.getLongitude() : "—" %></td>
            <td><%= pos != null ? pos.getDatePosition() : "—" %></td>
            <td>
                <% if (pos != null) { %>
                    <span class="badge-pos en-mer">En mer</span>
                <% } else { %>
                    <span class="badge-pos inconnu">Inconnu</span>
                <% } %>
            </td>
        </tr>
        <% } %>
    </table>
</div>

<script src="https://unpkg.com/leaflet@1.9.4/dist/leaflet.js"></script>
<script>
    var map = L.map('map').setView([-18.9137, 47.5361], 6);
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: '© OpenStreetMap'
    }).addTo(map);

    var markers = {};

    // Placer les marqueurs pour chaque bateau
    <% for (Map<String, Object> entry : bateauxAvecPosition) {
        Bateau b = (Bateau) entry.get("bateau");
        PositionGPS pos = (PositionGPS) entry.get("position");
        if (pos != null) { %>
    var marker_<%= b.getIdBateau() %> = L.marker(
        [<%= pos.getLatitude() %>, <%= pos.getLongitude() %>]
    ).addTo(map).bindPopup(
        "<b>⛵ Bateau #<%= b.getIdBateau() %></b><br>" +
        "Type : <%= b.getTypeBateau() %><br>" +
        "Lat : <%= pos.getLatitude() %><br>" +
        "Lng : <%= pos.getLongitude() %><br>" +
        "Date : <%= pos.getDatePosition() %>"
    );
    markers[<%= b.getIdBateau() %>] = marker_<%= b.getIdBateau() %>;
    <% } } %>

    // Zoom sur un bateau depuis le tableau
    function zoomBateau(idBateau) {
        if (markers[idBateau]) {
            markers[idBateau].openPopup();
            map.setView(markers[idBateau].getLatLng(), 12);
        }
    }

    // Rafraîchissement automatique toutes les 30 secondes
    setTimeout(function() { location.reload(); }, 30000);
</script>
</body>
</html>