package view;

import controller.ParticipationController;
import controller.MembreController;
import controller.ProjetController;
import model.Membre;
import model.Participation;
import model.Projet;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ParticipationForm extends JFrame {
    private ParticipationController participationController;
    private MembreController membreController;
    private ProjetController projetController;
    private JComboBox<Membre> cmbMembre;
    private JComboBox<Projet> cmbProjet;
    private JTextField txtRole;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnAjouter, btnSupprimer, btnActualiser;

    public ParticipationForm() {
        participationController = new ParticipationController();
        membreController = new MembreController();
        projetController = new ProjetController();
        setTitle("Gestion des Participations");
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

        // Projet
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Projet:"), gbc);
        gbc.gridx = 1;
        cmbProjet = new JComboBox<>();
        chargerProjets();
        formPanel.add(cmbProjet, gbc);

        // Rôle dans le projet
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Rôle dans le projet:"), gbc);
        gbc.gridx = 1;
        txtRole = new JTextField(20);
        formPanel.add(txtRole, gbc);

        // Boutons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        btnAjouter = new JButton("Associer");
        btnSupprimer = new JButton("Supprimer");
        btnActualiser = new JButton("Actualiser");

        buttonPanel.add(btnAjouter);
        buttonPanel.add(btnSupprimer);
        buttonPanel.add(btnActualiser);

        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2;
        formPanel.add(buttonPanel, gbc);

        panel.add(formPanel, BorderLayout.NORTH);

        // Table
        String[] columns = {"ID", "Membre", "Projet", "Rôle"};
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
        btnAjouter.addActionListener(e -> ajouterParticipation());
        btnSupprimer.addActionListener(e -> supprimerParticipation());
        btnActualiser.addActionListener(e -> chargerParticipations());

        add(panel);
        chargerParticipations();
    }

    private void chargerMembres() {
        cmbMembre.removeAllItems();
        List<Membre> membres = membreController.listerMembres();
        for (Membre m : membres) {
            cmbMembre.addItem(m);
        }
    }

    private void chargerProjets() {
        cmbProjet.removeAllItems();
        List<Projet> projets = projetController.listerProjets();
        for (Projet p : projets) {
            cmbProjet.addItem(p);
        }
    }

    private void ajouterParticipation() {
        try {
            Membre membre = (Membre) cmbMembre.getSelectedItem();
            Projet projet = (Projet) cmbProjet.getSelectedItem();
            
            if (membre == null || projet == null) {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner un membre et un projet!");
                return;
            }

            String role = txtRole.getText();

            if (participationController.associerMembreProjet(membre.getId(), projet.getId(), role)) {
                JOptionPane.showMessageDialog(this, "Participation créée avec succès!");
                viderFormulaire();
                chargerParticipations();
            } else {
                JOptionPane.showMessageDialog(this, "Erreur: Cette association existe déjà ou erreur lors de la création!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur: " + e.getMessage());
        }
    }

    private void supprimerParticipation() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner une participation!");
            return;
        }

        int id = (Integer) tableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Êtes-vous sûr de vouloir supprimer cette participation?", 
            "Confirmation", 
            JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (participationController.supprimerParticipation(id)) {
                JOptionPane.showMessageDialog(this, "Participation supprimée avec succès!");
                chargerParticipations();
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de la suppression!");
            }
        }
    }

    private void viderFormulaire() {
        txtRole.setText("");
    }

    private void chargerParticipations() {
        tableModel.setRowCount(0);
        List<Participation> participations = participationController.listerParticipations();
        List<Membre> membres = membreController.listerMembres();
        List<Projet> projets = projetController.listerProjets();
        
        for (Participation p : participations) {
            Membre membre = membres.stream()
                .filter(m -> m.getId() == p.getMembreId())
                .findFirst()
                .orElse(null);
            
            Projet projet = projets.stream()
                .filter(pr -> pr.getId() == p.getProjetId())
                .findFirst()
                .orElse(null);
            
            String nomMembre = membre != null ? membre.getNom() + " " + membre.getPrenom() : "Inconnu";
            String nomProjet = projet != null ? projet.getTitre() : "Inconnu";
            
            Object[] row = {
                p.getId(),
                nomMembre,
                nomProjet,
                p.getRoleDansProjet()
            };
            tableModel.addRow(row);
        }
    }
}

