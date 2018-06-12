package app.com.dunkeydelivery.modules.points.items;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Developer on 1/15/2018.
 */

public class Points {

    @SerializedName("UserPoints")
    UserPoints UserPoints;

    @SerializedName("Rewards")
    List<Reward> Rewards;

    public UserPoints getUserPoints() {
        if(UserPoints==null)
        {
            return null;
        }
        return UserPoints;
    }

    public void setUserPoints(UserPoints userPoints) {
        UserPoints = userPoints;
    }

    public List<Reward> getRewards() {
        if(Rewards==null)
        {
            return null;
        }
        return Rewards;
    }

    public void setRewards(List<Reward> rewards) {
        Rewards = rewards;
    }
}
