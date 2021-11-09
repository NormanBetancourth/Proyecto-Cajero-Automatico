package servidorApp.modelo;

import servidorApp.SQLConnection.SQLExecutor;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
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


public class ModeloServidor extends Thread {
    final int PUERTO = 7020;
    private SQLExecutor executor;
    private ServerSocket serverSocket; // se va a ejecutar siempre
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private final String usernameBD = "sa";
    private final String passwordBD = "password";

    public ModeloServidor() {
        abrirPuerto();
        esperarAlCliente();
        crearFlujos();
        start();
    }


    public void run() {
        try {
            while (true) {
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
                        if (isValidPassword(passwordNueva) && !passwordActual.equals(passwordNueva))
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
            exception.printStackTrace();
            //enviarMensajeDeError("Hubo un fallo en el proceso");
        }
    }

    public static boolean isValidPassword(String password) throws Exception {
        boolean isValid = true;
        if (password.length() > 15 || password.length() < 8)
        {
            isValid = false;
            throw new Exception("La contraseña debe tener menos de 20 y más de 8 caracteres.");
        }
        String upperCaseChars = "(.*[A-Z].*)";
        if (!password.matches(upperCaseChars ))
        {
            isValid = false;
            throw new Exception("La contraseña debe tener al menos un carácter en mayúscula");
        }
        String lowerCaseChars = "(.*[a-z].*)";
        if (!password.matches(lowerCaseChars ))
        {
            isValid = false;
            throw new Exception("La contraseña debe tener al menos un carácter en minúscula");
        }
        String numbers = "(.*[0-9].*)";
        if (!password.matches(numbers ))
        {
            isValid = false;
            throw new Exception("La contraseña debe tener al menos un número");
        }
        String specialChars = "(.*[@,#,$,%].*$)";
        if (!password.matches(specialChars ))
        {
            isValid = false;
            throw new Exception("La contraseña debe tener al menos un carácter especial ");
        }
        return isValid;
    }

    public void abrirPuerto() {
        try {
            serverSocket = new ServerSocket(PUERTO);
        } catch (IOException ex) {
            Logger.getLogger(ModeloServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void esperarAlCliente() {

                try {
                    socket = serverSocket.accept();
                } catch (IOException ex) {
                    Logger.getLogger(ModeloServidor.class.getName()).log(Level.SEVERE, null, ex);
                }

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
            Logger.getLogger(ModeloServidor.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ModeloServidor.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ModeloServidor.class.getName()).log(Level.SEVERE, null, ex);
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

    public void realizarRetiro(String id, String monto) {
        double saldoActualizado = 0.0;
        double saldo = 0.0;
        ResultSet resultSet;
        if (verificaSaldoSuficiente(id, monto)) {
            try {
                //id cliente, monto y id transaccion
                executor = new SQLExecutor(usernameBD, passwordBD);
                //Ingresar la transaccion
                String valores1[] = new String[5];
                valores1[0] = "INSERT INTO TRANSACCIONES(ID_TRANSACCION, ID_CLIENTE, MONTO_TRANSAC, TIPO_ID) VALUES (?,?,?,?)";
                valores1[1] = String.valueOf(new Random().nextInt(100));
                valores1[2] = id;
                valores1[3] = monto;
                valores1[4] = String.valueOf(2); // Suponiendo '2' como retiros
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
        Thread.currentThread().interrupt();
        System.exit(0);
    }

}
