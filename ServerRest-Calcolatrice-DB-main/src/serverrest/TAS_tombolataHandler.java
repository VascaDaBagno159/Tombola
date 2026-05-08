package TAS.handlers;

import TAS.db.TombolaRepository;
import TAS.models.Tombolata;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class TombolateHandler implements HttpHandler {

    private final TombolaRepository repo;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public TombolateHandler(TombolaRepository repo) {
        this.repo = repo;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();

        try {
            if (method.equalsIgnoreCase("GET")) {
                if (path.matches(".*/api/tombolate/\\d+")) {
                    int id = Integer.parseInt(path.substring(path.lastIndexOf("/") + 1));
                    Optional<Tombolata> t = repo.trovaTombolata(id);
                    if (t.isPresent()) rispondi(exchange, 200, gson.toJson(t.get()));
                    else rispondi(exchange, 404, "{\"errore\":\"tombolata non trovata\"}");
                } else {
                    List<Tombolata> lista = repo.listaTombolate();
                    rispondi(exchange, 200, gson.toJson(lista));
                }
            } else if (method.equalsIgnoreCase("POST")) {
                Tombolata t = leggiBody(exchange, Tombolata.class);
                if (t.getNome() == null || t.getNome().isBlank()) {
                    rispondi(exchange, 400, "{\"errore\":\"nome obbligatorio\"}");
                    return;
                }
                Tombolata creata = repo.creaTombolata(t);
                rispondi(exchange, 201, gson.toJson(creata));
            } else if (method.equalsIgnoreCase("PATCH") && path.endsWith("/stato")) {
                int id = Integer.parseInt(path.split("/")[3]);
                Tombolata body = leggiBody(exchange, Tombolata.class);
                Tombolata aggiornata = repo.aggiornaStato(id, body.getStato());
                rispondi(exchange, 200, gson.toJson(aggiornata));
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