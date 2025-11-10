package view;

import controller.CotisationController;
import controller.MembreController;
import model.Cotisation;
import model.Membre;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

public class CotisationForm extends JFrame {
    private CotisationController cotisationController;
    private MembreController membreController;
    private JComboBox<Membre> cmbMembre;
    private JTextField txtMontant, txtDatePaiement, txtPeriode;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnAjouter, btnSupprimer, btnActualiser;
    private JLabel lblTotal;

    public CotisationForm() {
        cotisationController = new CotisationController();
        membreController = new MembreController();
        setTitle("Gestion des Cotisations");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());

        // Panel formulaire
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Membre
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Membre:"), gbc);
        gbc.gridx = 1;
        cmbMembre = new JComboBox<>();
        chargerMembres();
        formPanel.add(cmbMembre, gbc);

        // Montant
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Montant:"), gbc);
        gbc.gridx = 1;
        txtMontant = new JTextField(20);
        formPanel.add(txtMontant, gbc);

        // Date paiement
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Date paiement (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1;
        txtDatePaiement = new JTextField(20);
        formPanel.add(txtDatePaiement, gbc);

        // Période
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Période:"), gbc);
        gbc.gridx = 1;
        txtPeriode = new JTextField(20);
        formPanel.add(txtPeriode, gbc);

        // Boutons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        btnAjouter = new JButton("Ajouter");
        btnSupprimer = new JButton("Supprimer");
        btnActualiser = new JButton("Actualiser");

        buttonPanel.add(btnAjouter);
        buttonPanel.add(btnSupprimer);
        buttonPanel.add(btnActualiser);

        gbc.gridx = 0; gbc.gridy = 4;
        gbc.gridwidth = 2;
        formPanel.add(buttonPanel, gbc);

        // Total
        lblTotal = new JLabel("Total des cotisations: 0.00");
        lblTotal.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0; gbc.gridy = 5;
        gbc.gridwidth = 2;
        formPanel.add(lblTotal, gbc);

        panel.add(formPanel, BorderLayout.NORTH);

        // Table
        String[] columns = {"ID", "Membre", "Montant", "Date paiement", "Période"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Listeners
        btnAjouter.addActionListener(e -> ajouterCotisation());
        btnSupprimer.addActionListener(e -> supprimerCotisation());
        btnActualiser.addActionListener(e -> {
            chargerCotisations();
            mettreAJourTotal();
        });

        add(panel);
        chargerCotisations();
        mettreAJourTotal();
    }

    private void chargerMembres() {
        cmbMembre.removeAllItems();
        List<Membre> membres = membreController.listerMembres();
        for (Membre m : membres) {
            cmbMembre.addItem(m);
        }
    }

    private void ajouterCotisation() {
        try {
            Membre membre = (Membre) cmbMembre.getSelectedItem();
            if (membre == null) {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner un membre!");
                return;
            }

            BigDecimal montant = new BigDecimal(txtMontant.getText());
            Date datePaiement = txtDatePaiement.getText().isEmpty() ? 
                new Date(System.currentTimeMillis()) : Date.valueOf(txtDatePaiement.getText());
            String periode = txtPeriode.getText();

            if (cotisationController.enregistrerCotisation(membre.getId(), montant, datePaiement, periode)) {
                JOptionPane.showMessageDialog(this, "Cotisation enregistrée avec succès!");
                viderFormulaire();
                chargerCotisations();
                mettreAJourTotal();
                chargerMembres(); // Recharger pour mettre à jour les statuts
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de l'enregistrement!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur: " + e.getMessage());
        }
    }

    private void supprimerCotisation() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner une cotisation!");
            return;
        }

        int id = (Integer) tableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Êtes-vous sûr de vouloir supprimer cette cotisation?", 
            "Confirmation", 
            JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (cotisationController.supprimerCotisation(id)) {
                JOptionPane.showMessageDialog(this, "Cotisation supprimée avec succès!");
                chargerCotisations();
                mettreAJourTotal();
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de la suppression!");
            }
        }
    }

    private void viderFormulaire() {
        txtMontant.setText("");
        txtDatePaiement.setText("");
        txtPeriode.setText("");
    }

    private void chargerCotisations() {
        tableModel.setRowCount(0);
        List<Cotisation> cotisations = cotisationController.listerCotisations();
        List<Membre> membres = membreController.listerMembres();
        
        for (Cotisation c : cotisations) {
            Membre membre = membres.stream()
                .filter(m -> m.getId() == c.getMembreId())
                .findFirst()
                .orElse(null);
            
            String nomMembre = membre != null ? membre.getNom() + " " + membre.getPrenom() : "Inconnu";
            
            Object[] row = {
                c.getId(),
                nomMembre,
                c.getMontant(),
                c.getDatePaiement(),
                c.getPeriode()
            };
            tableModel.addRow(row);
        }
    }

    private void mettreAJourTotal() {
        BigDecimal total = cotisationController.totalCotisations();
        lblTotal.setText("Total des cotisations: " + total.toString());
    }
}

