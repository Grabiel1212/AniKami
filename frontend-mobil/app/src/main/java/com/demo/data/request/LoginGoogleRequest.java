package com.demo.data.request;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginGoogleRequest {
    @SerializedName("google_id")
    private String idGoogle;
}
