package app.com.dunkeydelivery.items;

import android.text.TextUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Developer on 8/28/2017.
 */

public class TokenBO {

    @SerializedName("access_token")
    @Expose
    public String accessToken;
    @SerializedName("token_type")
    @Expose
    public String tokenType;
    @SerializedName("expires_in")
    @Expose
    public String expiresIn;
    @SerializedName("refresh_token")
    @Expose
    public String refreshToken;

    public String getAccessToken() {
        if(TextUtils.isEmpty(accessToken))
        {
            return "";
        }
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        if(TextUtils.isEmpty(tokenType))
        {
            return "";
        }
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getExpiresIn() {
        if(TextUtils.isEmpty(expiresIn))
        {
            return "";
        }
        return expiresIn;
    }

    public void setExpiresIn(String expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getRefreshToken() {
        if(TextUtils.isEmpty(refreshToken))
        {
            return "";
        }
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
