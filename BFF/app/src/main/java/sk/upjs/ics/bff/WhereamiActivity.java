package sk.upjs.ics.bff;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;


public class WhereamiActivity extends ActionBarActivity implements LocationListener {

    private LocationManager locationManager;
    private static final long TEN_SECONDS = 10 * 1000;
    private static final float ONE_HUNDRED_METERS = 100f;
    private static final boolean ONLY_ENABLED_LOCATION_PROVIDERS = true;
    private String locationProviderName;
    public static final String NO_PROVIDER = null;
    private TextView distanceTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whereami);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        this.distanceTextView = (TextView) findViewById(R.id.distanceTextView);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_whereami, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLocationChanged(Location location) {

      /*  Location kosiceLocation = new Location(NO_PROVIDER);
        kosiceLocation.setLatitude(48.697265);
        kosiceLocation.setLongitude(21.2644253429128);

        float distanceInMeters = kosiceLocation.distanceTo(location);

        DecimalFormat distanceFormatter = new DecimalFormat("#.# km");
        this.distanceTextView.setText(distanceFormatter.format(distanceInMeters / 1000));*/

    }

    @Override
    protected void onResume() {
        super.onResume();
        // locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10 * 1000, 100 /* metrov */, this);
        requestLocationUpdates();
    }

    private void requestLocationUpdates() {
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);


        //locationProviderName = locationManager.getBestProvider(criteria, ONLY_ENABLED_LOCATION_PROVIDERS);
        Toast.makeText(this, Integer.toString(locationManager.getAllProviders().size()), Toast.LENGTH_SHORT)
                .show();
        //locationManager.requestLocationUpdates(locationProviderName, TEN_SECONDS, ONE_HUNDRED_METERS, this);

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        requestLocationUpdates();

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
