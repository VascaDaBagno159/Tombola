package TAS.handlers;

import TAS.db.TombolaRepository;
import TAS.models.Estrazione;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

public class TAS_estrazioneHandler implements HttpHandler {

    private final TombolaRepository repo;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final Random rnd = new Random();

    public TAS_estrazioneHandler(TombolaRepository repo) {
        this.repo = repo;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (exchange.getRequestMethod().equalsIgnoreCase("POST")) {
                Estrazione body = leggiBody(exchange, Estrazione.class);
                int numero = body.getNumero() == 0 ? rnd.nextInt(90) + 1 : body.getNumero();
                Estrazione e = repo.estraiNumero(body.getTombolaId(), numero);
                rispondi(exchange, 201, gson.toJson(e));
            } else if (exchange.getRequestMethod().equalsIgnoreCase("GET")) {
                String q = exchange.getRequestURI().getQuery(); // tombolaId=1
                int id = Integer.parseInt(q.split("=")[1]);
                List<Estrazione> lista = repo.listaEstrazioni(id);
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