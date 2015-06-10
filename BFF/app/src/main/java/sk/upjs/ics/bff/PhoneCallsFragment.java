package sk.upjs.ics.bff;


import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import sk.upjs.ics.bff.util.Defaults;


/**
 * A simple {@link Fragment} subclass.
 */
public class PhoneCallsFragment extends Fragment {

    private static final int LOADER_ID = 0;
    private ListView contacts;
    private SimpleCursorAdapter adapter;
    public TextView outputText;


    public PhoneCallsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View fragmentLayout = inflater.inflate(R.layout.fragment_phone_calls, container, false);

        String[] adapterData = {"0905223223", "0905123456", "112", "055123456"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(fragmentLayout.getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, adapterData);

        contacts = (ListView) fragmentLayout.findViewById(R.id.ListViewContacts);
        contacts.setAdapter(adapter);

        return fragmentLayout;
    }


}
