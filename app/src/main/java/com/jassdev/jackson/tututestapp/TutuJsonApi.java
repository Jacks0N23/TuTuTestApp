package com.jassdev.jackson.tututestapp;

import com.jassdev.jackson.tututestapp.Utils.Models.TutuModel;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by Jackson on 18/10/2016.
 */

public interface TutuJsonApi {

    /**
     *
     * @param url - url запроса на скачивание json файла
     * @return - отфильтрованный список адвертов
     */

    @GET
    Observable<Response<TutuModel>> getTutuJson(@Url String url);
}
