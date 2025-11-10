package dao;

import model.Cotisation;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CotisationDAO {
    
    public boolean create(Cotisation cotisation) {
        String sql = "INSERT INTO cotisation (membre_id, montant, date_paiement, periode) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            pst.setInt(1, cotisation.getMembreId());
            pst.setBigDecimal(2, cotisation.getMontant());
            pst.setDate(3, cotisation.getDatePaiement());
            pst.setString(4, cotisation.getPeriode());
            
            int rowsAffected = pst.executeUpdate();
            
            // Mettre à jour le statut de cotisation du membre
            if (rowsAffected > 0) {
                updateMembreStatut(cotisation.getMembreId());
            }
            
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    private void updateMembreStatut(int membreId) {
        String sql = "UPDATE membre SET statut_cotisation = 'payé' WHERE id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            pst.setInt(1, membreId);
            pst.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public List<Cotisation> findAll() {
        List<Cotisation> cotisations = new ArrayList<>();
        String sql = "SELECT * FROM cotisation ORDER BY date_paiement DESC";
        
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            
            while (rs.next()) {
                Cotisation cotisation = new Cotisation(
                    rs.getInt("id"),
                    rs.getInt("membre_id"),
                    rs.getBigDecimal("montant"),
                    rs.getDate("date_paiement"),
                    rs.getString("periode")
                );
                cotisations.add(cotisation);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return cotisations;
    }
    
    public List<Cotisation> findByMembreId(int membreId) {
        List<Cotisation> cotisations = new ArrayList<>();
        String sql = "SELECT * FROM cotisation WHERE membre_id = ? ORDER BY date_paiement DESC";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            pst.setInt(1, membreId);
            ResultSet rs = pst.executeQuery();
            
            while (rs.next()) {
                Cotisation cotisation = new Cotisation(
                    rs.getInt("id"),
                    rs.getInt("membre_id"),
                    rs.getBigDecimal("montant"),
                    rs.getDate("date_paiement"),
                    rs.getString("periode")
                );
                cotisations.add(cotisation);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return cotisations;
    }
    
    public boolean delete(int id) {
        String sql = "DELETE FROM cotisation WHERE id = ?";
        
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
    
    public BigDecimal getTotalCotisations() {
        String sql = "SELECT SUM(montant) FROM cotisation";
        
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            
            if (rs.next()) {
                BigDecimal total = rs.getBigDecimal(1);
                return total != null ? total : BigDecimal.ZERO;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return BigDecimal.ZERO;
    }
}

