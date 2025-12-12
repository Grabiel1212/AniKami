package com.demo.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginData {
    private boolean login;
    private Usuario usuario; // puede ser null si login = false
}
