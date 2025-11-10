package view;

import controller.MembreController;
import model.Membre;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.List;

public class MembreForm extends JFrame {
    private MembreController controller;
    private JTextField txtNom, txtPrenom, txtEmail, txtRole;
    private JTextField txtDateAdhesion;
    private JComboBox<String> cmbStatut;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnAjouter, btnModifier, btnSupprimer, btnActualiser;
    private int selectedMembreId = -1;

    public MembreForm() {
        controller = new MembreController();
        setTitle("Gestion des Membres");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());

        // Panel formulaire
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Nom
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Nom:"), gbc);
        gbc.gridx = 1;
        txtNom = new JTextField(20);
        formPanel.add(txtNom, gbc);

        // Prénom
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Prénom:"), gbc);
        gbc.gridx = 1;
        txtPrenom = new JTextField(20);
        formPanel.add(txtPrenom, gbc);

        // Email
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        txtEmail = new JTextField(20);
        formPanel.add(txtEmail, gbc);

        // Rôle
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Rôle:"), gbc);
        gbc.gridx = 1;
        txtRole = new JTextField(20);
        formPanel.add(txtRole, gbc);

        // Date adhésion
        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(new JLabel("Date adhésion (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1;
        txtDateAdhesion = new JTextField(20);
        formPanel.add(txtDateAdhesion, gbc);

        // Statut cotisation
        gbc.gridx = 0; gbc.gridy = 5;
        formPanel.add(new JLabel("Statut cotisation:"), gbc);
        gbc.gridx = 1;
        cmbStatut = new JComboBox<>(new String[]{"non payé", "payé"});
        formPanel.add(cmbStatut, gbc);

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
        String[] columns = {"ID", "Nom", "Prénom", "Email", "Rôle", "Date adhésion", "Statut cotisation"};
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
        btnAjouter.addActionListener(e -> ajouterMembre());
        btnModifier.addActionListener(e -> modifierMembre());
        btnSupprimer.addActionListener(e -> supprimerMembre());
        btnActualiser.addActionListener(e -> chargerMembres());

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int row = table.getSelectedRow();
                selectedMembreId = (Integer) tableModel.getValueAt(row, 0);
                remplirFormulaire(selectedMembreId);
            }
        });

        add(panel);
        chargerMembres();
    }

    private void ajouterMembre() {
        try {
            String nom = txtNom.getText();
            String prenom = txtPrenom.getText();
            String email = txtEmail.getText();
            String role = txtRole.getText();
            Date dateAdhesion = txtDateAdhesion.getText().isEmpty() ? 
                new Date(System.currentTimeMillis()) : Date.valueOf(txtDateAdhesion.getText());
            String statut = (String) cmbStatut.getSelectedItem();

            if (nom.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Le nom est obligatoire!");
                return;
            }

            if (controller.ajouterMembre(nom, prenom, email, role, dateAdhesion, statut)) {
                JOptionPane.showMessageDialog(this, "Membre ajouté avec succès!");
                viderFormulaire();
                chargerMembres();
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur: " + e.getMessage());
        }
    }

    private void modifierMembre() {
        if (selectedMembreId == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un membre!");
            return;
        }

        try {
            String nom = txtNom.getText();
            String prenom = txtPrenom.getText();
            String email = txtEmail.getText();
            String role = txtRole.getText();
            Date dateAdhesion = txtDateAdhesion.getText().isEmpty() ? 
                new Date(System.currentTimeMillis()) : Date.valueOf(txtDateAdhesion.getText());
            String statut = (String) cmbStatut.getSelectedItem();

            if (controller.modifierMembre(selectedMembreId, nom, prenom, email, role, dateAdhesion, statut)) {
                JOptionPane.showMessageDialog(this, "Membre modifié avec succès!");
                viderFormulaire();
                chargerMembres();
                selectedMembreId = -1;
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de la modification!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur: " + e.getMessage());
        }
    }

    private void supprimerMembre() {
        if (selectedMembreId == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un membre!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, 
            "Êtes-vous sûr de vouloir supprimer ce membre?", 
            "Confirmation", 
            JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (controller.supprimerMembre(selectedMembreId)) {
                JOptionPane.showMessageDialog(this, "Membre supprimé avec succès!");
                viderFormulaire();
                chargerMembres();
                selectedMembreId = -1;
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de la suppression!");
            }
        }
    }

    private void remplirFormulaire(int id) {
        Membre membre = controller.trouverMembre(id);
        if (membre != null) {
            txtNom.setText(membre.getNom());
            txtPrenom.setText(membre.getPrenom());
            txtEmail.setText(membre.getEmail() != null ? membre.getEmail() : "");
            txtRole.setText(membre.getRole() != null ? membre.getRole() : "");
            txtDateAdhesion.setText(membre.getDateAdhesion() != null ? membre.getDateAdhesion().toString() : "");
            cmbStatut.setSelectedItem(membre.getStatutCotisation());
        }
    }

    private void viderFormulaire() {
        txtNom.setText("");
        txtPrenom.setText("");
        txtEmail.setText("");
        txtRole.setText("");
        txtDateAdhesion.setText("");
        cmbStatut.setSelectedIndex(0);
    }

    private void chargerMembres() {
        tableModel.setRowCount(0);
        List<Membre> membres = controller.listerMembres();
        for (Membre m : membres) {
            Object[] row = {
                m.getId(),
                m.getNom(),
                m.getPrenom(),
                m.getEmail(),
                m.getRole(),
                m.getDateAdhesion(),
                m.getStatutCotisation()
            };
            tableModel.addRow(row);
        }
    }
}

