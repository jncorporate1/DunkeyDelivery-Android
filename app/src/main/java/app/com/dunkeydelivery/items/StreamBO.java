package app.com.dunkeydelivery.items;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Developer on 5/31/2017.
 */

public class StreamBO implements Parcelable {

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("user_id")
    @Expose
    public String userId;
    @SerializedName("uuid")
    @Expose
    public String uuid;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("stream_ip")
    @Expose
    public String streamIp;
    @SerializedName("stream_port")
    @Expose
    public String streamPort;
    @SerializedName("stream_app")
    @Expose
    public String streamApp;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("privacy_setting")
    @Expose
    public String privacySetting;
    @SerializedName("quality")
    @Expose
    public String quality;
    @SerializedName("is_public")
    @Expose
    public String isPublic;
    @SerializedName("allow_comments")
    @Expose
    public String allowComments;
    @SerializedName("allow_tag_requests")
    @Expose
    public String allowTagRequests;
    @SerializedName("available_later")
    @Expose
    public String availableLater;
    @SerializedName("lng")
    @Expose
    public String lng;
    @SerializedName("lat")
    @Expose
    public String lat;
    @SerializedName("total_likes")
    @Expose
    public String totalLikes;
    @SerializedName("total_dislikes")
    @Expose
    public String totalDislikes;
    @SerializedName("total_shares")
    @Expose
    public String totalShares;
    @SerializedName("total_viewers")
    @Expose
    public String totalViewers;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("updated_at")
    @Expose
    public String updatedAt;
    @SerializedName("status_text")
    @Expose
    public String statusText;
    @SerializedName("quality_text")
    @Expose
    public String qualityText;

    public String streamUsername;
    public String streamPassword;
    public boolean isStreaming;

    public boolean isStreaming() {
        return isStreaming;
    }

    public void setStreaming(boolean streaming) {
        isStreaming = streaming;
    }

    public String getStreamUsername() {
        if(TextUtils.isEmpty(streamUsername))
        {
            return "";
        }
        return streamUsername;
    }

    public void setStreamUsername(String streamUsername) {
        this.streamUsername = streamUsername;
    }

    public String getStreamPassword() {
        if(TextUtils.isEmpty(streamPassword))
        {
            return "";
        }
        return streamPassword;
    }

    public void setStreamPassword(String streamPassword) {
        this.streamPassword = streamPassword;
    }

    public String getId() {
        if(TextUtils.isEmpty(id))
        {
            return "";
        }
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        if(TextUtils.isEmpty(userId))
        {
            return "";
        }
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUuid() {
        if(TextUtils.isEmpty(uuid))
        {
            return "";
        }
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        if(TextUtils.isEmpty(name))
        {
            return "";
        }
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreamIp() {
        if(TextUtils.isEmpty(streamIp))
        {
            return "";
        }
        return streamIp;
    }

    public void setStreamIp(String streamIp) {
        this.streamIp = streamIp;
    }

    public String getStreamPort() {
        if(TextUtils.isEmpty(streamPort))
        {
            return "";
        }
        return streamPort;
    }

    public void setStreamPort(String streamPort) {
        this.streamPort = streamPort;
    }

    public String getStreamApp() {
        if(TextUtils.isEmpty(streamApp))
        {
            return "";
        }
        return streamApp;
    }

    public void setStreamApp(String streamApp) {
        this.streamApp = streamApp;
    }

    public String getStatus() {
        if(TextUtils.isEmpty(status))
        {
            return "";
        }
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPrivacySetting() {
        if(TextUtils.isEmpty(privacySetting))
        {
            return "";
        }
        return privacySetting;
    }

    public void setPrivacySetting(String privacySetting) {
        this.privacySetting = privacySetting;
    }

    public String getQuality() {
        if(TextUtils.isEmpty(quality))
        {
            return "";
        }
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getIsPublic() {
        if(TextUtils.isEmpty(isPublic))
        {
            return "";
        }
        return isPublic;
    }

    public void setIsPublic(String isPublic) {
        this.isPublic = isPublic;
    }

    public String getAllowComments() {
        if(TextUtils.isEmpty(allowComments))
        {
            return "";
        }
        return allowComments;
    }

    public void setAllowComments(String allowComments) {
        this.allowComments = allowComments;
    }

    public String getAllowTagRequests() {
        if(TextUtils.isEmpty(allowTagRequests))
        {
            return "";
        }
        return allowTagRequests;
    }

    public void setAllowTagRequests(String allowTagRequests) {
        this.allowTagRequests = allowTagRequests;
    }

    public String getAvailableLater() {
        if(TextUtils.isEmpty(availableLater))
        {
            return "";
        }
        return availableLater;
    }

    public void setAvailableLater(String availableLater) {
        this.availableLater = availableLater;
    }

    public String getLng() {
        if(TextUtils.isEmpty(lng))
        {
            return "";
        }
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        if(TextUtils.isEmpty(lat))
        {
            return "";
        }
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getTotalLikes() {
        if(TextUtils.isEmpty(totalLikes))
        {
            return "";
        }
        return totalLikes;
    }

    public void setTotalLikes(String totalLikes) {
        this.totalLikes = totalLikes;
    }

    public String getTotalDislikes() {
        if(TextUtils.isEmpty(totalDislikes))
        {
            return "";
        }
        return totalDislikes;
    }

    public void setTotalDislikes(String totalDislikes) {
        this.totalDislikes = totalDislikes;
    }

    public String getTotalShares() {
        if(TextUtils.isEmpty(totalShares))
        {
            return "";
        }
        return totalShares;
    }

    public void setTotalShares(String totalShares) {
        this.totalShares = totalShares;
    }

    public String getTotalViewers() {
        if (!TextUtils.isEmpty(totalViewers))
            return totalViewers;
        else
            return "0";
    }

    public void setTotalViewers(String totalViewers) {
        this.totalViewers = totalViewers;
    }

    public String getCreatedAt() {
        if(TextUtils.isEmpty(createdAt))
        {
            return "";
        }
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        if(TextUtils.isEmpty(updatedAt))
        {
            return "";
        }
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getStatusText() {
        if(TextUtils.isEmpty(statusText))
        {
            return "";
        }
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public String getQualityText() {
        if(TextUtils.isEmpty(qualityText))
        {
            return "";
        }
        return qualityText;
    }

    public void setQualityText(String qualityText) {
        this.qualityText = qualityText;
    }


    public StreamBO() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.userId);
        dest.writeString(this.uuid);
        dest.writeString(this.name);
        dest.writeString(this.streamIp);
        dest.writeString(this.streamPort);
        dest.writeString(this.streamApp);
        dest.writeString(this.status);
        dest.writeString(this.privacySetting);
        dest.writeString(this.quality);
        dest.writeString(this.isPublic);
        dest.writeString(this.allowComments);
        dest.writeString(this.allowTagRequests);
        dest.writeString(this.availableLater);
        dest.writeString(this.lng);
        dest.writeString(this.lat);
        dest.writeString(this.totalLikes);
        dest.writeString(this.totalDislikes);
        dest.writeString(this.totalShares);
        dest.writeString(this.totalViewers);
        dest.writeString(this.createdAt);
        dest.writeString(this.updatedAt);
        dest.writeString(this.statusText);
        dest.writeString(this.qualityText);
        dest.writeString(this.streamUsername);
        dest.writeString(this.streamPassword);
        dest.writeByte(this.isStreaming ? (byte) 1 : (byte) 0);
    }

    protected StreamBO(Parcel in) {
        this.id = in.readString();
        this.userId = in.readString();
        this.uuid = in.readString();
        this.name = in.readString();
        this.streamIp = in.readString();
        this.streamPort = in.readString();
        this.streamApp = in.readString();
        this.status = in.readString();
        this.privacySetting = in.readString();
        this.quality = in.readString();
        this.isPublic = in.readString();
        this.allowComments = in.readString();
        this.allowTagRequests = in.readString();
        this.availableLater = in.readString();
        this.lng = in.readString();
        this.lat = in.readString();
        this.totalLikes = in.readString();
        this.totalDislikes = in.readString();
        this.totalShares = in.readString();
        this.totalViewers = in.readString();
        this.createdAt = in.readString();
        this.updatedAt = in.readString();
        this.statusText = in.readString();
        this.qualityText = in.readString();
        this.streamUsername = in.readString();
        this.streamPassword = in.readString();
        this.isStreaming = in.readByte() != 0;
    }

    public static final Creator<StreamBO> CREATOR = new Creator<StreamBO>() {
        @Override
        public StreamBO createFromParcel(Parcel source) {
            return new StreamBO(source);
        }

        @Override
        public StreamBO[] newArray(int size) {
            return new StreamBO[size];
        }
    };
}
