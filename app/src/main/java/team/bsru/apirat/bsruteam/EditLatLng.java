package team.bsru.apirat.bsruteam;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

/**
 * Created by Lottae on 17/2/2560.
 */

public class EditLatLng extends AsyncTask<String, Void, String>{

    // Explicit
    private Context context;
    private static final String urlPHP = "http://swiftcodingthai.com/bsru/edit_latlng_tae.php";
    private String idString;

    public EditLatLng(String idString, Context context) {
        this.idString = idString;
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {

        try {

            OkHttpClient okHttpClient = new OkHttpClient();
            RequestBody requestBody = new FormEncodingBuilder()
                    .add("isAdd", "true")
                    .add("id", idString)
                    .add("Lat", params[0])
                    .add("Lng", params[1])
                    .build();
            Request.Builder builder = new Request.Builder();
            Request request = builder.url(urlPHP).post(requestBody).build();
            Response response = okHttpClient.newCall(request).execute();
            return response.body().string();

        }catch (Exception e){
            Log.d("17febV2", "e doIn ==>" + e.toString());
            return null;
        }


    }
} // Main class
