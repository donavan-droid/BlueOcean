<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>BlueOcean — Inscription</title>
    <style>
        * { margin:0; padding:0; box-sizing:border-box; }
        body {
            font-family: Arial, sans-serif;
            background: linear-gradient(135deg, #0077b6, #00b4d8);
            min-height:100vh; display:flex;
            align-items:center; justify-content:center;
        }
        .card {
            background:white; padding:40px; border-radius:16px;
            width:420px; box-shadow:0 8px 32px rgba(0,0,0,0.2);
        }
        h2 { color:#0077b6; text-align:center; margin-bottom:20px; }
        label { display:block; margin-bottom:6px; color:#333; font-size:14px; }
        input {
            width:100%; padding:10px 14px; border:1px solid #ddd;
            border-radius:8px; margin-bottom:14px; font-size:14px;
        }
        button {
            width:100%; padding:12px; background:#0077b6; color:white;
            border:none; border-radius:8px; font-size:16px; cursor:pointer;
        }
        button:hover { background:#005f99; }
        .erreur {
            background:#ffe0e0; color:#c00; padding:10px;
            border-radius:8px; margin-bottom:16px;
        }
        .lien { text-align:center; margin-top:16px; font-size:14px; }
        .lien a { color:#0077b6; }
    </style>
</head>
<body>
<div class="card">
    <h2>🌊 Créer un compte</h2>
    <% if (request.getAttribute("erreur") != null) { %>
        <div class="erreur"><%= request.getAttribute("erreur") %></div>
    <% } %>
    <form method="post" action="inscription">
        <label>Nom complet</label>
        <input type="text" name="nom" placeholder="Ex: Jean Rakoto" required/>
        <label>CIN</label>
        <input type="text" name="cin" placeholder="Numéro CIN" required/>
        <label>Téléphone</label>
        <input type="text" name="telephone" placeholder="034 XX XXX XX" required/>
        <label>Nom d'utilisateur</label>
        <input type="text" name="username" placeholder="Username unique" required/>
        <label>Mot de passe</label>
        <input type="password" name="password"
               placeholder="Min. 6 caractères" required/>
        <button type="submit">S'inscrire</button>
    </form>
    <div class="lien">
        Déjà inscrit ? <a href="login">Se connecter</a>
    </div>
</div>
</body>
</html>