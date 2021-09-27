package com.osos.bottomsheetrecycler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    LinearLayout btn;
    private RequestQueue mQ;
    TextView textView;
    ImageView imageView;
    String a,b;

    ItemObject itemObject;
    MyBottomSheetFragment frag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.btmsheet);
        textView = findViewById(R.id.textView);
        imageView = findViewById(R.id.imageView);

        a = SharedPreferenceService.getLastUsedText(this);
        b = SharedPreferenceService.getLastUsedImage(this);

        if (!a.isEmpty() && !b.isEmpty()){
            textView.setText(a);
            Picasso.get().load(b).into(imageView);
        }

        mQ = Volley.newRequestQueue(this);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSheet();
            }
        });
    }
    boolean isNetworkConnectionAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info == null) return false;
        NetworkInfo.State network = info.getState();
        return (network == NetworkInfo.State.CONNECTED || network == NetworkInfo.State.CONNECTING);
    }
    private void openSheet() {
        if (isNetworkConnectionAvailable()) {
            Toast.makeText(MainActivity.this, "Please wait!!", Toast.LENGTH_SHORT).show();
            List<ItemObject> list = new ArrayList<>();
            String url = "https://api.printful.com/countries";
            JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONArray ja = response.getJSONArray("result");

                                for (int i = 0; i < ja.length(); i++) {
                                    JSONObject res = ja.getJSONObject(i);
                                    String cname = res.getString("name");
                                    String code = res.getString("code");
                                    String dup = "https://www.countryflags.io/" + code + "/flat/64.png";
                                    if (SharedPreferenceService.getLastUsedText(getApplicationContext())!=null && cname.equals(SharedPreferenceService.getLastUsedText(getApplicationContext()))){
                                        itemObject = new ItemObject(cname, dup ,true);
                                    }else {
                                        itemObject = new ItemObject(cname, dup ,false);
                                    }
                                    list.add(itemObject);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            frag = new MyBottomSheetFragment(list, new IClickListner() {
                                @Override
                                public void clickItem(ItemObject itemObject) {
                                    textView.setText(itemObject.getName());
                                    Picasso.get().load(itemObject.getImageUrl()).into(imageView);
                                    SharedPreferenceService.setLastUsedText(MainActivity.this, itemObject.getName());
                                    SharedPreferenceService.setLastUsedImage(MainActivity.this, itemObject.getImageUrl());
                                    frag.dismiss();
                                }
                            });
                            frag.show(getSupportFragmentManager(), frag.getTag());
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i("TAG", "onErrorResponse: " + error);
                }
            });
            mQ.add(req);

        }else {
            Toast.makeText(MainActivity.this, "Check you internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}