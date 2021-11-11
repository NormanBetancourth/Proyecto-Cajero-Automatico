package servidorApp.principal;

import servidorApp.modelo.ModeloServidor;

public class Principal {
    public static void main(String[] args) throws InterruptedException {
        try {
            System.out.println("Servidor");
            ModeloServidor modelo = new ModeloServidor();
            modelo.esperarAlCliente();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
