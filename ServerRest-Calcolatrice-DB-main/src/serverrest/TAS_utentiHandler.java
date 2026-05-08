package TAS.handlers;

import TAS.db.TombolaRepository;
import TAS.models.Utente;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.List;

public class TAS_utentiHandler implements HttpHandler {
    private final TombolaRepository repo;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public TAS_utentiHandler(TombolaRepository repo) {
        this.repo = repo;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (exchange.getRequestMethod().equalsIgnoreCase("POST")) {
                Utente u = leggiBody(exchange, Utente.class);
                if (u.getUsername() == null || u.getUsername().isBlank()) {
                    rispondi(exchange, 400, "{\"errore\":\"username obbligatorio\"}");
                    return;
                }
                Utente creato = repo.creaUtente(u);
                rispondi(exchange, 201, gson.toJson(creato));
            } else if (exchange.getRequestMethod().equalsIgnoreCase("GET")) {
                List<Utente> lista = repo.listaUtenti();
                rispondi(exchange, 200, gson.toJson(lista));
            } else {
                rispondi(exchange, 405, "{\"errore\":\"Metodo non consentito\"}");
            }
        } catch (SQLException e) {
            rispondi(exchange, 500, "{\"errore\":\"DB\"}");
        }
    }

    private <T> T leggiBody(HttpExchange ex, Class<T> c) throws IOException {
        try (Reader r = new InputStreamReader(ex.getRequestBody(), StandardCharsets.UTF_8)) {
            return gson.fromJson(r, c);
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