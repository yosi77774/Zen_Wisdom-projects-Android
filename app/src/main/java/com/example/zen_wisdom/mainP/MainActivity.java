package com.example.zen_wisdom.mainP;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.zen_wisdom.R;
import com.example.zen_wisdom.TextP.TextDowinloader;

public class MainActivity extends AppCompatActivity implements TextDowinloader.Callback {

    private TextView textViewPhrse;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewPhrse=(TextView)findViewById(R.id.textViewPhrase);
    }

    public void buttonPhrase_onClick(View view){
        String link ="http://android-demo-apis.appspot.com/simple/zen";
    TextDowinloader textDowinloader = new TextDowinloader(this);
    textDowinloader.execute(link);
    }


    public void onAboutToBegin() {
        textViewPhrse.setText("");
progressDialog=new ProgressDialog(this);
progressDialog.setTitle("Downloading...");
progressDialog.setMessage("Please Wait...");
progressDialog.setIcon(R.drawable.ic_launcher_foreground);
        progressDialog.show();
    }


    public void onsuccess(String downloadedText) {
        progressDialog.dismiss();
textViewPhrse.setText(downloadedText);
    }


    public void onError(int httpstatuscode, String erroMessage) {
progressDialog.dismiss();
textViewPhrse.setText("Error:\n http Status Code: " + httpstatuscode +"\n Error Message: " + erroMessage);
    }
}