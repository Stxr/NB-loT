package com.stxr.nb_lot.utils;

import android.os.AsyncTask;
import android.util.Log;

import com.show.api.ShowApiRequest;
import com.stxr.nb_lot.myconst.MyConst;

/**
 * Created by stxr on 2018/5/27.
 */

public class InterestUtil extends AsyncTask<String,Integer,String> {

    private InterestsResponse mResponse;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected String doInBackground(String... strings) {
        String s = new ShowApiRequest("http://route.showapi.com/268-1", MyConst.APPID, MyConst.SECRET)
                .addTextPara("keyword", strings[0])
                .post();
        Log.d("InterestUtil", s);
        return s;
    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        mResponse.success(s);
    }

    public InterestUtil setOnResponse(InterestsResponse response) {
        mResponse = response;
        return this;
    }

    public interface InterestsResponse {
        void success(String s);
    }
}
