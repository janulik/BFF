package sk.upjs.ics.bff;


import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class DrinkTestFragment extends Fragment {

    private ImageView drinkTestImage;
    private Button drinktestOKButton;
    private EditText drinkTestEditText;
    private int[] drunkTestImages = {
            R.drawable.drunktest_white,
            R.drawable.drunktest_green

    };
    private int pocet;

    public DrinkTestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View fragmentLayout = inflater.inflate(R.layout.fragment_drink_test2, container, false);
        this.pocet = 0;
        drinkTestImage = (ImageView) fragmentLayout.findViewById(R.id.imgDrinkTest);
        drinktestOKButton = (Button) fragmentLayout.findViewById(R.id.drunkTestButton);
        drinkTestEditText = (EditText) fragmentLayout.findViewById(R.id.drunkTestResponse);
        drinkTestImage.setImageResource(drunkTestImages[0]);
        drinktestOKButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (drinkTestEditText.getText().toString().trim().equalsIgnoreCase("WHITE") && pocet == 0) {
                    Toast.makeText(fragmentLayout.getContext(), "SOM V DOBROM IFE", Toast.LENGTH_LONG)
                            .show();
                    pocet++;
                    drinkTestImage.setImageResource(drunkTestImages[1]);
                } else if (drinkTestEditText.getText().toString().trim().equalsIgnoreCase("GREEN")) {
                    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(fragmentLayout.getContext());
                    mBuilder.setSmallIcon(R.drawable.abc_ic_menu_cut_mtrl_alpha);
                    mBuilder.setContentTitle("Notification Alert, Click Me!");
                    mBuilder.setContentText("HCONGRATULATION, YOU'RE SOBER AND CAPABLE OF YOUR DECISION!");
                    NotificationManager mNotificationManager = (NotificationManager) getActivity().getSystemService((Context.NOTIFICATION_SERVICE));
                    mNotificationManager.notify(1, mBuilder.build());
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(fragmentLayout.getContext(), "YOU'RE TOO DRUNK", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });
        return fragmentLayout;

    }

}
