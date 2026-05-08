package TAS.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {

    private static DatabaseManager instance;
    private Connection connection;

    private static final String SQLITE_URL = "jdbc:sqlite:taas.db";

    private DatabaseManager() throws SQLException {
        connection = DriverManager.getConnection(SQLITE_URL);
        initSchema();
    }

    public static synchronized DatabaseManager getInstance() throws SQLException {
        if (instance == null || instance.connection.isClosed()) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    private void initSchema() throws SQLException {
        String[] ddl = {
            "CREATE TABLE IF NOT EXISTS TAS_tombolate (" +
            " tom_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            " tom_nome TEXT NOT NULL," +
            " tom_timeStamp DATETIME DEFAULT CURRENT_TIMESTAMP," +
            " tom_luogo TEXT," +
            " tom_stato TEXT NOT NULL DEFAULT 'creata'" +
            ")",

            "CREATE TABLE IF NOT EXISTS TAS_utenti (" +
            " utente_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            " username TEXT NOT NULL UNIQUE," +
            " password_hash TEXT NOT NULL," +
            " nome TEXT," +
            " cognome TEXT," +
            " ruolo TEXT NOT NULL DEFAULT 'giocatore'" +
            ")",

            "CREATE TABLE IF NOT EXISTS TAS_cartelle (" +
            " car_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            " car_colonne INTEGER NOT NULL," +
            " car_righe INTEGER NOT NULL," +
            " car_numeri TEXT," +
            " car_ute_id INTEGER," +
            " car_tom_id INTEGER," +
            " FOREIGN KEY (car_ute_id) REFERENCES TAS_utenti(utente_id)," +
            " FOREIGN KEY (car_tom_id) REFERENCES TAS_tombolate(tom_id)" +
            ")",

            "CREATE TABLE IF NOT EXISTS TAS_numeriestratti (" +
            " num_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            " num_numero INTEGER NOT NULL," +
            " num_timeStamp DATETIME DEFAULT CURRENT_TIMESTAMP," +
            " num_tom_id INTEGER NOT NULL," +
            " UNIQUE(num_numero, num_tom_id)," +
            " FOREIGN KEY (num_tom_id) REFERENCES TAS_tombolate(tom_id)" +
            ")",

            "CREATE TABLE IF NOT EXISTS TAS_tipovincite (" +
            " tip_id INTEGER PRIMARY KEY," +
            " tip_tipo TEXT NOT NULL" +
            ")",

            "CREATE TABLE IF NOT EXISTS TAS_vincite (" +
            " vin_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            " vin_nVincitori INTEGER NOT NULL," +
            " vin_timeStamp DATETIME DEFAULT CURRENT_TIMESTAMP," +
            " vin_tip_id INTEGER," +
            " vin_car_id INTEGER," +
            " FOREIGN KEY (vin_tip_id) REFERENCES TAS_tipovincite(tip_id)," +
            " FOREIGN KEY (vin_car_id) REFERENCES TAS_cartelle(car_id)" +
            ")"
        };

        try (Statement stmt = connection.createStatement()) {
            for (String sql : ddl) stmt.execute(sql);
            stmt.execute("INSERT OR IGNORE INTO TAS_tipovincite (tip_id, tip_tipo) VALUES " +
                         "(1,'Ambo'),(2,'Terno'),(3,'Quaterna'),(4,'Cinquina'),(5,'Tombola')");
        }
    }
}