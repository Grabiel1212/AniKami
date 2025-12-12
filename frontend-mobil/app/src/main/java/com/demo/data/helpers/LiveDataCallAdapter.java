package com.demo.data.helpers;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.demo.data.common.BaseResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LiveDataCallAdapter {

    public static <T> LiveData<BaseResponse<T>> call(Call<BaseResponse<T>> call) {
        MutableLiveData<BaseResponse<T>> liveData = new MutableLiveData<>();

        call.enqueue(new Callback<BaseResponse<T>>() {
            @Override
            public void onResponse(Call<BaseResponse<T>> call, Response<BaseResponse<T>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    liveData.postValue(response.body());
                } else {
                    liveData.postValue(BaseResponse.error("Error en la respuesta del servidor"));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<T>> call, Throwable t) {
                liveData.postValue(BaseResponse.error(t.getMessage()));
            }
        });

        return liveData;
    }
}