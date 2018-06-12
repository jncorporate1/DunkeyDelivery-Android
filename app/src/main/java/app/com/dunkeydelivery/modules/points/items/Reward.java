package app.com.dunkeydelivery.modules.points.items;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Developer on 1/15/2018.
 */

public class Reward {

    @SerializedName("Id")
    Integer Id;
    @SerializedName("PointsRequired")
    Long PointsRequired;
    @SerializedName("AmountAward")
    Integer AmountAward;
    @SerializedName("Description")
    String Description;
    @SerializedName("RewardPrize_Id")
    Integer RewardPrize_Id;
    @SerializedName("RewardPrize")
    RewardPrize RewardPrize;


    public Integer getId() {
        if(Id==null)
        {
            return null;
        }
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Long getPointsRequired() {
        if(PointsRequired==null)
        {
            return null;
        }
        return PointsRequired;
    }

    public void setPointsRequired(Long pointsRequired) {
        PointsRequired = pointsRequired;
    }

    public Integer getAmountAward() {
        if(AmountAward==null)
        {
            return null;
        }
        return AmountAward;
    }

    public void setAmountAward(Integer amountAward) {
        AmountAward = amountAward;
    }

    public String getDescription() {
        if(Description==null)
        {
            return Description;
        }
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Integer getRewardPrize_Id() {
        if(RewardPrize_Id==null)
        {
            return null;
        }
        return RewardPrize_Id;
    }

    public void setRewardPrize_Id(Integer rewardPrize_Id) {
        RewardPrize_Id = rewardPrize_Id;
    }

    public app.com.dunkeydelivery.modules.points.items.RewardPrize getRewardPrize() {
        if(RewardPrize==null)
        {
            return null;
        }
        return RewardPrize;
    }

    public void setRewardPrize(app.com.dunkeydelivery.modules.points.items.RewardPrize rewardPrize) {
        RewardPrize = rewardPrize;
    }
}
