package com.eelis.myapplication;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    TextView txt;
    Button btn;
    String row;
    String thisWeeksJackpot = "";

    public static int MAXNUMBER = 50;

    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt = (TextView) findViewById(R.id.rivi);
        btn = (Button) findViewById(R.id.lottoButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LotteryNumbers lottoRow = new LotteryNumbers(5, 1, MAXNUMBER);
                row = lottoRow.toString();
                if (thisWeeksJackpot == "")
                    new WebCrawler().execute();
                //txt.setText(row);
                txt.setText(row + "\n\n" + thisWeeksJackpot);
            }
        });
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page")//Content title
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }


    public class WebCrawler extends AsyncTask<Void, Void, Void> {

        String text;

        @Override
        protected Void doInBackground(Void... voids) {
            StringBuilder builder = new StringBuilder();
            try {
                Document doc = Jsoup.connect("https://www.eurojackpot.org/en/").get();
                String elems = doc.getElementsByClass("ctawrapper").select("p").text();
                //int last = elems.indexOf('s') + 1;
                text = builder.toString();
                thisWeeksJackpot = "This weeks jackpot:\n" + elems;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            txt.setText(row + "\n\n" + thisWeeksJackpot);
        }
    }


}
