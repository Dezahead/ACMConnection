package com.csangels.acmconnection.acmconnection;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.csangels.acmconnection.acmconnection.Chat.DialogsActivity;
import com.csangels.acmconnection.acmconnection.Chat.NewDialogActivity;

public class PicturesActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pictures);
        View.OnClickListener listnr = new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(PicturesActivity.this, MainActivity2.class);
                startActivity(i);
            }
        };
        ImageView myimageView = (ImageView) findViewById(R.id.imageView);
        myimageView.setOnClickListener(listnr);
    }

    public void onClick4(View v) {
        Intent i = new Intent(PicturesActivity.this, MainActivity3.class);
        startActivity(i);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.rooms, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.chat) {

            // go to New Dialog activity(change below back please Dez from signinactivity to newdialogactivity)
            //
            Intent intent = new Intent(PicturesActivity.this, DialogsActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        else if (id == R.id.calendar){
            Intent intent = new Intent(PicturesActivity.this, CalendarActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        else if(id == R.id.pictures){
            Intent intent = new Intent(PicturesActivity.this, PicturesActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        else if(id == R.id.action_add){
            Intent intent = new Intent(PicturesActivity.this, NewDialogActivity.class);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
