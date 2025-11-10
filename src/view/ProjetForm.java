package view;

import controller.ProjetController;
import model.Projet;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

public class ProjetForm extends JFrame {
    private ProjetController controller;
    private JTextField txtTitre, txtDescription, txtDateDebut, txtDateFin, txtBudget;
    private JComboBox<String> cmbEtat;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnAjouter, btnModifier, btnSupprimer, btnActualiser;
    private int selectedProjetId = -1;

    public ProjetForm() {
        controller = new ProjetController();
        setTitle("Gestion des Projets");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());

        // Panel formulaire
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Titre
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Titre:"), gbc);
        gbc.gridx = 1;
        txtTitre = new JTextField(20);
        formPanel.add(txtTitre, gbc);

        // Description
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Description:"), gbc);
        gbc.gridx = 1;
        txtDescription = new JTextField(20);
        formPanel.add(txtDescription, gbc);

        // Date début
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Date début (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1;
        txtDateDebut = new JTextField(20);
        formPanel.add(txtDateDebut, gbc);

        // Date fin
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Date fin (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1;
        txtDateFin = new JTextField(20);
        formPanel.add(txtDateFin, gbc);

        // Budget
        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(new JLabel("Budget:"), gbc);
        gbc.gridx = 1;
        txtBudget = new JTextField(20);
        formPanel.add(txtBudget, gbc);

        // État
        gbc.gridx = 0; gbc.gridy = 5;
        formPanel.add(new JLabel("État:"), gbc);
        gbc.gridx = 1;
        cmbEtat = new JComboBox<>(new String[]{"en cours", "terminé", "annulé"});
        formPanel.add(cmbEtat, gbc);

        // Boutons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        btnAjouter = new JButton("Ajouter");
        btnModifier = new JButton("Modifier");
        btnSupprimer = new JButton("Supprimer");
        btnActualiser = new JButton("Actualiser");

        buttonPanel.add(btnAjouter);
        buttonPanel.add(btnModifier);
        buttonPanel.add(btnSupprimer);
        buttonPanel.add(btnActualiser);

        gbc.gridx = 0; gbc.gridy = 6;
        gbc.gridwidth = 2;
        formPanel.add(buttonPanel, gbc);

        panel.add(formPanel, BorderLayout.NORTH);

        // Table
        String[] columns = {"ID", "Titre", "Description", "Date début", "Date fin", "Budget", "État"};
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
        btnAjouter.addActionListener(e -> ajouterProjet());
        btnModifier.addActionListener(e -> modifierProjet());
        btnSupprimer.addActionListener(e -> supprimerProjet());
        btnActualiser.addActionListener(e -> chargerProjets());

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int row = table.getSelectedRow();
                selectedProjetId = (Integer) tableModel.getValueAt(row, 0);
                remplirFormulaire(selectedProjetId);
            }
        });

        add(panel);
        chargerProjets();
    }

    private void ajouterProjet() {
        try {
            String titre = txtTitre.getText();
            String description = txtDescription.getText();
            Date dateDebut = txtDateDebut.getText().isEmpty() ? null : Date.valueOf(txtDateDebut.getText());
            Date dateFin = txtDateFin.getText().isEmpty() ? null : Date.valueOf(txtDateFin.getText());
            BigDecimal budget = txtBudget.getText().isEmpty() ? null : new BigDecimal(txtBudget.getText());
            String etat = (String) cmbEtat.getSelectedItem();

            if (titre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Le titre est obligatoire!");
                return;
            }

            if (controller.creerProjet(titre, description, dateDebut, dateFin, budget, etat)) {
                JOptionPane.showMessageDialog(this, "Projet créé avec succès!");
                viderFormulaire();
                chargerProjets();
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de la création!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur: " + e.getMessage());
        }
    }

    private void modifierProjet() {
        if (selectedProjetId == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un projet!");
            return;
        }

        try {
            String titre = txtTitre.getText();
            String description = txtDescription.getText();
            Date dateDebut = txtDateDebut.getText().isEmpty() ? null : Date.valueOf(txtDateDebut.getText());
            Date dateFin = txtDateFin.getText().isEmpty() ? null : Date.valueOf(txtDateFin.getText());
            BigDecimal budget = txtBudget.getText().isEmpty() ? null : new BigDecimal(txtBudget.getText());
            String etat = (String) cmbEtat.getSelectedItem();

            if (controller.modifierProjet(selectedProjetId, titre, description, dateDebut, dateFin, budget, etat)) {
                JOptionPane.showMessageDialog(this, "Projet modifié avec succès!");
                viderFormulaire();
                chargerProjets();
                selectedProjetId = -1;
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de la modification!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur: " + e.getMessage());
        }
    }

    private void supprimerProjet() {
        if (selectedProjetId == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un projet!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, 
            "Êtes-vous sûr de vouloir supprimer ce projet?", 
            "Confirmation", 
            JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (controller.supprimerProjet(selectedProjetId)) {
                JOptionPane.showMessageDialog(this, "Projet supprimé avec succès!");
                viderFormulaire();
                chargerProjets();
                selectedProjetId = -1;
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de la suppression!");
            }
        }
    }

    private void remplirFormulaire(int id) {
        Projet projet = controller.trouverProjet(id);
        if (projet != null) {
            txtTitre.setText(projet.getTitre());
            txtDescription.setText(projet.getDescription() != null ? projet.getDescription() : "");
            txtDateDebut.setText(projet.getDateDebut() != null ? projet.getDateDebut().toString() : "");
            txtDateFin.setText(projet.getDateFin() != null ? projet.getDateFin().toString() : "");
            txtBudget.setText(projet.getBudget() != null ? projet.getBudget().toString() : "");
            cmbEtat.setSelectedItem(projet.getEtat());
        }
    }

    private void viderFormulaire() {
        txtTitre.setText("");
        txtDescription.setText("");
        txtDateDebut.setText("");
        txtDateFin.setText("");
        txtBudget.setText("");
        cmbEtat.setSelectedIndex(0);
    }

    private void chargerProjets() {
        tableModel.setRowCount(0);
        List<Projet> projets = controller.listerProjets();
        for (Projet p : projets) {
            Object[] row = {
                p.getId(),
                p.getTitre(),
                p.getDescription(),
                p.getDateDebut(),
                p.getDateFin(),
                p.getBudget(),
                p.getEtat()
            };
            tableModel.addRow(row);
        }
    }
}

