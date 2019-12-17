package prafull.com.radiusagent_uiapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

import android.graphics.*;
import android.graphics.drawable.Drawable;
import android.os.*;
import android.util.Log;
import android.view.*;
import android.widget.*;
import com.squareup.picasso.Picasso;
import org.json.*;
import java.util.*;

public class MainActivity extends AppCompatActivity {
    androidx.appcompat.widget.Toolbar tb;
    TextView title;
    TextView name,country,ridval,freridval,credval;
    CircleImageView civ;
    private String TAG = "MainActivity";

    private TripListAdapter mAdapter;
    private LayoutInflater mInflater;
    private RecyclerView.LayoutManager mLayoutManager;
    private androidx.recyclerview.widget.RecyclerView mRecyclerView;
    private List<Trip> com;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTheme(android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        title=findViewById(R.id.toolbar_title);
        tb = findViewById(R.id.toolbar);

        final Drawable upArrow = getResources().getDrawable(R.drawable.back);
        upArrow.setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.SRC_ATOP);

        tb.setNavigationIcon(R.drawable.back);
        tb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final Drawable dots = getResources().getDrawable(R.drawable.threedots);
        dots.setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.SRC_ATOP);
        tb.inflateMenu(R.menu.menu);
        tb.setOverflowIcon(ContextCompat.getDrawable(getApplicationContext(),R.drawable.threedots));
         tb.getOverflowIcon().setColorFilter(Color.WHITE , PorterDuff.Mode.SRC_ATOP);

         //Binding elements with Static Data
        civ=findViewById(R.id.prfimg);
        name=findViewById(R.id.usrname);
        country=findViewById(R.id.usrsubname);
        ridval=findViewById(R.id.ridtit);
        freridval=findViewById(R.id.frerridtit);
        credval=findViewById(R.id.credtit);

        //Binding with Dynamic Data
        mInflater = LayoutInflater.from(this);
        mRecyclerView =findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(MainActivity.this,1);
        mRecyclerView.setLayoutManager(mLayoutManager);

        new GetData().execute();


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.menuAbout:

                Toast.makeText(this, "You clicked on about page", Toast.LENGTH_SHORT).show();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    //JSON Operation
    private class GetData extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String url = "https://gist.githubusercontent.com/iranjith4/522d5b466560e91b8ebab54743f2d0fc/raw/7b108e4aaac287c6c3fdf93c3343dd1c62d24faf/radius-mobile-intern.json";
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null)
            {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONObject dataobj = jsonObj.getJSONObject("data");

                    JSONArray keyobj = dataobj.names();
                    for (int i = 0; i < keyobj.length(); i++)
                    {
                        String keys = keyobj.getString(i);

                        if (keys.equalsIgnoreCase("profile")) {
                            JSONObject profobj = dataobj.getJSONObject("profile");
                            final String firnam = profobj.getString("first_name");
                            final String profimgurl = profobj.getString("middle_name");
                            final String lastnam = profobj.getString("last_name");
                            final String city = profobj.getString("city");
                            final String count = profobj.getString("Country");

                            runOnUiThread(new Runnable() {
                                public void run() {
                                    Picasso.get().load(profimgurl).into(civ);
                                    name.setText(firnam + " " + lastnam);
                                    country.setText(city + ", " + count);
                                }
                            });
                        }
                        if (keys.equalsIgnoreCase("stats")) {
                            JSONObject profobj = dataobj.getJSONObject("stats");
                            final String rideval = profobj.getString("rides");
                            final String freeridval = profobj.getString("free_rides");

                            JSONObject credobj = profobj.getJSONObject("credits");
                            final String credvalue = credobj.getString("value");
                            final String credsym = credobj.getString("currency_symbol");

                            runOnUiThread(new Runnable() {
                                public void run() {
                                    ridval.setText(rideval);
                                    freridval.setText(freeridval);
                                    credval.setText(credsym + credvalue);
                                }
                            });
                        }
                        if (keys.equalsIgnoreCase("trips")) {
                            final JSONArray triparr = dataobj.getJSONArray("trips");
                            com = new ArrayList<>();
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    try {
                                        operation(triparr);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });

                        }
                        try {
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } catch (Exception e) {
                            Log.e(TAG, "Json parsing error: " + e.getMessage());
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    Toast.makeText(getApplicationContext(),
                            "Json parsing error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                Toast.makeText(getApplicationContext(),
                        "Couldn't get json from server. Check LogCat for possible errors!",
                        Toast.LENGTH_LONG).show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
         }

        private void operation(JSONArray triparr) throws JSONException {
            com.clear();
            try {
                for (int j = 0; j < triparr.length(); j++) {

                    JSONObject tempobj = triparr.getJSONObject(j);

                    String fromvalue = tempobj.getString("from");
                    String tovalue = tempobj.getString("to");
                    String tripdura = tempobj.getString("trip_duration_in_mins");

                    JSONObject costobj = tempobj.getJSONObject("cost");
                    String costvalue = costobj.getString("value");
                    String costsym = costobj.getString("currency_symbol");

                    com.add(new Trip(fromvalue, tovalue, costvalue, costsym, tripdura));
                }
                mAdapter = new TripListAdapter(MainActivity.this, com);
                mRecyclerView.setAdapter(mAdapter);
            } catch (JSONException e) {
                Log.v("JSON Exception:  ", e.getMessage());
                Toast.makeText(MainActivity.this, "Something Went Wrong,Please Contact to Developer/Admin", Toast.LENGTH_LONG).show();
            }
        }
    }
}