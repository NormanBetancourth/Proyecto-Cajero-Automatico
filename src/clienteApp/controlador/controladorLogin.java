package clienteApp.controlador;

import clienteApp.vista.VistaLogin;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class controladorLogin {
    private VistaLogin vistaLogin;
    private ActionCommand actionCommand;
    private ControladorPrincipal ctrl;

    private String user;
    private String password;

    public String getUser() {
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

    public controladorLogin(ControladorPrincipal controladorPrincipal) {
        ctrl = controladorPrincipal;
    }



    public void iniciar() {
        vistaLogin = new VistaLogin();
        vistaLogin.addListener( new ActionCommand());
    }

    private class ActionCommand implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()){
                case "Aceptar" ->{
                    //todo: conexion con el server y validacion
                    vistaLogin.setVisible(false);
                    ctrl.menuPrincipal();


                }

                case "Cancelar" -> {
                    vistaLogin.salir();
                }

            }


        }
    }
}
