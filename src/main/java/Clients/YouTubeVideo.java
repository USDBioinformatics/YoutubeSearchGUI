package Clients;

import java.math.BigInteger;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Kurtis.VanGent
 */
public class YouTubeVideo {
    public static final String ID_KEY = "id";
    public static final String TITLE_KEY = "title"; 
    public static final String DESC_KEY = "description";
    public static final String DURATION_KEY = "duration";
    public static final String VIEW_CT_KEY = "viewCount";
    public static final String LIKE_CT_KEY = "likeCount";
    public static final String DISLIKE_CT_KEY = "dislikeCount";
    public static final String FAVORITED_CT_KEY = "favoritedCount";
    
    private String id;
    private String title;
    private String description;
    private int duration;
    private BigInteger viewCount;
    private BigInteger likes;
    private BigInteger dislikes;
    private BigInteger favoritedCount;
    
    
    public String toJSON() throws JSONException{
        JSONObject obj = new JSONObject();
        obj.put(ID_KEY, id);
        obj.put(TITLE_KEY, title);
        obj.put(DESC_KEY, description);
        obj.put(DURATION_KEY, duration);
        obj.put(VIEW_CT_KEY, viewCount);
        obj.put(LIKE_CT_KEY, likes);
        obj.put(DISLIKE_CT_KEY, dislikes);
        obj.put(FAVORITED_CT_KEY, favoritedCount);   
        return obj.toString();        
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the duration
     */
    public int getDuration() {
        return duration;
    }

    /**
     * @param duration the duration to set
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * @return the viewCount
     */
    public BigInteger getViewCount() {
        return viewCount;
    }

    /**
     * @param viewCount the viewCount to set
     */
    public void setViewCount(BigInteger viewCount) {
        this.viewCount = viewCount;
    }

    /**
     * @return the likes
     */
    public BigInteger getLikes() {
        return likes;
    }

    /**
     * @param likes the likes to set
     */
    public void setLikes(BigInteger likes) {
        this.likes = likes;
    }

    /**
     * @return the dislikes
     */
    public BigInteger getDislikes() {
        return dislikes;
    }

    /**
     * @param dislikes the dislikes to set
     */
    public void setDislikes(BigInteger dislikes) {
        this.dislikes = dislikes;
    }

    /**
     * @return the favoritedCount
     */
    public BigInteger getFavoritedCount() {
        return favoritedCount;
    }

    /**
     * @param favoritedCount the favoritedCount to set
     */
    public void setFavoritedCount(BigInteger favoritedCount) {
        this.favoritedCount = favoritedCount;
    }

   
}
