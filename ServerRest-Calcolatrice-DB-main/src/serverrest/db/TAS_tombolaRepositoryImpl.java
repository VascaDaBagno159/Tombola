package TAS.db;

import TAS.models.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TombolaRepositoryImpl implements TombolaRepository {

    private final DatabaseManager db;

    public TombolaRepositoryImpl(DatabaseManager db) {
        this.db = db;
    }

    @Override
    public Tombolata creaTombolata(Tombolata t) throws SQLException {
        String sql = "INSERT INTO TAS_tombolate (tom_nome, tom_luogo, tom_stato) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = db.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, t.getNome());
            stmt.setString(2, t.getLuogo());
            stmt.setString(3, t.getStato());
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) t.setId(rs.getInt(1));
            }
        }
        return t;
    }

    @Override
    public List<Tombolata> listaTombolate() throws SQLException {
        List<Tombolata> lista = new ArrayList<>();
        try (Statement stmt = db.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM TAS_tombolate ORDER BY tom_id DESC")) {
            while (rs.next()) lista.add(mapTombolata(rs));
        }
        return lista;
    }

    @Override
    public Optional<Tombolata> trovaTombolata(int id) throws SQLException {
        String sql = "SELECT * FROM TAS_tombolate WHERE tom_id = ?";
        try (PreparedStatement stmt = db.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return Optional.of(mapTombolata(rs));
            }
        }
        return Optional.empty();
    }

    @Override
    public Tombolata aggiornaStato(int id, String nuovoStato) throws SQLException {
        String sql = "UPDATE TAS_tombolate SET tom_stato = ? WHERE tom_id = ?";
        try (PreparedStatement stmt = db.getConnection().prepareStatement(sql)) {
            stmt.setString(1, nuovoStato);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        }
        return trovaTombolata(id).orElseThrow();
    }

    @Override
    public Utente creaUtente(Utente u) throws SQLException {
        String sql = "INSERT INTO TAS_utenti (username, password_hash, nome, cognome, ruolo) VALUES (?,?,?,?,?)";
        try (PreparedStatement stmt = db.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, u.getUsername());
            stmt.setString(2, u.getPasswordHash());
            stmt.setString(3, u.getNome());
            stmt.setString(4, u.getCognome());
            stmt.setString(5, u.getRuolo());
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) u.setId(rs.getInt(1));
            }
        }
        return u;
    }

    @Override
    public List<Utente> listaUtenti() throws SQLException {
        List<Utente> lista = new ArrayList<>();
        try (Statement stmt = db.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM TAS_utenti ORDER BY utente_id DESC")) {
            while (rs.next()) {
                Utente u = new Utente();
                u.setId(rs.getInt("utente_id"));
                u.setUsername(rs.getString("username"));
                u.setPasswordHash(rs.getString("password_hash"));
                u.setNome(rs.getString("nome"));
                u.setCognome(rs.getString("cognome"));
                u.setRuolo(rs.getString("ruolo"));
                lista.add(u);
            }
        }
        return lista;
    }

    @Override
    public Cartella creaCartella(Cartella c) throws SQLException {
        String sql = "INSERT INTO TAS_cartelle (car_colonne, car_righe, car_numeri, car_ute_id, car_tom_id) VALUES (?,?,?,?,?)";
        try (PreparedStatement stmt = db.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, c.getColonne());
            stmt.setInt(2, c.getRighe());
            stmt.setString(3, c.getNumeri());
            stmt.setInt(4, c.getUtenteId());
            stmt.setInt(5, c.getTombolaId());
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) c.setId(rs.getInt(1));
            }
        }
        return c;
    }

    @Override
    public Estrazione estraiNumero(int tombolaId, int numero) throws SQLException {
        String sql = "INSERT INTO TAS_numeriestratti (num_numero, num_tom_id) VALUES (?,?)";
        try (PreparedStatement stmt = db.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, numero);
            stmt.setInt(2, tombolaId);
            stmt.executeUpdate();
            Estrazione e = new Estrazione();
            e.setNumero(numero);
            e.setTombolaId(tombolaId);
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) e.setId(rs.getInt(1));
            }
            return e;
        }
    }

    @Override
    public List<Estrazione> listaEstrazioni(int tombolaId) throws SQLException {
        List<Estrazione> lista = new ArrayList<>();
        String sql = "SELECT * FROM TAS_numeriestratti WHERE num_tom_id = ? ORDER BY num_id DESC";
        try (PreparedStatement stmt = db.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, tombolaId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Estrazione e = new Estrazione();
                    e.setId(rs.getInt("num_id"));
                    e.setNumero(rs.getInt("num_numero"));
                    e.setTimestamp(rs.getString("num_timeStamp"));
                    e.setTombolaId(rs.getInt("num_tom_id"));
                    lista.add(e);
                }
            }
        }
        return lista;
    }

    @Override
    public List<Vincita> listaVincite(int tombolaId) throws SQLException {
        List<Vincita> lista = new ArrayList<>();
        String sql =
            "SELECT v.* FROM TAS_vincite v " +
            "JOIN TAS_cartelle c ON v.vin_car_id = c.car_id " +
            "WHERE c.car_tom_id = ? ORDER BY v.vin_id DESC";
        try (PreparedStatement stmt = db.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, tombolaId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Vincita v = new Vincita();
                    v.setId(rs.getInt("vin_id"));
                    v.setNumeroVincitori(rs.getInt("vin_nVincitori"));
                    v.setTimestamp(rs.getString("vin_timeStamp"));
                    v.setTipoId(rs.getInt("vin_tip_id"));
                    v.setCartellaId(rs.getInt("vin_car_id"));
                    lista.add(v);
                }
            }
        }
        return lista;
    }

    private Tombolata mapTombolata(ResultSet rs) throws SQLException {
        Tombolata t = new Tombolata();
        t.setId(rs.getInt("tom_id"));
        t.setNome(rs.getString("tom_nome"));
        t.setTimestamp(rs.getString("tom_timeStamp"));
        t.setLuogo(rs.getString("tom_luogo"));
        t.setStato(rs.getString("tom_stato"));
        return t;
    }
}