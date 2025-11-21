import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class App {
    public static void main(String[] args) throws Exception {
        
        JFrame f = new JFrame("Sudoku");
        Sudoku s = new Sudoku(20);

        JPanel topPanel = new JPanel();
        JButton btnReset = new JButton("Reset");
        topPanel.add(btnReset);
        btnReset.addActionListener(e -> {
            // istruzioni per resettare la griglia
        });

        JButton btnSolution = new JButton("Genera Soluzione");
        topPanel.add(btnSolution);
        btnSolution.addActionListener(e -> {
            // generare una soluzione completa e randomizzata
        });

        JButton btnClue = new JButton("Inizializza");
        topPanel.add(btnClue);
        btnClue.addActionListener(e -> {
            // inizializzare la griglia con solo gli indizi iniziali
        });

        JButton btnSolve = new JButton("Risolvi");
        topPanel.add(btnSolve); 
        btnSolve.addActionListener(e -> {
            // risolvere la griglia partendo dagli indizi iniziali
        });

        f.getContentPane().add(topPanel, BorderLayout.NORTH);
        f.getContentPane().add(s);

        f.setSize(470, 530);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }
}
