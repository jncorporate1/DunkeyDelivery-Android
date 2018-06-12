package app.com.dunkeydelivery.items;


import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import app.com.dunkeydelivery.utils.EnumUtils.ServiceResponseMessage;
import app.com.dunkeydelivery.utils.EnumUtils.ServiceName;
import app.com.dunkeydelivery.utils.Keys;

/**
 * Created by Developer on 30/05/2017.
 */
public class TaskItem {

	private String response;
	private ServiceName serviceName;
	private boolean isError;
	private String serviceStatusMessage;
	private int errorCode;

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	private ServiceResponseMessage serviceError;

	public String getServiceStatusMessage() {
		return serviceStatusMessage;
	}

	public void setServiceStatusMessage(String serviceStatusMessage) {
		this.serviceStatusMessage = serviceStatusMessage;
	}

	/**
	 * Returns the actual data object from the json response
	 * @return
	 */
	public String getResponse() {
		try {
			JSONObject jObj = new JSONObject(response);
			return jObj.getJSONObject(Keys.DATA_ITEM_JSON_WEBSERVICE).toString();
		} catch (JSONException e) {
		} catch (NullPointerException e) {
		}
		serviceError = ServiceResponseMessage.InvalidResponse;
		return "";
	}

	//in case if we need the whole raw response...
	public String getRawResponse() {
		if(TextUtils.isEmpty(response))
		{
			return "";
		}
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public ServiceName getServiceName() {
		return serviceName;
	}

	public void setServiceName(ServiceName serviceName) {
		this.serviceName = serviceName;
	}

	public boolean isError() {
		return isError;
	}

	public void setError(boolean isError) {
		this.isError = isError;
	}

	public ServiceResponseMessage getServiceError() {
		return serviceError;
	}

	public void setServiceError(ServiceResponseMessage serviceError) {
		this.serviceError = serviceError;
	}
}
