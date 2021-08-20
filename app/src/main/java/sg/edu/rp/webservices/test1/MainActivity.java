package sg.edu.rp.webservices.test1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView lv;
    ArrayAdapter aa;
    ArrayList<Location> al;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        al = new ArrayList<Location>();
        lv = findViewById(R.id.listView);
        aa = new ArrayAdapter<Location>(this, android.R.layout.simple_list_item_1, al);
        lv.setAdapter(aa);
        fab = findViewById(R.id.fab);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Location location = (Location) lv.getItemAtPosition(i);
                Double latitude = location.getLatitude();
                Double longitude = location.getLongitude();
                String loc_name = location.getLoc_name();
                Intent intent = new Intent(MainActivity.this, MapActivity.class);


                Bundle bundle = new Bundle();
                bundle.putDouble("latitude", latitude);
                bundle.putDouble("longitude", longitude);
                intent.putExtras(bundle);
                intent.putExtra("loc_name", loc_name);
                startActivity(intent);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuilder.setTitle("Add New Location");

                LinearLayout layout = new LinearLayout(MainActivity.this);
                layout.setOrientation(LinearLayout.VERTICAL);

                final EditText etLatitude = new EditText(MainActivity.this);
                etLatitude.setHint("Latitude");
                layout.addView(etLatitude);

                final EditText etLongitude = new EditText(MainActivity.this);
                etLongitude.setHint("Longitude");
                layout.addView(etLongitude);

                final EditText etLocName = new EditText(MainActivity.this);
                etLocName.setHint("Name");
                layout.addView(etLocName);

                alertDialogBuilder.setView(layout);

                alertDialogBuilder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        if (etLatitude.getText().toString().length() != 0 && etLongitude.getText().toString().length() != 0 && etLocName.getText().toString().length() != 0) {

                            Double latitude = Double.valueOf(etLatitude.getText().toString());
                            Double longitude = Double.valueOf(etLongitude.getText().toString());
                            String loc_name = etLocName.getText().toString();
                            DBHelper dbh = new DBHelper(MainActivity.this);

                            boolean exists = false;
                            for(int i = 0; i <  dbh.getLocations().size(); i++){
                                if (loc_name.equals( dbh.getLocations().get(i).getLoc_name())){
                                    exists = true;
                                }
                            }

                            if (exists == true){
                                Toast.makeText(MainActivity.this, "Location already exists.", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                dbh.insertLocation(latitude, longitude, loc_name);
                                Toast.makeText(MainActivity.this, "Location Added", Toast.LENGTH_SHORT).show();
                                onResume();
                            }
                            dbh.close();
                        }
                        else {
                            Toast.makeText(MainActivity.this, "Please ensure that input is not empty", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                alertDialogBuilder.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        DBHelper dbh = new DBHelper(MainActivity.this);
        al.clear();
        al = dbh.getLocations();
        dbh.close();
        aa = new ArrayAdapter<Location>(this, android.R.layout.simple_list_item_1, al);
        lv.setAdapter(aa);
        aa.notifyDataSetChanged();
    }
}