package com.foodlabelapp.foodlabel;

import android.content.Context;
import android.os.AsyncTask;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

/**
 * Created by Daniel on 7/18/15.
 */
public class FetchOCRImageResponseTask extends AsyncTask<File, Void, String> {

    Context mContext;

    @Override
    protected String doInBackground(File... files) {
        File file = files[0];

        String endpoint = mContext.getString(R.string.url);
        String apiKey = mContext.getString(R.string.api_key);
        String result = null;
        try {
            HttpResponse<JsonNode> response = Unirest
                    .post(endpoint)
                    .field("file", file)
                    .field("mode", "scene_photo")
                    .field("apikey", apiKey)
                    .asJson();

            JSONObject textblock =(JSONObject) response.getBody().getObject().getJSONArray("text_block").get(0);
            result= textblock.getString("text");
        } catch (UnirestException | JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(String s) {

    }
}
