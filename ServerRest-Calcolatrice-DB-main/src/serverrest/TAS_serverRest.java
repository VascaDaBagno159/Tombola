package TAS;

import TAS.db.DatabaseManager;
import TAS.db.TombolaRepository;
import TAS.db.TombolaRepositoryImpl;
import TAS.handlers.*;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.sql.SQLException;

public class TAS_serverRest {

    public static void avviaServer(int porta) {
        try {
            DatabaseManager db = DatabaseManager.getInstance();
            TombolaRepository repo = new TombolaRepositoryImpl(db);

            HttpServer server = HttpServer.create(new InetSocketAddress(porta), 0);

            server.createContext("/api/tombolate", new TAS_tombolateHandler(repo));
            server.createContext("/api/utenti", new TAS_utentiHandler(repo));
            server.createContext("/api/cartelle", new TAS_cartelleHandler(repo));
            server.createContext("/api/estrazioni", new TAS_estrazioneHandler(repo));
            server.createContext("/api/vincite", new TAS_vinciteHandler(repo));
            server.setExecutor(null);
            server.start();

            System.out.println("TaaS server avviato su http://localhost:" + porta);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}