package net.bouzuya.torzder.models;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class CaptionListRequest extends Request<List<Caption>> {
    private final Response.Listener<List<Caption>> listener;

    public CaptionListRequest(
            int id,
            Response.Listener<List<Caption>> listener,
            Response.ErrorListener errorListener
    ) {
        super(Method.GET, createCaptionUrl(id), errorListener);
        this.listener = listener;
    }

    @Override
    protected Response<List<Caption>> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            List<Caption> c = parseCaptionsFromString(jsonString);
            return Response.success(c,
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(List<Caption> response) {
        listener.onResponse(response);
    }

    private static String createCaptionUrl(int id) {
        return "http://www.ted.com/talks/subtitles/id/" + Integer.toString(id) + "/lang/en";
    }

    private List<Caption> parseCaptionsFromString(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            return parseCaptions(jsonObject);
        } catch (JSONException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private List<Caption> parseCaptions(JSONObject jsonObject) {
        List<Caption> captionList = new ArrayList<>();
        try {
            JSONArray jsonCaptions = jsonObject.getJSONArray("captions");
            for (int i = 0; i < jsonCaptions.length(); i++) {
                JSONObject jsonCaption = jsonCaptions.getJSONObject(i);
                Caption caption = parseCaption(jsonCaption);
                captionList.add(caption);
            }
            return captionList;
        } catch (JSONException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private Caption parseCaption(JSONObject jsonCaption) {
        try {
            Caption caption = new Caption();
            caption.startTime = jsonCaption.getInt("startTime");
            caption.startOfParagraph = jsonCaption.getBoolean("startOfParagraph");
            caption.content = jsonCaption.getString("content");
            caption.duration = jsonCaption.getInt("duration");
            return caption;
        } catch (JSONException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
