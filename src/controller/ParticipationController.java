package controller;

import dao.ParticipationDAO;
import model.Participation;
import java.util.List;

public class ParticipationController {
    private ParticipationDAO participationDAO;

    public ParticipationController() {
        this.participationDAO = new ParticipationDAO();
    }

    public boolean associerMembreProjet(int membreId, int projetId, String roleDansProjet) {
        // Vérifier si la participation existe déjà
        if (participationDAO.exists(membreId, projetId)) {
            return false; // Déjà associé
        }
        
        Participation participation = new Participation(membreId, projetId, roleDansProjet);
        return participationDAO.create(participation);
    }

    public boolean supprimerParticipation(int id) {
        return participationDAO.delete(id);
    }

    public List<Participation> listerParticipations() {
        return participationDAO.findAll();
    }

    public List<Participation> listerParticipationsProjet(int projetId) {
        return participationDAO.findByProjetId(projetId);
    }

    public List<Participation> listerParticipationsMembre(int membreId) {
        return participationDAO.findByMembreId(membreId);
    }
}

