package smikhlevskiy.countrylist.threads;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

import smikhlevskiy.countrylist.R;
import smikhlevskiy.countrylist.model.Country;
import smikhlevskiy.countrylist.model.CountrysDB;


/**
 * Created by "SMikhlevskiy" on 09-Feb-16.
 */
public class GetCountrysAsyncTask extends AsyncTask<Void, Void, ArrayList<Country>> {
    public static final String TAG = GetCountrysAsyncTask.class.getSimpleName();
    private WeakReference<Context> context;
    private WeakReference<Handler> resultHandler;

    public GetCountrysAsyncTask(Context context, Handler resultHandler) {
        this.context = new WeakReference<Context>(context);
        this.resultHandler = new WeakReference<Handler>(resultHandler);
    }

    /**
     * reads List of Countrys from XML in resource
     *
     * @return
     */
    public ArrayList<Country> getCountrysFromResource() {
        ArrayList<Country> countrys = new ArrayList<Country>();

        if (context.get() == null) return countrys;

        XmlResourceParser xpp = ((Context) context.get()).getResources().getXml(R.xml.countrys);

        try {

            int event = xpp.getEventType();

            while (event != XmlPullParser.END_DOCUMENT) {
                switch (event) {
                    case XmlPullParser.START_TAG:
                        String name = xpp.getName();

                        if (name.equals("Country")) {
                            Country country = new Country();
                            country.setCode(xpp.getAttributeValue(null, "Code"));
                            country.setName(xpp.getAttributeValue(null, "Name"));
                            countrys.add(country);

                        }

                        break;
                    case XmlPullParser.END_TAG:

                        break;
                    case XmlPullParser.TEXT:

                        break;

                }
                event = xpp.next();
            }


        } catch (
                XmlPullParserException e
                )

        {
            e.printStackTrace();

        } catch (
                IOException e
                )

        {
            e.printStackTrace();

        }

        return countrys;
    }

    @Override
    protected ArrayList<Country> doInBackground(Void... params) {

        if (context.get() == null) return null;

        CountrysDB countrysDB = new CountrysDB((Context) context.get(), CountrysDB.DB_NAME, null, CountrysDB.DB_VERSION);

        Log.i(TAG, "Read Countrys from DB");
        ArrayList<Country> countries = countrysDB.getCountrys();

        if ((countries == null) || (countries.size() <= 0)) {

            //    Toast.makeText(TAG, "DB was empty: Read Countrys from Resource");
            countries = getCountrysFromResource();

            Log.i(TAG, "Save Countrys to DB: "+countries.size());
            countrysDB.setCountrys(countries);


        }

        return countries;
    }

    @Override
    protected void onPostExecute(ArrayList<Country> countries) {

        if ((countries != null) && (resultHandler.get() != null)) {
            Message message = new Message();
            message.what = 1;
            message.obj = countries;
            resultHandler.get().handleMessage(message);
        }
        super.onPostExecute(countries);
    }
}
