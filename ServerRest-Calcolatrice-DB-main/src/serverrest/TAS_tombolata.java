package TAS.models;

public class Tombolata {
    private int id;
    private String nome;
    private String timestamp;
    private String luogo;
    private String stato = "creata";

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }

    public String getLuogo() { return luogo; }
    public void setLuogo(String luogo) { this.luogo = luogo; }

    public String getStato() { return stato; }
    public void setStato(String stato) { this.stato = stato; }
}