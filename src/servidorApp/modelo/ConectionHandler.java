package servidorApp.modelo;

import servidorApp.SQLConnection.SQLExecutor;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.security.SecureRandom;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


/*
El servidor debe recibir
• Solicitud de conexión: el servidor recibe el usuario y clave de un cliente, verifica sus datos en la
base de datos y si concuerda la información, el servidor retorna un mensaje de conexión exitosa.
En caso contrario, se debe retornar un error para que el cajero pueda informar al cliente.
• Solicitud de cambio de clave: el servidor recibe la nueva clave a ser asignada al cliente, y actualiza
la base de datos. Retorna al cajero un mensaje del cambio realizado de forma exitosa.
• Solicitud de saldo: el servidor retorna el saldo del cliente consultado.
• Solicitud de retiro: el servidor retorna un mensaje de éxito al cajero en caso de que se pueda
retirar el dinero, o un mensaje de error en caso de que el saldo no sea suficiente.

 */

public class ConectionHandler extends  Thread{

    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private final String usernameBD = "sa";
    private final String passwordBD = "password";
    private SQLExecutor executor;


    public ConectionHandler(Socket socket) {
        this.socket = socket;
        crearFlujos();
    }

    public void crearFlujos() {
        try {
            InputStream inputStream = socket.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            // espera la información para ser capturada y mostrada
            bufferedReader = new BufferedReader(inputStreamReader);

            OutputStream outputStream = socket.getOutputStream();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            // espera la información para escribirse y enviarse
            bufferedWriter = new BufferedWriter(outputStreamWriter);
        } catch (IOException ex) {
            Logger.getLogger(ModeloServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public String recibirMensaje() {
        try {
                return bufferedReader.readLine();
        } catch (IOException ex) {
            salir();
            //Logger.getLogger(ModeloServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    public void enviarMensajeDeError(String mensaje) {
        try {
            bufferedWriter.write("Error: " + mensaje);
            //Preparar el buffer para que quede limpio en el caso de ingresar
            //enviar más información
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException ex) {
            //Logger.getLogger(ModeloServidor.class.getName()).log(Level.SEVERE, null, ex);
            salir();
        }
    }

    public void enviarMensaje(String mensaje) {
        try {
            bufferedWriter.write(mensaje);
            //Preparar el buffer para que quede limpio en el caso de ingresar
            //enviar más información
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException ex) {
            //Logger.getLogger(ModeloServidor.class.getName()).log(Level.SEVERE, null, ex);
            salir();
        }
    }

    private void realizarLogin(String usernameCliente, String passwordCliente) {
        String cadena;
        ResultSet resultSet;
        try {
            executor = new SQLExecutor(usernameBD, passwordBD);
            resultSet = executor.ejecutaQuery("SELECT * FROM CLIENTE");
            while (resultSet.next()) {
                if (resultSet.getString("USUARIO").equals(usernameCliente) &&
                        resultSet.getString("CLAVE").equals(passwordCliente)) {
                    break;
                }
            }
            cadena = resultSet.getString("NOMBRE");
            enviarMensaje("Conexión exitosa. ¡Bienvenido " + cadena + "!");
            enviarMensaje(resultSet.getString("CEDULA"));
        } catch (SQLException throwables) {
            enviarMensajeDeError("Nombre de usuario o contraseña incorrecto");
        }
    }

    public void realizarCambioDeClave(String identificacion, String passwordActual, String nuevaPassword) {
        try {
            executor = new SQLExecutor(usernameBD, passwordBD);

            String valores[] = new String[4];
            valores[0] = "UPDATE CLIENTE SET CLAVE = ? WHERE CEDULA = ? AND CLAVE = ?";
            valores[1] = nuevaPassword;
            valores[2] = identificacion;
            valores[3] = passwordActual;

            executor.prepareStatement(valores);
            enviarMensaje("Proceso completado exitosamente");
            //System.out.println("Cambio de clave completado"); // Para probar
        } catch (Exception throwables) {
            throwables.printStackTrace();
            enviarMensajeDeError("Hubo un fallo en el proceso");
        }
    }

    public void realizaConsultaDeSaldo(String id) {
        ResultSet resultSet;
        try {
            executor = new SQLExecutor(usernameBD, passwordBD);
            resultSet = executor.ejecutaQuery("SELECT * FROM CLIENTE");
            while(resultSet.next()){
                if(resultSet.getString("CEDULA").equals(id)){
                    break;
                }
            }
            enviarMensaje(resultSet.getString("SALDO"));
        } catch (SQLException exception) {
            exception.printStackTrace();
            enviarMensajeDeError("Hubo un fallo en el proceso");
        }
    }

    public boolean verificaSaldoSuficiente(String id, String monto) {
        ResultSet resultSet;
        double saldo = 0.0;

        try {
            executor = new SQLExecutor(usernameBD, passwordBD);
            resultSet = executor.ejecutaQuery("SELECT * FROM CLIENTE");
            while(resultSet.next()){
                if(resultSet.getString("CEDULA").equals(id)){
                    break;
                }
            }
            saldo = Double.parseDouble(resultSet.getString("SALDO"));
            return saldo >= Double.parseDouble(monto);

        } catch (SQLException exception) {
            exception.printStackTrace();
            enviarMensaje("Hubo un fallo en el proceso");
        }
        return false;
    }

    public String crearCodigo(String id){
        SecureRandom random = new SecureRandom();
        char caracter1 = (char) (random.nextInt(26) + 'A');
        char caracter2 = (char) (random.nextInt(26) + 'A');
        char caracter3 = (char) (random.nextInt(26) + 'A');
        return id + "-" + caracter1 + caracter2 + caracter3;
    }

    public void realizarRetiro(String id, String monto) {
        double saldoActualizado = 0.0;
        double saldo = 0.0;
        ResultSet resultSet;
        ResultSet resultSetTransaccion;
        String idTipoTransaccion;

        if (verificaSaldoSuficiente(id, monto)) {
            try {
                //id cliente, monto y id transaccion
                executor = new SQLExecutor(usernameBD, passwordBD);

                //Obtener id de la transacción
                resultSetTransaccion = executor.ejecutaQuery("SELECT * FROM TIPO_TRANSACCIONES");

                while(resultSetTransaccion.next()){
                    if(resultSetTransaccion.getString("TIPO_TRANSACCION").equals("RETIRO")){
                        break;
                    }
                }
                idTipoTransaccion = resultSetTransaccion.getString("ID");

                //Ingresar la transaccion
                String valores1[] = new String[5];
                valores1[0] = "INSERT INTO TRANSACCIONES(ID_TRANSACCION, ID_CLIENTE, MONTO_TRANSAC, TIPO_ID) VALUES (?,?,?,?)";
                valores1[1] = crearCodigo(id);
                valores1[2] = id;
                valores1[3] = monto;
                valores1[4] = idTipoTransaccion;
                executor.prepareStatement(valores1);

                //Actualizar saldo
                resultSet = executor.ejecutaQuery("SELECT * FROM CLIENTE");

                while(resultSet.next()){
                    if(resultSet.getString("CEDULA").equals(id)){
                        break;
                    }
                }

                saldo = Double.parseDouble(resultSet.getString("SALDO"));
                saldoActualizado = saldo - Double.parseDouble(monto);

                String valores2[] = new String[3];
                valores2[0] = "UPDATE CLIENTE SET SALDO = ? WHERE CEDULA = ?";
                valores2[1] = String.valueOf(saldoActualizado);
                valores2[2] = id;

                executor.prepareStatement(valores2);
                enviarMensaje(String.valueOf(saldoActualizado));
                enviarMensaje("Proceso completado exitosamente");

            } catch (Exception ex) {
                enviarMensajeDeError("Hubo un fallo en el proceso");
                Logger.getLogger(ModeloServidor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else{
            enviarMensajeDeError("Saldo insuficiente para realizar el retiro");
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


    @Override
    public void run() {
        try {
            while (socket.isConnected()) {
                String mensaje = recibirMensaje();
                switch (mensaje) {
                    case "login" -> {
                        String username = recibirMensaje();
                        String password = recibirMensaje();
                        realizarLogin(username, password);
                    }
                    case "cambioClave" -> {
                        String identificacion = recibirMensaje();
                        String passwordActual = recibirMensaje();
                        String passwordNueva = recibirMensaje();
                        realizarCambioDeClave(identificacion, passwordActual, passwordNueva);
                    }
                    case "consultaDeSaldo" -> {
                        String identificacion = recibirMensaje();
                        realizaConsultaDeSaldo(identificacion);
                    }
                    case "retiroDeDinero" -> {
                        String identificacion = recibirMensaje();
                        String monto = recibirMensaje();
                        realizarRetiro(identificacion, monto);
                    }
                    case "salir" -> {
                        salir();
                    }

                }
            }
        } catch (Exception exception) {
            //exception.printStackTrace();
            //enviarMensajeDeError("Hubo un fallo en el proceso");
            salir();
        }
    }
}
