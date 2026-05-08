package TAS.db;

import TAS.models.*;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface TAS_tombolaRepository {

    Tombolata creaTombolata(Tombolata t) throws SQLException;
    List<Tombolata> listaTombolate() throws SQLException;
    Optional<Tombolata> trovaTombolata(int id) throws SQLException;
    Tombolata aggiornaStato(int id, String nuovoStato) throws SQLException;

    Utente creaUtente(Utente u) throws SQLException;
    List<Utente> listaUtenti() throws SQLException;

    Cartella creaCartella(Cartella c) throws SQLException;

    Estrazione estraiNumero(int tombolaId, int numero) throws SQLException;
    List<Estrazione> listaEstrazioni(int tombolaId) throws SQLException;

    List<Vincita> listaVincite(int tombolaId) throws SQLException;
}