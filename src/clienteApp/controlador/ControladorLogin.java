package clienteApp.controlador;

import clienteApp.modeloCliente.ModeloCliente;
import clienteApp.vista.VistaLogin;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorLogin implements IControlador {
    private VistaLogin vistaLogin;
    private ControladorPrincipal ctrl;
    private ModeloCliente modelo;
    private String user;
    private String password;
    private final String LOGIN = "login";

    public ControladorLogin(ControladorPrincipal controladorPrincipal, ModeloCliente modeloCliente) {
        ctrl = controladorPrincipal;
        modelo = modeloCliente;
        modelo.setControlador(this);
        vistaLogin = new VistaLogin();
        vistaLogin.addListener(new ActionCommand());
    }

    public String getUser() {
        updateUser();
        return user;
    }

    public String getPassword() {
        updatePwd();
        return password;
    }

    private void updatePwd() {
        password = vistaLogin.getPassword();
    }

    private void updateUser() {
        user = vistaLogin.getUsuario();
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private boolean procesoSalir() {
        if(vistaLogin.yesNoMessage("¿Desea salir?") == JOptionPane.YES_OPTION){
            return true;
        }
        return false;
    }

    @Override
    public void agregarMensaje(String mensaje) {
        vistaLogin.showMessage(mensaje);

    }

    private class ActionCommand implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String mensaje;
            switch (e.getActionCommand()) {
                case "Aceptar" -> {
                    try{
                        if(vistaLogin.getUsuario().isBlank() || vistaLogin.getPassword().isBlank()){
                            throw new Exception("Existen campos de información vacíos");
                        }
                        else{
                            modelo.enviarMensaje(LOGIN);
                            modelo.enviarMensaje(getUser());
                            modelo.enviarMensaje(getPassword());

                            mensaje = modelo.recibirMensaje();
                            if (mensaje.startsWith("Error")) {
                                vistaLogin.showErrorMessage(mensaje);
                                vistaLogin.clearTF();
                            } else {
                                if (mensaje.startsWith("Conexión exitosa.")) {
                                    agregarMensaje(mensaje);
                                    ctrl.setIdentificacionCliente(modelo.recibirMensaje());
                                    vistaLogin.dispose();
                                    ctrl.controladorMenuPrincipal();
                                }
                            }
                        }
                    }
                    catch (Exception exception){
                        vistaLogin.clearTF();
                        vistaLogin.showErrorMessage(exception.getMessage());
                    }
                }
                case "Cancelar" -> {
                    if(procesoSalir()){
                        modelo.enviarMensaje(ctrl.SALIR);
                        vistaLogin.salir();
                    }
                }
            }
        }
    }
}