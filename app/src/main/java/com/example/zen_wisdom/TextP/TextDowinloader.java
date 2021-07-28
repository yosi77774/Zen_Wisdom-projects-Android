package com.example.zen_wisdom.TextP;


import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.security.auth.callback.Callback;

public class TextDowinloader extends AsyncTask<String , Void ,String> {

    private  Callback listener;
    private int httpstatuscode;
    private String erroMessage;

    public TextDowinloader(Callback listener) {
        this.listener = listener;
    }


    protected void onPreExecute() {
        listener.onAboutToBegin();
    }


    @Override
    protected String doInBackground(String... params) {

        HttpURLConnection httpURLConnection=null;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader=null;

        try {
            String link = params[0];
            URL url = new URL(link);
            httpURLConnection= (HttpURLConnection)url.openConnection();
            httpstatuscode = httpURLConnection.getResponseCode();
            if(httpstatuscode != HttpURLConnection.HTTP_OK) {
                erroMessage = httpURLConnection.getResponseMessage();
                return null;
            }
            inputStream=httpURLConnection.getInputStream();
            inputStreamReader=new InputStreamReader(inputStream);
            bufferedReader =new BufferedReader(inputStreamReader);

            StringBuilder stringBuilder = new StringBuilder();
            String oneLine =  bufferedReader.readLine() ;
            while (oneLine != null){
                stringBuilder.append(oneLine);
                stringBuilder.append("\n");
                oneLine = bufferedReader.readLine();
            }
            return stringBuilder.toString();
        }
        catch (Exception ex){
            erroMessage=ex.getMessage();
            return null;
        }
        finally {
            if (httpURLConnection != null){
                httpURLConnection.disconnect();
            }
            if (inputStream != null){
                try {
                    inputStream.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            if (inputStreamReader != null){
                try {
                    inputStreamReader.close();
                }catch (IOException e){
                    e.printStackTrace();
                }

            }
            if (bufferedReader != null){
                try {
                    bufferedReader.close();
                }catch (IOException e){
                    e.printStackTrace();
                }

            }
        }

    }


    protected void onPostExecute(String downloadedText) {
        if(downloadedText != null){
            listener.onsuccess(downloadedText);
        }
        else {
            listener.onError(httpstatuscode,erroMessage);
        }
    }



    public interface Callback {
        void onAboutToBegin();
        void onsuccess(String downloadedText);
        void onError(int httpstatuscode,String erroMessage);

    }


}
