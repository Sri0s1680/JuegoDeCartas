import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;

public class FrmJuego extends JFrame {

    private JButton btnRepartir;
    private JButton btnVerificar;
    private JPanel pnlJugador1;
    private JPanel pnlJugador2;
    private JTabbedPane tpJugadores;

    private Jugador jugador1, jugador2;

    public FrmJuego() {
        btnRepartir = new JButton();
        btnVerificar = new JButton();
        tpJugadores = new JTabbedPane();
        pnlJugador1 = new JPanel();
        pnlJugador2 = new JPanel();

        setSize(600, 300);
        setTitle("Juego de Cartas");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        pnlJugador1.setBackground(new Color(153, 255, 51));
        pnlJugador1.setLayout(null);
        pnlJugador2.setBackground(new Color(0, 255, 255));
        pnlJugador2.setLayout(null);

        tpJugadores.setBounds(10, 40, 550, 170);
        tpJugadores.addTab("Martín Estrada Contreras", pnlJugador1);
        tpJugadores.addTab("Raul Vidal", pnlJugador2);

        btnRepartir.setBounds(10, 10, 100, 25);
        btnRepartir.setText("Repartir");
        btnRepartir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnRepartirClick(evt);
            }
        });

        btnVerificar.setBounds(120, 10, 100, 25);
        btnVerificar.setText("Verificar");
        btnVerificar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnVerificarClick(evt);
            }
        });

        getContentPane().setLayout(null);
        getContentPane().add(tpJugadores);
        getContentPane().add(btnRepartir);
        getContentPane().add(btnVerificar);

        jugador1 = new Jugador();
        jugador2 = new Jugador();
    }

    private void btnRepartirClick(ActionEvent evt) {
        jugador1.repartir();
        jugador1.mostrar(pnlJugador1);

        jugador2.repartir();
        jugador2.mostrar(pnlJugador2);
    }

    private void btnVerificarClick(ActionEvent evt) {
        StringBuilder mensaje = new StringBuilder();

        switch (tpJugadores.getSelectedIndex()) {
            case 0: // Jugador 1 (Martín Estrada Contreras)
                mensaje.append("Resultados para Martín Estrada Contreras:\n");
                mensaje.append(jugador1.getGrupos()).append("\n");
                mensaje.append(jugador1.getEscalerasMismaPinta()).append("\n");
                mensaje.append("Puntaje de cartas restantes: ").append(jugador1.calcularPuntaje()).append("\n");
                break;
            case 1: // Jugador 2 (Raul Vidal)
                mensaje.append("Resultados para Raul Vidal:\n");
                mensaje.append(jugador2.getGrupos()).append("\n");
                mensaje.append(jugador2.getEscalerasMismaPinta()).append("\n");
                mensaje.append("Puntaje de cartas restantes: ").append(jugador2.calcularPuntaje()).append("\n");
                break;
        }

        // Mostrar el mensaje en un JOptionPane
        JOptionPane.showMessageDialog(this, mensaje.toString(), "Resultados de Verificación",
                JOptionPane.INFORMATION_MESSAGE);

    }

}