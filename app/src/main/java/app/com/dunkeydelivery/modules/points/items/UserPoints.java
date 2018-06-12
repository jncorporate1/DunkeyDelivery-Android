package app.com.dunkeydelivery.modules.points.items;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Developer on 1/15/2018.
 */

public class UserPoints {

    @SerializedName("RewardPoints")
    int RewardPoints;

    public int getRewardPoints() {
        if(RewardPoints==0)
        {
            return 0;
        }
        return RewardPoints;
    }

    public void setRewardPoints(int rewardPoints) {
        RewardPoints = rewardPoints;
    }
}
