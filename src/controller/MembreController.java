package controller;

import dao.MembreDAO;
import model.Membre;
import java.sql.Date;
import java.util.List;

public class MembreController {
    private MembreDAO membreDAO;

    public MembreController() {
        this.membreDAO = new MembreDAO();
    }

    public boolean ajouterMembre(String nom, String prenom, String email, String role, Date dateAdhesion, String statutCotisation) {
        Membre membre = new Membre(nom, prenom, email, role, dateAdhesion, statutCotisation);
        return membreDAO.create(membre);
    }

    public boolean modifierMembre(int id, String nom, String prenom, String email, String role, Date dateAdhesion, String statutCotisation) {
        Membre membre = new Membre(id, nom, prenom, email, role, dateAdhesion, statutCotisation);
        return membreDAO.update(membre);
    }

    public boolean supprimerMembre(int id) {
        return membreDAO.delete(id);
    }

    public List<Membre> listerMembres() {
        return membreDAO.findAll();
    }

    public Membre trouverMembre(int id) {
        return membreDAO.findById(id);
    }

    public int nombreMembres() {
        return membreDAO.count();
    }
}

