package net.bouzuya.torzder.receivers;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.ParcelFileDescriptor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class DownloadCompleteReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
        DownloadManager manager = getDownloadManager(context);
        ParcelFileDescriptor descriptor = null;
        try {
            descriptor = manager.openDownloadedFile(id);
        } catch (FileNotFoundException e) {
            throw new IllegalStateException(e);
        }
        FileChannel input = getInputChannel(descriptor);
        FileChannel output = getOutputChannel(context, "index.html");
        copyFile(input, output);
    }

    private void copyFile(FileChannel input, FileChannel output) {
        try {
            try {
                input.transferTo(0, input.size(), output);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    output.close();
                } catch (IOException ignore) {
                    throw new RuntimeException(ignore);
                }
            }
        } finally {
            try {
                input.close();
            } catch (IOException ignore) {
                throw new RuntimeException(ignore);
            }
        }
    }

    private DownloadManager getDownloadManager(Context context) {
        return (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
    }

    private FileChannel getInputChannel(ParcelFileDescriptor descriptor) {
        return new FileInputStream(descriptor.getFileDescriptor()).getChannel();
    }

    private FileChannel getOutputChannel(Context context, String fileName) {
        try {
            return context.openFileOutput(fileName, Context.MODE_PRIVATE).getChannel();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
