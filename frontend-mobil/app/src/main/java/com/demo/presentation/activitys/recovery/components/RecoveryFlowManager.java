package com.demo.presentation.activitys.recovery.components;


import com.demo.presentation.activitys.register.components.TipoRegistro;

public class RecoveryFlowManager {

        private RegistroPasosRecovery pasoActual;

        public RecoveryFlowManager() {
            this.pasoActual = RegistroPasosRecovery.EMAIL; // primer paso
        }

        public RegistroPasosRecovery getPasoActual() {
            return pasoActual;
        }

        public void siguientePaso() {
            switch (pasoActual) {

                case EMAIL:
                    pasoActual = RegistroPasosRecovery.VALIDACION;
                    break;

                case VALIDACION:
                    pasoActual = RegistroPasosRecovery.RECUPERACION;
                    break;

                case RECUPERACION:
                    // ya no hay siguiente, se queda en recuperación
                    pasoActual = RegistroPasosRecovery.RECUPERACION;
                    break;
            }
        }

        public void anteriorPaso() {
            switch (pasoActual) {

                case RECUPERACION:
                    pasoActual = RegistroPasosRecovery.VALIDACION;
                    break;

                case VALIDACION:
                    pasoActual = RegistroPasosRecovery.EMAIL;
                    break;

                case EMAIL:
                    // ya no hay anterior, se queda en email
                    pasoActual = RegistroPasosRecovery.EMAIL;
                    break;
            }
        }

        public boolean esPrimerPaso() {
            return pasoActual == RegistroPasosRecovery.EMAIL;
        }

        public boolean esUltimoPaso() {
            return pasoActual == RegistroPasosRecovery.RECUPERACION;
        }
}
