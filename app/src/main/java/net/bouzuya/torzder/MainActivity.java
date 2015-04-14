package net.bouzuya.torzder;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import net.bouzuya.torzder.models.Caption;
import net.bouzuya.torzder.models.Talk;
import net.bouzuya.torzder.receivers.DownloadCompleteReceiver;
import net.bouzuya.torzder.views.CaptionDetailView;
import net.bouzuya.torzder.views.TalkDetailView;
import net.bouzuya.torzder.views.TalkListView;

public class MainActivity extends Activity {
    private TalkListView talkListView;
    private TalkDetailView talkDetailView;
    private CaptionDetailView captionDetailView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        talkListView = new TalkListView(this);
        talkDetailView = new TalkDetailView(this);
        captionDetailView = new CaptionDetailView(this);

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.container);
        layout.addView(talkListView);

        IntentFilter filter = new IntentFilter();
        filter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        registerReceiver(new DownloadCompleteReceiver(), filter);
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
        switch (id) {
            case android.R.id.home:
                if (isHomeAsUpEnabled()) {
                    onBackPressed();
                }
                return true;
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        // NOTE: (finish) <- TalkListView <- TalkDetailView <- CaptionDetailView
        if (talkListView.getParent() != null) {
            // (finish) <- TalkListView
            finish();
        } else {
            // TalkListView <- TalkDetailView <- CaptionDetailView
            RelativeLayout layout = (RelativeLayout) findViewById(R.id.container);
            View nextView;
            boolean hasBackButton;

            if (talkDetailView.getParent() != null) {
                nextView = talkListView;
                hasBackButton = false;
            } else if (captionDetailView.getParent() != null) {
                nextView = talkDetailView;
                hasBackButton = true;
            } else {
                throw new IllegalStateException("onBackPressed");
            }
            layout.removeAllViews();
            layout.addView(nextView);
            setHomeAsUp(hasBackButton);
        }
    }

    public void showCaption(Caption caption) {
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.container);
        layout.removeAllViews();
        captionDetailView.setCaption(caption);
        layout.addView(captionDetailView);
        setHomeAsUp(true);
    }

    public void showTalk(Talk talk) {
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.container);
        layout.removeAllViews();
        talkDetailView.setTalk(talk);
        layout.addView(talkDetailView);
        setHomeAsUp(true);
    }

    private boolean isHomeAsUpEnabled() {
        return (getActionBar().getDisplayOptions() & ActionBar.DISPLAY_HOME_AS_UP) != 0;
    }

    private void setHomeAsUp(boolean enabled) {
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(enabled);
    }
}
