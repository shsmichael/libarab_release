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
import com.facebook.common.logging.FLog;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ProgressBarDrawable;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
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
    List<String> pagesStr = new ArrayList<String>();
    private String ID = "NNL_ALEPH003157499";
    private String userId= "100";
    private String creationdate;
    private String title;
    private String author;
    private String weblink;
    private String publisher;
    private String source;
    private ViewItemTask mAuthTask = null;
    int i = 0;
    int j = 0;
    int isNoPages =0;
    boolean isJump = false;
    ViewPager vpGallery;
    EditText etchange;
    TextView textView1;
    ImageView mybg;
    DiscreteSeekBar discreteSeekBar1;
    private Book book;
    private String type;
    final String typeBook="book";
    final String typeSheet="sheet";
    int count=0;
    int k=0;
    int counti=0;


    public void bookinfo(View v){

        Intent bookinfoactivity = new Intent(this,BookinfoActivity.class);
        bookinfoactivity.putExtra("creationdate" ,creationdate);
        bookinfoactivity.putExtra("title"        ,title);
        bookinfoactivity.putExtra("author"       ,author);
        bookinfoactivity.putExtra("webLink"      ,weblink);
        bookinfoactivity.putExtra("publisher"    ,publisher);
        bookinfoactivity.putExtra("source"       ,source);
        startActivity(bookinfoactivity);
    }

    public void changepage(View v){

        String stringnumber = etchange.getText().toString();

        if (!(stringnumber.matches(""))) {
            vpGallery.setCurrentItem(Integer.parseInt(stringnumber) - 1);
            textView1.setText(stringnumber + "/" + pagesStr.size());
            isJump = true;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        FLog.setMinimumLoggingLevel(FLog.VERBOSE);
        setContentView(R.layout.activity_view_pager);
        //etchange =(EditText)findViewById(R.id.et_changepage);
        mybg  =    (ImageView) findViewById(R.id.bg);
        textView1=(TextView) findViewById(R.id.textView);

        textView1.setTextSize(20);
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
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            ID  = extras.getString("recordId");
            userId=extras.getString("userId");
            type= extras.getString("type");
            creationdate = extras.getString("creationdate");
            title        = extras.getString("title");
            author       = extras.getString("author");
            weblink      = extras.getString("webLink");
            publisher    = extras.getString("publisher");
            source       = extras.getString("source");
            //  userId="100";
        }

        if(type.equals("sheet")){
            pagesStr.add(weblink);
            try {
                InputStream is = (InputStream) new URL(weblink).getContent();
                Bitmap d = BitmapFactory.decodeStream(is);
                is.close();
             //   return d;
            } catch (Exception e) {
              //  return null;
            }
            vpGallery = (ViewPager) findViewById(R.id.vp_gallery);
            vpGallery.setVisibility(View.GONE);
            TextView textView9=(TextView) findViewById(R.id.textView13);
            textView9.setVisibility(View.GONE);
            ImageView imageView = (ImageView) findViewById(R.id.imageView2);
            //  imageView.setImageBitmap(bitmaps.get(position));
            // imageView.setImageDrawable(d[position]);
            Picasso.with(getApplicationContext()).load(weblink).into(imageView);
            imageView.setVisibility(View.VISIBLE);
            imageView.setClickable(true);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(),"image view",Toast.LENGTH_LONG).show();                }
            }

            );

//            vpGallery = (ViewPager) findViewById(R.id.vp_gallery);
//            vpGallery.setAdapter(new ImagePagerAdapter());
            return;
        }

        book = new Book();
        book.setCreationdate(creationdate);
        book.setTitle(title);
        book.setAuthor(author);
        book.setWeblink(weblink);
        book.setPublisher(publisher);
        book.setSource(source);

        mAuthTask = new ViewItemTask(ID,userId);
        mAuthTask.execute((Void) null);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Fresco.shutDown();
    }

//////////////////////////////////////////////////////////////////////////////// ASYNC TASK
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
                final String SERVER_BASE_URL = //"http://172.20.10.6:8080/LibArab/"+"search/bookquery?";
                        Params.getServer() + "search/bookquery?";
                //TODO: amal sheetquery
                // "search/bookquery/userId/recordId
                //TODO: change according to the server function format

                Uri builtUri = Uri.parse(SERVER_BASE_URL)
                        .buildUpon()
                        .appendQueryParameter(ID_PARAM, bookId)
                        .appendQueryParameter(USER_PARAM,userId)
                        .build();
                URL url = new URL(builtUri.toString());
                Log.v("URL", builtUri.toString());
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

            JSONArray pages3 = null;
            try {
                pages = success.getJSONArray("pages");

                // JSONObject page2 = pages3.getJSONObject(0);
                //  pages = page2.getJSONArray("canvases");

                // JSONObject object = pages.getJSONObject(0);
                //JSONObject object1 = pages.getJSONObject(pages.length() - 1);

                String first = "http://iiif.nli.org.il/IIIF/";
                String last = "/full/full/0/default.jpg";
                String tmp = "";
                /*
                Glide.with(mybg.getContext())
                        .load(first +pages.getString(1) + last)
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .bitmapTransform(new BlurTransformation(mybg.getContext(),100,2 ))
                        .into(mybg);
                */
                ImageView imageView = (ImageView) findViewById(R.id.imageView2);
                imageView.setVisibility(View.GONE);
                for (int i = 1; i < pages.length()-1; i++) {
                    pagesStr.add(first +pages.getString(i) + last);
                    Log.e("ItemsQ pages",tmp);
                }
                int a=pagesStr.size();
                if((pagesStr.size()==0)||(pagesStr.size()==1)){
                    TextView textView9=(TextView) findViewById(R.id.textView13);
                    ViewPager viewPager = (ViewPager) findViewById(R.id.vp_gallery);
                    viewPager.setVisibility(View.GONE);
                    textView9.setText(R.string.no_pages_error);
                    textView9.setTextSize(30);
                    textView9.setTextColor(Color.WHITE);
                    textView9.setVisibility(View.VISIBLE);


                    isNoPages =1;
                }
                if(isNoPages ==0){

                    TextView textView9=(TextView) findViewById(R.id.textView13);
                    textView9.setVisibility(textView9.GONE);
                    ViewPager viewPager = (ViewPager) findViewById(R.id.vp_gallery);
                    viewPager.setVisibility(View.VISIBLE);
                }
                // ViewPagerActivity.GalleryAdapter(pagesStr);

                // Intent intent=new Intent(getApplicationContext(),ViewPagerActivity.class);
                // startActivity(intent);

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
            Picasso.with(getApplicationContext()).load(weblink).into(imageView);
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
}


