package com.foodlabelapp.foodlabel;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;

import com.foodlabelapp.foodlabel.api.models.TextBlock;
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
    ArrayAdapter mArrayAdapter;

    public FetchOCRImageResponseTask(Context context, ArrayAdapter<String> adapter) {
        mContext = context;
        mArrayAdapter = adapter;
    }

    @Override
    protected String doInBackground(File... files) {
        File file = files[0];

        String endpoint = "https://api.idolondemand.com/1/api/sync/ocrdocument/v1";
        String apiKey = "7364a7dc-b711-4543-8186-882b5fd07faa";
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
        mArrayAdapter.clear();
        TextBlock textBlock = new TextBlock(s);
        if (s != null) {
            for (String data : textBlock.getTextList()) {
                mArrayAdapter.add(data);
            }
        }

    }
}
