package com.example.sky.notesstack;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.HashSet;

public class NotesEditActivity extends AppCompatActivity {
    Intent intent;
    EditText NoteEditor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_edit);
        intent=getIntent();
        final int CurrentNode;
        NoteEditor=findViewById(R.id.Note_Editor);
        CurrentNode=intent.getIntExtra("CurrentNote",-1);
        if(CurrentNode==-1){
            MainActivity.Notes.add("");
            NoteEditor.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    MainActivity.Notes.set(MainActivity.Notes.size()-1,charSequence.toString());
                    MainActivity.adapter.notifyDataSetChanged();

                    SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences("com.example.sky.notesstack", Context.MODE_PRIVATE);
                    HashSet<String> set=new HashSet<>(MainActivity.Notes);
                    sharedPreferences.edit().putStringSet("Saved Notes",set).apply();
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }
        else {
            NoteEditor.setText(MainActivity.Notes.get(CurrentNode));
            NoteEditor.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    MainActivity.Notes.set(CurrentNode, charSequence.toString());
                    MainActivity.adapter.notifyDataSetChanged();
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }

    }


}
