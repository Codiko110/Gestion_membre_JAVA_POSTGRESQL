package model;

public class Participation {
    private int id;
    private int membreId;
    private int projetId;
    private String roleDansProjet;

    public Participation() {
    }

    public Participation(int membreId, int projetId, String roleDansProjet) {
        this.membreId = membreId;
        this.projetId = projetId;
        this.roleDansProjet = roleDansProjet;
    }

    public Participation(int id, int membreId, int projetId, String roleDansProjet) {
        this.id = id;
        this.membreId = membreId;
        this.projetId = projetId;
        this.roleDansProjet = roleDansProjet;
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

    public int getProjetId() {
        return projetId;
    }

    public void setProjetId(int projetId) {
        this.projetId = projetId;
    }

    public String getRoleDansProjet() {
        return roleDansProjet;
    }

    public void setRoleDansProjet(String roleDansProjet) {
        this.roleDansProjet = roleDansProjet;
    }
}

