package com.foodlabelapp.foodlabel.api.service;

import com.foodlabelapp.foodlabel.api.models.OCRImage;

import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.mime.TypedFile;

/**
 * Created by Daniel on 7/19/15.
 */
public interface IdolOnDemandService {

    @Multipart
    @POST("/1/api/sync/ocrdocument/v1")
    OCRImage ocrDocument(@Part("file")TypedFile image);
}
