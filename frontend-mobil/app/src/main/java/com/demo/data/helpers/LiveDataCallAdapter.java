package com.demo.data.helpers;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.demo.data.common.BaseResponse;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LiveDataCallAdapter {

    public static <T> LiveData<BaseResponse<T>> call(Call<BaseResponse<T>> call) {

        MutableLiveData<BaseResponse<T>> liveData = new MutableLiveData<>();
        Gson gson = new Gson();

        call.enqueue(new Callback<BaseResponse<T>>() {
            @Override
            public void onResponse(Call<BaseResponse<T>> call,
                                   Response<BaseResponse<T>> response) {

                if (response.isSuccessful() && response.body() != null) {
                    // ✅ 200 OK
                    liveData.postValue(response.body());
                    return;
                }

                // ⚠️ HTTP 400 / 401 / 500 → leer errorBody
                if (response.errorBody() != null) {
                    try {
                        String json = response.errorBody().string();
                        BaseResponse<T> error =
                                gson.fromJson(json, BaseResponse.class);

                        liveData.postValue(error);
                        return;

                    } catch (Exception e) {
                        // parseo falló
                    }
                }

                // fallback
                liveData.postValue(
                        BaseResponse.error("Error HTTP: " + response.code())
                );
            }

            @Override
            public void onFailure(Call<BaseResponse<T>> call, Throwable t) {
                liveData.postValue(
                        BaseResponse.error("Error de conexión con el servidor")
                );
            }
        });

        return liveData;
    }
}
