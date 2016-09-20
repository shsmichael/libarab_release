package com.example.michaelg.myapplication.Fragments;


import android.animation.Animator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.michaelg.myapplication.R;

import static android.R.attr.id;


/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment {

    private final String TAG =this.getClass().getSimpleName();
    public MenuFragment() {
        // Required empty public constructor
    }
    Button btn;
    TextView txt;
    FrameLayout search,trivia,favorites,about,settings,bibilo;
    View myview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myview = inflater.inflate(R.layout.fragment_menu, container, false);
        getActivity().setTitle("Menu");
        txt = (TextView) myview.findViewById(R.id.tv_search);

        search = (FrameLayout) myview.findViewById(R.id.frame_search);
        trivia = (FrameLayout) myview.findViewById(R.id.frame_trivia);
        favorites = (FrameLayout) myview.findViewById(R.id.frame_favorites);
        about = (FrameLayout) myview.findViewById(R.id.frame_about);
        settings = (FrameLayout) myview.findViewById(R.id.frame_settings);
        bibilo = (FrameLayout) myview.findViewById(R.id.frame_biblio);


// previously invisible view


        // make the view visible and start the animation

        myview.post(new Runnable()
        {
            @Override
            public void run()
            {
                // get the center for the clipping circle
                int search_cx = search.getWidth() / 2;
                int search_cy =  search.getHeight() / 2;
                //
                int triv_cx = trivia.getWidth() / 2;
                int triv_cy =  trivia.getHeight() / 2;

                int fav_cx = favorites.getWidth() / 2;
                int fav_cy =  favorites.getHeight() / 2;

                int about_cx = about.getWidth() / 2;
                int about_cy =  about.getHeight() / 2;

                int settings_cx = settings.getWidth() / 2;
                int settings_cy =  settings.getHeight() / 2;

                int bibilo_cx = bibilo.getWidth() / 2;
                int bibilo_cy =  bibilo.getHeight() / 2;


                // get the final radius for the clipping circle

                float finalRadius = (float) Math.hypot(search_cx, search_cy);
                // create the animator for this view (the start radius is zero)
                Animator anim =
                        ViewAnimationUtils.createCircularReveal(search, search_cx, search_cy, 0, finalRadius);
                Animator anim2 =
                        ViewAnimationUtils.createCircularReveal(trivia, triv_cx, triv_cy, 0, finalRadius);
                Animator anim3 =
                        ViewAnimationUtils.createCircularReveal(favorites, fav_cx, fav_cy, 0, finalRadius);
                Animator anim4 =
                        ViewAnimationUtils.createCircularReveal(about, about_cx, about_cy, 0, finalRadius);
                Animator anim5 =
                        ViewAnimationUtils.createCircularReveal(settings, settings_cx, settings_cy, 0, finalRadius);
                Animator anim6 =
                        ViewAnimationUtils.createCircularReveal(bibilo, bibilo_cx, bibilo_cy, 0, finalRadius);


                search.setVisibility(View.VISIBLE);
                trivia.setVisibility(View.VISIBLE);
                favorites.setVisibility(View.VISIBLE);
                about.setVisibility(View.VISIBLE);
                search.setVisibility(View.VISIBLE);
                settings.setVisibility(View.VISIBLE);
                //anim.setStartDelay(500);
                anim.start();
                anim3.setStartDelay(100);
                anim3.start();

                anim4.setStartDelay(200);
                anim4.start();
                anim5.setStartDelay(300);
                anim5.start();
                anim2.setStartDelay(400);
                anim2.start();
                anim6.setStartDelay(500);
                anim6.start();
            }
        });



        return myview;
    }



}
