package com.example.sky.notesstack;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {
    static ArrayList<String> Notes=new ArrayList<>();
    SharedPreferences sharedPreferences;
    static ListView NotesView;
    static ArrayAdapter adapter;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if(item.getItemId()==R.id.newNote);
        Intent intent=new Intent(this,NotesEditActivity.class);
        startActivity(intent);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //SharedPreferences sharedPreferences=getSharedPreferences("SavedNotes",)
        NotesView=findViewById(R.id.notes_view);
        sharedPreferences=getApplicationContext().getSharedPreferences("com.example.sky.notesstack", Context.MODE_PRIVATE);

        HashSet<String> set= (HashSet<String>) sharedPreferences.getStringSet("SavedNotes",null);
        if(set==null){
            Notes.add("Example");
        }
        else{
            Notes=new ArrayList<>(set);
        }

        adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,Notes);
        NotesView.setAdapter(adapter);
        NotesView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(MainActivity.this,NotesEditActivity.class);
                intent.putExtra("CurrentNote",i);
                startActivity(intent);
                
            }

        });
        NotesView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int ItemToDelete=i;
                new AlertDialog.Builder(MainActivity.this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Deletion")
                        .setMessage("Do you want to delete this note?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Notes.remove(ItemToDelete);
                                adapter.notifyDataSetChanged();

                                HashSet<String> set=new HashSet<>(MainActivity.Notes);
                                sharedPreferences.edit().putStringSet("Saved Notes",set).apply();
                            }
                        }).setNegativeButton("NO", null).show();

                return true;
            }
        });

    }


}

