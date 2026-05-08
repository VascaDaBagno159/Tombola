package TAS.models;

public class TAS_vincita {
    private int id;
    private int numeroVincitori;
    private String timestamp;
    private int tipoId;
    private int cartellaId;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getNumeroVincitori() { return numeroVincitori; }
    public void setNumeroVincitori(int numeroVincitori) { this.numeroVincitori = numeroVincitori; }

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }

    public int getTipoId() { return tipoId; }
    public void setTipoId(int tipoId) { this.tipoId = tipoId; }

    public int getCartellaId() { return cartellaId; }
    public void setCartellaId(int cartellaId) { this.cartellaId = cartellaId; }
}