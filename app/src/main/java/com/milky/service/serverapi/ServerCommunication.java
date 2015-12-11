package com.milky.service.databaseutils.serverapi;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
/**
 * Created by Neha on 12/10/2015.
 */


import java.util.List;

public class ServerCommunication extends AsyncTask<String, Void, JSONObject> {

    private String _url;

    private String _type;

    private List<NameValuePair> _nameValuePair = null;

    OnTaskCompleteListner _listner;

    public void runRequest(String url, List<NameValuePair> nameValuePair, OnTaskCompleteListner listner, String type) {
        _url = ServerApis.API_ROOT+url;
        _type = type;
        _nameValuePair = nameValuePair;
        _listner = listner;
        execute("");
    }

    @Override
    protected JSONObject doInBackground(String... params) {
        Log.i("HttpAsycTask_PreExec", "type: " + _type + " __ request: " + _url);
        try {
            HttpParams httpParameters = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParameters, 8000);
            HttpConnectionParams.setSoTimeout(httpParameters, 8000);

            HttpClient httpClient = new DefaultHttpClient(httpParameters);

            HttpPost httpPost = new HttpPost(_url);
            httpPost.addHeader("smsheader", "android");
            if (_nameValuePair != null)
                httpPost.setEntity(new UrlEncodedFormEntity(_nameValuePair));
            HttpResponse response = httpClient.execute(httpPost);
            if (response != null) {
                HttpEntity e = response.getEntity();
                String data = EntityUtils.toString(e);
                JSONObject last = new JSONObject(data);
                return last;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected void onPostExecute(JSONObject result) {
        JSONObject _result = result;
//        try {
//            if (_result == null) {
//                _result = new JSONObject();
//                _result.put(Constants.URL_REQ_STATUS, Constants.STATUS_FAIL);
//                _result.put("error", "We're having trouble getting data.");
//                Log.i("HttpAsycTask_PostExec", "type: " + _type + " __ status: response null");
//            } else {
//                Integer success = (result.getString("status").equalsIgnoreCase("ZERO_RESULTS")||result.getString("status").equalsIgnoreCase("OK")) ?0:(Integer) Integer.parseInt(_result.get(Constants.URL_REQ_STATUS).toString()); // 2
//                if (success != null && success == 0) {
//                    _result.put(Constants.URL_REQ_STATUS, Constants.STATUS_SUCCESS);
//                } else {
//                    _result.put(Constants.URL_REQ_STATUS, Constants.STATUS_FAIL);
//                    if(result.isNull("error"))
//                        _result.put("error",result.isNull("message")?"We're having trouble getting data.":result.getString("message"));
//                }
//                Log.i("HttpAsycTask", "type: " + _type + " __ status: response success");
//            }
//            _listner.onTaskCompleted(_result, _type);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        _listner.onTaskCompleted(_result, _type);
    }

}
