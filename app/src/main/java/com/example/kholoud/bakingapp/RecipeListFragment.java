package com.example.kholoud.bakingapp;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kholoud.bakingapp.MainActivity;
import com.example.kholoud.bakingapp.R;
import com.example.kholoud.bakingapp.RecipeAdapter;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeListFragment extends Fragment {

    @BindView(R.id.recipe_card_rv) RecyclerView CardRecyclerView;

    private RecyclerView.Adapter adapter;

    private RecyclerView.LayoutManager CardLayoutManager;
// Constractor Empty *****************
    public RecipeListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        final View view =
                inflater.inflate(R.layout.fragment_recipe_list, container, false);
        ButterKnife.bind(this, view);

        CardRecyclerView.setHasFixedSize(true);
// If Tablet  --->
        if (MainActivity.isTablet) {
            CardLayoutManager = new GridLayoutManager(getContext(), 1, LinearLayoutManager.VERTICAL, false);
            CardRecyclerView.setLayoutManager(CardLayoutManager);
        }

        else {
            CardLayoutManager = new LinearLayoutManager(getContext());
            CardRecyclerView.setLayoutManager(CardLayoutManager);
        }

        new catchRecipes().execute();

        return view;
    }

    public URL buildUrl() {
        Uri recipeUri = Uri.parse(MainActivity.recipeLink);
        try {
            return new URL(recipeUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public class catchRecipes extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            try {
                URL recipeUrl = buildUrl();
                HttpURLConnection urlConnection = (HttpURLConnection) recipeUrl.openConnection();
                try {


                    InputStream in = urlConnection.getInputStream();
                    Scanner scanner = new Scanner(in);
                    scanner.useDelimiter("\\A");
                    boolean hasInput = scanner.hasNext();
                    if (hasInput) return scanner.next();
                     else return null;

                } finally {
                    urlConnection.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        //******************************************

        @Override
        protected void onPostExecute(String jsonString) {
            adapter = new RecipeAdapter(getContext(), jsonString);
            CardRecyclerView.setAdapter(adapter);
        }
    }
}
