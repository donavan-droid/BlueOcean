<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="model.Utilisateur, java.util.List, dao.UtilisateurDAO,
                 dao.CaptureDAO, dao.SignalementDAO" %>
<%
    Utilisateur user = (Utilisateur) session.getAttribute("user");
    if (user == null || !"admin".equals(user.getRole())) {
        response.sendRedirect("login.jsp"); return;
    }
    List utilisateurs = new UtilisateurDAO().listerTous();
    List captures     = new CaptureDAO().listerToutes();
    List signalements = new SignalementDAO().listerTous();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Dashboard Admin</title>
    <style>
        * { margin:0; padding:0; box-sizing:border-box; }
        body { font-family:Arial,sans-serif; background:#f0f4f8; }
        nav {
            background:#003f5c; color:white; padding:16px 24px;
            display:flex; justify-content:space-between; align-items:center;
        }
        nav h1 { font-size:20px; }
        nav a { color:white; text-decoration:none; margin-left:16px; }
        .container { padding:30px; max-width:1100px; margin:0 auto; }
        h2 { color:#003f5c; margin-bottom:24px; }
        .stats {
            display:grid; grid-template-columns:repeat(3,1fr);
            gap:20px; margin-bottom:32px;
        }
        .stat {
            background:white; border-radius:12px; padding:24px;
            text-align:center; box-shadow:0 2px 8px rgba(0,0,0,0.08);
        }
        .stat .num { font-size:40px; font-weight:bold; color:#0077b6; }
        .stat p { color:#666; margin-top:6px; }
        table {
            width:100%; background:white; border-radius:12px;
            border-collapse:collapse;
            box-shadow:0 2px 8px rgba(0,0,0,0.08);
            margin-bottom:24px;
        }
        th {
            background:#0077b6; color:white; padding:12px 16px;
            text-align:left; font-size:14px;
        }
        td { padding:12px 16px; border-bottom:1px solid #eee; font-size:14px; }
        tr:hover td { background:#f5f9ff; }
        .badge {
            padding:4px 10px; border-radius:20px; font-size:12px;
        }
        .badge.admin { background:#003f5c; color:white; }
        .badge.pecheur { background:#00b4d8; color:white; }
        .btn-del {
            background:#e63946; color:white; border:none;
            padding:4px 10px; border-radius:6px; cursor:pointer;
        }
    </style>
</head>
<body>
<nav>
    <h1>🌊 BlueOcean — Administration</h1>
    <div>
        <a href="gps">📍 Carte GPS</a>
        <a href="meteo">🌦 Météo</a>
        <a href="signalement">⚠ Signalements</a>
        <a href="logout">Déconnexion</a>
    </div>
</nav>
<div class="container">
    <h2>Tableau de bord administrateur</h2>
    <div class="stats">
        <div class="stat">
            <div class="num"><%= utilisateurs.size() %></div>
            <p>Utilisateurs</p>
        </div>
        <div class="stat">
            <div class="num"><%= captures.size() %></div>
            <p>Captures enregistrées</p>
        </div>
        <div class="stat">
            <div class="num"><%= signalements.size() %></div>
            <p>Signalements</p>
        </div>
    </div>

    <h3 style="margin-bottom:12px;color:#003f5c;">Gestion des utilisateurs</h3>
    <table>
        <tr><th>ID</th><th>Username</th><th>Rôle</th><th>Action</th></tr>
        <% for (Object obj : utilisateurs) {
            Utilisateur u = (Utilisateur) obj; %>
        <tr>
            <td><%= u.getIdUser() %></td>
            <td><%= u.getUsername() %></td>
            <td>
                <span class="badge <%= u.getRole() %>">
                    <%= u.getRole() %>
                </span>
            </td>
            <td>
                <form method="post" action="admin/supprimerUser"
                      style="display:inline">
                    <input type="hidden" name="idUser"
                           value="<%= u.getIdUser() %>"/>
                    <button class="btn-del"
                        onclick="return confirm('Supprimer cet utilisateur ?')">
                        Supprimer
                    </button>
                </form>
            </td>
        </tr>
        <% } %>
    </table>

    <h3 style="margin-bottom:12px;color:#003f5c;">Derniers signalements</h3>
    <table>
        <tr><th>ID</th><th>Type</th><th>Description</th>
            <th>Localisation</th><th>Date</th></tr>
        <% for (Object obj : signalements) {
            model.Signalement s = (model.Signalement) obj; %>
        <tr>
            <td><%= s.getIdSignalement() %></td>
            <td><%= s.getType() %></td>
            <td><%= s.getDescription() %></td>
            <td><%= s.getLocalisation() %></td>
            <td><%= s.getDateSignalement() %></td>
        </tr>
        <% } %>
    </table>
</div>
</body>
</html>