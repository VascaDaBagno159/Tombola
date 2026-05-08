package TAS;

import TAS.db.DatabaseManager;
import TAS.db.TombolaRepository;
import TAS.db.TombolaRepositoryImpl;
import TAS.handlers.*;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.sql.SQLException;

public class ServerRest {

    public static void avviaServer(int porta) {
        try {
            DatabaseManager db = DatabaseManager.getInstance();
            TombolaRepository repo = new TombolaRepositoryImpl(db);

            HttpServer server = HttpServer.create(new InetSocketAddress(porta), 0);

            server.createContext("/api/tombolate", new TombolateHandler(repo));
            server.createContext("/api/utenti", new UtentiHandler(repo));
            server.createContext("/api/cartelle", new CartelleHandler(repo));
            server.createContext("/api/estrazioni", new EstrazioniHandler(repo));
            server.createContext("/api/vincite", new VinciteHandler(repo));

            server.setExecutor(null);
            server.start();

            System.out.println("TaaS server avviato su http://localhost:" + porta);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}