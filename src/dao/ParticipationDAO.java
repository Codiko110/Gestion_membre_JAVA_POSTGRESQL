package dao;

import model.Participation;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ParticipationDAO {
    
    public boolean create(Participation participation) {
        String sql = "INSERT INTO participation (membre_id, projet_id, role_dans_projet) VALUES (?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            pst.setInt(1, participation.getMembreId());
            pst.setInt(2, participation.getProjetId());
            pst.setString(3, participation.getRoleDansProjet());
            
            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<Participation> findAll() {
        List<Participation> participations = new ArrayList<>();
        String sql = "SELECT * FROM participation ORDER BY id";
        
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            
            while (rs.next()) {
                Participation participation = new Participation(
                    rs.getInt("id"),
                    rs.getInt("membre_id"),
                    rs.getInt("projet_id"),
                    rs.getString("role_dans_projet")
                );
                participations.add(participation);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return participations;
    }
    
    public List<Participation> findByProjetId(int projetId) {
        List<Participation> participations = new ArrayList<>();
        String sql = "SELECT * FROM participation WHERE projet_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            pst.setInt(1, projetId);
            ResultSet rs = pst.executeQuery();
            
            while (rs.next()) {
                Participation participation = new Participation(
                    rs.getInt("id"),
                    rs.getInt("membre_id"),
                    rs.getInt("projet_id"),
                    rs.getString("role_dans_projet")
                );
                participations.add(participation);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return participations;
    }
    
    public List<Participation> findByMembreId(int membreId) {
        List<Participation> participations = new ArrayList<>();
        String sql = "SELECT * FROM participation WHERE membre_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            pst.setInt(1, membreId);
            ResultSet rs = pst.executeQuery();
            
            while (rs.next()) {
                Participation participation = new Participation(
                    rs.getInt("id"),
                    rs.getInt("membre_id"),
                    rs.getInt("projet_id"),
                    rs.getString("role_dans_projet")
                );
                participations.add(participation);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return participations;
    }
    
    public boolean delete(int id) {
        String sql = "DELETE FROM participation WHERE id = ?";
        
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
    
    public boolean exists(int membreId, int projetId) {
        String sql = "SELECT * FROM participation WHERE membre_id = ? AND projet_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            pst.setInt(1, membreId);
            pst.setInt(2, projetId);
            ResultSet rs = pst.executeQuery();
            return rs.next();
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

