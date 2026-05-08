package TAS;

public class TAS_app {
    public static void main(String[] args) {
        int porta = 8080;
        if (args.length > 0) {
            try {
                porta = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.err.println("Porta non valida, uso 8080");
            }
        }
        ServerRest.avviaServer(porta);
    }
}