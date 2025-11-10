package model;

import java.math.BigDecimal;
import java.sql.Date;

public class Cotisation {
    private int id;
    private int membreId;
    private BigDecimal montant;
    private Date datePaiement;
    private String periode;

    public Cotisation() {
    }

    public Cotisation(int membreId, BigDecimal montant, Date datePaiement, String periode) {
        this.membreId = membreId;
        this.montant = montant;
        this.datePaiement = datePaiement;
        this.periode = periode;
    }

    public Cotisation(int id, int membreId, BigDecimal montant, Date datePaiement, String periode) {
        this.id = id;
        this.membreId = membreId;
        this.montant = montant;
        this.datePaiement = datePaiement;
        this.periode = periode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMembreId() {
        return membreId;
    }

    public void setMembreId(int membreId) {
        this.membreId = membreId;
    }

    public BigDecimal getMontant() {
        return montant;
    }

    public void setMontant(BigDecimal montant) {
        this.montant = montant;
    }

    public Date getDatePaiement() {
        return datePaiement;
    }

    public void setDatePaiement(Date datePaiement) {
        this.datePaiement = datePaiement;
    }

    public String getPeriode() {
        return periode;
    }

    public void setPeriode(String periode) {
        this.periode = periode;
    }
}

