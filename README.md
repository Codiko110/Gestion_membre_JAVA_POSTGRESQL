# Gestion de Membres d'Association

Application Java Swing pour la gestion des membres, projets, cotisations et participations d'une association.

## Fonctionnalités

- 🔑 Connexion administrateur
- 👥 Gestion des membres (ajout, modification, suppression, liste)
- 📦 Gestion des projets (création, modification, suppression, liste)
- 💰 Gestion des cotisations
- 🤝 Gestion des participations (association membres-projets)
- 📊 Tableau de bord avec statistiques

## Structure du Projet

```
src/
├── model/          → Classes POJO (entités)
├── dao/            → Accès à la base de données
├── view/           → Interfaces graphiques Swing
├── controller/     → Logique métier
└── App.java        → Point d'entrée principal
```

## Prérequis

- Java JDK 8 ou supérieur
- PostgreSQL
- Driver PostgreSQL JDBC (déjà inclus dans `lib/postgresql-42.7.7.jar`)

## Pour importer la base
``` psql -U postgres -d association_db -f membre.sql ```

## Si tu n'a pas encore creer la base
```- createdb -U postgres association_db & psql -U postgres -d association_db -f membre.sql ```


## Configuration de la Base de Données

1. Créez la base de données PostgreSQL :
```sql
CREATE DATABASE association_db;
\c association_db;

-- Table des administrateurs
CREATE TABLE admin (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL
);

-- Table des membres
CREATE TABLE membre (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100),
    email VARCHAR(100) UNIQUE,
    role VARCHAR(50),
    date_adhesion DATE DEFAULT CURRENT_DATE,
    statut_cotisation VARCHAR(20) DEFAULT 'non payé'
);

-- Table des projets
CREATE TABLE projet (
    id SERIAL PRIMARY KEY,
    titre VARCHAR(150) NOT NULL,
    description TEXT,
    date_debut DATE,
    date_fin DATE,
    budget NUMERIC(12,2),
    etat VARCHAR(50) DEFAULT 'en cours'
);

-- Table des participations
CREATE TABLE participation (
    id SERIAL PRIMARY KEY,
    membre_id INT REFERENCES membre(id) ON DELETE CASCADE,
    projet_id INT REFERENCES projet(id) ON DELETE CASCADE,
    role_dans_projet VARCHAR(50)
);

-- Table des cotisations
CREATE TABLE cotisation (
    id SERIAL PRIMARY KEY,
    membre_id INT REFERENCES membre(id) ON DELETE CASCADE,
    montant NUMERIC(10,2) NOT NULL,
    date_paiement DATE DEFAULT CURRENT_DATE,
    periode VARCHAR(20)
);
```

2. Créez un administrateur :
```sql
INSERT INTO admin (username, password) VALUES ('admin', 'admin123');
```

3. Configurez les paramètres de connexion dans `src/dao/DBConnection.java` :
   - URL: `jdbc:postgresql://localhost:5432/association_db`
   - User: `postgres`
   - Password: `1234` (modifiez selon votre configuration)

## Compilation

**Linux/Mac:**
```bash
javac -cp "lib/postgresql-42.7.7.jar" -d out $(find src -name "*.java")
```

**Windows:**
```cmd
javac -cp "lib/postgresql-42.7.7.jar" -d out -sourcepath src src\**\*.java
```

## Exécution

**Linux/Mac (utilisez `:` comme séparateur):**
```bash
java -cp "out:lib/postgresql-42.7.7.jar" App
```

**Windows (utilisez `;` comme séparateur):**
```cmd
java -cp "out;lib/postgresql-42.7.7.jar" App
```





