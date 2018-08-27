package jsouptutorial.androidbegin.com.jsouptutorial;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.Calendar;
import java.util.Random;

import static jsouptutorial.androidbegin.com.jsouptutorial.MainActivity.SaveInPreference;

class SalatTimes{
    public String fajr_athan = "", thuhr_athan = "", asr_athan = "", maghrib_athan = "", isha_athan = "";
    public String fajr_iqama = "", thuhr_iqama = "", asr_iqama = "", maghrib_iqama = "", isha_iqama = "";

    public int _hour, _second, _minute, _month, _date, _year, _am_pm;

    public boolean is_empty(){
        if(fajr_athan == ""){
            return true;
        }
        return false;
    }

    public boolean is_stale(){
        Calendar calendar = Calendar.getInstance();
        int date, month, year;
        date = calendar.get(Calendar.DATE);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);

        if(year == _year){
            if(month == _month){
                if(date == _date){
                    return false;
                }
            }
        }
        return true;
    }
}


class Hadith{
    public String hadith_title = "", hadith_text = "", hadith_source = "1";
    public String hadith_url = "http://sunnah.com/bukhari/";

    public boolean is_empty(){
        if(hadith_text == "" && hadith_title == ""){
            return true;
        }
        return false;
    }
}

/*
 *
 *
 *
 *
 */
public class GrabSalatTimes {
    // to save the salat times data
    private static final String PREFS_NAME = "MyPrefsFile";

    String url = "http://alfarooqmasjid.org";
    private SalatTimes salat_times = new SalatTimes();
    private Hadith hadith = new Hadith();

    private Context _context;

    // String for hadith source (default)
    private String Hadith_source = Integer.toString(1);
    private String hadith_url = "http://sunnah.com/bukhari/"; // + String.valueOf(hadith_book_number);
    private String hadith_title, hadith_text;


    public GrabSalatTimes(Context context){
        _context = context;
        this.hadith.hadith_source = Hadith_source;
        this.hadith.hadith_url = hadith_url;
    }

    /*
    *
    *
    *
    *
    */

    public SalatTimes get_salat_times() {
        if (salat_times.is_empty()){
            new grab_latest_times(salat_times, _context).execute();
        }
        else if(salat_times.is_stale()){
            new grab_latest_times(salat_times, _context).execute();
        }
        return salat_times;
    }

    public Hadith get_hadith(){
        if (hadith.is_empty()){
            new grab_latest_hadiths(this.hadith).execute();
        }
        else{
            new grab_latest_hadiths(this.hadith).execute();
        }
        return hadith;
    }

    /*
     *
     *
     *
     *
     */

    private class grab_latest_times extends AsyncTask<Void, Void, Void> {
        private SalatTimes salat_times;
        private Context contex;
        public grab_latest_times(SalatTimes salat_times, Context context){
            this.salat_times = salat_times;
            this.contex = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            ProgressDialog progress_bar = new ProgressDialog(MainActivity.context);
//            progress_bar.setTitle("Fetching Salat Times Now");
//            progress_bar.setMessage("Loading...");
//            progress_bar.setIndeterminate(false);
//            progress_bar.show();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            String TAG = "Getting Title";
            try {
                // Url of alfarooq website
                String url = "http://alfarooqmasjid.org";
                // Fetch the html webpage
                Document doc = Jsoup.connect(url).get();
                // get the title of the url
                String Title = doc.title();
                // print it out
                System.out.println("Title: " + Title);
                // Get the table of data from the url
                Elements tbody = doc.select("tbody");
                // print it out
                System.out.println("tbody.first: " + tbody.first());

                // get the table of salat times
                Elements tr = tbody.select("tr");
                // from the table, we get the first element
                Elements Fajr = tr.eq(0);
                // now we get the time
                salat_times.fajr_athan= (Fajr.select("td").eq(1).text());
                salat_times.fajr_iqama = (Fajr.select("td").eq(2).text());


                Elements Zuhr = tr.eq(1);
                salat_times.thuhr_athan = (Zuhr.select("td").eq(1).text());
                salat_times.thuhr_iqama = (Zuhr.select("td").eq(2).text());
                Elements Asr = tr.eq(2);
                salat_times.asr_athan = (Asr.select("td").eq(1).text());
                salat_times.asr_iqama = (Asr.select("td").eq(2).text());
                Elements Maghrib = tr.eq(3);
                salat_times.maghrib_athan = (Maghrib.select("td").eq(1).text());
                salat_times.maghrib_iqama = (Maghrib.select("td").eq(2).text());
                Elements Isha = tr.eq(4);
                salat_times.isha_athan = (Isha.select("td").eq(1).text());
                salat_times.isha_iqama = (Isha.select("td").eq(2).text());




                Context settings = _context;
                SaveInPreference(settings, "fajr_start_time", "100");

                // Now we will save the times...
                SharedPreferences saved_salat_times = _context.getSharedPreferences(PREFS_NAME, 0);
                SharedPreferences.Editor editor = saved_salat_times.edit();
                editor.putString("fajr_start_time", (salat_times.fajr_athan));
                editor.putString("fajr_iqama_time", (salat_times.fajr_iqama));
                editor.putString("zuhr_start_time", (salat_times.thuhr_athan));
                editor.putString("zuhr_iqama_time", (salat_times.thuhr_iqama));
                editor.putString("asr_start_time", (salat_times.asr_athan));
                editor.putString("asr_iqama_time", (salat_times.asr_iqama));
                editor.putString("maghrib_start_time", (salat_times.maghrib_athan));
                editor.putString("maghrib_iqama_time", (salat_times.maghrib_iqama));
                editor.putString("isha_start_time", (salat_times.isha_athan));
                editor.putString("isha_iqama_time", (salat_times.isha_iqama));

                // For the times

                salat_times._second = Calendar.getInstance().get(Calendar.SECOND);
                salat_times._minute = Calendar.getInstance().get(Calendar.MINUTE);
                salat_times._hour = Calendar.getInstance().get(Calendar.HOUR);
                salat_times._date = Calendar.getInstance().get(Calendar.DATE);
                salat_times._month = Calendar.getInstance().get(Calendar.MONTH);
                salat_times._year= Calendar.getInstance().get(Calendar.YEAR);

                Calendar now = Calendar.getInstance();
                if(now.get(Calendar.AM_PM) == Calendar.AM){
                    // AM
                    salat_times._am_pm = 0;
                }else{
                    // PM
                    salat_times._am_pm = 1;
                }




                editor.putInt("hour", salat_times._hour);
                editor.putInt("minute", salat_times._minute);
                editor.putInt("second", salat_times._second);
                editor.putInt("Month", salat_times._month);
                editor.putInt("Date", salat_times._date);
                editor.putInt("Year", salat_times._year);
                editor.putInt("AmPm", salat_times._am_pm);

                // for user settings on font style and hadith source

                editor.putString("saved_hadith_source",Hadith_source);

                editor.apply();

            } catch (IOException e) {
                e.printStackTrace();
                Log.d(TAG, "... Failed");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }



    }

    private class grab_latest_hadiths extends AsyncTask<Void, Void, Void>{
        private Hadith hadith;
        String TAG = "Getting Title";

        public grab_latest_hadiths(Hadith hadith){
            this.hadith = hadith;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            String TAG = "Getting Title";
            try {
                Log.e("DEBUG_Hadith_source",hadith.hadith_source);

                // Fetch the html webpage using one of the below strings:
                if (hadith.hadith_source.equals(Integer.toString(1))) {
                    hadith.hadith_url = "http://sunnah.com/bukhari/";// + String.valueOf(hadith_book_number);
                } else if (hadith.hadith_source.equals(Integer.toString(2))) {
                    hadith.hadith_url = "http://sunnah.com/muslim/";// + String.valueOf(hadith_book_number);
                } else if (hadith.hadith_source.equals(Integer.toString(3))) {
                    hadith.hadith_url = "http://sunnah.com/nasai/";// + String.valueOf(hadith_book_number);
                } else if (hadith.hadith_source.equals(Integer.toString(4))) {
                    hadith.hadith_url = "http://sunnah.com/abudawud/";// + String.valueOf(hadith_book_number);
                } else if (hadith.hadith_source.equals(Integer.toString(5))) {
                    hadith.hadith_url = "http://sunnah.com/tirmidhi/";// + String.valueOf(hadith_book_number);
                } else if (hadith.hadith_source.equals(Integer.toString(6))) {
                    hadith.hadith_url = "http://sunnah.com/ibnmajah/";// + String.valueOf(hadith_book_number);
                } else if (hadith.hadith_source.equals(Integer.toString(7))) {
                    hadith.hadith_url = "http://sunnah.com/malik/";// + String.valueOf(hadith_book_number);
                } else if (hadith.hadith_source.equals(Integer.toString(8))) {
                    hadith.hadith_url = "http://sunnah.com/nawawi40/";// + String.valueOf(hadith_book_number);
                } else if (hadith.hadith_source.equals(Integer.toString(9))) {
                    hadith.hadith_url = "http://sunnah.com/riyadussaliheen/";// + String.valueOf(hadith_book_number);
                } else if (hadith.hadith_source.equals(Integer.toString(10))) {
                    hadith.hadith_url = "http://sunnah.com/adab/";// + String.valueOf(hadith_book_number);
                } else if (hadith.hadith_source.equals(Integer.toString(11))) {
                    hadith.hadith_url = "http://sunnah.com/qudsi40/";// + String.valueOf(hadith_book_number);
                } else if (hadith.hadith_source.equals(Integer.toString(12))) {
                    hadith.hadith_url = "http://sunnah.com/shamail/";// + String.valueOf(hadith_book_number);
                } else if (hadith.hadith_source.equals(Integer.toString(13))) {
                    hadith.hadith_url = "http://sunnah.com/bulugh/";// + String.valueOf(hadith_book_number);
                }


                // debug statement to see how many chapters we have
//                String debug_hadith_url = "http://sunnah.com/bukhari/";

                // lazy me
                String debug_hadith_url = hadith_url;
                Document debug_hadith_doc = Jsoup.connect(debug_hadith_url).get();


                Elements book_num_classes = debug_hadith_doc.getElementsByClass("book_number");


                // Now get size of class -> bound of max random number
                int max_book_size = book_num_classes.size();

                // debug output
                Log.e("DEBUG_max_book_size",Integer.toString(max_book_size));


                Random hadith_book = new Random();

                // now we generate the random number for the hadith volume
                // sometimes we get a hadith_book_number = 0, which doesn't exist
                int hadith_book_number = hadith_book.nextInt(max_book_size - 1) + 1;

                // debug output
                Log.e("DEBUG_hadith_book_num", Integer.toString(hadith_book_number));


                // now we regenerate url with new book volume number

                debug_hadith_url = debug_hadith_url + Integer.toString(hadith_book_number);
//                hadith_url = debug_hadith_url;
                // debug output
                Log.e("DEBUG_url",debug_hadith_url);


                // now we look for echapno for chapter number
                // first reload document with chapter
                debug_hadith_doc = Jsoup.connect(debug_hadith_url).get();


                Elements chapeter_numbers = debug_hadith_doc.getElementsByClass("echapno");
                int max_chapter_size = chapeter_numbers.size();

                // debug output
                Log.e("DEBUG_max_chapter_size",Integer.toString(max_chapter_size));


                Random chapter_number = new Random();
                int chapter_number_selected = chapter_number.nextInt(max_chapter_size - 1) + 1;

                // debug output
                Log.e("DEBUG_chapter_number_se",Integer.toString(chapter_number_selected));

                // final update of hadith url
                debug_hadith_url+= "/" + Integer.toString(chapter_number_selected);

                // debug output
                Log.e("DEBUG_hadith_url",debug_hadith_url);


//
//                String book_num =hadith_doc.getElementsByClass("book_number").text();
//                Log.e("DEBUG",book_num);

                // generate random number to pick up hadith.

//                Random hadith_number_random = new Random();
                // hadith_number_random.nextInt(7);

//                int hadith_number = (hadith_number_random.nextInt(7));

                // Hadith title

                //int hadith_number =1;
//                hadith_title = hadith_classes.eq(hadith_number).eq(0).eq(0).text();


                Document hadith_doc = Jsoup.connect(debug_hadith_url).get();
                // get the title of the url
                String title = hadith_doc.title();
                // print it out
                System.out.println("Title: " + title);
                // Get the table of data from the url
                Elements hadith_body = hadith_doc.select("div");
                // print it out
                Elements hadith_classes = hadith_doc.getElementsByClass("hadith_narrated");

                Log.e("DEBUG_hadith_classes",hadith_classes.toString());


                hadith_title = hadith_classes.text();
                Log.e("DEBUG_hadith_title",hadith_title);



//                hadith_title = hadith_classes.eq(chapter_number_selected).eq(0).eq(0).text();

                //System.out.println("hadith 1:  " + hadith_title);


                Elements hadith_texts = hadith_doc.getElementsByClass("text_details");

                hadith_text = hadith_texts.text();



//                Log.e("DEBUG_hadith_doc",hadith_doc.toString());

                Log.e("DEBUG_hadith_text",hadith_text);

                hadith.hadith_title = hadith_title;
                hadith.hadith_text = hadith_text;

//                hadith_text = hadith_texts.eq(chapter_number_selected).eq(0).eq(0).text();
            } catch (Exception e){
                e.printStackTrace();
                Log.d(TAG, "... Failed");

                //throw new RuntimeException(e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

            // getting hadith

            // problem: some book volumes have different number of chapters, and different volumes.
            // to find number of volumes -> search for book_number
            // to find number of chapters -> search for echapno
            // then generate random number of bounded by max size of volume and chapter number.

            // so first find website
            // next find maxs
            // then generate bounded random number
            // then finally display

            // alternative is to look at each volume, then chapter to obtain list of maximum bounds


//                Random hadith_book = new Random();
//                int hadith_book_number = hadith_book.nextInt(97);

//                String hadith_url = "http://sunnah.com/bukhari/"; // + String.valueOf(hadith_book_number);


            // problem: when app is run before configuring setting, it will crash here
            // so we will try to catch it first

        }
    }


