package app.emporioDaVila.ExceptionHandlers;

public class PedidoExceptions {
    public static class PedidoJaEmAndamento extends RuntimeException {
        public PedidoJaEmAndamento(String message) { super(message); }
    }
}
