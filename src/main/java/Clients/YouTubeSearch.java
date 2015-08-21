package Clients;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.common.collect.Lists;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.YouTube.Search;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import org.json.*;

import java.util.*;
import org.joda.time.format.ISOPeriodFormat;
import org.joda.time.format.PeriodFormatter;

/**
 * @author Kurtis.VanGent
 */
public class YouTubeSearch {
    private static final String PROPERTIES_FILENAME = "youtube.properties";
    
    private static YouTube youtube;
    private static YouTube.Search.List search;

    private static long maxResults = 25;
    private static String type = "video";
    private static String order = "relevance";
   
    public YouTubeSearch(){
        
        //Loads in the file at PROPERTIES_FILENAME to get the API key 
        Properties properties = new Properties();
        try {
            List<String> scopes = Lists.newArrayList("https://www.googleapis.com/auth/youtube");
            Credential credential = Auth.authorize(scopes, "testing");
            
            InputStream in = Search.class.getResourceAsStream("/" + PROPERTIES_FILENAME);
            properties.load(in);
            
            youtube = new YouTube.Builder(Auth.HTTP_TRANSPORT, Auth.JSON_FACTORY, credential)
                    .setApplicationName("youtubesearch").build();
            
            search = youtube.search().list("id,snippet");
            search.setKey(properties.getProperty("youtube.apikey"));
            search.setType(type);
            search.setMaxResults(maxResults);
            search.setOrder(order);
            search.setVideoDuration("any");
            search.setFields("items(id/videoId)"); //ensure low qouta

        } catch (GoogleJsonResponseException e) {
            System.err.println("There was a service error: " + e.getDetails().getCode() + " : "
                    + e.getDetails().getMessage());
        } catch (IOException e) {
            System.err.println("There was an error reading " + PROPERTIES_FILENAME + ": " + e.getCause()
                    + " : " + e.getMessage());
            System.exit(1);
        } 
    }
    
    public String search(String query) throws IOException, JSONException{
        search.setQ(query);
        SearchListResponse searchResponse = search.execute();
        List<SearchResult> searchResultList = searchResponse.getItems();
        
        JSONObject videos = new JSONObject();
        JSONArray videoList = new JSONArray();
        for(SearchResult searchResult: searchResultList){
            //Uses ID to get more details on each video, which is converts into a JSON array
            YouTube.Videos.List listVideosRequest = youtube.videos().list("id, snippet, contentDetails, statistics, topicDetails").setId(searchResult.getId().getVideoId());
            VideoListResponse listResponse = listVideosRequest.execute();
            Video ytResult = listResponse.getItems().get(0);
            
            YouTubeVideo newVid = new YouTubeVideo();
            newVid.setId(ytResult.getId());
            newVid.setTitle(ytResult.getSnippet().getTitle());
            newVid.setDescription(ytResult.getSnippet().getDescription());
            newVid.setViewCount(ytResult.getStatistics().getViewCount());
            newVid.setLikes(ytResult.getStatistics().getLikeCount());
            newVid.setDislikes(ytResult.getStatistics().getDislikeCount());
            newVid.setFavoritedCount(ytResult.getStatistics().getFavoriteCount());
            
            //Converts ISO8601 String format into seconds
            String duration = ytResult.getContentDetails().getDuration();
            newVid.setDuration(ISOPeriodFormat.standard().parsePeriod(duration).toStandardSeconds().getSeconds());
            
            videoList.put(new JSONObject(newVid.toJSON()));
        }
        
        videos.put("videos", videoList);        
        return videos.toString();
    }
    
    public int getMaxResults(){
        return search.getMaxResults().intValue();
    }

    //Sets the Max Results and return true if it works
    public boolean setMaxResults(int max){
        maxResults = max;
        search.setMaxResults(maxResults);
        return maxResults == getMaxResults();
    } 
    
    //Return 0 for any, 1 for high, 2 for standard, 99 for error
    public int getDef(){
        String def = search.getVideoDefinition();
        if(def.equals("any")) return 0;
        else if (def.equals("high")) return 1;
        else if (def.equals("standard")) return 2;
        return 99;
    } 
    
    //Sets the definition and returns true if set correctly
    public boolean setDef(int def){
        if(def == 0) search.setVideoDefinition("any");
        else if(def == 1) search.setVideoDefinition("high");
        else if(def == 2) search.setVideoDefinition("standard");
        else return false;
        return getDef() == def;
    } 
    
    public int getDuration(){
        String dur = search.getVideoDuration();
        if(dur.equals("any")) return 0;
        else if (dur.equals("short")) return 1;
        else if (dur.equals("medium")) return 2;
        else if (dur.equals("long")) return 3;
        return 99;
    }
    
    public boolean setDuration(int dur){
        if(dur == 0) search.setVideoDefinition("any");
        else if(dur == 1) search.setVideoDefinition("short");
        else if(dur == 2) search.setVideoDefinition("medium");
        else if(dur == 3) search.setVideoDefinition("long");
        else return false;
        return getDuration() == dur;
    }
}
