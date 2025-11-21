public class Cella {

    private int riga, colonna;
    private int valore;
    
    public Cella(int r, int c) {
        this.riga = r;
        this.colonna = c;
        this.valore = 0;
    }
    
    public int getRiga() {
        return riga;
    }

    public int getColonna() {
        return colonna;
    }

    public int getValore() {
        return valore;
    }
    
    public void setValore(int valore) {
        this.valore = valore;
    }
}
