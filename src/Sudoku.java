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
                tabella[r][c] = new Cella(r, c);  // sovrascrivo un cella vuota
                tabella[r][c].setValore(0); // nel dubbio metto a 0
                soluzione[r][c] = new Cella(r, c);
                soluzione[r][c].setValore(0);
            }
        }
        // faccio il repaint
        repaint();
    }

    /**
     * Metodo per controllare se un numero è già presente in una riga
     * @param riga indice di riga
     * @param nuovo nuovo numero da controllare
     * @return true se il numero non è presente, false altrimenti
     */
    private boolean isRigaLibera(int riga, int nuovo) {
        boolean presente = true; // variabile per risultato, di base non è presente
        // scorro le colonne (la riga è sempre la stessa)
        for (int c = 0; c < tabella[0].length; c++) {
            if (tabella[riga][c].getValore() == nuovo) { // se contiene il numero
                presente = false; // presente
            }
        }
        
        // ritorno il risultato
        return presente;
    }

    /**
     * Metodo per controllare se un numero è già presente in una colonna
     * @param col indice di colonna
     * @param nuovo nuovo numero da controllare
     * @return true se il numero non è presente, false altrimenti
     */
    private boolean isColonnaLibera(int col, int nuovo) {
        // stesso ragionamento metodo isRigaLibera
        boolean presente = true;
        for (int r = 0; r < tabella.length; r++) {
            if (tabella[r][col].getValore() == nuovo) {
                presente = false; 
            }
        }
        

        return presente;
    }

    /**
     * Metodo per controllare se un numero è già presente in una regione 3x3
     * @param riga indice di riga
     * @param col indice di colonna
     * @param nuovo nuovo numero da controllare
     * @return true se il numero non è presente, false altrimenti
     */
    private boolean isRegioneLibera(int riga, int col, int nuovo) {
        boolean presente = true;

        // calcolo l'inizio della regione 3x3
        // per calcolare l'inizio, divido per 3 (dimensione regione) e moltiplico per 3 (per tornare al multiplo di 3 più vicino))
        // esempio
        // se riga = 5, 5 / 3 = 1, 1 * 3 = 3 --> inizio regione riga = 3
        // se col = 7, 7 / 3 = 2, 2 * 3 = 6 --> inizio regione colonna = 6
        int rigaInizio = (riga / 3) * 3;  
        int colInizio = (col / 3) * 3; 
        
        // scorro la regione 3x3
        for (int r = rigaInizio; r < rigaInizio + 3; r++) {
            for (int c = colInizio; c < colInizio + 3; c++) {
                if (tabella[r][c].getValore() == nuovo) { // controllo
                    presente = false;
                }
            }
        }
        return presente;
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
        reset(); // svuoto le matrici
        
        
        // 1. Risolvo la prima riga con Fisher-Yates
        int array[] = {1,2,3,4,5,6,7,8,9}; // numeri iniziali
        shuffleArray(array); // mescolo l'array
        for (int c = 0; c < tabella[0].length; c++) { // ciclo per inserire i valori
            tabella[0][c].setValore(array[c]);
        }

        // Risolvo di conseguenza
        risolvi();
        
        // Salvo la soluzione completa
        for (int r = 0; r < tabella.length; r++) {
            for (int c = 0; c < tabella[0].length; c++) {
                soluzione[r][c].setValore(tabella[r][c].getValore());
            }
        }
        
        // Svuoto tabella 
        for (int r = 0; r < tabella.length; r++) {
            for (int c = 0; c < tabella[0].length; c++) {
                tabella[r][c].setValore(0);
            }
        }

        // repaint alla fine di tutto
        repaint();
    }

    /**
     * Metodo per generare gli indizi iniziali a partire dalla soluzione completa
     */
    public void generaIndizi() {
        // Svuoto la tabella di gioco
        for (int r = 0; r < tabella.length; r++) {
            for (int c = 0; c < tabella[0].length; c++) {
                tabella[r][c].setValore(0);
            }
        }
        
        // Inserisco gli indizi casuali dalla soluzione
        int count = 0;
        while (count < indizi) {
            int r = rand.nextInt(9); // random riga 
            int c = rand.nextInt(9); // random colonna
            
            // Aggiungo l'indizio solo se la cella è ancora vuota
            if (tabella[r][c].getValore() == 0) {
                tabella[r][c].setValore(soluzione[r][c].getValore());
                count++;
            }
        }

        // repaint alla fine
        repaint();
    }

    /**
     * Metodo per trovare la prima cella vuota nella griglia 
     * @return la cella vuota trovata, null se non ci sono celle vuote
     */
    private Cella trovaVuota() {
        Cella vuota = null; // inizializzo una cella 
        for (int r = 0; r < tabella.length; r++) {
            for (int c = 0; c < tabella[0].length; c++) { // scorro
                if (tabella[r][c].getValore() == 0) { // se trova una cella vuota, con valore 0
                    vuota = tabella[r][c]; // salvo l'istanza nella variabile vuota
                    return vuota; // la ritorno
                }
            }
        }

        return vuota; // se non ci sono, null
    }

    public boolean risolvi() {
        // cerco la prima cella vuota
        Cella vuota = trovaVuota();

        // clausola di uscita
        if (vuota == null) { // non ci sono più celle vuote
            return true; // soluzione trovata
        }

        // recupero le coordinate della cella vuota
        int r = vuota.getRiga();
        int c = vuota.getColonna();


        // provo a inserire i numeri da 1 a 9
        for (int num = 1; num <= 9; num++) {
            // se la mossa è valida, eseguo la chiamata ricorsiva
            if (isMossaValida(r, c, num)) {
                tabella[r][c].setValore(num); // provo la mossa
                // - se true, allora la soluzione è stata trovata
                // - se false, resetto la cella e provo con il numero successivo (backtracking)

                if (risolvi()) { // chiamata ricorsiva
                    return true; // soluzione trovata
                }

                tabella[r][c].setValore(0); // resetto la cella (backtracking)
            }
        }

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
