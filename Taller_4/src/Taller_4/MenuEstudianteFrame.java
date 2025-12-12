package Taller_4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class MenuEstudianteFrame extends JFrame {
    private String rutEstudiante;
    private Estudiante estudiante;
    private JTabbedPane tabbedPane;
    
    public MenuEstudianteFrame(String rut) {
        this.rutEstudiante = rut;
        this.estudiante = buscarEstudiante(rut);
        
        setTitle("AcademicCore - Menú Estudiante: " + (estudiante != null ? estudiante.getNombre() : ""));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 750);
        setLocationRelativeTo(null);
        
        initComponents();
    }
    
    private Estudiante buscarEstudiante(String rut) {
        for (Estudiante est : Main.getSistema().getEstudiantes()) {
            if (est.getRut().equals(rut)) {
                return est;
            }
        }
        return null;
    }
    
    private void initComponents() {
        // Barra de menú
        JMenuBar menuBar = new JMenuBar();
        
        JMenu menuPerfil = new JMenu("Perfil");
        JMenuItem itemCerrarSesion = new JMenuItem("Cerrar Sesión");
        itemCerrarSesion.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            this.dispose();
        });
        menuPerfil.add(itemCerrarSesion);
        
        JMenu menuAyuda = new JMenu("Ayuda");
        JMenuItem itemAcercaDe = new JMenuItem("Acerca de");
        itemAcercaDe.addActionListener(e -> 
            JOptionPane.showMessageDialog(this, "AcademicCore v1.0\nSistema de Gestión de Certificaciones\nITI ICCI 2025"));
        menuAyuda.add(itemAcercaDe);
        
        menuBar.add(menuPerfil);
        menuBar.add(menuAyuda);
        setJMenuBar(menuBar);
        
        // Panel de bienvenida
        JPanel panelBienvenida = new JPanel(new BorderLayout());
        panelBienvenida.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelBienvenida.setBackground(new Color(240, 240, 255));
        
        JLabel lblBienvenida = new JLabel("Bienvenido, " + estudiante.getNombre());
        lblBienvenida.setFont(new Font("Arial", Font.BOLD, 18));
        lblBienvenida.setForeground(new Color(0, 102, 204));
        
        JLabel lblInfo = new JLabel("Carrera: " + estudiante.getCarrera() + 
                                   " | Semestre: " + estudiante.getSemestre() + 
                                   " | RUT: " + estudiante.getRut());
        
        panelBienvenida.add(lblBienvenida, BorderLayout.NORTH);
        panelBienvenida.add(lblInfo, BorderLayout.CENTER);
        
        // Panel principal con pestañas
        tabbedPane = new JTabbedPane();
        
        // Pestaña 1: Perfil Completo
        tabbedPane.addTab("📊 Mi Perfil", crearPanelPerfil());
        
        // Pestaña 2: Malla Curricular
        tabbedPane.addTab("📚 Malla Curricular", crearPanelMalla());
        
        // Pestaña 3: Certificaciones
        tabbedPane.addTab("🏆 Certificaciones", crearPanelCertificaciones());
        
        // Pestaña 4: Progreso
        tabbedPane.addTab("📈 Mi Progreso", crearPanelProgreso());
        
        setLayout(new BorderLayout());
        add(panelBienvenida, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);
    }
    
    private JPanel crearPanelPerfil() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Panel izquierdo con información personal
        JPanel panelInfo = new JPanel(new GridLayout(6, 2, 10, 10));
        panelInfo.setBorder(BorderFactory.createTitledBorder("Información Personal"));
        
        panelInfo.add(new JLabel("RUT:"));
        JLabel lblRut = new JLabel(estudiante.getRut());
        lblRut.setFont(new Font("Arial", Font.BOLD, 12));
        panelInfo.add(lblRut);
        
        panelInfo.add(new JLabel("Nombre:"));
        JLabel lblNombre = new JLabel(estudiante.getNombre());
        lblNombre.setFont(new Font("Arial", Font.BOLD, 12));
        panelInfo.add(lblNombre);
        
        panelInfo.add(new JLabel("Carrera:"));
        panelInfo.add(new JLabel(estudiante.getCarrera()));
        
        panelInfo.add(new JLabel("Semestre Actual:"));
        JLabel lblSemestre = new JLabel(String.valueOf(estudiante.getSemestre()));
        lblSemestre.setFont(new Font("Arial", Font.BOLD, 12));
        panelInfo.add(lblSemestre);
        
        panelInfo.add(new JLabel("Correo:"));
        panelInfo.add(new JLabel(estudiante.getCorreo()));
        
        panelInfo.add(new JLabel("Promedio General:"));
        String promedio = calcularPromedioGeneral();
        JLabel lblPromedio = new JLabel(promedio);
        lblPromedio.setFont(new Font("Arial", Font.BOLD, 12));
        lblPromedio.setForeground(new Color(0, 150, 0));
        panelInfo.add(lblPromedio);
        
        // Panel derecho con estadísticas
        JPanel panelStats = new JPanel(new GridLayout(4, 1, 10, 10));
        panelStats.setBorder(BorderFactory.createTitledBorder("Estadísticas Académicas"));
        
        int aprobadas = contarCursosAprobados();
        int cursando = contarCursosCursando();
        int total = Main.getSistema().getCursos().size();
        
        JLabel lblAprobadas = new JLabel("Cursos Aprobados: " + aprobadas);
        JLabel lblCursando = new JLabel("Cursos Cursando: " + cursando);
        JLabel lblRestantes = new JLabel("Cursos Restantes: " + (total - aprobadas - cursando));
        JLabel lblProgreso = new JLabel("Progreso Carrera: " + calcularPorcentajeCarrera() + "%");
        
        lblProgreso.setFont(new Font("Arial", Font.BOLD, 12));
        lblProgreso.setForeground(new Color(0, 102, 204));
        
        panelStats.add(lblAprobadas);
        panelStats.add(lblCursando);
        panelStats.add(lblRestantes);
        panelStats.add(lblProgreso);
        
        JPanel panelCentral = new JPanel(new GridLayout(1, 2, 20, 0));
        panelCentral.add(panelInfo);
        panelCentral.add(panelStats);
        
        // Panel inferior con botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        JButton btnCambiarPass = new JButton("Cambiar Contraseña");
        btnCambiarPass.setBackground(new Color(0, 102, 204));
        btnCambiarPass.setForeground(Color.WHITE);
        btnCambiarPass.addActionListener(e -> mostrarDialogoCambiarContraseña());
        
        JButton btnVerHistorial = new JButton("Ver Historial Académico");
        btnVerHistorial.setBackground(new Color(0, 102, 204));
        btnVerHistorial.setForeground(Color.WHITE);
        btnVerHistorial.addActionListener(e -> mostrarHistorialAcademico());
        
        panelBotones.add(btnCambiarPass);
        panelBotones.add(btnVerHistorial);
        
        panel.add(panelCentral, BorderLayout.CENTER);
        panel.add(panelBotones, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private void mostrarHistorialAcademico() {
        JDialog dialog = new JDialog(this, "Historial Académico", true);
        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new BorderLayout());
        
        String[] columnas = {"Código", "Asignatura", "Semestre", "Nota", "Estado", "Créditos"};
        Object[][] datos = obtenerDatosHistorial();
        
        JTable tabla = new JTable(datos, columnas);
        JScrollPane scrollPane = new JScrollPane(tabla);
        
        // Resaltar filas según estado
        tabla.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, 
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                String estado = (String) table.getValueAt(row, 4);
                if (estado.equals("Aprobada")) {
                    c.setBackground(new Color(220, 255, 220));
                } else if (estado.equals("Reprobada")) {
                    c.setBackground(new Color(255, 220, 220));
                } else if (estado.equals("Cursando")) {
                    c.setBackground(new Color(220, 220, 255));
                } else {
                    c.setBackground(Color.WHITE);
                }
                
                return c;
            }
        });
        
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dialog.dispose());
        
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(btnCerrar, BorderLayout.SOUTH);
        
        dialog.add(panel);
        dialog.setVisible(true);
    }
    
    private Object[][] obtenerDatosHistorial() {
        List<Nota> notasEstudiante = new ArrayList<>();
        Sistema sistema = Main.getSistema();
        
        for (Nota nota : sistema.getNotas()) {
            if (nota.getRut().equals(rutEstudiante)) {
                notasEstudiante.add(nota);
            }
        }
        
        Object[][] datos = new Object[notasEstudiante.size()][6];
        
        for (int i = 0; i < notasEstudiante.size(); i++) {
            Nota nota = notasEstudiante.get(i);
            datos[i][0] = nota.getCodigoCurso();
            datos[i][1] = obtenerNombreCurso(nota.getCodigoCurso());
            datos[i][2] = nota.getSemestre();
            datos[i][3] = nota.getCalificacion() > 0 ? String.format("%.1f", nota.getCalificacion()) : "-";
            datos[i][4] = nota.getEstado();
            datos[i][5] = obtenerCreditosCurso(nota.getCodigoCurso());
        }
        
        return datos;
    }
    
    private String obtenerNombreCurso(String codigo) {
        for (Curso curso : Main.getSistema().getCursos()) {
            if (curso.getNrc().equals(codigo)) {
                return curso.getNombre();
            }
        }
        return "Desconocido";
    }
    
    private int obtenerCreditosCurso(String codigo) {
        for (Curso curso : Main.getSistema().getCursos()) {
            if (curso.getNrc().equals(codigo)) {
                return curso.getCreditos();
            }
        }
        return 0;
    }
    
    private String calcularPromedioGeneral() {
        double suma = 0;
        int count = 0;
        
        for (Nota nota : Main.getSistema().getNotas()) {
            if (nota.getRut().equals(rutEstudiante) && nota.getEstado().equals("Aprobada")) {
                suma += nota.getCalificacion();
                count++;
            }
        }
        
        return count > 0 ? String.format("%.2f", suma / count) : "0.00";
    }
    
    private int contarCursosAprobados() {
        int count = 0;
        for (Nota nota : Main.getSistema().getNotas()) {
            if (nota.getRut().equals(rutEstudiante) && nota.getEstado().equals("Aprobada")) {
                count++;
            }
        }
        return count;
    }
    
    private int contarCursosCursando() {
        int count = 0;
        for (Nota nota : Main.getSistema().getNotas()) {
            if (nota.getRut().equals(rutEstudiante) && nota.getEstado().equals("Cursando")) {
                count++;
            }
        }
        return count;
    }
    
    private int calcularPorcentajeCarrera() {
        int totalCursos = Main.getSistema().getCursos().size();
        int cursosAprobados = contarCursosAprobados();
        
        if (totalCursos == 0) return 0;
        return (cursosAprobados * 100) / totalCursos;
    }
    
    private void mostrarDialogoCambiarContraseña() {
        JDialog dialog = new JDialog(this, "Cambiar Contraseña", true);
        dialog.setSize(350, 250);
        dialog.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        panel.add(new JLabel("Contraseña Actual:"));
        JPasswordField txtPassActual = new JPasswordField();
        panel.add(txtPassActual);
        
        panel.add(new JLabel("Nueva Contraseña:"));
        JPasswordField txtPassNueva = new JPasswordField();
        panel.add(txtPassNueva);
        
        panel.add(new JLabel("Confirmar Nueva:"));
        JPasswordField txtPassConfirm = new JPasswordField();
        panel.add(txtPassConfirm);
        
        JButton btnCambiar = new JButton("Cambiar");
        btnCambiar.setBackground(new Color(0, 102, 204));
        btnCambiar.setForeground(Color.WHITE);
        
        JButton btnCancelar = new JButton("Cancelar");
        
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotones.add(btnCambiar);
        panelBotones.add(btnCancelar);
        
        dialog.setLayout(new BorderLayout());
        dialog.add(panel, BorderLayout.CENTER);
        dialog.add(panelBotones, BorderLayout.SOUTH);
        
        btnCambiar.addActionListener(e -> {
            String passActual = new String(txtPassActual.getPassword());
            String passNueva = new String(txtPassNueva.getPassword());
            String passConfirm = new String(txtPassConfirm.getPassword());
            
            if (!passActual.equals(estudiante.getContraseña())) {
                JOptionPane.showMessageDialog(dialog, "Contraseña actual incorrecta", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (!passNueva.equals(passConfirm)) {
                JOptionPane.showMessageDialog(dialog, "Las nuevas contraseñas no coinciden", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (passNueva.length() < 6) {
                JOptionPane.showMessageDialog(dialog, "La contraseña debe tener al menos 6 caracteres", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            estudiante.setContraseña(passNueva);
            JOptionPane.showMessageDialog(dialog, "Contraseña cambiada exitosamente");
            dialog.dispose();
        });
        
        btnCancelar.addActionListener(e -> dialog.dispose());
        
        dialog.setVisible(true);
    }
    
    private JPanel crearPanelMalla() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Panel superior con filtros
        JPanel panelFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        JLabel lblFiltrar = new JLabel("Filtrar por semestre:");
        JComboBox<String> comboFiltro = new JComboBox<>(new String[]{"Todos", "1", "2", "3", "4", "5", "6", "7", "8"});
        
        JButton btnVerMallaGrafica = new JButton("Ver Malla Gráfica");
        btnVerMallaGrafica.setBackground(new Color(0, 102, 204));
        btnVerMallaGrafica.setForeground(Color.WHITE);
        btnVerMallaGrafica.addActionListener(e -> mostrarMallaGrafica());
        
        panelFiltros.add(lblFiltrar);
        panelFiltros.add(comboFiltro);
        panelFiltros.add(btnVerMallaGrafica);
        
        // Crear tabla de cursos por semestre
        String[] columnas = {"Semestre", "Código", "Asignatura", "Créditos", "Área", "Estado", "Nota"};
        Object[][] datos = cargarDatosMalla();
        
        JTable tablaMalla = new JTable(datos, columnas) {
            @Override
            public Class<?> getColumnClass(int column) {
                return getValueAt(0, column).getClass();
            }
        };
        
        // Configurar renderer para colorear según estado
        tablaMalla.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, 
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                String estado = (String) table.getValueAt(row, 5);
                if (estado.equals("Aprobada")) {
                    c.setBackground(new Color(220, 255, 220)); // Verde claro
                } else if (estado.equals("Reprobada")) {
                    c.setBackground(new Color(255, 220, 220)); // Rojo claro
                } else if (estado.equals("Cursando")) {
                    c.setBackground(new Color(220, 220, 255)); // Azul claro
                } else if (estado.equals("Pendiente")) {
                    c.setBackground(Color.WHITE);
                } else {
                    c.setBackground(Color.LIGHT_GRAY);
                }
                
                // Resaltar asignaturas del área de certificación
                String area = (String) table.getValueAt(row, 4);
                if (area.equals("Desarrollo de Software") || area.equals("Sistemas Inteligentes") || 
                    area.equals("Ciberseguridad")) {
                    c.setFont(c.getFont().deriveFont(Font.BOLD));
                }
                
                return c;
            }
        });
        
        tablaMalla.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(tablaMalla);
        
        // Panel inferior con estadísticas
        JPanel panelStats = new JPanel(new GridLayout(1, 4, 10, 0));
        
        int[] estadisticas = calcularEstadisticasMalla();
        
        JLabel lblAprobadas = new JLabel("Aprobadas: " + estadisticas[0], SwingConstants.CENTER);
        lblAprobadas.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        lblAprobadas.setOpaque(true);
        lblAprobadas.setBackground(new Color(220, 255, 220));
        
        JLabel lblCursando = new JLabel("Cursando: " + estadisticas[1], SwingConstants.CENTER);
        lblCursando.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        lblCursando.setOpaque(true);
        lblCursando.setBackground(new Color(220, 220, 255));
        
        JLabel lblReprobadas = new JLabel("Reprobadas: " + estadisticas[2], SwingConstants.CENTER);
        lblReprobadas.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        lblReprobadas.setOpaque(true);
        lblReprobadas.setBackground(new Color(255, 220, 220));
        
        JLabel lblPendientes = new JLabel("Pendientes: " + estadisticas[3], SwingConstants.CENTER);
        lblPendientes.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        lblPendientes.setOpaque(true);
        lblPendientes.setBackground(Color.LIGHT_GRAY);
        
        panelStats.add(lblAprobadas);
        panelStats.add(lblCursando);
        panelStats.add(lblReprobadas);
        panelStats.add(lblPendientes);
        
        // Agregar listener al combo para filtrar
        comboFiltro.addActionListener(e -> {
            String filtro = (String) comboFiltro.getSelectedItem();
            filtrarTablaPorSemestre(tablaMalla, filtro);
        });
        
        panel.add(panelFiltros, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(panelStats, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private void filtrarTablaPorSemestre(JTable tabla, String filtro) {
        if (filtro.equals("Todos")) {
            // Mostrar todos los cursos
            for (int i = 0; i < tabla.getRowCount(); i++) {
                tabla.setRowHeight(i, 25);
            }
        } else {
            int semestreFiltro = Integer.parseInt(filtro);
            for (int i = 0; i < tabla.getRowCount(); i++) {
                int semestre = (int) tabla.getValueAt(i, 0);
                if (semestre == semestreFiltro) {
                    tabla.setRowHeight(i, 25);
                } else {
                    tabla.setRowHeight(i, 0);
                }
            }
        }
    }
    
    private Object[][] cargarDatosMalla() {
        List<Curso> todosCursos = Main.getSistema().getCursos();
        List<Nota> notasEstudiante = new ArrayList<>();
        
        // Obtener notas del estudiante
        for (Nota nota : Main.getSistema().getNotas()) {
            if (nota.getRut().equals(rutEstudiante)) {
                notasEstudiante.add(nota);
            }
        }
        
        // Crear matriz de datos
        Object[][] datos = new Object[todosCursos.size()][7];
        
        for (int i = 0; i < todosCursos.size(); i++) {
            Curso curso = todosCursos.get(i);
            datos[i][0] = curso.getSemestre();
            datos[i][1] = curso.getNrc();
            datos[i][2] = curso.getNombre();
            datos[i][3] = curso.getCreditos();
            datos[i][4] = curso.getArea();
            
            // Buscar estado y nota del estudiante para este curso
            String estado = "Pendiente";
            double nota = 0.0;
            
            for (Nota n : notasEstudiante) {
                if (n.getCodigoCurso().equals(curso.getNrc())) {
                    estado = n.getEstado();
                    nota = n.getCalificacion();
                    break;
                }
            }
            
            datos[i][5] = estado;
            datos[i][6] = estado.equals("Aprobada") || estado.equals("Reprobada") ? 
                          String.format("%.1f", nota) : "-";
        }
        
        return datos;
    }
    
    private int[] calcularEstadisticasMalla() {
        int[] stats = new int[4]; // [aprobadas, cursando, reprobadas, pendientes]
        Object[][] datos = cargarDatosMalla();
        
        for (Object[] fila : datos) {
            String estado = (String) fila[5];
            switch (estado) {
                case "Aprobada": stats[0]++; break;
                case "Cursando": stats[1]++; break;
                case "Reprobada": stats[2]++; break;
                case "Pendiente": stats[3]++; break;
            }
        }
        
        return stats;
    }
    
    private void mostrarMallaGrafica() {
        JDialog dialog = new JDialog(this, "Malla Curricular Gráfica", true);
        dialog.setSize(800, 600);
        dialog.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel panelMalla = new JPanel(new GridLayout(8, 6, 5, 5));
        
        // Obtener cursos organizados por semestre
        List<List<Curso>> cursosPorSemestre = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            cursosPorSemestre.add(new ArrayList<>());
        }
        
        for (Curso curso : Main.getSistema().getCursos()) {
            int semestre = curso.getSemestre() - 1;
            if (semestre >= 0 && semestre < 8) {
                cursosPorSemestre.get(semestre).add(curso);
            }
        }
        
        // Crear botones para cada curso
        for (int sem = 0; sem < 8; sem++) {
            JLabel lblSemestre = new JLabel("Semestre " + (sem + 1), SwingConstants.CENTER);
            lblSemestre.setBorder(BorderFactory.createLineBorder(Color.BLUE));
            lblSemestre.setOpaque(true);
            lblSemestre.setBackground(new Color(200, 220, 255));
            panelMalla.add(lblSemestre);
            
            List<Curso> cursosSemestre = cursosPorSemestre.get(sem);
            for (int i = 0; i < 5; i++) {
                if (i < cursosSemestre.size()) {
                    Curso curso = cursosSemestre.get(i);
                    JButton btnCurso = new JButton("<html><center>" + curso.getNrc() + 
                                                  "<br>" + curso.getNombre().substring(0, Math.min(15, curso.getNombre().length())) + 
                                                  "...</center></html>");
                    
                    // Determinar color según estado
                    String estado = obtenerEstadoCurso(curso.getNrc());
                    Color color = Color.WHITE;
                    
                    if (estado.equals("Aprobada")) {
                        color = new Color(200, 255, 200);
                    } else if (estado.equals("Cursando")) {
                        color = new Color(200, 200, 255);
                    } else if (estado.equals("Reprobada")) {
                        color = new Color(255, 200, 200);
                    }
                    
                    btnCurso.setBackground(color);
                    btnCurso.addActionListener(e -> mostrarDetalleCurso(curso));
                    panelMalla.add(btnCurso);
                } else {
                    panelMalla.add(new JLabel());
                }
            }
        }
        
        JScrollPane scrollPane = new JScrollPane(panelMalla);
        
        // Panel de leyenda
        JPanel panelLeyenda = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        JLabel lblAprobada = new JLabel("Aprobada");
        lblAprobada.setOpaque(true);
        lblAprobada.setBackground(new Color(200, 255, 200));
        lblAprobada.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        lblAprobada.setPreferredSize(new Dimension(80, 20));
        
        JLabel lblCursando = new JLabel("Cursando");
        lblCursando.setOpaque(true);
        lblCursando.setBackground(new Color(200, 200, 255));
        lblCursando.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        lblCursando.setPreferredSize(new Dimension(80, 20));
        
        JLabel lblReprobada = new JLabel("Reprobada");
        lblReprobada.setOpaque(true);
        lblReprobada.setBackground(new Color(255, 200, 200));
        lblReprobada.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        lblReprobada.setPreferredSize(new Dimension(80, 20));
        
        JLabel lblPendiente = new JLabel("Pendiente");
        lblPendiente.setOpaque(true);
        lblPendiente.setBackground(Color.WHITE);
        lblPendiente.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        lblPendiente.setPreferredSize(new Dimension(80, 20));
        
        panelLeyenda.add(new JLabel("Leyenda: "));
        panelLeyenda.add(lblAprobada);
        panelLeyenda.add(lblCursando);
        panelLeyenda.add(lblReprobada);
        panelLeyenda.add(lblPendiente);
        
        panel.add(new JLabel("Malla Curricular Visual", SwingConstants.CENTER), BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(panelLeyenda, BorderLayout.SOUTH);
        
        dialog.add(panel);
        dialog.setVisible(true);
    }
    
    private String obtenerEstadoCurso(String codigoCurso) {
        for (Nota nota : Main.getSistema().getNotas()) {
            if (nota.getRut().equals(rutEstudiante) && nota.getCodigoCurso().equals(codigoCurso)) {
                return nota.getEstado();
            }
        }
        return "Pendiente";
    }
    
    private void mostrarDetalleCurso(Curso curso) {
        JDialog dialog = new JDialog(this, "Detalle del Curso", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new GridLayout(8, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Obtener estado y nota del estudiante
        String estado = "Pendiente";
        double nota = 0.0;
        String semestreCursado = "No cursado";
        
        for (Nota n : Main.getSistema().getNotas()) {
            if (n.getRut().equals(rutEstudiante) && n.getCodigoCurso().equals(curso.getNrc())) {
                estado = n.getEstado();
                nota = n.getCalificacion();
                semestreCursado = n.getSemestre();
                break;
            }
        }
        
        panel.add(new JLabel("Código:"));
        panel.add(new JLabel(curso.getNrc()));
        
        panel.add(new JLabel("Nombre:"));
        panel.add(new JLabel(curso.getNombre()));
        
        panel.add(new JLabel("Semestre:"));
        panel.add(new JLabel(String.valueOf(curso.getSemestre())));
        
        panel.add(new JLabel("Créditos:"));
        panel.add(new JLabel(String.valueOf(curso.getCreditos())));
        
        panel.add(new JLabel("Área:"));
        panel.add(new JLabel(curso.getArea()));
        
        panel.add(new JLabel("Estado:"));
        JLabel lblEstado = new JLabel(estado);
        if (estado.equals("Aprobada")) {
            lblEstado.setForeground(Color.GREEN.darker());
        } else if (estado.equals("Reprobada")) {
            lblEstado.setForeground(Color.RED);
        } else if (estado.equals("Cursando")) {
            lblEstado.setForeground(Color.BLUE);
        }
        panel.add(lblEstado);
        
        panel.add(new JLabel("Nota:"));
        panel.add(new JLabel(estado.equals("Pendiente") ? "-" : String.format("%.1f", nota)));
        
        panel.add(new JLabel("Semestre Cursado:"));
        panel.add(new JLabel(semestreCursado));
        
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dialog.dispose());
        
        dialog.setLayout(new BorderLayout());
        dialog.add(panel, BorderLayout.CENTER);
        dialog.add(btnCerrar, BorderLayout.SOUTH);
        
        dialog.setVisible(true);
    }
    
    private JPanel crearPanelCertificaciones() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Panel superior con botones
        JPanel panelSuperior = new JPanel(new BorderLayout());
        
        JLabel lblTitulo = new JLabel("Certificaciones Disponibles", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnInscribir = new JButton("Inscribirse en Certificación");
        btnInscribir.setBackground(new Color(0, 102, 204));
        btnInscribir.setForeground(Color.WHITE);
        btnInscribir.addActionListener(e -> mostrarDialogoInscripcion());
        
        panelBotones.add(btnInscribir);
        panelSuperior.add(lblTitulo, BorderLayout.CENTER);
        panelSuperior.add(panelBotones, BorderLayout.EAST);
        
        // Panel con certificaciones
        JPanel panelCertificaciones = new JPanel();
        panelCertificaciones.setLayout(new BoxLayout(panelCertificaciones, BoxLayout.Y_AXIS));
        
        List<Certificacion> certificaciones = Main.getSistema().getCertificaciones();
        
        for (Certificacion cert : certificaciones) {
            JPanel panelCert = crearPanelCertificacion(cert);
            panelCertificaciones.add(panelCert);
            panelCertificaciones.add(Box.createRigidArea(new Dimension(0, 10)));
        }
        
        JScrollPane scrollPane = new JScrollPane(panelCertificaciones);
        
        panel.add(panelSuperior, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel crearPanelCertificacion(Certificacion certificacion) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        panel.setBackground(Color.WHITE);
        
        // Panel izquierdo con información básica
        JPanel panelInfo = new JPanel(new GridLayout(4, 1, 5, 5));
        
        JLabel lblNombre = new JLabel(certificacion.getNombre());
        lblNombre.setFont(new Font("Arial", Font.BOLD, 14));
        
        JLabel lblID = new JLabel("ID: " + certificacion.getId());
        JLabel lblCreditos = new JLabel("Créditos requeridos: " + certificacion.getCreditosRequeridos());
        JLabel lblValidez = new JLabel("Validez: " + certificacion.getAñosValidez() + " años");
        
        panelInfo.add(lblNombre);
        panelInfo.add(lblID);
        panelInfo.add(lblCreditos);
        panelInfo.add(lblValidez);
        
        // Panel derecho con botones y progreso
        JPanel panelDerecho = new JPanel(new BorderLayout(5, 5));
        
        // Verificar si ya está inscrito
        boolean inscrito = false;
        int progreso = 0;
        String estado = "No inscrito";
        
        for (Registro registro : Main.getSistema().getRegistros()) {
            if (registro.getRut().equals(rutEstudiante) && 
                registro.getIdCertificacion().equals(certificacion.getId())) {
                inscrito = true;
                progreso =
        }
        }
        
    }