package view;

import dao.AdminDAO;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class LoginForm extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private AdminDAO adminDAO;

    public LoginForm() {
        adminDAO = new AdminDAO();
        setTitle("Connexion - Gestion Association");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel lblTitle = new JLabel("Connexion Administrateur");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitle.setBounds(60, 20, 300, 30);
        panel.add(lblTitle);

        JLabel lblUsername = new JLabel("Nom d'utilisateur:");
        lblUsername.setBounds(50, 80, 120, 25);
        panel.add(lblUsername);

        txtUsername = new JTextField();
        txtUsername.setBounds(180, 80, 150, 25);
        panel.add(txtUsername);

        JLabel lblPassword = new JLabel("Mot de passe:");
        lblPassword.setBounds(50, 120, 120, 25);
        panel.add(lblPassword);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(180, 120, 150, 25);
        panel.add(txtPassword);

        btnLogin = new JButton("Se connecter");
        btnLogin.setBounds(130, 170, 130, 30);
        panel.add(btnLogin);

        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });

        // Permettre la connexion avec la touche Entrée
        txtPassword.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });

        add(panel);
    }

    private void login() {
        String username = txtUsername.getText();
        String password = String.valueOf(txtPassword.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.");
            return;
        }

        if (adminDAO.authenticate(username, password)) {
            JOptionPane.showMessageDialog(this, "Connexion réussie !");
            this.dispose();
            new MainDashboard().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Identifiants incorrects !");
            txtPassword.setText("");
        }
    }
}
