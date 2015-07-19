package com.foodlabelapp.foodlabel.api.service;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

/**
 * Created by Daniel on 7/19/15.
 */
public class IdolOnDemandApi {

    public static final String IDOL_ON_DEMAND_WEB_API_ENDPOINT = "https://api.idolondemand.com/1/api/sync/ocrdocument/v1";

    private String mApiKey;

    private final IdolOnDemandService mIdolOnDemandService;

    private class WebApiAuthenticator implements RequestInterceptor {
        @Override
        public void intercept(RequestFacade request) {
            if (mApiKey != null) {
                request.addQueryParam("apikey", mApiKey);
            }
        }
    }

    public IdolOnDemandApi(Executor httpExecutor, Executor callbackExecutor) {
        mIdolOnDemandService = init(httpExecutor, callbackExecutor);
    }

    private IdolOnDemandService init(Executor httpExecutor, Executor callbackExecutor) {

        final RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.BASIC)
                .setExecutors(httpExecutor, callbackExecutor)
                .setEndpoint(IDOL_ON_DEMAND_WEB_API_ENDPOINT)
                .setRequestInterceptor(new WebApiAuthenticator())
                .build();

        return restAdapter.create(IdolOnDemandService.class);
    }

    public IdolOnDemandApi(String apiKey) {
        Executor httpExecutor = Executors.newSingleThreadExecutor();
        mIdolOnDemandService = init(httpExecutor, null);
        mApiKey = apiKey;
    }

    public IdolOnDemandService getService() {
        return mIdolOnDemandService;
    }
}
