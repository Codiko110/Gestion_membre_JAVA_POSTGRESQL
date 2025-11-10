package view;

import controller.CotisationController;
import controller.MembreController;
import controller.ProjetController;
import java.awt.*;
import java.math.BigDecimal;
import javax.swing.*;

public class MainDashboard extends JFrame {

    private MembreController membreController;
    private ProjetController projetController;
    private CotisationController cotisationController;

    public MainDashboard() {
        membreController = new MembreController();
        projetController = new ProjetController();
        cotisationController = new CotisationController();
        
        setTitle("Tableau de Bord - Association");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JMenuBar menuBar = new JMenuBar();

        JMenu menuMembre = new JMenu("Membres");
        JMenuItem itemGestionMembre = new JMenuItem("Gérer les membres");
        itemGestionMembre.addActionListener(e -> {
            new MembreForm().setVisible(true);
        });

        menuMembre.add(itemGestionMembre);

        JMenu menuProjet = new JMenu("Projets");
        JMenuItem itemGestionProjet = new JMenuItem("Gérer les projets");
        itemGestionProjet.addActionListener(e -> {
            new ProjetForm().setVisible(true);
        });

        menuProjet.add(itemGestionProjet);

        JMenu menuCotisation = new JMenu("Cotisations");
        JMenuItem itemCotisation = new JMenuItem("Gérer les cotisations");
        itemCotisation.addActionListener(e -> {
            new CotisationForm().setVisible(true);
        });

        menuCotisation.add(itemCotisation);

        JMenu menuParticipation = new JMenu("Participations");
        JMenuItem itemParticipation = new JMenuItem("Associer membres & projets");
        itemParticipation.addActionListener(e -> {
            new ParticipationForm().setVisible(true);
        });

        menuParticipation.add(itemParticipation);

        JMenu menuQuitter = new JMenu("Quitter");
        JMenuItem itemExit = new JMenuItem("Fermer l'application");
        itemExit.addActionListener(e -> System.exit(0));
        menuQuitter.add(itemExit);

        menuBar.add(menuMembre);
        menuBar.add(menuProjet);
        menuBar.add(menuCotisation);
        menuBar.add(menuParticipation);
        menuBar.add(menuQuitter);

        setJMenuBar(menuBar);

        // Contenu principal - Tableau de bord
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel lblWelcome = new JLabel("Tableau de Bord - Gestion de l'Association", SwingConstants.CENTER);
        lblWelcome.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(lblWelcome, BorderLayout.NORTH);

        // Panel de statistiques
        JPanel statsPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Nombre de membres
        JPanel panelMembres = new JPanel(new BorderLayout());
        panelMembres.setBorder(BorderFactory.createTitledBorder("Membres"));
        JLabel lblMembres = new JLabel(String.valueOf(membreController.nombreMembres()), SwingConstants.CENTER);
        lblMembres.setFont(new Font("Arial", Font.BOLD, 24));
        panelMembres.add(lblMembres, BorderLayout.CENTER);
        statsPanel.add(panelMembres);

        // Nombre de projets
        JPanel panelProjets = new JPanel(new BorderLayout());
        panelProjets.setBorder(BorderFactory.createTitledBorder("Projets"));
        JLabel lblProjets = new JLabel(String.valueOf(projetController.nombreProjets()), SwingConstants.CENTER);
        lblProjets.setFont(new Font("Arial", Font.BOLD, 24));
        panelProjets.add(lblProjets, BorderLayout.CENTER);
        statsPanel.add(panelProjets);

        // Projets actifs
        JPanel panelProjetsActifs = new JPanel(new BorderLayout());
        panelProjetsActifs.setBorder(BorderFactory.createTitledBorder("Projets Actifs"));
        JLabel lblProjetsActifs = new JLabel(String.valueOf(projetController.listerProjetsActifs().size()), SwingConstants.CENTER);
        lblProjetsActifs.setFont(new Font("Arial", Font.BOLD, 24));
        panelProjetsActifs.add(lblProjetsActifs, BorderLayout.CENTER);
        statsPanel.add(panelProjetsActifs);

        // Total des cotisations
        JPanel panelCotisations = new JPanel(new BorderLayout());
        panelCotisations.setBorder(BorderFactory.createTitledBorder("Total Cotisations"));
        BigDecimal totalCotisations = cotisationController.totalCotisations();
        JLabel lblCotisations = new JLabel(totalCotisations.toString() + " €", SwingConstants.CENTER);
        lblCotisations.setFont(new Font("Arial", Font.BOLD, 24));
        panelCotisations.add(lblCotisations, BorderLayout.CENTER);
        statsPanel.add(panelCotisations);

        panel.add(statsPanel, BorderLayout.CENTER);

        // Bouton actualiser
        JButton btnActualiser = new JButton("Actualiser les statistiques");
        btnActualiser.addActionListener(e -> {
            lblMembres.setText(String.valueOf(membreController.nombreMembres()));
            lblProjets.setText(String.valueOf(projetController.nombreProjets()));
            lblProjetsActifs.setText(String.valueOf(projetController.listerProjetsActifs().size()));
            lblCotisations.setText(cotisationController.totalCotisations().toString() + " €");
        });
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnActualiser);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);
    }
}
