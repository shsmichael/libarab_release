
package com.example.michaelg.myapplication.Fragments;


        import android.os.Bundle;
        import android.support.v4.app.Fragment;
        import android.support.v4.app.FragmentTabHost;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;

        import com.example.michaelg.myapplication.R;

public class SearchTabHostFragment extends Fragment {

    private final String TAG =this.getClass().getSimpleName();
    private FragmentTabHost mTabHost;

    //Mandatory Constructor
    public SearchTabHostFragment() {
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_search_tabhost,container, false);


        mTabHost = (FragmentTabHost)rootView.findViewById(android.R.id.tabhost);
        mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.realtabcontent);

        mTabHost.addTab(mTabHost.newTabSpec("fragmentb").setIndicator(getString(R.string.search_book)),
                SearchBookFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("fragmentc").setIndicator(getString(R.string.search_sheet)),
                SearchSheetFragment.class, null);
       // mTabHost.addTab(mTabHost.newTabSpec("fragmentd").setIndicator("Map"),
         //       SearchMapFragment.class, null);


        return rootView;
    }
}