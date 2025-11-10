package dao;

import model.Projet;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjetDAO {
    
    public boolean create(Projet projet) {
        String sql = "INSERT INTO projet (titre, description, date_debut, date_fin, budget, etat) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            pst.setString(1, projet.getTitre());
            pst.setString(2, projet.getDescription());
            pst.setDate(3, projet.getDateDebut());
            pst.setDate(4, projet.getDateFin());
            pst.setBigDecimal(5, projet.getBudget());
            pst.setString(6, projet.getEtat());
            
            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<Projet> findAll() {
        List<Projet> projets = new ArrayList<>();
        String sql = "SELECT * FROM projet ORDER BY titre";
        
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            
            while (rs.next()) {
                Projet projet = new Projet(
                    rs.getInt("id"),
                    rs.getString("titre"),
                    rs.getString("description"),
                    rs.getDate("date_debut"),
                    rs.getDate("date_fin"),
                    rs.getBigDecimal("budget"),
                    rs.getString("etat")
                );
                projets.add(projet);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return projets;
    }
    
    public Projet findById(int id) {
        String sql = "SELECT * FROM projet WHERE id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                return new Projet(
                    rs.getInt("id"),
                    rs.getString("titre"),
                    rs.getString("description"),
                    rs.getDate("date_debut"),
                    rs.getDate("date_fin"),
                    rs.getBigDecimal("budget"),
                    rs.getString("etat")
                );
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public boolean update(Projet projet) {
        String sql = "UPDATE projet SET titre = ?, description = ?, date_debut = ?, date_fin = ?, budget = ?, etat = ? WHERE id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            pst.setString(1, projet.getTitre());
            pst.setString(2, projet.getDescription());
            pst.setDate(3, projet.getDateDebut());
            pst.setDate(4, projet.getDateFin());
            pst.setBigDecimal(5, projet.getBudget());
            pst.setString(6, projet.getEtat());
            pst.setInt(7, projet.getId());
            
            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean delete(int id) {
        String sql = "DELETE FROM projet WHERE id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            pst.setInt(1, id);
            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public int count() {
        String sql = "SELECT COUNT(*) FROM projet";
        
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            
            if (rs.next()) {
                return rs.getInt(1);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return 0;
    }
    
    public List<Projet> findActive() {
        List<Projet> projets = new ArrayList<>();
        String sql = "SELECT * FROM projet WHERE etat = 'en cours' ORDER BY titre";
        
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            
            while (rs.next()) {
                Projet projet = new Projet(
                    rs.getInt("id"),
                    rs.getString("titre"),
                    rs.getString("description"),
                    rs.getDate("date_debut"),
                    rs.getDate("date_fin"),
                    rs.getBigDecimal("budget"),
                    rs.getString("etat")
                );
                projets.add(projet);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return projets;
    }
}

