package com.main;

import org.json.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class NewsManager {
    private ArrayList<Profile> profiles;
	private String apiKey = ""; //Insert your API key here

    public NewsManager(){
        profiles = new ArrayList<Profile>();
    }

    public void add(String name, String pass) {
        profiles.add(new Profile(name, pass));
    }

    public int getIndex(String name, String pass){
        for (int i = 0; i < profiles.size(); i++){
            if (profiles.get(i).verifyAccount(name, pass))
                return i;
        }

        return -1;
    }

    public boolean checkUniqueAccount(String name){
        for (Profile profile : profiles){
            if (profile.getName().equals(name))
                return false;
        }

        return true;
    }

    public Profile getProfile(int index){
        return profiles.get(index);
    }

    public String[][][] getNews(String name, String pass) throws Exception{
        int index = getIndex(name, pass);
        String[][][] output = new String[profiles.get(index).getNumTopics()][3][2];

        if (index == -1){
            String[][][] error = {{{"Cannot find profile"}}};
            return error;
        }

        for (int i = 0; i < profiles.get(index).getNumTopics(); i++) {
            // build a URL
            String apiLink = "https://newsapi.org/v2/top-headlines?q=" + profiles.get(index).getTopic(i)
                    + "&apiKey=" + apiKey;

            URL url = new URL(apiLink);

            // read from the URL
            String str = new String();
            JSONObject obj = null;
            try {
                Scanner URLReader = new Scanner(url.openStream());

                while (URLReader.hasNext())
                    str += URLReader.nextLine();
                URLReader.close();

                obj = new JSONObject(str);
            } catch (java.io.IOException e){
                output[i][0][0] = "Could not find anything for " + profiles.get(i).getTopic(i);
            } catch (org.json.JSONException e){
                output[i][0][0] = "Could not find anything for " + profiles.get(i).getTopic(i);
            }

            //gets 3 articles for each topic
            for (int j = 0; j < 3; j++) {

                try {
                    JSONObject article = obj.getJSONArray("articles").getJSONObject(j);
                    output[i][j][0] = article.getString("title");
                    output[i][j][1] = article.getString("url");
                } catch (JSONException e){
                    break;
                } catch (NullPointerException e){
                    break;
                }
            }

        }

        return output;
    }

}
