package clienteApp.principal;

import clienteApp.controlador.ControladorPrincipal;

import javax.swing.*;

public class Principal {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                System.out.println("cliente main " + Thread.currentThread().getName());
                new ControladorPrincipal();
            }
        });

    }
}
