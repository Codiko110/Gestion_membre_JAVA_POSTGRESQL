package dao;

import model.Membre;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MembreDAO {
    
    public boolean create(Membre membre) {
        String sql = "INSERT INTO membre (nom, prenom, email, role, date_adhesion, statut_cotisation) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            pst.setString(1, membre.getNom());
            pst.setString(2, membre.getPrenom());
            pst.setString(3, membre.getEmail());
            pst.setString(4, membre.getRole());
            pst.setDate(5, membre.getDateAdhesion());
            pst.setString(6, membre.getStatutCotisation());
            
            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<Membre> findAll() {
        List<Membre> membres = new ArrayList<>();
        String sql = "SELECT * FROM membre ORDER BY nom";
        
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            
            while (rs.next()) {
                Membre membre = new Membre(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("email"),
                    rs.getString("role"),
                    rs.getDate("date_adhesion"),
                    rs.getString("statut_cotisation")
                );
                membres.add(membre);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return membres;
    }
    
    public Membre findById(int id) {
        String sql = "SELECT * FROM membre WHERE id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                return new Membre(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("email"),
                    rs.getString("role"),
                    rs.getDate("date_adhesion"),
                    rs.getString("statut_cotisation")
                );
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public boolean update(Membre membre) {
        String sql = "UPDATE membre SET nom = ?, prenom = ?, email = ?, role = ?, date_adhesion = ?, statut_cotisation = ? WHERE id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            pst.setString(1, membre.getNom());
            pst.setString(2, membre.getPrenom());
            pst.setString(3, membre.getEmail());
            pst.setString(4, membre.getRole());
            pst.setDate(5, membre.getDateAdhesion());
            pst.setString(6, membre.getStatutCotisation());
            pst.setInt(7, membre.getId());
            
            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean delete(int id) {
        String sql = "DELETE FROM membre WHERE id = ?";
        
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
        String sql = "SELECT COUNT(*) FROM membre";
        
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
}

