package com.jameskozlowski.randomnumberapi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RedditSeeds {

    Logger logger = LoggerFactory.getLogger(RedditSeeds.class);

    private static final String COMMENT_URL = "https://api.reddit.com/r/all/comments/.json?limit=100";
    private static final String USER_AGENT = "randomnuumberapi/0.1 by JK";

    private static List<Integer> seeds = new ArrayList<Integer>();

    public static int get() {
        if (seeds.size() == 0)
            ReadSeeds();
        if (seeds.size() == 0)
            return -1;
        return seeds.remove(0);
    }

    private static void ReadSeeds() {
        try {
            JSONObject json = readJsonFromUrl(COMMENT_URL);
            JSONObject data = (JSONObject)json.get("data");
            JSONArray comments = data.getJSONArray("children");
            for(int i = 0; i < comments.length(); i++) {
                JSONObject commentdata = (JSONObject) comments.getJSONObject(i).get("data");
                String s = (String) commentdata.get("body");
                int hash = s.hashCode();
                seeds.add(hash);
            }
        } catch (IOException ignored) {
        }
    }

    private static JSONObject readJsonFromUrl(String urlString) throws IOException, JSONException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("User-Agent", USER_AGENT);
        try (InputStreamReader is = new InputStreamReader(conn.getInputStream())) {
            BufferedReader rd = new BufferedReader(is);
            String jsonText = readAll(rd);
            return new JSONObject(jsonText);
        }
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }
}
