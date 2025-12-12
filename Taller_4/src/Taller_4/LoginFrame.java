package Taller_4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {
    private JTextField txtUsuario;
    private JPasswordField txtContraseña;
    private JButton btnLogin;
    private JComboBox<String> comboTipoUsuario;
    private JPanel panelLogin;
    
    public LoginFrame() {
        setTitle("AcademicCore - Sistema de Certificaciones");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 350);
        setLocationRelativeTo(null);
        
        initComponents();
        add(panelLogin);
    }
    
    private void initComponents() {
        panelLogin = new JPanel();
        panelLogin.setLayout(new BorderLayout(10, 10));
        panelLogin.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Panel superior con título
        JPanel panelTitulo = new JPanel();
        panelTitulo.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel lblTitulo = new JLabel("ACADEMICCORE");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(0, 102, 204));
        panelTitulo.add(lblTitulo);
        
        JLabel lblSubtitulo = new JLabel("Sistema de Gestión de Certificaciones");
        lblSubtitulo.setFont(new Font("Arial", Font.PLAIN, 12));
        panelTitulo.add(lblSubtitulo);
        
        // Panel central con formulario
        JPanel panelForm = new JPanel();
        panelForm.setLayout(new GridLayout(5, 1, 10, 10));
        
        JLabel lblTipoUsuario = new JLabel("Tipo de Usuario:");
        comboTipoUsuario = new JComboBox<>(new String[]{"Estudiante", "Coordinador", "Administrador"});
        
        JLabel lblUsuario = new JLabel("Usuario:");
        txtUsuario = new JTextField();
        
        JLabel lblContraseña = new JLabel("Contraseña:");
        txtContraseña = new JPasswordField();
        
        btnLogin = new JButton("Iniciar Sesión");
        btnLogin.setBackground(new Color(0, 102, 204));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFont(new Font("Arial", Font.BOLD, 14));
        
        panelForm.add(lblTipoUsuario);
        panelForm.add(comboTipoUsuario);
        panelForm.add(lblUsuario);
        panelForm.add(txtUsuario);
        panelForm.add(lblContraseña);
        panelForm.add(txtContraseña);
        panelForm.add(new JLabel()); // Espacio en blanco
        panelForm.add(btnLogin);
        
        // Panel inferior con información
        JPanel panelInfo = new JPanel();
        panelInfo.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel lblInfo = new JLabel("Universidad Católica del Mish - ITI ICCI");
        lblInfo.setFont(new Font("Arial", Font.ITALIC, 10));
        panelInfo.add(lblInfo);
        
        panelLogin.add(panelTitulo, BorderLayout.NORTH);
        panelLogin.add(panelForm, BorderLayout.CENTER);
        panelLogin.add(panelInfo, BorderLayout.SOUTH);
        
        // Acción del botón login
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                autenticarUsuario();
            }
        });
    }
    
    private void autenticarUsuario() {
        String tipoUsuario = (String) comboTipoUsuario.getSelectedItem();
        String usuario = txtUsuario.getText();
        String contraseña = new String(txtContraseña.getPassword());
        
        Sistema sistema = Main.getSistema();
        
        if (usuario.isEmpty() || contraseña.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor complete todos los campos", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        boolean autenticado = false;
        String rol = "";
        
        switch (tipoUsuario) {
            case "Estudiante":
                for (Estudiante estudiante : sistema.getEstudiantes()) {
                    if (estudiante.getRut().equals(usuario) && estudiante.getContraseña().equals(contraseña)) {
                        autenticado = true;
                        rol = "Estudiante";
                        break;
                    }
                }
                break;
                
            case "Coordinador":
                for (Usuario user : sistema.getUsuarios()) {
                    if (user.getNombreUsuario().equals(usuario) && 
                        user.getContraseña().equals(contraseña) && 
                        user.getRol().equals("Coordinador")) {
                        autenticado = true;
                        rol = "Coordinador";
                        break;
                    }
                }
                break;
                
            case "Administrador":
                for (Usuario user : sistema.getUsuarios()) {
                    if (user.getNombreUsuario().equals(usuario) && 
                        user.getContraseña().equals(contraseña) && 
                        user.getRol().equals("Admin")) {
                        autenticado = true;
                        rol = "Administrador";
                        break;
                    }
                }
                break;
        }
        
        if (autenticado) {
            JOptionPane.showMessageDialog(this, "¡Bienvenido " + usuario + "!", 
                    "Login Exitoso", JOptionPane.INFORMATION_MESSAGE);
            
            // Abrir el menú correspondiente
            abrirMenu(rol, usuario);
            
        } else {
            JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos", 
                    "Error de Autenticación", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void abrirMenu(String rol, String usuario) {
        this.dispose(); // Cerrar ventana de login
        
        switch (rol) {
            case "Estudiante":
                new MenuEstudianteFrame(usuario).setVisible(true);
                break;
            case "Coordinador":
                new MenuCoordinadorFrame(usuario).setVisible(true);
                break;
            case "Administrador":
                new MenuAdminFrame().setVisible(true);
                break;
        }
    }
}