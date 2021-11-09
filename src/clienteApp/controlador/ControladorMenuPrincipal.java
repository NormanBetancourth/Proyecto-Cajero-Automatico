package clienteApp.controlador;

import clienteApp.modeloCliente.ModeloCliente;
import clienteApp.vista.VistaPrincipal;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorMenuPrincipal implements IControlador{
    private VistaPrincipal vista;
    private ControladorPrincipal ctrl;
    private ModeloCliente modelo;

    public ControladorMenuPrincipal(ControladorPrincipal controladorPrincipal, ModeloCliente modeloCliente) {
        ctrl = controladorPrincipal;
        modelo = modeloCliente;
        modelo.setControlador(this);
        vista = new VistaPrincipal();
        addListeners();
    }

    public void addListeners(){
        vista.addListeners(new ActionCommand());
    }

    @Override
    public void agregarMensaje(String mensaje) {
        vista.showMessage(mensaje);
    }

    private class ActionCommand implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()){
                case "retiro" ->{
                    vista.dispose();
                    ctrl.controladorRetiro();
                }
                case "clave" ->{
                    vista.dispose();
                    ctrl.controladorCambioClave();
                }
                case "salir" ->{
                    vista.dispose();
                    //modelo.enviarMensaje(ctrl.SALIR);
                    new ControladorPrincipal();
                }

            }
        }
    }
}
