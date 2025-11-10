package model;

import java.sql.Date;

public class Membre {
    private int id;
    private String nom;
    private String prenom;
    private String email;
    private String role;
    private Date dateAdhesion;
    private String statutCotisation;

    public Membre() {
    }

    public Membre(String nom, String prenom, String email, String role, Date dateAdhesion, String statutCotisation) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.role = role;
        this.dateAdhesion = dateAdhesion;
        this.statutCotisation = statutCotisation;
    }

    public Membre(int id, String nom, String prenom, String email, String role, Date dateAdhesion, String statutCotisation) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.role = role;
        this.dateAdhesion = dateAdhesion;
        this.statutCotisation = statutCotisation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Date getDateAdhesion() {
        return dateAdhesion;
    }

    public void setDateAdhesion(Date dateAdhesion) {
        this.dateAdhesion = dateAdhesion;
    }

    public String getStatutCotisation() {
        return statutCotisation;
    }

    public void setStatutCotisation(String statutCotisation) {
        this.statutCotisation = statutCotisation;
    }

    @Override
    public String toString() {
        return nom + " " + prenom;
    }
}

