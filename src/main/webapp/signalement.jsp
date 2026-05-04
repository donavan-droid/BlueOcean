<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="model.Utilisateur, model.Signalement, java.util.List" %>
<%
    Utilisateur user = (Utilisateur) session.getAttribute("user");
    if (user == null) { response.sendRedirect("login.jsp"); return; }
    List<Signalement> signalements =
        (List<Signalement>) request.getAttribute("signalements");
    if (signalements == null) signalements = new java.util.ArrayList<>();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Signalement</title>
    <style>
        * { margin:0; padding:0; box-sizing:border-box; }
        body { font-family:Arial,sans-serif; background:#f0f4f8; }
        nav {
            background:#0077b6; color:white; padding:16px 24px;
            display:flex; justify-content:space-between;
        }
        nav a { color:white; text-decoration:none; margin-left:16px; }
        .container { padding:30px; max-width:900px; margin:0 auto; }
        h2 { color:#e63946; margin-bottom:24px; }
        .form-card {
            background:white; border-radius:12px; padding:24px;
            margin-bottom:28px; box-shadow:0 2px 8px rgba(0,0,0,0.08);
        }
        .form-card h3 { color:#e63946; margin-bottom:16px; }
        label { display:block; margin-bottom:6px; color:#333; font-size:14px; }
        input, select, textarea {
            width:100%; padding:10px; border:1px solid #ddd;
            border-radius:8px; margin-bottom:14px; font-size:14px;
        }
        textarea { height:90px; resize:vertical; }
        button {
            padding:10px 24px; background:#e63946; color:white;
            border:none; border-radius:8px; cursor:pointer; font-size:14px;
        }
        button:hover { background:#c1121f; }
        .succes {
            background:#e0f7e9; color:#007a33; padding:12px;
            border-radius:8px; margin-bottom:16px;
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
        td { padding:12px 16px; border-bottom:1px solid #eee; font-size:14px; }
        .empty { text-align:center; color:#999; padding:24px; }
        .badge {
            padding:4px 10px; border-radius:20px;
            font-size:12px; font-weight:bold; color:white;
        }
        .peche { background:#e63946; }
        .meteo { background:#f4a261; }
        .securite { background:#2a9d8f; }
        .autre { background:#888; }
    </style>
</head>
<body>
<nav>
    <strong>🌊 BlueOcean</strong>
    <div>
        <a href="dashboard_pecheur.jsp">🏠 Accueil</a>
        <a href="captures">🐟 Captures</a>
        <a href="gps">📍 GPS</a>
        <a href="logout">Déconnexion</a>
    </div>
</nav>
<div class="container">
    <h2>⚠️ Signalement d'un problème</h2>

    <% if (request.getParameter("succes") != null) { %>
    <div class="succes">✅ Signalement envoyé avec succès !</div>
    <% } %>

    <div class="form-card">
        <h3>Nouveau signalement</h3>
        <form method="post" action="signalement">
            <label>Type de problème</label>
            <select name="type" required>
                <option value="">-- Choisir --</option>
                <option value="peche">Pêche illégale</option>
                <option value="meteo">Danger météo</option>
                <option value="securite">Problème de sécurité</option>
                <option value="autre">Autre</option>
            </select>
            <label>Localisation</label>
            <input type="text" name="localisation"
                   placeholder="Ex: Baie de Toamasina, coordonnées..."/>
            <label>Description</label>
            <textarea name="description"
                      placeholder="Décrivez le problème en détail..."
                      required></textarea>
            <button type="submit">📤 Envoyer le signalement</button>
        </form>
    </div>

    <h3 style="color:#e63946; margin-bottom:12px;">
        Mes signalements
    </h3>
    <table>
        <tr>
            <th>Type</th><th>Localisation</th>
            <th>Description</th><th>Date</th>
        </tr>
        <% if (signalements.isEmpty()) { %>
        <tr>
            <td colspan="4" class="empty">
                Aucun signalement enregistré
            </td>
        </tr>
        <% } else {
            for (Signalement s : signalements) { %>
        <tr>
            <td>
                <span class="badge <%= s.getType() %>">
                    <%= s.getType() %>
                </span>
            </td>
            <td><%= s.getLocalisation() %></td>
            <td><%= s.getDescription() %></td>
            <td><%= s.getDateSignalement() %></td>
        </tr>
        <% }} %>
    </table>
</div>
</body>
</html>