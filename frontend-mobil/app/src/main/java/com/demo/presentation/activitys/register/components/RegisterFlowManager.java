package com.demo.presentation.activitys.register.components;

public class RegisterFlowManager {

    private RegistroPasos pasoActual;
    private TipoRegistro tipo;

    public RegisterFlowManager(TipoRegistro tipoRegistro){
        this.tipo = tipoRegistro;

        if(tipoRegistro == TipoRegistro.EMAIL){
            this.pasoActual = RegistroPasos.EMAIL_USUARIO;
        } else {
            this.pasoActual = RegistroPasos.DATOS_PERSONALES_USUARIO;
        }
    }

    public RegistroPasos getPasoActual(){
        return pasoActual;
    }

    public void siguientePaso(){
        switch (pasoActual){

            case EMAIL_USUARIO:
                pasoActual = RegistroPasos.DATOS_PERSONALES_USUARIO;
                break;

            case DATOS_PERSONALES_USUARIO:
                if(tipo == TipoRegistro.GOOGLE){
                    pasoActual = RegistroPasos.FOTO_USUARIO;
                } else {
                    pasoActual = RegistroPasos.CONTRASEÑA_USUARIO;
                }
                break;

            case CONTRASEÑA_USUARIO:
                pasoActual = RegistroPasos.FOTO_USUARIO;
                break;

            case FOTO_USUARIO:
                pasoActual = RegistroPasos.PREFERENCIAS_USUARIO;
                break;
        }
    }

    public void anteriorPaso() {

        switch (pasoActual){

            case PREFERENCIAS_USUARIO:
                pasoActual = RegistroPasos.FOTO_USUARIO;
                break;

            case FOTO_USUARIO:
                if(tipo == TipoRegistro.GOOGLE){
                    pasoActual = RegistroPasos.DATOS_PERSONALES_USUARIO;
                } else {
                    pasoActual = RegistroPasos.CONTRASEÑA_USUARIO;
                }
                break;

            case CONTRASEÑA_USUARIO:
                pasoActual = RegistroPasos.DATOS_PERSONALES_USUARIO;
                break;

            case DATOS_PERSONALES_USUARIO:
                if(tipo == TipoRegistro.GOOGLE){
                    pasoActual = RegistroPasos.DATOS_PERSONALES_USUARIO;
                } else {
                    pasoActual = RegistroPasos.EMAIL_USUARIO;
                }
                break;
        }
    }

    public boolean esUltimoPaso(){
        return pasoActual == RegistroPasos.PREFERENCIAS_USUARIO;
    }

    public boolean esPrimerPaso(){
        if(tipo == TipoRegistro.GOOGLE){
            return pasoActual == RegistroPasos.DATOS_PERSONALES_USUARIO;
        } else {
            return pasoActual == RegistroPasos.EMAIL_USUARIO;
        }
    }
}
