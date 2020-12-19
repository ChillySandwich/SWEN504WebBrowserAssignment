package com.example.swen504webbrowserassignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private String URL;
    private WebView myWebView;
    private List<String> history = new ArrayList<String>();
    private List<String> bookmarks = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("");


        myWebView = (WebView) findViewById(R.id.webview);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.setWebViewClient(new MyWebViewClient());
        myWebView.loadUrl("https://www.example.com");


        Button searchBtn = (Button) findViewById(R.id.search);
        searchBtn.setOnClickListener((View v) -> {
            TextView txt = (TextView) findViewById(R.id.siteSearch);
            String text = txt.getText().toString();
            if (!(text.startsWith("https://"))) {
                text = "https://" + text;
            }

            this.URL = text;
            history.add(URL);
            myWebView.loadUrl(URL);
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId())
        {
            case R.id.refresh:
                Toast.makeText(this, "Refresh", Toast.LENGTH_SHORT).show();
                    myWebView.loadUrl(URL);
                break;

            case R.id.BackBtn:
                Toast.makeText(this, "Back", Toast.LENGTH_SHORT).show();
                if (myWebView.canGoBack()) {
                    myWebView.goBack();
                }
                break;

            case R.id.ForwardBtn:
                Toast.makeText(this, "Forward", Toast.LENGTH_SHORT).show();
                if (myWebView.canGoForward()) {
                    myWebView.goForward();
                }
                break;

            case R.id.HistoryBtn:
                Toast.makeText(this, "History", Toast.LENGTH_SHORT).show();
                break;

            case R.id.BookmarkSiteBtn:
                boolean flag = false;
                for(String s: bookmarks){
                    if(URL.equalsIgnoreCase(s)){
                        flag = true;
                         Toast.makeText(this, "Site has already been Bookmarked", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
                if(flag = false){
                    Toast.makeText(this, "Site has been Bookmarked", Toast.LENGTH_SHORT).show();
                    bookmarks.add(URL);
                }
                break;

            case R.id.BookmarksBtn:
                Toast.makeText(this, "Bookmarks", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem menuItem = menu.findItem(R.id.HistoryBtn);
        Spinner spinner = (Spinner) menuItem.getActionView();
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, history);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return false;
        }
    }
}