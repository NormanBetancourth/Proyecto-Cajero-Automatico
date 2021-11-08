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
        return password;
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
    }

    private class ActionCommand implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()){

            }


        }
    }
}
