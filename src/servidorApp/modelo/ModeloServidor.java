package servidorApp.modelo;

import servidorApp.SQLConnection.SQLExecutor;

import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;



public class ModeloServidor{
    final int PUERTO = 7020;
    private ServerSocket serverSocket; // se va a ejecutar siempre

    public ModeloServidor() {
        try{
            abrirPuerto();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void abrirPuerto() {
        try {
            serverSocket = new ServerSocket(PUERTO);
        } catch (IOException ex) {
            Logger.getLogger(ModeloServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void close(){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                System.out.println("Close para cerrar el servidor");
                Scanner scanner = new Scanner(System.in);
                String msg = scanner.nextLine();
                if (msg.equals("close")){
                    try {
                        serverSocket.close();
                        System.exit(0);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void esperarAlCliente() {
        Socket socket = null;
        try {
            while (!serverSocket.isClosed()){
                socket = serverSocket.accept();
                ConectionHandler conectionHandler = new ConectionHandler(socket);
                //System.out.println("Numero de clientes conectados: " + conexiones);
                //conectionHandler.start();

                Thread thread = new Thread(conectionHandler);
                thread.start();
            }
        } catch (IOException ex) {
            close();
        }
    }

}
