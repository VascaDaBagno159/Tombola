package TAS.models;

public class TAS_cartella {
    private int id;
    private int colonne = 9;
    private int righe = 3;
    private String numeri;
    private int utenteId;
    private int tombolaId;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getColonne() { return colonne; }
    public void setColonne(int colonne) { this.colonne = colonne; }

    public int getRighe() { return righe; }
    public void setRighe(int righe) { this.righe = righe; }

    public String getNumeri() { return numeri; }
    public void setNumeri(String numeri) { this.numeri = numeri; }

    public int getUtenteId() { return utenteId; }
    public void setUtenteId(int utenteId) { this.utenteId = utenteId; }

    public int getTombolaId() { return tombolaId; }
    public void setTombolaId(int tombolaId) { this.tombolaId = tombolaId; }
}