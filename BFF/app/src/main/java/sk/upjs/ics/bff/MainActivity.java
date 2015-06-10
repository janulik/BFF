package sk.upjs.ics.bff;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.app.NotificationManager;
import android.content.AsyncQueryHandler;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.PersistableBundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import sk.upjs.ics.bff.provider.Provider;
import sk.upjs.ics.bff.provider.StatisticsContentProvider;
import sk.upjs.ics.bff.util.Defaults;


public class MainActivity extends ActionBarActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int USER_LOADER_ID = 0;
    private static final String BUNDLE_KEY_NAME = "name";
    private static final String BUNDLE_KEY_WEIGHT ="weight";
    private static final String BUNDLE_KEY_NUMBER = "bffNumber";

    private SimpleCursorAdapter adapter;

    private int weight = 0;

    private int id_user;

    private String name;

    private String bffNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getLoaderManager().initLoader(USER_LOADER_ID, Bundle.EMPTY, this);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        SpinnerAdapter spinnerAdapter = (SpinnerAdapter) initializeAdapter();
        spinner.setAdapter((
                new NothingSelectedSpinnerAdapter(
                        spinnerAdapter,
                        R.layout.contact_spinner_row_nothing_selected,
                        // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                        this)));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    Cursor selectedNameCursor = (Cursor) parent.getItemAtPosition(position);
                    weight = selectedNameCursor.getInt(2);
                    id_user = selectedNameCursor.getInt(0);
                    name = selectedNameCursor.getString(1);
                    bffNumber = selectedNameCursor.getString(3);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(parent.getContext(),
                        "OnItemSelectedListener : ",
                        Toast.LENGTH_SHORT).show();
                return;
            }
        });

    }

    private ListAdapter initializeAdapter() {
        String[] from = {Provider.user.NAME, Provider.user.WEIGHT};
        int[] to = {android.R.id.text1, android.R.id.text2};
        this.adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, Defaults.NO_CURSOR, from, to, Defaults.NO_FLAGS);
        return this.adapter;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.statistics) {
            Intent intent = new Intent(this, Statistics.class);
            startActivity(intent);
        }
        if (id == R.id.action_update) {
            updateProfile();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateProfile() {
        final EditText nameEditText = new EditText(this);
        final EditText weightEditText = new EditText(this);
        final EditText numberEditText = new EditText(this);
        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.addView(nameEditText);
        ll.addView(weightEditText);
        ll.addView(numberEditText);
        new AlertDialog.Builder(this)
                .setTitle("Update profile")
                .setView(ll)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = nameEditText.getText().toString();
                        String weight = weightEditText.getText().toString();
                        String number = numberEditText.getText().toString();
                        updateContentProvider(name, weight, number);
                    }
                })
                .setNegativeButton("Cancel", Defaults.DISMISS_ACTION)
                .show();

    }

    private void updateContentProvider(String name, String weight, String number) {
        Uri uri = ContentUris.withAppendedId(StatisticsContentProvider.CONTENT_URI_USER, id_user);
        ContentValues values = new ContentValues();
        values.put(Provider.user.NAME, name);
        values.put(Provider.user.WEIGHT, weight);
        values.put(Provider.user.BFF_NUMBER, number);

        AsyncQueryHandler updateHandler = new AsyncQueryHandler(getContentResolver()) {
            @Override
            protected void onUpdateComplete(int token, Object cookie, int result) {
                Toast.makeText(MainActivity.this, "Note was saved", Toast.LENGTH_SHORT)
                        .show();
            }
        };
        updateHandler.startUpdate(0, Defaults.NO_COOKIE, uri, values, Defaults.NO_SELECTION, Defaults.NO_SELECTION_ARGS);
    }

    public void startClick(View v) {
        Intent intent = new Intent(this, DrinkActivity.class);
        intent.putExtra("USER_WEIGHT", weight);
        startActivity(intent);

    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader loader = new CursorLoader(this);
        loader.setUri(StatisticsContentProvider.CONTENT_URI_USER);
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        this.adapter.swapCursor(data);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

        this.adapter.swapCursor(Defaults.NO_CURSOR);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable(BUNDLE_KEY_NAME, name);
        outState.putSerializable(BUNDLE_KEY_WEIGHT, weight);
        outState.putSerializable(BUNDLE_KEY_NUMBER, bffNumber);

    }
}
