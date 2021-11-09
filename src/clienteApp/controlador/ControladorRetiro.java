package clienteApp.controlador;

import clienteApp.modeloCliente.ModeloCliente;
import clienteApp.vista.VentanaRetiroDinero;
import clienteApp.vista.VistaCambioClave;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorRetiro implements IControlador{
    private ModeloCliente modelo;
    private VentanaRetiroDinero vista;
    private ControladorPrincipal ctrl;
    private final String RETIRO = "retiroDeDinero";
    private final String CONSULTA = "consultaDeSaldo";
    private String nuevoSaldo;

    public ControladorRetiro(ControladorPrincipal controladorPrincipal, ModeloCliente modeloCliente) {
        vista = new VentanaRetiroDinero();
        vista.addListener(new ActionCommand());
        ctrl = controladorPrincipal;
        modelo = modeloCliente;
        modelo.setControlador(this);
        actualizaCampos();
    }

    private void actualizaCampos(){
        modelo.enviarMensaje(CONSULTA);
        modelo.enviarMensaje(ctrl.getIdentificacionCliente());
        vista.setSaldo(modelo.recibirMensaje());
    }

    @Override
    public void agregarMensaje(String mensaje) {
        vista.showMessage(mensaje);
    }

    private class ActionCommand implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String mensaje;
            String nuevoMonto;

            switch (e.getActionCommand()){
                case "Limpiar" -> {
                    vista.clearTF();
                    vista.setSaldo(nuevoSaldo);
                }
                case "Aceptar" -> {
                    try{
                        if(vista.getMonto().isBlank()){
                            throw new Exception("Existen campos de información vacíos");
                        }
                        else{
                            modelo.enviarMensaje(RETIRO);
                            modelo.enviarMensaje(ctrl.getIdentificacionCliente());
                            modelo.enviarMensaje(vista.getMonto());
                            nuevoMonto = modelo.recibirMensaje();
                            nuevoSaldo = nuevoMonto;
                            vista.setRestante(nuevoMonto);

                            mensaje = modelo.recibirMensaje();
                            if(mensaje.startsWith("Error")){
                                vista.showErrorMessage(mensaje);
                                vista.clearTF();
                            }
                            else{
                                if(mensaje.startsWith("Proceso completado")){
                                    agregarMensaje(mensaje);
                                }
                            }
                        }
                    }
                    catch (Exception exception){
                        vista.clearTF();
                        vista.showErrorMessage(exception.getMessage());
                    }
                }
                case "Regresar" -> {
                    vista.dispose();
                    ctrl.controladorMenuPrincipal();
                }

            }

        }
    }
}
