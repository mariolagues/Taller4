package Taller_4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuAdminFrame extends JFrame {
    private JTabbedPane tabbedPane;
    private JPanel panelUsuarios;
    private JTable tablaUsuarios;
    
    public MenuAdminFrame() {
        setTitle("AcademicCore - Menú Administrador");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        initComponents();
    }
    
    private void initComponents() {
        // Crear barra de menú
        JMenuBar menuBar = new JMenuBar();
        
        JMenu menuArchivo = new JMenu("Archivo");
        JMenuItem itemSalir = new JMenuItem("Salir");
        itemSalir.addActionListener(e -> System.exit(0));
        menuArchivo.add(itemSalir);
        
        JMenu menuAyuda = new JMenu("Ayuda");
        JMenuItem itemAcercaDe = new JMenuItem("Acerca de");
        itemAcercaDe.addActionListener(e -> 
            JOptionPane.showMessageDialog(this, "AcademicCore v1.0\nSistema de Gestión de Certificaciones\nITI ICCI 2025"));
        menuAyuda.add(itemAcercaDe);
        
        menuBar.add(menuArchivo);
        menuBar.add(menuAyuda);
        setJMenuBar(menuBar);
        
        // Crear panel principal con pestañas
        tabbedPane = new JTabbedPane();
        
        // Pestaña 1: Gestión de Usuarios
        panelUsuarios = crearPanelGestionUsuarios();
        tabbedPane.addTab("Gestión de Usuarios", panelUsuarios);
        
        // Pestaña 2: Reportes
        JPanel panelReportes = crearPanelReportes();
        tabbedPane.addTab("Reportes", panelReportes);
        
        // Pestaña 3: Configuración
        JPanel panelConfig = crearPanelConfiguracion();
        tabbedPane.addTab("Configuración", panelConfig);
        
        add(tabbedPane);
    }
    
    private JPanel crearPanelGestionUsuarios() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Panel superior con botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        JButton btnNuevoEstudiante = new JButton("Nuevo Estudiante");
        JButton btnNuevoCoordinador = new JButton("Nuevo Coordinador");
        JButton btnModificar = new JButton("Modificar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnRestablecer = new JButton("Restablecer Contraseña");
        
        // Estilo de botones
        Color colorBoton = new Color(0, 102, 204);
        btnNuevoEstudiante.setBackground(colorBoton);
        btnNuevoEstudiante.setForeground(Color.WHITE);
        btnNuevoCoordinador.setBackground(colorBoton);
        btnNuevoCoordinador.setForeground(Color.WHITE);
        
        panelBotones.add(btnNuevoEstudiante);
        panelBotones.add(btnNuevoCoordinador);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnRestablecer);
        
        // Tabla de usuarios
        String[] columnas = {"Usuario", "Rol", "Área", "Estado"};
        Object[][] datos = cargarDatosUsuarios();
        
        tablaUsuarios = new JTable(datos, columnas);
        JScrollPane scrollPane = new JScrollPane(tabbaUsuarios);
        
        // Panel inferior con estadísticas
        JPanel panelStats = new JPanel(new GridLayout(1, 3, 10, 0));
        
        JLabel lblTotalEstudiantes = new JLabel("Total Estudiantes: " + contarEstudiantes());
        lblTotalEstudiantes.setHorizontalAlignment(SwingConstants.CENTER);
        lblTotalEstudiantes.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        
        JLabel lblTotalCoordinadores = new JLabel("Total Coordinadores: " + contarCoordinadores());
        lblTotalCoordinadores.setHorizontalAlignment(SwingConstants.CENTER);
        lblTotalCoordinadores.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        
        JLabel lblTotalAdmins = new JLabel("Total Administradores: " + contarAdministradores());
        lblTotalAdmins.setHorizontalAlignment(SwingConstants.CENTER);
        lblTotalAdmins.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        
        panelStats.add(lblTotalEstudiantes);
        panelStats.add(lblTotalCoordinadores);
        panelStats.add(lblTotalAdmins);
        
        panel.add(panelBotones, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(panelStats, BorderLayout.SOUTH);
        
        // Acciones de botones
        btnNuevoEstudiante.addActionListener(e -> mostrarDialogoNuevoEstudiante());
        btnNuevoCoordinador.addActionListener(e -> mostrarDialogoNuevoCoordinador());
        
        return panel;
    }
    
    private Object[][] cargarDatosUsuarios() {
        Sistema sistema = Main.getSistema();
        int totalUsuarios = sistema.getUsuarios().size() + sistema.getEstudiantes().size();
        Object[][] datos = new Object[totalUsuarios][4];
        
        int i = 0;
        // Agregar usuarios del sistema
        for (Usuario usuario : sistema.getUsuarios()) {
            datos[i][0] = usuario.getNombreUsuario();
            datos[i][1] = usuario.getRol();
            datos[i][2] = usuario.getArea();
            datos[i][3] = "Activo";
            i++;
        }
        
        // Agregar estudiantes
        for (Estudiante estudiante : sistema.getEstudiantes()) {
            datos[i][0] = estudiante.getRut();
            datos[i][1] = "Estudiante";
            datos[i][2] = estudiante.getCarrera();
            datos[i][3] = "Activo";
            i++;
        }
        
        return datos;
    }
    
    private int contarEstudiantes() {
        return Main.getSistema().getEstudiantes().size();
    }
    
    private int contarCoordinadores() {
        int count = 0;
        for (Usuario usuario : Main.getSistema().getUsuarios()) {
            if (usuario.getRol().equals("Coordinador")) {
                count++;
            }
        }
        return count;
    }
    
    private int contarAdministradores() {
        int count = 0;
        for (Usuario usuario : Main.getSistema().getUsuarios()) {
            if (usuario.getRol().equals("Admin")) {
                count++;
            }
        }
        return count;
    }
    
    private void mostrarDialogoNuevoEstudiante() {
        JDialog dialog = new JDialog(this, "Nuevo Estudiante", true);
        dialog.setSize(400, 400);
        dialog.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new GridLayout(7, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        panel.add(new JLabel("RUT:"));
        JTextField txtRut = new JTextField();
        panel.add(txtRut);
        
        panel.add(new JLabel("Nombre:"));
        JTextField txtNombre = new JTextField();
        panel.add(txtNombre);
        
        panel.add(new JLabel("Carrera:"));
        JTextField txtCarrera = new JTextField();
        panel.add(txtCarrera);
        
        panel.add(new JLabel("Semestre:"));
        JSpinner spinnerSemestre = new JSpinner(new SpinnerNumberModel(1, 1, 12, 1));
        panel.add(spinnerSemestre);
        
        panel.add(new JLabel("Correo:"));
        JTextField txtCorreo = new JTextField();
        panel.add(txtCorreo);
        
        panel.add(new JLabel("Contraseña:"));
        JPasswordField txtPass = new JPasswordField();
        panel.add(txtPass);
        
        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");
        
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        
        dialog.setLayout(new BorderLayout());
        dialog.add(panel, BorderLayout.CENTER);
        dialog.add(panelBotones, BorderLayout.SOUTH);
        
        btnGuardar.addActionListener(e -> {
            // Aquí guardarías el nuevo estudiante
            JOptionPane.showMessageDialog(dialog, "Estudiante creado exitosamente");
            dialog.dispose();
        });
        
        btnCancelar.addActionListener(e -> dialog.dispose());
        
        dialog.setVisible(true);
    }
    
    private void mostrarDialogoNuevoCoordinador() {
        JDialog dialog = new JDialog(this, "Nuevo Coordinador", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        panel.add(new JLabel("Usuario:"));
        JTextField txtUsuario = new JTextField();
        panel.add(txtUsuario);
        
        panel.add(new JLabel("Contraseña:"));
        JPasswordField txtPass = new JPasswordField();
        panel.add(txtPass);
        
        panel.add(new JLabel("Área:"));
        JComboBox<String> comboArea = new JComboBox<>(new String[]{
            "Sistemas Inteligentes", "Ciberseguridad", "Desarrollo de Software"
        });
        panel.add(comboArea);
        
        panel.add(new JLabel("Confirmar Contraseña:"));
        JPasswordField txtPassConfirm = new JPasswordField();
        panel.add(txtPassConfirm);
        
        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");
        
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        
        dialog.setLayout(new BorderLayout());
        dialog.add(panel, BorderLayout.CENTER);
        dialog.add(panelBotones, BorderLayout.SOUTH);
        
        btnGuardar.addActionListener(e -> {
            String pass1 = new String(txtPass.getPassword());
            String pass2 = new String(txtPassConfirm.getPassword());
            
            if (!pass1.equals(pass2)) {
                JOptionPane.showMessageDialog(dialog, "Las contraseñas no coinciden", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Aquí guardarías el nuevo coordinador
            JOptionPane.showMessageDialog(dialog, "Coordinador creado exitosamente");
            dialog.dispose();
        });
        
        btnCancelar.addActionListener(e -> dialog.dispose());
        
        dialog.setVisible(true);
    }
    
    private JPanel crearPanelReportes() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel lblTitulo = new JLabel("Reportes del Sistema", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        
        JTextArea txtReporte = new JTextArea();
        txtReporte.setEditable(false);
        txtReporte.setText(generarReporteSistema());
        
        JScrollPane scrollPane = new JScrollPane(txtReporte);
        
        JButton btnGenerarReporte = new JButton("Generar Nuevo Reporte");
        btnGenerarReporte.addActionListener(e -> txtReporte.setText(generarReporteSistema()));
        
        panel.add(lblTitulo, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(btnGenerarReporte, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private String generarReporteSistema() {
        Sistema sistema = Main.getSistema();
        StringBuilder reporte = new StringBuilder();
        
        reporte.append("=== REPORTE DEL SISTEMA ===\n\n");
        reporte.append("ESTADÍSTICAS GENERALES:\n");
        reporte.append("Total Estudiantes: ").append(sistema.getEstudiantes().size()).append("\n");
        reporte.append("Total Cursos: ").append(sistema.getCursos().size()).append("\n");
        reporte.append("Total Certificaciones: ").append(sistema.getCertificaciones().size()).append("\n");
        reporte.append("Total Registros Activos: ").append(sistema.getRegistros().size()).append("\n\n");
        
        reporte.append("CERTIFICACIONES DISPONIBLES:\n");
        for (Certificacion cert : sistema.getCertificaciones()) {
            reporte.append("- ").append(cert.getNombre())
                   .append(" (").append(cert.getId()).append(")\n");
        }
        
        return reporte.toString();
    }
    
    private JPanel crearPanelConfiguracion() {
        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JButton btnBackup = new JButton("Realizar Backup de Datos");
        JButton btnRestore = new JButton("Restaurar Datos");
        JButton btnLimpiarLogs = new JButton("Limpiar Logs del Sistema");
        JButton btnConfigSistema = new JButton("Configuración del Sistema");
        JButton btnExportarDatos = new JButton("Exportar Datos a Excel");
        
        panel.add(btnBackup);
        panel.add(btnRestore);
        panel.add(btnLimpiarLogs);
        panel.add(btnConfigSistema);
        panel.add(btnExportarDatos);
        
        return panel;
    }
}