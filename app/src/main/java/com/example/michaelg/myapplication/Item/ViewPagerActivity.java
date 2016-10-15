package com.example.michaelg.myapplication.Item;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.michaelg.myapplication.Fragments.Params;
import com.example.michaelg.myapplication.Item.discreteseekbar.DiscreteSeekBar;
import com.example.michaelg.myapplication.ListviewActivity.Book;
import com.example.michaelg.myapplication.R;
import com.example.michaelg.myapplication.Item.zoomable.ZoomableDraweeView;
import com.example.michaelg.myapplication.SignUp;
import com.example.michaelg.myapplication.Trivia.AddQuestion;
import com.example.michaelg.myapplication.User;
import com.example.michaelg.myapplication.favorites.Fragments.BookGridFragment;
import com.example.michaelg.myapplication.favorites.adapter.BookGridAdapter;
import com.facebook.common.logging.FLog;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ProgressBarDrawable;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;

public class ViewPagerActivity extends AppCompatActivity{
    private final String _ADD_FAV_URL_="http://52.29.110.203:8080/LibArab/favorites/addToFavorites?";
    private final String _REMOVE_FAV_URL_="http://52.29.110.203:8080/LibArab/favorites/removeFromFavorites?";

    private final String TAG =this.getClass().getSimpleName();
    List<String> pagesStr = new ArrayList<String>();
    private String ID = "NNL_ALEPH003157499";
    private String userId= "100";
    private String weblink;
    private String usertype;
    private ViewItemTask mAuthTask = null;
    int i = 0;
    int j = 0;
    int isNoPages =0;
    boolean isJump = false;
    ViewPager vpGallery;
    EditText etchange;
    TextView textView1;
    ImageView mybg;
    private Book book;
    private String type;
    private ArrayList<Integer> favoritePages;
    private ArrayList<com.example.michaelg.myapplication.favorites.bean.Book> bookList;
    private BookGridAdapter bookGridAdapter; // Data Adapter
    ZoomableDraweeView view2;


    public void bookinfo(View v){

        Intent bookinfoactivity = new Intent(this,BookinfoActivity.class);
        bookinfoactivity.putExtra("creationdate" ,book.getCreationdate());
        bookinfoactivity.putExtra("title"        ,book.getTitle());
        bookinfoactivity.putExtra("author"       ,book.getAuthor());
        bookinfoactivity.putExtra("webLink"      ,book.getWeblink());
        bookinfoactivity.putExtra("publisher"    ,book.getPublisher());
        bookinfoactivity.putExtra("source"       ,book.getSource());
        startActivity(bookinfoactivity);
    }

    public void changepage(View v){

        String stringnumber = etchange.getText().toString();

        if (!(stringnumber.matches(""))) {
            vpGallery.setCurrentItem(Integer.parseInt(stringnumber) - 1);
            if(Integer.parseInt(stringnumber)>pagesStr.size()){
                textView1.setText(pagesStr.size() + "/" + pagesStr.size());
            }
            else {
                textView1.setText(stringnumber + "/" + pagesStr.size());
            }
            isJump = true;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        FLog.setMinimumLoggingLevel(FLog.VERBOSE);
        setContentView(R.layout.activity_view_pager);
        MaterialFavoriteButton lovebutton = (MaterialFavoriteButton) findViewById(R.id.lovebutton);
        vpGallery = (ViewPager) findViewById(R.id.vp_gallery);
        mybg  =    (ImageView) findViewById(R.id.bg);
        textView1=(TextView) findViewById(R.id.textView);
        textView1.setTextSize(20);
        bookList = new ArrayList<com.example.michaelg.myapplication.favorites.bean.Book>();
        favoritePages = new ArrayList<Integer>();
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            ID  = extras.getString("recordId");
            userId=extras.getString("userId");
            usertype = extras.getString("usertype");
            type= extras.getString("type");
            weblink      = extras.getString("webLink");
        }

        ImageView addbutton = (ImageView) findViewById(R.id.add_question_button);
        // if usertype is a guest user should be 0
        if(usertype.equals("0")){
            addbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(ViewPagerActivity.this, R.string.login_to_access, Toast.LENGTH_SHORT).show();
                }
            });

            lovebutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(ViewPagerActivity.this, R.string.login_to_access, Toast.LENGTH_SHORT).show();
                }
            });

        }else{
            addbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addQuestion(v);
                }
            });

            lovebutton.setOnFavoriteChangeListener(
                    new MaterialFavoriteButton.OnFavoriteChangeListener() {
                        @Override
                        public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                            if (favorite) {
                                Toast.makeText(getApplicationContext(),(vpGallery.getCurrentItem()+1) +"",Toast.LENGTH_LONG).show();
                                Uri builtUri =  Uri.parse(_ADD_FAV_URL_).buildUpon()
                                        .appendQueryParameter("username",    userId)


                                        //////////////////THIS WAS COMMENTED I DIDNT TOUCH IT (EMIL)
                                        // .appendQueryParameter("title",    title.getText().toString())
                                        .appendQueryParameter("bibId",    "0")
                                        .appendQueryParameter("itemId", ID)
                                        .appendQueryParameter("type", type)
                                        .appendQueryParameter("pagelink", pagesStr.get(vpGallery.getCurrentItem())+"")
                                        .appendQueryParameter("pagenum", (vpGallery.getCurrentItem()+1)+"")
                                        .appendQueryParameter("desc", "No Description")

                                        ///////////// THIS IS WHAT I COMMENTED (EMIL)
                                        //.appendQueryParameter("title", book.getTitle()+"")
                                        //.appendQueryParameter("author", book.getAuthor())
                                        //.appendQueryParameter("publisher", book.getPublisher())
                                        //.appendQueryParameter("creationDate", book.getCreationdate())


                                        ///////////// THIS WAS COMMENTED... I DIDNT TOUCH THEM (EMIL)
                                        //.appendQueryParameter("source", book.getSource())
                                        //.appendQueryParameter("other", book.getOther())
                                        .build();
                                Log.v(TAG + "ADDFAVURL", builtUri.toString());
                                FavoritesTask fav = new FavoritesTask(builtUri.toString());
                                fav.execute((Void) null);

                            } else {

                                Toast.makeText(getApplicationContext(),vpGallery.getCurrentItem() +"",Toast.LENGTH_LONG).show();
                                Uri builtUri =  Uri.parse(_REMOVE_FAV_URL_).buildUpon()
                                        .appendQueryParameter("userId",    userId)
                                        // .appendQueryParameter("title",    title.getText().toString())
                                        .appendQueryParameter("bibId",    "0")
                                        .appendQueryParameter("itemId", ID)
                                        .appendQueryParameter("pagenum", (vpGallery.getCurrentItem()+1)+"")
                                        .build();
                                Log.v(TAG + "REMOVEFAVURL", builtUri.toString());
                                FavoritesTask fav = new FavoritesTask(builtUri.toString());
                                fav.execute((Void) null);
                            }
                        }
                    });
        }

        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(ViewPagerActivity.this);
                alertDialog.setTitle(R.string.jump_to_page);
                alertDialog.setMessage(R.string.enter_page);

                etchange = new EditText(ViewPagerActivity.this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                etchange.setInputType(InputType.TYPE_CLASS_NUMBER);
                etchange.setLayoutParams(lp);
                alertDialog.setView(etchange);
                alertDialog.setPositiveButton("GO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        changepage(v);
                    }
                });

                alertDialog.show();
            }
        });
        // TODO: 13/10/2016 Review Code again ( Michael ) 
        //SHEET
        if(type.equals("sheet")){

            //add sheet page linke
            pagesStr.add(weblink);
           // vpGallery.setVisibility(View.GONE);
           // TextView textView9=(TextView) findViewById(R.id.textView13);
            //textView9.setVisibility(View.GONE);
            ImageView imageView = (ImageView) findViewById(R.id.imageView2);
           // Picasso.with(getApplicationContext()).load(weblink).into(imageView);
           // imageView.setVisibility(View.VISIBLE);
          //  textView1.setText( 1+"/"+1);
            view2= new ZoomableDraweeView(imageView.getContext());
            view2.setController(
                    Fresco.newDraweeControllerBuilder()
                            .setUri(Uri.parse(weblink))
                            .build());
            GenericDraweeHierarchy hierarchy =
                    new GenericDraweeHierarchyBuilder(getApplicationContext().getResources())
                            .setActualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER)
                            .setProgressBarImage(new ProgressBarDrawable())
                            .build();
            view2.setHierarchy(hierarchy);

            book = new Book();

            mAuthTask = new ViewItemTask(ID, userId);
            mAuthTask.execute((Void) null);
            getfavorites();
            /*getFavoritesTask getfavtask = new getFavoritesTask();
            getfavtask.execute((Void) null);*/
            for(int i = 0 ; i < bookList.size() -1 ; i++){
                if(bookList.get(i).getBookid() == ID){
                    favoritePages.clear();
                    favoritePages = bookList.get(i).getPageList();
                    break;
                }
            }
            return;
        }
        //BOOK OR MAP
        else {
            book = new Book();
            mAuthTask = new ViewItemTask(ID, userId);
            mAuthTask.execute((Void) null);
            getfavorites();
            /*getFavoritesTask getfavtask = new getFavoritesTask();
            getfavtask.execute((Void) null);*/
            for(int i = 0 ; i < bookList.size() - 1 ; i++){
                if(bookList.get(i).getBookid() == ID){
                    favoritePages = bookList.get(i).getPageList();
                }
                int j =0;
            }
        }

    }

    private void getfavorites() {
        getFavoritesTask getfavtask = new getFavoritesTask();
        getfavtask.execute((Void) null);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Fresco.shutDown();
    }

    public void addQuestion(View view) {

        Intent addq = new Intent(this, AddQuestion.class);
        addq.putExtra("userId", userId);
        addq.putExtra("itemId", ID);
        addq.putExtra("author", book.getAuthor());
        addq.putExtra("itemName", book.getTitle());
        startActivity(addq);

    }
    /**************************************************************************************
        This is a AsyncTASK for the Data fetching of the Book/MAP *************************
     *************************************************************************************
    */

    public class ViewItemTask extends AsyncTask<Void, Void, JSONObject> {

        private final String bookId;
        private final String userId;


        ViewItemTask(String bookId,String userId) {
            this.bookId = bookId;
            this.userId=userId;
        }

        @Override
        protected JSONObject doInBackground(Void... params) {

            if (params.length == 0) {
                return null;
            }

            Log.v("connect", "CONNECTED");
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String serverJsonStr = null;

            try {
                final String ID_PARAM = "recordId";
                final String USER_PARAM ="userId";
                final String SERVER_BASE_URL = Params.getServer() + "search/bookquery?";
                Uri builtUri = Uri.parse(SERVER_BASE_URL)
                        .buildUpon()
                        .appendQueryParameter(ID_PARAM, bookId)
                        .appendQueryParameter(USER_PARAM,userId)
                        .build();
                URL url = new URL(builtUri.toString());
                Log.v(TAG + "Type: " + type, builtUri.toString());
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;

                while ((line = reader.readLine()) != null) {

                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    return null;
                }

                serverJsonStr = buffer.toString();
                Log.e("PROBLEM", serverJsonStr);

            } catch (IOException e) {
                Log.e("LOGE", "Error ", e);
                return null;

            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("LOGE", "Error closing stream", e);
                    }
                }
            }

            JSONObject serverJson = null;
            try {
                serverJson = new JSONObject(serverJsonStr);
                return serverJson;

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }


        protected void onPostExecute(final JSONObject success) {
            JSONArray pages=null;
            JSONObject fetchedbook =null;
            JSONArray pages3 = null;
            String creationdate,thumbnail,author,subject,webLink,publisher,source,title;
            try {

                //Handle Data for the Fetched Book

                fetchedbook =success.getJSONObject("book");
                book.setCreationdate(fetchedbook.getString("creationdate"));
                book.setThumbnail(fetchedbook.getString("thumbnail"));
                book.setAuthor(fetchedbook.getString("author"));
                //book.setSubject(fetchedbook.getString("subject"));
                book.setWeblink(fetchedbook.getString("webLink"));
                book.setPublisher(fetchedbook.getString("publisher"));
                book.setSource(fetchedbook.getString("source"));
                book.setTitle(fetchedbook.getString("title"));

                //Handle Array of Pages of Fetched Book

                pages = success.getJSONArray("pages");

                String first = "http://iiif.nli.org.il/IIIF/";
                String last = "/full/full/0/default.jpg";
                String tmp = "";

                ImageView imageView = (ImageView) findViewById(R.id.imageView2);
                imageView.setVisibility(View.GONE);

                for (int i = 1; i < pages.length()-1; i++) {
                    pagesStr.add(first +pages.getString(i) + last);
                    Log.e("ItemsQ pages",tmp);
                }
                if(type.equals("book")) {
                    if ((pagesStr.size() == 0) || (pagesStr.size() == 1)) {
                        TextView textView9 = (TextView) findViewById(R.id.textView13);
                        ViewPager viewPager = (ViewPager) findViewById(R.id.vp_gallery);
                        viewPager.setVisibility(View.GONE);
                        textView9.setText(R.string.no_pages_error);
                        textView9.setTextSize(30);
                        textView9.setTextColor(Color.WHITE);
                        textView9.setVisibility(View.VISIBLE);


                        isNoPages = 1;
                    }
                }
                if(isNoPages ==0){

                    TextView textView9=(TextView) findViewById(R.id.textView13);
                    textView9.setVisibility(textView9.GONE);
                    ViewPager viewPager = (ViewPager) findViewById(R.id.vp_gallery);
                    viewPager.setVisibility(View.VISIBLE);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            vpGallery = (ViewPager) findViewById(R.id.vp_gallery);
            vpGallery.setAdapter(new GalleryAdapter(pagesStr));
        }


        protected void onCancelled() {
            mAuthTask = null;
            //   showProgress(false);
        }

    }

    /*****************************************************************************************************************************************
        This is a AsyncTASK for the Favorites*************************************************************************************************
    */

    public class FavoritesTask extends AsyncTask<Void, Void, JSONObject> {

        private String _SERVER_COMMAND;

        public FavoritesTask(String servercomman) {
            _SERVER_COMMAND = servercomman;
        }

        @Override
        protected JSONObject doInBackground(Void... params) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String serverJsonStr = null;
            try {
                Uri builtUri = Uri.parse(_SERVER_COMMAND).buildUpon().build();
                URL url = new URL(builtUri.toString());
                Log.v("FavoritesURL:", builtUri.toString());
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null)
                    return null; // Nothing to do.
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }
                if (buffer.length() == 0)
                    return null;
                serverJsonStr = buffer.toString();
                Log.d("getFavoritesjson:", serverJsonStr);

            } catch (IOException e) {
                Log.e("LOGE", "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attempting
                // to parse it.
                return null;

            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("LOGE", "Error closing stream", e);
                    }
                }
            }

            JSONObject serverJson = null;
            try {
                serverJson = new JSONObject(serverJsonStr);
                return serverJson;

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        ////////////////////////////////////////////////////////////////////// ON POST EXECUTE
        @Override
        protected void onPostExecute(final JSONObject object) {

        }
    }

    class GalleryAdapter extends PagerAdapter {

        List<String> items;

        // };
        public GalleryAdapter(List<String> pages){
            items=new ArrayList<String>();
            if(items.addAll(pagesStr)==false){
                return;
            }

        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //    count=0;
            //if(!isJump) {
            //counti=1;
            // if (isNoPages == 0) {
            if (j == 0) {
                textView1.setText(position + "/" + items.size());
//                        if (position == items.size() - 1) {
                i = 1;
            }
            if(type.equals("map")){
                textView1.setText(1 + "/" + 1);
            }
          //  if(count==2){
           //     textView1.setText(items.size()-1 + "/" + items.size());
            //    count=0;
           // }



//                    }
//                    if (j == 1) {
//                        textView1.setText(items.size() - 1 + "/" + items.size());
//                        j = 0;
//                    }
//                }
//            }


               ZoomableDraweeView view = new ZoomableDraweeView(container.getContext());
               view.setController(
                       Fresco.newDraweeControllerBuilder()
                               .setUri(Uri.parse(items.get(position)))
                               .build());

               GenericDraweeHierarchy hierarchy =
                       new GenericDraweeHierarchyBuilder(container.getResources())
                               .setActualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER)
                               .setProgressBarImage(new ProgressBarDrawable())
                               .build();

               view.setHierarchy(hierarchy);

               container.addView(view,
                       ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);



            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

           /*if(counti==0) {
                if (!isJump) {
                    if (i == 0) {
                        textView1.setText((position - 1) + "/" + items.size());

                    } else {
                        j = 1;
                        textView1.setText(items.size() + "/" + items.size());
                        i = 0;
                    }
                } else {
                    if (count % 2 == 1) {
                        isJump = false;
                        count++;

                    } else {
                        count++;
                    }
                }
            }
            else  counti=0;*/

            if((i==1)&&(isJump==false)){
                textView1.setText((position - 1) + "/" + items.size());
                i=0;
            }
            if((i==1)&&(isJump==true)){
                isJump=false;
                i=0;
            }
            int s=position;
          //  if((isJump==true)){
           //     count++;
           // }
            if(position==2) {
                textView1.setText(1 + "/" + items.size());
            }





            container.removeView((View) object);

        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    /************************************/
    private class ImagePagerAdapter extends PagerAdapter {


        @Override
        public int getCount() {
            //this.notifyDataSetChanged();
            return 1;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((ImageView) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
         //  Context context = PhotoBeforeFoeFragment.this.getActivity();

            ImageView imageView = new ImageView(getApplicationContext());
          //  imageView.setImageBitmap(bitmaps.get(position));
            // imageView.setImageDrawable(d[position]);
            Picasso.with(getApplicationContext()).load(weblink).fit().into(imageView);
            ((ViewPager) container).addView(imageView);
            return imageView;
        }
        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((ImageView) object);
        }


    }

    public class getFavoritesTask extends AsyncTask<Void, Void, JSONObject> {
        @Override
        protected JSONObject doInBackground(Void... params) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String serverJsonStr = null;
            try {
                User user= (User) getIntent().getSerializableExtra("user");

                final String SERVER_GETFAVORITES = "http://52.29.110.203:8080/LibArab/favorites/getFavorites?";

                //final String SERVER_BASE_URL = "http://www.mocky.io/v2/57f0a2d70f0000f60901353f";
                Uri builtUri = Uri.parse(SERVER_GETFAVORITES).buildUpon()
                        .appendQueryParameter("userId",    user.getUsername())
                        .appendQueryParameter("type",    "book")
                        .build();
                URL url = new URL(builtUri.toString());
                Log.v("getFavoritesURL:", builtUri.toString());
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null)
                    return null; // Nothing to do.
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }
                if (buffer.length() == 0)
                    return null;
                serverJsonStr = buffer.toString();
                Log.d("getFavoritesjson:", serverJsonStr);

            } catch (IOException e) {
                Log.e("LOGE", "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attempting
                // to parse it.
                return null;

            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("LOGE", "Error closing stream", e);
                    }
                }
            }

            JSONObject serverJson = null;
            try {
                serverJson = new JSONObject(serverJsonStr);
                return serverJson;

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        ////////////////////////////////////////////////////////////////////// ON POST EXECUTE
        @Override
        protected void onPostExecute(final JSONObject object) {
            try {
                JSONArray jarray = null;
                String answer = null;

                if (object == null) {
                    // something to do???
                    return;
                }
                bookList.clear();
                jarray = object.getJSONArray("favorites");

                for (int i = 0; i < jarray.length(); i++) {

                    com.example.michaelg.myapplication.favorites.bean.Book lastBook = null;
                    String lastId = null;
                    String currentId = null;
                    String pageNumber = null;
                    int pageNumber2 = 0;
                    int lastId2 = 0;
                    int currentId2 = 1;

                    if(i != 0){
                        lastBook = bookList.get(bookList.size() - 1);
                    }

                    JSONObject bookobj = null;
                    bookobj = jarray.getJSONObject(i);
                    com.example.michaelg.myapplication.favorites.bean.Book currentbook = new com.example.michaelg.myapplication.favorites.bean.Book();

                    currentbook.setId(i);
                    currentbook.setBookid(bookobj.getString("bookID"));
                    currentbook.setTitle(bookobj.getString("title"));
                    currentbook.setImage(bookobj.getString("pageLink"));
                    currentbook.setDescription(bookobj.getString("description"));

                    //needed for viewer
                    currentbook.setAuthor(bookobj.getString("author"));
                    currentbook.setPublisher(bookobj.getString("publisher"));
                    currentbook.setCreationDate(bookobj.getString("creationDate"));
                    currentbook.setPagenum(bookobj.getString("pageNumber"));
                    pageNumber = bookobj.getString("pageNumber");
                    pageNumber2 = Integer.parseInt(pageNumber);
                    if(lastBook != null){
                        lastId = lastBook.getBookid();
                        currentId =currentbook.getBookid();
                        String[] separate = lastId.split("H");
                        String[] separate1 = currentId.split("H");
                        lastId2 = Integer.parseInt(separate[1]);
                        currentId2 = Integer.parseInt(separate1[1]);
                    }
                    if (lastId2 == currentId2){
                        lastId2 = 0;
                        currentId2 = 1;
                        //lastBook.getPageList().add(pageNumber2);
                        //bookList.remove(bookList.size() - 1);
                        bookList.get(bookList.size() - 1).getPageList().add(pageNumber2);
                    }else{
                        bookList.add(currentbook);
                    }



                }

                /*bookGridAdapter.setData(bookList);
                bookGridAdapter.notifyDataSetChanged();*/

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }
}


