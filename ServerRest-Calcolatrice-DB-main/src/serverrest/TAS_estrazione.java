package TAS.models;

public class TAS_estrazione {
    private int id;
    private int numero;
    private String timestamp;
    private int tombolaId;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getNumero() { return numero; }
    public void setNumero(int numero) { this.numero = numero; }

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }

    public int getTombolaId() { return tombolaId; }
    public void setTombolaId(int tombolaId) { this.tombolaId = tombolaId; }
}