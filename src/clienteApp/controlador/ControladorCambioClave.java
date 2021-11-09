package clienteApp.controlador;

import clienteApp.modeloCliente.ModeloCliente;
import clienteApp.vista.VistaCambioClave;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorCambioClave implements IControlador {
    private ModeloCliente modelo;
    private VistaCambioClave vista;
    private ControladorPrincipal ctrl;
    private final String CAMBIOCLAVE = "cambioClave";

    public ControladorCambioClave(ControladorPrincipal controladorPrincipal, ModeloCliente modeloCliente) {
        ctrl = controladorPrincipal;
        modelo = modeloCliente;
        modelo.setControlador(this);
        vista = new VistaCambioClave();
        vista.addListener(new ActionCommand());
    }

    @Override
    public void agregarMensaje(String mensaje) {
        vista.showMessage(mensaje);

    }

    private class ActionCommand implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String mensaje;
            switch (e.getActionCommand()){
                case "Limpiar" ->{
                    vista.clearTF();
                }
                case "Aceptar" ->{
                    try{
                        if(vista.getTxtClaveActual().isBlank() || vista.getTxtNuevaClave().isBlank()
                                || vista.getTxtValidarNuevaClave().isBlank()){
                            throw new Exception("Existen campos de información vacíos");
                        }
                        else{
                            try{
                                if(!vista.getTxtValidarNuevaClave().equals(vista.getTxtNuevaClave())){
                                    throw new Exception("El campo de validación de la nueva contraseña no coincide");
                                }
                                else{
                                    modelo.enviarMensaje(CAMBIOCLAVE);
                                    modelo.enviarMensaje(ctrl.getIdentificacionCliente());
                                    modelo.enviarMensaje(vista.getTxtClaveActual());
                                    modelo.enviarMensaje(vista.getTxtNuevaClave());

                                    mensaje = modelo.recibirMensaje();
                                    if(mensaje.startsWith("Error")){
                                        vista.showErrorMessage(mensaje);
                                        vista.clearTF();
                                    }
                                    else{
                                        if(mensaje.startsWith("Proceso completado")){
                                            vista.clearTF();
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
                    }
                    catch (Exception exception){
                        vista.clearTF();
                        vista.showErrorMessage(exception.getMessage());
                    }
                }
                case "Regresar" ->{
                    vista.dispose();
                    ctrl.controladorMenuPrincipal();
                }
            }

        }
    }
}
