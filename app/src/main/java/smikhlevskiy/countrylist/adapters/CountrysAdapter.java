package smikhlevskiy.countrylist.adapters;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;

import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


import smikhlevskiy.countrylist.MainActivity;
import smikhlevskiy.countrylist.R;
import smikhlevskiy.countrylist.model.Country;

/**
 * Created by "SMikhlevskiy" on 09-Feb-16.
 */


public class CountrysAdapter extends BaseAdapter {


    private ArrayList<Country> countries = null;
    private Context context;

    public CountrysAdapter(Context context){
        this.context=context;
    }

    public void setCountys(ArrayList<Country> countries) {
        this.countries = countries;
    }

    @Override
    public int getCount() {
        if (countries != null) return countries.size();
        else return 0;
    }

    @Override
    public Object getItem(int position) {
        return countries.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }



    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater lInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = lInflater.inflate(R.layout.menu_item, parent, false);


        }
        Country country=(Country)getItem(position);
        if (country!=null) {
            RelativeLayout rl=((RelativeLayout) convertView.findViewById(R.id.pen_button));
            rl.setTag(position);
            rl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Country c = (Country) getItem(position);
                    Toast.makeText(context, c.getName() + " " + c.getCode(), Toast.LENGTH_LONG).show();
                }
            });

            rl=((RelativeLayout) convertView.findViewById(R.id.delete_button));
            rl.setTag(position);
            rl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Country c= (Country) getItem(position);
                    //Toast.makeText(context,c.getName()+" "+c.getCode(),Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(context, MainActivity.class);

                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);//one activity work
                    PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent, 0);


                    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                            .setContentTitle(context.getString(R.string.countrylist))
                            .setContentText(c.getName()+" "+c.getCode())
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentIntent(pIntent);
                    Notification noti = mBuilder.build();


                    NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);

                    noti.flags |= Notification.FLAG_AUTO_CANCEL;// hide the notification after its selected


                    notificationManager.notify(0, noti);

                }
            });


            TextView textName = (TextView) convertView.findViewById(R.id.itemName);
            textName.setText(country.getName());
            TextView textCode = (TextView) convertView.findViewById(R.id.itemCode);
            textCode.setText(country.getCode());


        }
/*
        ContactsContract.CommonDataKinds.Organization organization = (ContactsContract.CommonDataKinds.Organization) getItem(position);

        TextView textName = (TextView) convertView.findViewById(R.id.itemName);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.image);
        imageView.setImageResource(UAFConst.getBankImage(organization.getOrgType(),organization.getTitle()));


        String title=organization.getTitle();
        for (int n = 0; n < UAFConst.banksLc.length; n++)
            if (title.toLowerCase().contains(UAFConst.banksLc[n]))
                title = UAFConst.banks[n];

        textName.setText(title);

        TextView textDistance = (TextView) convertView.findViewById(R.id.itemDistance);
        textDistance.setVisibility(View.INVISIBLE);
        if (deviceLocation != null) {
            Location locationOrganization = new Location(organization.getTitle());

            LatLng latLng = geoLocationDB.getLocation(UAFConst.getAddressbyAdressCity(financeUA.getCities().get(organization.getCityId()), organization.getAddress()));
            if (latLng != null) {
                locationOrganization.setLongitude(latLng.longitude);
                locationOrganization.setLatitude(latLng.latitude);


                float distance = locationOrganization.distanceTo(deviceLocation) / 1000;
                textDistance.setText(new DecimalFormat("####.#").format(distance) + " " + context.getString(R.string.km));
                textDistance.setVisibility(View.VISIBLE);
            }
        }


        //UAFPreferences.getAskBid(), UAFPreferences.getCity(), UAFPreferences.getCurrancie()


        TextView textCurrancie = (TextView) convertView.findViewById(R.id.itemCurrencie);
        textCurrancie.setText(new Double(organization.getCurrentCurrencyVal()).toString());
*/

        return convertView;
    }
}
