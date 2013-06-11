package com.example.todo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends Activity {

	private ArrayList<String> items;
	private ListView lvItems;
	private ArrayAdapter<String> itemsAdapter;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
//        items = new ArrayList<String>();
//        items.add("Item1");
//        items.add("Item2");
        readItems();
        
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        
        lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(itemsAdapter);
        lvItems.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long rowId) {
				
				items.remove(position);
				itemsAdapter.notifyDataSetChanged(); //We have to use this since we are channging the underlying data set.
				
				//Another way to do this would be. 
				//Once you have an adapter it is recommended to always use the adapter to manipulate data
				/*
				String item = itemsAdapter.getItem(position);
				itemsAdapter.remove(item);
				*/
				
				writeItems();				
				return true;
			}
		});
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    public void onNewItem(View view){
    	EditText etext = (EditText)findViewById(R.id.addNewItem);
    	itemsAdapter.add(etext.getText().toString());
    	etext.setText("");
    	
    	writeItems();
    }
    
    private void readItems(){
    	File filesDir = getFilesDir();
    	File todoFile = new File(filesDir, "todo.txt");
    	try {
			items = new ArrayList<String>(FileUtils.readLines(todoFile));
		} catch (IOException e) {
			items = new ArrayList<String>();
			e.printStackTrace();
		}
    }
    
    private void writeItems(){
    	File filesDir = getFilesDir();
    	File todoFile = new File(filesDir, "todo.txt");
    	try {
			FileUtils.writeLines(todoFile, items);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
 
