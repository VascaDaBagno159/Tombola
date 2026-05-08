package TAS.handlers;

import TAS.db.TombolaRepository;
import TAS.models.Vincita;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.List;

public class VinciteHandler implements HttpHandler {

    private final TombolaRepository repo;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public VinciteHandler(TombolaRepository repo) {
        this.repo = repo;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equalsIgnoreCase("GET")) {
            rispondi(exchange, 405, "{\"errore\":\"Metodo non consentito\"}");
            return;
        }
        try {
            String q = exchange.getRequestURI().getQuery(); // tombolaId=1
            int id = Integer.parseInt(q.split("=")[1]);
            List<Vincita> lista = repo.listaVincite(id);
            rispondi(exchange, 200, gson.toJson(lista));
        } catch (SQLException e) {
            rispondi(exchange, 500, "{\"errore\":\"DB\"}");
        }
    }

    private void rispondi(HttpExchange ex, int code, String json) throws IOException {
        ex.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
        ex.sendResponseHeaders(code, bytes.length);
        ex.getResponseBody().write(bytes);
        ex.getResponseBody().close();
    }
}