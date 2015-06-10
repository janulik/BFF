package sk.upjs.ics.bff;

import android.app.LoaderManager;
import android.content.AsyncQueryHandler;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import sk.upjs.ics.bff.provider.Provider;
import sk.upjs.ics.bff.provider.StatisticsContentProvider;
import sk.upjs.ics.bff.util.Defaults;


public class DrinkActivity extends ActionBarActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int ALCOHOL_LOADER_ID = 0;

    private static final int INSERT_NOTE_TOKEN = 0;

    private SimpleCursorAdapter cursorAdapter;

    private int numberOfClicks = 0;

    private int acloholId;

    private int volumeOfAlcohol;

    private int weight;

    private double promile;

    private TextView lblCurrentWeight;

    private TextView lblCurrentPromile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);

        lblCurrentWeight = (TextView) findViewById(R.id.lblCurrentWeight);
        lblCurrentPromile = (TextView) findViewById(R.id.lblCurrentPromile);

        weight = (int) getIntent().getIntExtra("USER_WEIGHT", 0);


        lblCurrentWeight.setText(Integer.toString(weight));

        getLoaderManager().initLoader(ALCOHOL_LOADER_ID, Bundle.EMPTY, this);
        GridView alcoholGridView = (GridView) findViewById(R.id.gridviewDrinkActivity);
        alcoholGridView.setAdapter(initializeAdapter());
        alcoholGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                numberOfClicks++;
                Toast.makeText(parent.getContext(), "CHeers", Toast.LENGTH_SHORT)
                        .show();
                Cursor selectedAlcoholCursor = (Cursor) parent.getItemAtPosition(position);
                acloholId = selectedAlcoholCursor.getInt(0);
                volumeOfAlcohol = selectedAlcoholCursor.getInt(2);

                //lebo ja pocitam ze jeden klik je jeden poldecak a vzorec je na decaky

                promile = (numberOfClicks * volumeOfAlcohol * 0.8) / weight;
                lblCurrentPromile.setText(Double.toString(promile));


                Uri uri = StatisticsContentProvider.CONTENT_URI_STATISTICS;
                ContentValues values = new ContentValues();
                values.put(Provider.statistics.ID_ALCOHOL, acloholId);

                AsyncQueryHandler insertHandler = new AsyncQueryHandler(getContentResolver()) {
                    @Override
                    protected void onInsertComplete(int token, Object cookie, Uri uri) {
                        Toast.makeText(DrinkActivity.this, "Note was saved", Toast.LENGTH_SHORT)
                                .show();
                    }
                };

                insertHandler.startInsert(INSERT_NOTE_TOKEN, Defaults.NO_COOKIE, uri, values);
            }
        });


    }

    private ListAdapter initializeAdapter() {

        String[] from = {Provider.alcohol.NAME, Provider.alcohol.VOLUME};
        int[] to = {android.R.id.text1, android.R.id.text2};
        this.cursorAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, Defaults.NO_CURSOR, from, to, Defaults.NO_FLAGS);
        return this.cursorAdapter;

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_drink, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.skip) {
            Intent intent = new Intent(this, HaveFunActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader loader = new CursorLoader(this);
        loader.setUri(StatisticsContentProvider.CONTENT_URI_ALCOHOL);
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        this.cursorAdapter.swapCursor(data);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        this.cursorAdapter.swapCursor(Defaults.NO_CURSOR);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("weight", (String) lblCurrentWeight.getText());
    }
}
