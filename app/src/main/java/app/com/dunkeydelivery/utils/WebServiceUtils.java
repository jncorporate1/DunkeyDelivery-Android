package app.com.dunkeydelivery.utils;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import app.com.dunkeydelivery.Constants;
import app.com.dunkeydelivery.items.TaskItem;


/**
 * Created by Developer on 8/11/2015.
 */

public class WebServiceUtils {

    public static String getResponseMessage(TaskItem taskItem) {
        if (taskItem.getServiceError() != null) {
            EnumUtils.ServiceResponseMessage serviceErrors = taskItem.getServiceError();
            if (serviceErrors != null) {
                switch (serviceErrors) {
                    case InvalidResponse:
                        return taskItem.getServiceStatusMessage(); // "Invalid Response"
                    case NetworkError:
                        return "The internet connection seems to be offline.";
                    case ServerNotReachable:
                        return "Server not reachable.";
                    case ConnectionTimeOut:
                        return "Connection Timeout";
                    default:
                        return "Invalid Service";
                }
            }
        } else {
            return taskItem.getServiceStatusMessage();
        }

        return "";
    }

    public static HashMap<String, Object> getServiceParams(String[] keyArray, Object[] valueArray) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for (int i = 0; i < keyArray.length; i++) {
            map.put(keyArray[i], valueArray[i]);
        }
        return map;
    }

    public static boolean isEncrypt(String key) {
        if (key.contains(":")) {
            if (key.split(":")[1].equals("Encrypt"))
                return true;
            else
                return false;
        } else {
            return false;
        }
    }

    public static void showResponseError(Context context, TaskItem taskItem) {
        AlertOP.showAlert(context, "", WebServiceUtils.getResponseMessage(taskItem));
    }


    //if service name contain '-' this function will change to '_'. because we are using enum for webservice name.
    public static String filterServiceName(String serviceName) {
        if (serviceName.contains("_id")) {
            serviceName = serviceName.toString().replace("_id", "");
        }else if (serviceName.contains("_")) {
            serviceName = serviceName.toString().replace("_", "-");
        }
        return serviceName;
    }

    //if service name contain '-' this function will change to '_'. because we are using enum for webservice name.
    public static boolean checkServiceNameID(EnumUtils.ServiceName serviceName) {
        return serviceName.toString().contains("_id");
    }

    public static TaskItem parseResponse(String response, EnumUtils.ServiceName serviceName, boolean isExternalService) {

        TaskItem taskItem = new TaskItem();
        taskItem.setServiceName(serviceName);
        try {
            LogUtils.i(serviceName.toString(), "parseResponse: " + response);
            if (isExternalService) {
                taskItem.setError(false);
                taskItem.setResponse(response);
            } else {
                JSONObject jsonObject = new JSONObject(response);
                taskItem.setResponse(response);
                taskItem.setServiceStatusMessage(jsonObject
                        .getString("Message"));
                int responseCode = jsonObject
                        .getInt("StatusCode");
                taskItem.setErrorCode(responseCode);

                if (responseCode != Constants.VALID_RESPONSE_CODE &&
                        responseCode != Constants.CREATED_RESPONSE_CODE &&
                        responseCode != Constants.UPDATED_RESPONSE_CODE) {

                    taskItem.setError(true);
                    taskItem.setServiceError(EnumUtils.ServiceResponseMessage.InvalidResponse);
                    if(jsonObject.has("Result")){
                        taskItem.setServiceStatusMessage(jsonObject.getJSONObject("Result")
                                .getString("ErrorMessage"));
                    }

                } else {
                    taskItem.setError(false);
                }
            }

        } catch (JSONException e) {
            taskItem.setError(true);
            taskItem.setServiceError(EnumUtils.ServiceResponseMessage.InvalidResponse);
            e.printStackTrace();
        }

        return taskItem;
    }

}//main
