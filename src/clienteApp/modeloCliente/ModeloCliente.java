package clienteApp.modeloCliente;

import clienteApp.controlador.IControlador;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ModeloCliente extends Thread {
    IControlador controlador;
    final int PUERTO = 7020;
    final String HOST = "localhost";
    Socket socket;
    BufferedReader bufferedReader;
    BufferedWriter bufferedWriter;

    public ModeloCliente() {
        conectarConElServidor();
        crearFlujos();
        start();
    }

    public void run() {
        new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                while (true) {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            String mensaje = recibirMensaje();
                            controlador.agregarMensaje(mensaje);
                        }
                    });

                }
            }
        };
    }

    public void setControlador(IControlador controlador){
        this.controlador = controlador;
    }

    public void conectarConElServidor(){
        try{
            socket = new Socket(HOST, PUERTO);
        }
        catch (IOException exception){
            Logger.getLogger(ModeloCliente.class.getName()).log(Level.SEVERE, null, exception);
        }
    }

    public void crearFlujos(){
        try{
            InputStream inputStream = socket.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            // espera la información para ser capturada y mostrada
            bufferedReader = new BufferedReader(inputStreamReader);

            OutputStream outputStream = socket.getOutputStream();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            // espera la información para escribirse y enviarse
            bufferedWriter = new BufferedWriter(outputStreamWriter);
        }
        catch (IOException ex){
            Logger.getLogger(ModeloCliente.class.getName()).log(Level.SEVERE, null,ex);
        }
    }

    public String recibirMensaje(){
        try{
            String mensaje = bufferedReader.readLine();
            return mensaje;
        }
        catch (IOException ex){
            Logger.getLogger(ModeloCliente.class.getName()).log(Level.SEVERE, null,ex);
        }
        return "";
    }

    public void enviarMensaje(String mensaje){
        try{
            bufferedWriter.write(mensaje);
            //Preparar el buffer para que quede limpio en el caso de ingresar
            //enviar más información
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }
        catch (IOException ex){
            salir();
            controlador.agregarMensaje("Error: El servidor está desconectado");
            System.exit(0);
        }
    }

    public void salir(){

        try {
            //this.interrupt();

            if(bufferedWriter != null)
                bufferedWriter.close();

            if(bufferedReader != null)
                bufferedReader.close();

            if(socket != null){
                socket.close();
            }

        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

}
