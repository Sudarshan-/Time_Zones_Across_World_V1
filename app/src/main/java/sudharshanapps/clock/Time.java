package sudharshanapps.clock;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;

import java.util.HashMap;

import java.util.TimeZone;
import java.util.Calendar;
import java.io.InputStream;
import java.io.Writer;
import java.io.Reader;
import java.io.StringWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import android.app.AlertDialog;


//Importing constants from Constants class
import static sudharshanapps.clock.Constants.FIRST_COLUMN;
import static sudharshanapps.clock.Constants.SECOND_COLUMN;


// I am extending Activity class to create my own activity of displaying time in different parts of world

public class Time extends Activity {

    // Original hour format counter 0 = 24 hour format; 1 = 12 hour format
    private int hour_counter = 0;

    //Public class to driver Adapter customization instead of using arrayadapters etc.
    private TwocolumnAdapter listAdapter;

    //Array Lists, which contains list of timezones under a group
    private HashMap<String,ArrayList<String[]>> stringArrayList = new HashMap<>();

    //List of time zones
    private ArrayList<String> headersList = new ArrayList<>();

    // This is mandatory\default function which fill menu of options
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_time, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        MenuItem _clockformat = menu.findItem(R.id.search_button); // Retrieve reference of Menu from layout

        if(hour_counter == 1){
            _clockformat.setTitle("Time in 24 Hour Format"); // Change Menu text based on old selection
        }else {
            _clockformat.setTitle("Time in 12 Hour Format"); // Change Menu text based on old selection
        }

        return super.onPrepareOptionsMenu(menu);
    }

    // Function invoked when About us option is choosed in Menu
    private void aboutUs() {

        // Create an instance of Alert box
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        //Set a title to Alert box created
        alertDialog.setTitle("About Time across Globe");

        //Prepare list of content to be displayed in list format
        String[] users = {"Version 1.1", "Author:Sudharshan Vaddi", "Contact:feedback@gmail.com"};

        // This function assigns prepared list content to static alert box
        alertDialog.setItems(users, null);

        //This function creates an OK button for instantiated Static alert box
        alertDialog.setPositiveButton("OK", null);

        // Show to user static alert box just now created
        alertDialog.show();
    }

    // Function invoked when settings option is chosen
    private void configure(){

        hour_counter = (hour_counter ==0) ? 1:0;
        //Call Function to include new time format changes
        //listAdapter.ChangeTimeFormat(hour_counter);

    }

    // mandatory \default function created for menus
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        //Select Item in menu based on ID
        switch (item.getItemId()) {
            // Invoke About Us section
            case R.id.action_settings:
                aboutUs();
                break;

            //Invoke Options section
            case R.id.search_button:
                configure();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    //Function used for reading list of timezones from JSON File
    private String readFile(){

        // Select resource(JSON File) using file name
        InputStream is = getResources().openRawResource(R.raw.timezone);

        //Select a handle for writing data as string
        Writer writer = new StringWriter();

        //Read a standard amount each time
        char[] buffer = new char[1024];

        try {
            // Read file using input stream
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } catch (Exception e){
            //Exception handling
            e.printStackTrace();
        }

        //Convert all data to string format
        return writer.toString();

    }

    //Function used to read specific data from JSON string created by readFile() function
    private HashMap<String,ArrayList<String[]>> parseJSONString(String jsonString){


        try {
            // Creating JSONObject from String
            JSONObject jsonObjMain = new JSONObject(jsonString),jsonObj;

            // Creating JSONArray from JSONObject
            JSONArray jsonArray = jsonObjMain.getJSONArray("TimeZones");

            //Strings to hold 24 hour, 12 hour times, AM\PM details, Offset values, Time Zone name and Zone data
            String time24hour, variation,name, zone;
            TimeZone tz;
            Calendar c;
            // HashMaps for twentyfour and twelve hour format


            // JSONArray has four JSONObject
            for (int i = 0; i < jsonArray.length(); i++) {

                ArrayList<String[]> childInfo = new ArrayList<>();

                // Creating JSONObject from JSONArray
                jsonObj = jsonArray.getJSONObject(i);

                // Getting data from individual JSONObject OFFSET Value and time zone name

                variation = jsonObj.getString("offset");
                name = jsonObj.getString("value");

                //Formulating complete GMT Zone value
                zone = "GMT" + variation;

                //Get time of current zone
                tz = TimeZone.getTimeZone(zone);

                //Get current calendar instance
                c = Calendar.getInstance(tz);

                time24hour = String.format("%02d" , c.get(Calendar.HOUR_OF_DAY))+":"+
                         String.format("%02d" , c.get(Calendar.MINUTE));


                String[] twentyfourhour=new String[2]; //Prepare Hashmaps with indices
                twentyfourhour[FIRST_COLUMN] = name;
                twentyfourhour[SECOND_COLUMN] = time24hour;

                //Preparing timezone references
                childInfo.add(twentyfourhour);

                if(!(headersList.contains(zone))){ //Add current zone to headers list
                    headersList.add(zone); // No more duplicates
                }

                //Checking if a zone already exist in hashmap
                ArrayList<String[]> checkKey = stringArrayList.get(zone);

                if(checkKey== null){
                    stringArrayList.put(zone,childInfo); //No zone available so add to arraylist
                }else {
                    checkKey.add(twentyfourhour); // ZOne available, retrieve old zone information
                    stringArrayList.put(zone,checkKey); //add to existing zone information
                }
            }

        } catch (JSONException e) {
            //Exception handling
            e.printStackTrace();
        }


        //returning 24 hour format data by default

        return stringArrayList;
    }


    private int GetPixelFromDips(float pixels) {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale + 0.5f);
    }

    // This is invoked when ListView is created and loaded to system

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Assigning main activity name for drawing
        setContentView(R.layout.activity_time);

        // Find the ListView resource and assign to ListView Object created
        ExpandableListView mainListView = (ExpandableListView) findViewById(R.id.textClock);

        // Initialize adapter with data by parsing JSON
        listAdapter = new TwocolumnAdapter(this, parseJSONString(readFile()),headersList);

        // Set the ArrayAdapter as the ListView's adapter.
        mainListView.setAdapter(listAdapter);

        for(int i=0; i < listAdapter.getGroupCount(); i++) //Get all groups
            mainListView.expandGroup(i); //Expanding each group by default

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;

        mainListView.setIndicatorBounds(width - GetPixelFromDips(50), width - GetPixelFromDips(10));


        //Listener when group is expanded
        mainListView.setOnGroupExpandListener(new OnGroupExpandListener()
        {
            @Override
            public void onGroupExpand(int groupPosition)
            {
               // Log.e("onGroupExpand", "OK");
            }
        });

        mainListView.setOnGroupCollapseListener(new OnGroupCollapseListener()
        {
            @Override
            public void onGroupCollapse(int groupPosition)
            {
                //Log.e("onGroupCollapse", "OK");
            }
        });


    }

}
