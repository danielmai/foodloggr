package com.foodlabelapp.foodlabel;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.foodlabelapp.foodlabel.api.models.OCRImage;
import com.foodlabelapp.foodlabel.api.models.TextBlock;
import com.foodlabelapp.foodlabel.api.service.IdolOnDemandApi;
import com.foodlabelapp.foodlabel.api.service.IdolOnDemandService;

import java.io.File;

import retrofit.mime.TypedFile;

/**
 * Created by Daniel on 7/18/15.
 */
public class FetchOCRImageResponseTask extends AsyncTask<File, Void, TextBlock> {

    private static final String LOG_TAG = FetchOCRImageResponseTask.class.getSimpleName();

    Context mContext;
    ArrayAdapter mArrayAdapter;

    public FetchOCRImageResponseTask(Context context, ArrayAdapter<String> adapter) {
        mContext = context;
        mArrayAdapter = adapter;
    }

    @Override
    protected TextBlock doInBackground(File... files) {
        Log.d(LOG_TAG, "We're in do in background!");
        File file = files[0];
        TypedFile imageFile = new TypedFile("multipart/form-data", file);

        IdolOnDemandApi api = new IdolOnDemandApi("7364a7dc-b711-4543-8186-882b5fd07faa");
        String apikey = "7364a7dc-b711-4543-8186-882b5fd07faa";
        IdolOnDemandService service = api.getService();
        OCRImage ocrImage = service.ocrDocument(apikey, imageFile);
        return ocrImage.text_block.get(0);
    }

    @Override
    protected void onPostExecute(TextBlock tb) {
        Log.d(LOG_TAG, "Hiii");
        mArrayAdapter.clear();
        Log.d(LOG_TAG, "tb == null = " + (tb == null));
        if (tb != null) {
            for (String data : tb.getTextList()) {
                mArrayAdapter.add(data);
            }
        }
    }
}
