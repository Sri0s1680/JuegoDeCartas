import java.util.Random;
import java.util.Arrays;
import javax.swing.JPanel;

public class Jugador {

    private int TOTAL_CARTAS = 10;
    private int MARGEN = 10;
    private int DISTANCIA = 40;

    private Carta[] cartas = new Carta[TOTAL_CARTAS];

    Random r = new Random();

    public void repartir() {
        for (int i = 0; i < cartas.length; i++) {
            cartas[i] = new Carta(r);
        }
    }

    public void mostrar(JPanel pnl) {
        pnl.removeAll();
        int posicion = MARGEN + cartas.length * DISTANCIA;
        for (Carta c : cartas) {
            c.mostrar(pnl, posicion, MARGEN);
            posicion -= DISTANCIA;
        }
        pnl.repaint();
    }

    public String getGrupos() {
        String mensaje = "No se encontraron grupos";

        int[] contadores = new int[NombreCarta.values().length];
        for (Carta c : cartas) {
            contadores[c.getNombre().ordinal()]++;
        }

        boolean hayGrupos = false;
        for (int c : contadores) {
            if (c >= 2) {
                hayGrupos = true;
                break;
            }
        }

        if (hayGrupos) {
            mensaje = "Se encontraron los siguientes grupos:\n";
            int p = 0;
            for (int c : contadores) {
                if (c >= 2) {
                    mensaje += Grupo.values()[c] + " de " + NombreCarta.values()[p] + "\n";
                }
                p++;
            }
        }

        return mensaje;
    }

    public String getEscalerasMismaPinta() {
        String mensaje = "No se encontraron escaleras de la misma pinta";
    
        
        Arrays.sort(cartas, (c1, c2) -> c1.getNombre().ordinal() - c2.getNombre().ordinal());
    
     
        for (Pinta pinta : Pinta.values()) {
            int contador = 0;
            StringBuilder secuencia = new StringBuilder();
            Carta anterior = null;
    
            for (Carta c : cartas) {
                if (c.getPinta() == pinta) {
                    if (anterior == null || c.getNombre().ordinal() == anterior.getNombre().ordinal() + 1) {
                        secuencia.append(c.getNombre().toString()).append(" ");
                        contador++;
                    } else {
                        
                        if (contador >= 4) {
                            break;
                        }
                        
                        secuencia = new StringBuilder(c.getNombre().toString() + " ");
                        contador = 1;
                    }
                    anterior = c;
                }
            }
    
            
            if (contador >= 4) {
                mensaje = "Se encontró una escalera de " + pinta + ": " + secuencia.toString().trim();
                break;
            }
        }
    
        return mensaje;
    }

    

    public int calcularPuntaje() {
        int puntaje = 0;

        
        boolean[] enGrupo = new boolean[cartas.length];
        int[] contadores = new int[NombreCarta.values().length];
        for (int i = 0; i < cartas.length; i++) {
            contadores[cartas[i].getNombre().ordinal()]++;
        }

        for (int i = 0; i < cartas.length; i++) {
            if (contadores[cartas[i].getNombre().ordinal()] >= 2) {
                enGrupo[i] = true;
            }
        }

        
        boolean[] enEscalera = new boolean[cartas.length];
        Arrays.sort(cartas, (c1, c2) -> c1.getNombre().ordinal() - c2.getNombre().ordinal());

        for (Pinta pinta : Pinta.values()) {
            int contador = 0;
            for (int i = 0; i < cartas.length; i++) {
                if (cartas[i].getPinta() == pinta) {
                    if (contador == 0 || cartas[i].getNombre().ordinal() == cartas[i - 1].getNombre().ordinal() + 1) {
                        contador++;
                    } else {
                        if (contador >= 4) {
                            for (int j = i - contador; j < i; j++) {
                                enEscalera[j] = true;
                            }
                        }
                        contador = 1;
                    }
                }
            }
            if (contador >= 4) {
                for (int j = cartas.length - contador; j < cartas.length; j++) {
                    enEscalera[j] = true;
                }
            }
        }

        
        for (int i = 0; i < cartas.length; i++) {
            if (!enGrupo[i] && !enEscalera[i]) {
                NombreCarta nombre = cartas[i].getNombre();
                if (nombre == NombreCarta.AS || nombre == NombreCarta.JACK || nombre == NombreCarta.QUEEN
                        || nombre == NombreCarta.KING) {
                    puntaje += 10;
                } else {
                    puntaje += nombre.ordinal() + 1;
                }
            }
        }

        return puntaje;
    }
}