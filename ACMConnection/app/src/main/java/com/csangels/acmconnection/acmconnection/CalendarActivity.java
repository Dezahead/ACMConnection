package com.csangels.acmconnection.acmconnection;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CalendarView;

import com.csangels.acmconnection.acmconnection.Chat.ChatActivity;
import com.csangels.acmconnection.acmconnection.Chat.DialogsActivity;
import com.csangels.acmconnection.acmconnection.Chat.NewDialogActivity;

public class CalendarActivity extends ActionBarActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        CalendarView cw = (CalendarView) findViewById(R.id.calendar);
        cw.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                                       public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                                           Dialog d = new Dialog(CalendarActivity.this);

                                           switch (dayOfMonth) {
                                               case 23:
                                                   d.setTitle("Study Session @ 5PM");
                                                   d.show();
                                                   break;
                                               case 22:
                                                   d.setTitle("App Seminar @ 3PM");
                                                   d.show();
                                                   break;
                                               case 20:
                                                   d.setTitle("Special Topics  @ 7PM");
                                                   d.show();
                                                   break;
                                               case 21:
                                                   d.setTitle("Info Session @ 9AM");
                                                   d.show();
                                                   break;

                                               default:
                                                   d.setTitle("No Events Today");
                                                   d.show()
                                                   ;break;
                                           }




                                       }
                                   }
        );
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
            Intent intent = new Intent(CalendarActivity.this, DialogsActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        else if (id == R.id.calendar){
            Intent intent = new Intent(CalendarActivity.this, CalendarActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        else if(id == R.id.pictures){
            Intent intent = new Intent(CalendarActivity.this, PicturesActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        else if(id == R.id.action_add){
            Intent intent = new Intent(CalendarActivity.this, NewDialogActivity.class);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
