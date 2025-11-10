package controller;

import dao.CotisationDAO;
import model.Cotisation;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

public class CotisationController {
    private CotisationDAO cotisationDAO;

    public CotisationController() {
        this.cotisationDAO = new CotisationDAO();
    }

    public boolean enregistrerCotisation(int membreId, BigDecimal montant, Date datePaiement, String periode) {
        Cotisation cotisation = new Cotisation(membreId, montant, datePaiement, periode);
        return cotisationDAO.create(cotisation);
    }

    public boolean supprimerCotisation(int id) {
        return cotisationDAO.delete(id);
    }

    public List<Cotisation> listerCotisations() {
        return cotisationDAO.findAll();
    }

    public List<Cotisation> listerCotisationsMembre(int membreId) {
        return cotisationDAO.findByMembreId(membreId);
    }

    public BigDecimal totalCotisations() {
        return cotisationDAO.getTotalCotisations();
    }
}

