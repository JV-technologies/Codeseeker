package com.example.jvtcred;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import static com.example.jvtcred.DBqueries.lists;
import static com.example.jvtcred.DBqueries.loadCategories;
import static com.example.jvtcred.DBqueries.loadFragmentData;
import static com.example.jvtcred.DBqueries.loadedCategoriesNames;

public class CategoryActivity extends AppCompatActivity {

    private RecyclerView categoryRecyclerView;
    HomePageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category); //setting tool bar to action bar//
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String title = getIntent().getStringExtra("CategoryName"); // to set title//
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // this will show our back arrow

        categoryRecyclerView = findViewById(R.id.category_recyclerview);









        ///////////////////

        LinearLayoutManager testingLayoutManager = new LinearLayoutManager(this);
        testingLayoutManager.setOrientation(RecyclerView.VERTICAL);
        categoryRecyclerView.setLayoutManager(testingLayoutManager); //setting the layout manager//


         int listPostion = 0;
         for(int x=0;x<loadedCategoriesNames.size();x++){
             if(loadedCategoriesNames.get(x).equals(title.toUpperCase())){
                 listPostion = x;

             }
         }
         if(listPostion==0){
             //using the data first time//
             loadedCategoriesNames.add(title.toUpperCase());
             lists.add(new ArrayList<HomePageModel>());//if the list is null then we load the data from the firebse//
             adapter = new HomePageAdapter(lists.get(loadedCategoriesNames.size()-1));
             loadFragmentData(adapter,this,loadedCategoriesNames.size()-1,title); //this method adds data


         }else{                       //used it already and the data is reused//

             adapter = new HomePageAdapter(lists.get(listPostion));

         }

        categoryRecyclerView.setAdapter(adapter); // setting the adapter o testing recycler view//
        adapter.notifyDataSetChanged();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_icon, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //for selecting the items//

        int id = item.getItemId();

        if (id == R.id.main_search_icon) {
            return true;
        }else if (id==android.R.id.home){
            // for back key//
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
