<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="model.Utilisateur, model.Bateau, java.util.List" %>
<%
    Utilisateur user = (Utilisateur) session.getAttribute("user");
    if (user == null) { response.sendRedirect("login.jsp"); return; }
    List<Bateau> bateaux = (List<Bateau>) request.getAttribute("bateaux");
    if (bateaux == null) bateaux = new java.util.ArrayList<>();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Suivi GPS</title>
    <link rel="stylesheet"
          href="https://unpkg.com/leaflet@1.9.4/dist/leaflet.css"/>
    <style>
        * { margin:0; padding:0; box-sizing:border-box; }
        body { font-family:Arial,sans-serif; background:#f0f4f8; }
        nav {
            background:#0077b6; color:white; padding:16px 24px;
            display:flex; justify-content:space-between;
        }
        nav a { color:white; text-decoration:none; margin-left:16px; }
        .container { padding:20px; max-width:1100px; margin:0 auto; }
        h2 { color:#0077b6; margin-bottom:16px; }
        #map { height:450px; border-radius:12px;
               box-shadow:0 2px 8px rgba(0,0,0,0.15); }
        .form-card {
            background:white; border-radius:12px; padding:20px;
            margin-top:20px; box-shadow:0 2px 8px rgba(0,0,0,0.08);
        }
        .form-row { display:flex; gap:12px; align-items:flex-end; }
        select, input {
            flex:1; padding:10px; border:1px solid #ddd;
            border-radius:8px; font-size:14px;
        }
        button {
            padding:10px 20px; background:#0077b6; color:white;
            border:none; border-radius:8px; cursor:pointer;
        }
        #msg { margin-top:10px; color:green; font-size:14px; }
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
            <a href="captures">🐟 Captures</a>
            <a href="meteo">🌦 Météo</a>
            <a href="signalement"> ⚠ Signaler</a>
        <% } %>
        <a href="logout">Déconnexion</a>
    </div>
</nav>
<div class="container">
    <h2>📍 Suivi GPS des bateaux</h2>
    <div id="map"></div>

    <div class="form-card">
    <h3 style="color:#0077b6;margin-bottom:14px;">
        Envoyer ma position
    </h3>

    <% if (bateaux == null || bateaux.isEmpty()) { %>
        <div style="background:#fff3cd; padding:12px; border-radius:8px;
                    color:#856404; margin-bottom:12px;">
            ⚠️ Vous n'avez pas encore de bateau enregistré.
            <a href="bateau" style="color:#0077b6;">
                Ajouter un bateau ici
            </a>
        </div>
    <% } else { %>
    <div class="form-row">
        <select id="idBateau">
            <% for (Bateau b : bateaux) { %>
            <option value="<%= b.getIdBateau() %>">
                Bateau #<%= b.getIdBateau() %>
                — <%= b.getTypeBateau() %>
            </option>
            <% } %>
        </select>
        <input type="number" id="lat" step="0.0001"
               placeholder="Latitude (ex: -18.9137)"/>
        <input type="number" id="lng" step="0.0001"
               placeholder="Longitude (ex: 47.5361)"/>
        <button onclick="envoyerPosition()">📡 Envoyer</button>
    </div>
    <% } %>
    <div id="msg"></div>
</div>
</div>

<script src="https://unpkg.com/leaflet@1.9.4/dist/leaflet.js"></script>
<script>
    // Carte centrée sur Madagascar
    var map = L.map('map').setView([-18.9137, 47.5361], 6);
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: '© OpenStreetMap'
    }).addTo(map);

    var marker = null;

    // Géolocalisation automatique
	if (navigator.geolocation) {
	    navigator.geolocation.getCurrentPosition(
	        function(pos) {
	            var lat = pos.coords.latitude;
	            var lng = pos.coords.longitude;
	            document.getElementById('lat').value = lat.toFixed(6);
	            document.getElementById('lng').value = lng.toFixed(6);
	            if (marker) {
	                marker.setLatLng([lat, lng]);
	            } else {
	                marker = L.marker([lat, lng])
	                    .addTo(map)
	                    .bindPopup("📍 Votre position actuelle")
	                    .openPopup();
	            }
	            map.setView([lat, lng], 13);
	        },
	        function(err) {
	            // Si géolocalisation refusée, permettre saisie manuelle
	            document.getElementById('msg').innerHTML =
	                '<span style="color:orange;">⚠️ Géolocalisation non disponible. ' +
	                'Entrez manuellement vos coordonnées.</span>';
	        },
	        { enableHighAccuracy: true, timeout: 10000 }
	    );
	} else {
	    document.getElementById('msg').innerHTML =
	        '<span style="color:red;">❌ Géolocalisation non supportée.</span>';
	}

    function envoyerPosition() {
        var idBateau = document.getElementById('idBateau').value;
        var lat = document.getElementById('lat').value;
        var lng = document.getElementById('lng').value;
        if (!lat || !lng) {
            alert("Position non disponible.");
            return;
        }
        fetch('gps', {
            method: 'POST',
            headers: {'Content-Type':'application/x-www-form-urlencoded'},
            body: 'idBateau='+idBateau+'&latitude='+lat+'&longitude='+lng
        }).then(r => r.json()).then(data => {
            if (data.status === 'ok') {
                document.getElementById('msg').textContent =
                    '✅ Position enregistrée avec succès !';
            }
        });
    }
</script>
</body>
</html>