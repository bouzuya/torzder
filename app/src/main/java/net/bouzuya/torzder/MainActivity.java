package net.bouzuya.torzder;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import net.bouzuya.torzder.models.Talk;
import net.bouzuya.torzder.views.TalkDetailView;
import net.bouzuya.torzder.views.TalkListView;


public class MainActivity extends ActionBarActivity {
    private TalkListView talkListView;
    private TalkDetailView talkDetailView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        talkListView = new TalkListView(this);
        talkDetailView = new TalkDetailView(this);

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.container);
        layout.addView(talkListView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // NOTE: (finish) <- TalkListView <- TalkDetailView
        if (talkListView.getParent() != null) {
            // (finish) <- TalkListView
            finish();
        } else {
            // TalkListView <- TalkDetailView
            RelativeLayout layout = (RelativeLayout) findViewById(R.id.container);
            layout.removeAllViews();
            layout.addView(talkListView);
        }
    }

    public void showTalk(Talk talk) {
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.container);
        layout.removeAllViews();
        talkDetailView.setTalk(talk);
        layout.addView(talkDetailView);
    }

}
