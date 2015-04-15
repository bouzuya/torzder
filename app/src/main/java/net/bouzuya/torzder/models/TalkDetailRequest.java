package net.bouzuya.torzder.models;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TalkDetailRequest extends Request<Talk> {
    private final Response.Listener<Talk> listener;

    public TalkDetailRequest(
            String key,
            Response.Listener<Talk> listener,
            Response.ErrorListener errorListener
    ) {
        super(Method.GET, createUrl(key), errorListener);
        this.listener = listener;
    }

    @Override
    protected Response<Talk> parseNetworkResponse(NetworkResponse response) {
        try {
            String html = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            Talk talk = parseTalkFromString(html);
            return Response.success(talk,
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(Talk response) {
        listener.onResponse(response);
    }

    private static String createUrl(String key) {
        return "http://www.ted.com/talks/" + key;
    }

    private Talk parseTalkFromString(String html) {
        String m = ".*<script>q\\(\"talkPage.init\",(.*)\\)</script>.*";
        Pattern pattern = Pattern.compile(m, Pattern.MULTILINE | Pattern.DOTALL);
        Matcher matcher = pattern.matcher(html);
        if (matcher.matches()) {
            String jsonString = matcher.group(1);
            try {
                JSONObject jsonTalksObject = new JSONObject(jsonString);
                JSONObject jsonTalk = jsonTalksObject.getJSONArray("talks").getJSONObject(0);

                JSONObject jsonResources = jsonTalk.getJSONObject("resources");
                JSONObject jsonH264 = jsonResources.getJSONArray("h264").getJSONObject(0);
                double introDuration = jsonTalk.getDouble("introDuration");
                double adDuration = Double.parseDouble(jsonTalk.getString("adDuration"));
                int id = jsonTalk.getInt("id");
                String speaker = jsonTalk.getString("speaker");
                String title = jsonTalk.getString("title");
                String file = jsonH264.getString("file");

                Talk talk = new Talk();
                talk.id = id;
                talk.speaker = speaker;
                talk.title = title;
                talk.file = file;
                talk.offset = (int) ((introDuration + adDuration) * 1000);

                return talk;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return new Talk();
    }

}
