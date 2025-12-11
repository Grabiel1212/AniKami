package com.demo.presentation.util;

import android.widget.EditText;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Validator {

    private final EditText editText;
    private final List<String> errors = new ArrayList<>();

    private Validator(EditText editText) {
        this.editText = editText;
    }

    public static Validator with(EditText editText) {
        return new Validator(editText);
    }

    private String getValue() {
        return editText.getText().toString().trim();
    }

    //==================== REGLAS ====================//

    public Validator required(String message) {
        if (getValue().isEmpty()) {
            errors.add(message != null ? message : "Este campo es obligatorio");
        }
        return this;
    }

    public Validator min(int min, String message) {
        if (getValue().length() < min) {
            errors.add(message != null ? message : "Debe tener al menos " + min + " caracteres");
        }
        return this;
    }

    public Validator max(int max, String message) {
        if (getValue().length() > max) {
            errors.add(message != null ? message : "Debe tener máximo " + max + " caracteres");
        }
        return this;
    }

    public Validator length(int len, String message) {
        if (getValue().length() != len) {
            errors.add(message != null ? message : "Debe tener " + len + " caracteres");
        }
        return this;
    }

    public Validator matches(String regex, String message) {
        if (!Pattern.matches(regex, getValue())) {
            errors.add(message != null ? message : "Formato incorrecto");
        }
        return this;
    }

    public Validator email() {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        if (!Pattern.matches(emailRegex, getValue())) {
            errors.add("Correo no válido");
        }
        return this;
    }

    public Validator numeric() {
        if (!getValue().matches("\\d+")) {
            errors.add("Solo se permiten números");
        }
        return this;
    }

    public Validator isDate() {
        if (!isValidDate(getValue())) {
            errors.add("La fecha debe ser válida (dd/MM/yyyy)");
        }
        return this;
    }

    //==================== EJECUTAR VALIDACIÓN ====================//
    public boolean validate() {
        if (!errors.isEmpty()) {
            editText.setError(errors.get(0)); // muestra solo el primer error
            editText.requestFocus();
            return false;
        }
        return true;
    }

    //==================== MÉTODOS DE APOYO ====================//
    private boolean isValidDate(String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false);
            sdf.parse(date);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
