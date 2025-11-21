import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;

import javax.swing.JPanel;

public class Sudoku extends JPanel {
    
    Random rand = new Random();
    Cella tabella[][] = new Cella[9][9];
    Cella soluzione[][] = new Cella[9][9];
    int dimCella = 50;
    int indizi;

    //int tentativi;

    public Sudoku(int nIndizi) {
        rand = new Random();
        this.indizi = nIndizi;

        reset();
        setBackground(Color.white);
    }

    /**
     * Metodo per resettare la griglia e svuotare la matrice
     */
    public void reset() {

        for (int r = 0; r < tabella.length; r++) {
            for (int c = 0; c < tabella[0].length; c++) {
                tabella[r][c] = new Cella(r, c);
                soluzione[r][c] = new Cella(r, c);
            }
        }
    }

    /**
     * Metodo per controllare se un numero è già presente in una riga
     * @param riga indice di riga
     * @param nuovo nuovo numero da controllare
     * @return true se il numero non è presente, false altrimenti
     */
    private boolean isRigaLibera(int riga, int nuovo) {

        // TODO
        return true;
    }

    /**
     * Metodo per controllare se un numero è già presente in una colonna
     * @param col indice di colonna
     * @param nuovo nuovo numero da controllare
     * @return true se il numero non è presente, false altrimenti
     */
    private boolean isColonnaLibera(int col, int nuovo) {

        // TODO
        return true;
    }

    /**
     * Metodo per controllare se un numero è già presente in una regione 3x3
     * @param riga indice di riga
     * @param col indice di colonna
     * @param nuovo nuovo numero da controllare
     * @return true se il numero non è presente, false altrimenti
     */
    private boolean isRegioneLibera(int riga, int col, int nuovo) {

        // TODO
        return true;
    }

    /**
     * Metodo per controllare se una mossa è valida
     * @param riga indice di riga
     * @param col indice di colonna
     * @param nuovo nuovo numero da controllare
     * @return true se la mossa è valida, false altrimenti
     */
    private boolean isMossaValida(int riga, int col, int nuovo) {
        return isRigaLibera(riga, nuovo) && 
                isColonnaLibera(col, nuovo) && 
                isRegioneLibera(riga, col, nuovo);
    }

    // vi sconsiglio questo approccio...
    public void generaIndiziCasuale() {

    }

    // algoritmo di Fisher-Yates per mescolare un array
    private void shuffleArray(int[] v) {
        for (int i = v.length - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);

            int temp = v[i];
            v[i] = v[j];
            v[j] = temp;
        }
    }

    /**
     * Metodo per generare una soluzione completa e randomizzata
     * La strategia è quella di inserire la prima riga randomizzata, usando Fisher-Yates,
     * e poi risolvere il Sudoku a partire da questa configurazione iniziale
     */
    public void generaSoluzione() {
        
        // TODO
    }

    /**
     * Metodo per generare gli indizi iniziali a partire dalla soluzione completa
     */
    public void generaIndizi() {
        
        // TODO
    }

    /**
     * Metodo per trovare la prima cella vuota nella griglia 
     * @return la cella vuota trovata, null se non ci sono celle vuote
     */
    private Cella trovaVuota() {

        // TODO
        return null;
    }

    public boolean risolvi() {
        // cerco la prima cella vuota
        Cella vuota = trovaVuota();

        // clausola di uscita
        // TODO

        // recupero le coordinate della cella vuota
        int r = vuota.getRiga();
        int c = vuota.getColonna();

        // provo a inserire i numeri da 1 a 9
        // se la mossa è valida, eseguo la chiamata ricorsiva
        // - se true, allora la soluzione è stata trovata
        // - se false, resetto la cella e provo con il numero successivo (backtracking)
        // TODO

        return false; 
    }

    /**
     * Metodo per disegnare la griglia del Sudoku
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int offset = 3;

        g.setColor(Color.black);
        g.setFont(new Font("Comic Sans MS", Font.PLAIN, 24));

        // Disegna la griglia del Sudoku
        for (int r = 0; r < tabella.length; r++) {
            for (int c = 0; c < tabella[0].length; c++) {
                g.setColor(Color.black);
                g.drawRect(offset + c * dimCella, offset + r * dimCella, dimCella, dimCella);

                if (tabella[r][c].getValore() != 0)                        
                    g.drawString(tabella[r][c].getValore() + "", offset + c * dimCella + 18, offset + r * dimCella + 32);
                       
            }
        }

        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(3));
        g.setColor(Color.black);

        // Disegna le linee più spesse per le sezioni 3x3
        for (int r = 0; r < tabella.length; r += 3) {
            for (int c = 0; c < tabella[0].length; c += 3) {
                g2.drawRect(offset + c * dimCella, offset + r * dimCella, dimCella * 3, dimCella * 3);
            }
        }
    }
}
