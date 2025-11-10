package controller;

import dao.ProjetDAO;
import model.Projet;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

public class ProjetController {
    private ProjetDAO projetDAO;

    public ProjetController() {
        this.projetDAO = new ProjetDAO();
    }

    public boolean creerProjet(String titre, String description, Date dateDebut, Date dateFin, BigDecimal budget, String etat) {
        Projet projet = new Projet(titre, description, dateDebut, dateFin, budget, etat);
        return projetDAO.create(projet);
    }

    public boolean modifierProjet(int id, String titre, String description, Date dateDebut, Date dateFin, BigDecimal budget, String etat) {
        Projet projet = new Projet(id, titre, description, dateDebut, dateFin, budget, etat);
        return projetDAO.update(projet);
    }

    public boolean supprimerProjet(int id) {
        return projetDAO.delete(id);
    }

    public List<Projet> listerProjets() {
        return projetDAO.findAll();
    }

    public List<Projet> listerProjetsActifs() {
        return projetDAO.findActive();
    }

    public Projet trouverProjet(int id) {
        return projetDAO.findById(id);
    }

    public int nombreProjets() {
        return projetDAO.count();
    }
}

