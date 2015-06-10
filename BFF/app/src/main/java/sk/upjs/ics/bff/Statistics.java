package sk.upjs.ics.bff;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import sk.upjs.ics.bff.provider.Provider;
import sk.upjs.ics.bff.provider.StatisticsContentProvider;
import sk.upjs.ics.bff.util.Defaults;


public class Statistics extends ActionBarActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int STATISTICS_LOADER_ID = 0;

    private SimpleCursorAdapter cursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        getLoaderManager().initLoader(STATISTICS_LOADER_ID, Bundle.EMPTY, this);
        GridView statisticsGridView = (GridView) findViewById(R.id.statisticsGridView);
        statisticsGridView.setAdapter(initializeAdapter());

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_statistics, menu);
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

    private ListAdapter initializeAdapter() {
        String[] from = {Provider.statistics.TIMESTAMP, Provider.statistics.ID_ALCOHOL};
        int[] to = {android.R.id.text1, android.R.id.text2};
        this.cursorAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, Defaults.NO_CURSOR, from, to, Defaults.NO_FLAGS);
        return this.cursorAdapter;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader loaderStatistics = new CursorLoader(this);
        CursorLoader loaderAlcohol = new CursorLoader(this);
        loaderStatistics.setUri(StatisticsContentProvider.CONTENT_URI_STATISTICS);
        loaderAlcohol.setUri(StatisticsContentProvider.CONTENT_URI_ALCOHOL);
        return loaderStatistics;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        int allStatisticsCount = data.getCount();
        data.moveToLast();
        int last = data.getInt(1);
        data.moveToPrevious();
        int previous = data.getInt(1);
        this.cursorAdapter.swapCursor(data);
        //TODO posun ten rozdiel dalej na vypocet promile
        if (data.getCount() > 0) {
            Toast.makeText(this, Integer.toString(last - previous), Toast.LENGTH_SHORT)
                    .show();


        } else {
            Toast.makeText(this, "Neni nic", Toast.LENGTH_SHORT)
                    .show();
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        this.cursorAdapter.swapCursor(Defaults.NO_CURSOR);

    }
}
