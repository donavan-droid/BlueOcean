<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="model.Utilisateur, model.Bateau, java.util.List" %>
<%
    Utilisateur user = (Utilisateur) session.getAttribute("user");
    if (user == null) { response.sendRedirect("login.jsp"); return; }
    List<Bateau> bateaux =
        (List<Bateau>) request.getAttribute("bateaux");
    if (bateaux == null) bateaux = new java.util.ArrayList<>();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Mes Bateaux</title>
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
        .form-row { display:flex; gap:12px; }
        input, select {
            flex:1; padding:10px; border:1px solid #ddd;
            border-radius:8px; font-size:14px;
        }
        button {
            padding:10px 20px; background:#0077b6; color:white;
            border:none; border-radius:8px; cursor:pointer;
        }
        button:hover { background:#005f99; }
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
            background:#0077b6; color:white;
            padding:12px 16px; text-align:left;
        }
        td { padding:12px 16px; border-bottom:1px solid #eee; }
        .btn-del {
            background:#e63946; color:white; border:none;
            padding:6px 14px; border-radius:6px; cursor:pointer;
        }
        .empty { text-align:center; color:#999; padding:24px; }
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
             <a href="gps">📍 GPS</a>
            <a href="captures">🐟 Captures</a>
            <a href="meteo">🌦 Météo</a>
            <a href="signalement"> ⚠ Signaler</a>
        <% } %>
        <a href="logout">Déconnexion</a>
    </div>
</nav>
<div class="container">
    <h2>⛵ Mes Bateaux</h2>

    <% if (request.getParameter("succes") != null) { %>
    <div class="succes">✅ Opération effectuée avec succès !</div>
    <% } %>

    <div class="form-card">
        <h3>Ajouter un bateau</h3>
        <form method="post" action="bateau">
            <div class="form-row">
                <select name="typeBateau" required>
                    <option value="">-- Type de bateau --</option>
                    <option value="Pirogue">Pirogue</option>
                    <option value="Bateau moteur">Bateau moteur</option>
                    <option value="Chalutier">Chalutier</option>
                    <option value="Voilier">Voilier</option>
                    <option value="Autre">Autre</option>
                </select>
                <button type="submit">➕ Ajouter</button>
            </div>
        </form>
    </div>

    <table>
        <tr>
            <th>#</th><th>Type</th><th>Action</th>
        </tr>
        <% if (bateaux.isEmpty()) { %>
        <tr>
            <td colspan="3" class="empty">
                Aucun bateau enregistré
            </td>
        </tr>
        <% } else {
            for (Bateau b : bateaux) { %>
        <tr>
            <td><%= b.getIdBateau() %></td>
            <td>⛵ <%= b.getTypeBateau() %></td>
            <td>
                <form method="post" action="bateau" style="display:inline">
                    <input type="hidden" name="action" value="supprimer"/>
                    <input type="hidden" name="idBateau"
                           value="<%= b.getIdBateau() %>"/>
                    <button class="btn-del"
                        onclick="return confirm('Supprimer ce bateau ?')">
                        🗑 Supprimer
                    </button>
                </form>
            </td>
        </tr>
        <% }} %>
    </table>
</div>
</body>
</html>