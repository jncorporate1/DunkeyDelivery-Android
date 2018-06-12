package app.com.dunkeydelivery.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import app.com.dunkeydelivery.Constants;
import app.com.dunkeydelivery.R;
import app.com.dunkeydelivery.interfaces.AsyncResponseCallBack;
import app.com.dunkeydelivery.items.TaskItem;
import app.com.dunkeydelivery.utils.EnumUtils;
import app.com.dunkeydelivery.utils.EnumUtils.ServiceResponseMessage;
import app.com.dunkeydelivery.utils.EnumUtils.AppDomain;
import app.com.dunkeydelivery.utils.EnumUtils.RequestMethod;
import app.com.dunkeydelivery.utils.EnumUtils.ServiceName;
import app.com.dunkeydelivery.utils.Keys;
import app.com.dunkeydelivery.utils.LogUtils;
import app.com.dunkeydelivery.utils.WebServiceUtils;

/**
 * Created by Developer on 5/30/2017.
 * <p>
 * This class contains new WebService architecture that is dependent on Volley library for calling services.
 * Currently this class is not using in the app but we will use this in future and replace this with
 * the current Services architecture.
 */
public class WebServicesVolleyTask {

    private Context context;
    private boolean isShowLoader, isExternalService = false;
    private ServiceName serviceName;
    private RequestMethod requestMethod;
    private HashMap<String, Object> serviceParams = new HashMap<String, Object>();
    private HashMap<String, Object> serviceHeaders;
    private String serverUrl;
    public AsyncResponseCallBack delegate = null;
    String dialogMessage, postData = null;
    ProgressDialog alertDialogFragment;
    private StringRequest request;
    private String TAG = this.getClass().getSimpleName();

    public WebServicesVolleyTask(Context context, boolean isShowLoader,
                                 String dialogMessage, ServiceName serviceName,
                                 RequestMethod requestMethod, HashMap<String, Object> service_Params,
                                 HashMap<String, Object> serviceHeaders,
                                 AsyncResponseCallBack delegate) {
        this.context = context;
        this.isShowLoader = isShowLoader;
        this.dialogMessage = dialogMessage;
        this.serviceName = serviceName;
        this.requestMethod = requestMethod;
        this.serviceParams = service_Params;
        this.serviceHeaders = serviceHeaders;
        this.delegate = delegate;
        this.isExternalService = false;

        // ** Here we check which url is to execute **//

        if (Constants.appDomain == AppDomain.LIVE) {
            serverUrl = context.getResources().getString(R.string.base_url_live);
        } else if (Constants.appDomain == AppDomain.QA) {
            serverUrl = context.getResources().getString(R.string.base_url_qa);
        } else if (Constants.appDomain == AppDomain.DEV) {
            serverUrl = context.getResources().getString(R.string.base_url_dev);
        }

        serverUrl = serverUrl + serviceName.toString();

        if (serviceName != ServiceName.ExternalService) {
            if (requestMethod == RequestMethod.GET) {
                callVolleyGetRequest();
            } else {
                callVolleyPostRequest();
            }
        }


    }

    // this constructor is used to handle some special service names that are not handled by enum
    // it only include the serviceName in string...
    public WebServicesVolleyTask(Context context, boolean isShowLoader,
                                 String dialogMessage, ServiceName serviceName, String strServiceName,
                                 RequestMethod requestMethod, HashMap<String, Object> service_Params,
                                 HashMap<String, Object> serviceHeaders,
                                 AsyncResponseCallBack delegate) {
        this.context = context;
        this.isShowLoader = isShowLoader;
        this.dialogMessage = dialogMessage;
        this.serviceName = serviceName;
        this.requestMethod = requestMethod;
        this.serviceParams = service_Params;
        this.serviceHeaders = serviceHeaders;
        this.delegate = delegate;
        this.isExternalService = false;

        // ** Here we check which url is to execute **//

        if (Constants.appDomain == AppDomain.LIVE) {
            serverUrl = context.getResources().getString(R.string.base_url_live);
        } else if (Constants.appDomain == AppDomain.QA) {
            serverUrl = context.getResources().getString(R.string.base_url_qa);
        } else if (Constants.appDomain == AppDomain.DEV) {
            serverUrl = context.getResources().getString(R.string.base_url_dev);
        }

        serverUrl = serverUrl + strServiceName;

        if (serviceName != ServiceName.ExternalService) {
            if (requestMethod == RequestMethod.GET) {
                callVolleyGetRequest();
            } else {
                callVolleyPostRequest();
            }
        }
    }


    //if user wants to send some specific url then call this constructor....
    public WebServicesVolleyTask(Context context, boolean isShowLoader,
                                 String dialogMessage, ServiceName serviceName,
                                 RequestMethod requestMethod, HashMap<String, Object> serviceParams,
                                 HashMap<String, Object> serviceHeaders, String serverUrl, String postBody,
                                 AsyncResponseCallBack delegate) {

        this(context, isShowLoader, dialogMessage, serviceName, requestMethod, serviceParams, serviceHeaders,
                delegate);
        this.isExternalService = true;
        this.serverUrl = serverUrl;


        if (requestMethod == RequestMethod.GET) {
            callVolleyGetRequest();
        } else {
            callVolleyPostRequest();
        }

    }
//
//    public WebServicesVolleyTask(Context context, boolean isShowLoader, String dialogMessage, ServiceName serviceName, String servicePath, RequestMethod post, String s, boolean b, HashMap<String, Object> tokenServiceHeaderParams, AsyncResponseCallBack delegate) {
//    }


    private ServiceResponseMessage getServiceError(String strErrorCode) {
        ServiceResponseMessage serviceResponseMessage = ServiceResponseMessage.InvalidResponse;
        try {
            int errorCode = Integer.parseInt(strErrorCode);
            serviceResponseMessage = ServiceResponseMessage.values()[errorCode];
        } catch (Exception ex) {
        }
        return serviceResponseMessage;
    }


    private void callVolleyPostRequest() {

//        addParams();
        onPreExecute();
        LogUtils.i("WebServiceTask", "callVolleyPostRequest: " + serverUrl);
        StringRequest request = new StringRequest(
                requestMethod.ordinal(),
                serverUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        onPostExecute(WebServiceUtils.parseResponse(response, serviceName, isExternalService));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        TaskItem taskItem = new TaskItem();
                        taskItem.setError(true);
                        taskItem.setServiceError(ServiceResponseMessage.NetworkError);
                        taskItem.setServiceName(serviceName);
                        onPostExecute(taskItem);
                        error.printStackTrace();
                    }
                }) {

//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                return getServiceParams();
//            }

            @Override
            public String getBodyContentType() {
                return super.getBodyContentType();
            }

            @Override
            public byte[] getBody() {
                try {
                    String postData = getPostData();
                    if (postData != null)
                        return postData.getBytes("UTF-8");
                    else {
                        return super.getBody();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    return null;
                }
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return getServiceHeaders();
            }

        };
        request.setRetryPolicy(new DefaultRetryPolicy(Constants.WEBSERVICE_WAITTIME,
                -1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }

    private Map<String, String> getServiceParams() {
        Map<String, String> paramsMap = new HashMap<>();

        if (serviceParams != null && serviceParams.size() > 0) {
            Iterator it = serviceParams.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();

                String key = ((String) pair.getKey());
                Object value = pair.getValue();

//                if (WebServiceUtils.isEncrypt(((String) pair.getKey()))) {
//                    paramsMap.put(name.split(":")[0], EncryptionOp
//                            .encryptToB64String(value + ""));
//
//                    LogUtils.i("RestClient", "Encrypted params: " + name.split(":")[0] +
//                            " value: " + EncryptionOp
//                            .encryptToB64String(value + ""));
//                } else
                if (!key.equals(Keys.Body))
                    paramsMap.put(key, value + "");

            }
        }
        return paramsMap;
    }


    @Nullable
    private String getPostData() {
        String postData = null;
        try {
            if (serviceParams != null && serviceParams.size() > 0) {
//                Map.Entry<String, Object> entry = serviceParams.entrySet().iterator().next();
//                String key = entry.getKey();

                postData = "";
                Iterator it = serviceParams.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry) it.next();

                    String key = ((String) pair.getKey());
                    String value = pair.getValue().toString();
                    if (!key.equals(Keys.Body)) {
                        String paramString = key + "="
                                + URLEncoder.encode(value, "UTF-8");
//                            String paramString = key + "="
//                                    + value;
                        if (postData.length() > 1) {
                            postData += "&" + paramString;
                        } else {
                            postData += paramString;
                        }
                    } else {
                        postData = (String) pair.getValue();
                    }
                }


//                if (key.equals(Keys.Body)) {
//                    postData = (String) entry.getValue();
//                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return postData;
    }


    //multipart
    private void callMultiPart() {

    }


    //------------------------ For volley Get service implementation -----------------------------//
    private void callVolleyGetRequest() {

        addParams();
        onPreExecute();
        LogUtils.i(TAG, "callVolleyGetRequest: " + serverUrl);

        request = new StringRequest(
                requestMethod.ordinal(),
                serverUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        onPostExecute(WebServiceUtils.parseResponse(response, serviceName, isExternalService));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();

                        TaskItem taskItem = new TaskItem();
                        taskItem.setError(true);
                        if (error instanceof NetworkError || error instanceof NoConnectionError) {

                            taskItem.setServiceError(ServiceResponseMessage.NetworkError);

                        } else if (error instanceof TimeoutError) {
                            taskItem.setServiceError(ServiceResponseMessage.ConnectionTimeOut);
                        } else {

                            taskItem.setServiceStatusMessage("Invalid Response");
                            taskItem.setServiceError(ServiceResponseMessage.InvalidResponse);
                        }


                        taskItem.setServiceName(serviceName);
                        onPostExecute(taskItem);
                    }
                }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return getServiceHeaders();
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(Constants.WEBSERVICE_WAITTIME,
                -1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request.setShouldCache(true);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }

    public void cancelTask() {
        if (request != null) {
            request.cancel();
        }
    }

    // add params is used to append params in service url for GET Request
    private void addParams() {

        try {
            String combinedParams = "";
            if (serviceParams != null && serviceParams.size() > 0) {
                combinedParams += "?";

                Iterator it = serviceParams.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry) it.next();

                    String key = ((String) pair.getKey());
                    String value = pair.getValue().toString();

                    // if encryption is required to any value then do it...
                    if (WebServiceUtils.isEncrypt(key)) {
//                        key = key.split(":")[0];
//                        value = EncryptionOp
//                                .encryptToB64String(value + "");
                    }
                    if (!key.equals(Keys.Body)) {
                        String paramString = key + "="
                                + URLEncoder.encode(value, "UTF-8");
                        if (combinedParams.length() > 1) {
                            combinedParams += "&" + paramString;
                        } else {
                            combinedParams += paramString;
                        }
                    }
                }
                serverUrl = serverUrl + combinedParams;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //---------------------- General Helping Methods used in both Get/Post requests --------------------//
    private void onPreExecute() {
        try {
            if (isShowLoader) {
                if (TextUtils.isEmpty(dialogMessage)) {
                    dialogMessage = "Loading...";
                }
//                AppCompatActivity activity = ((AppCompatActivity) context);
                alertDialogFragment = ProgressDialog.show(context, "Please wait", dialogMessage);
//                alertDialogFragment.show(activity.getSupportFragmentManager(), "");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void onPostExecute(TaskItem taskItem) {
        try {
            if (isShowLoader && alertDialogFragment != null && alertDialogFragment.isShowing()) {
                alertDialogFragment.dismiss();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (delegate != null)
            delegate.onTaskComplete(taskItem);
    }


    private Map<String, String> getServiceHeaders() {
        Map<String, String> headerMap = new HashMap<>();

        if (serviceHeaders != null) {
            Iterator it = serviceHeaders.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();

                String name = ((String) pair.getKey());
                Object value = pair.getValue();

                if (WebServiceUtils.isEncrypt(((String) pair.getKey()))) {
                    //used for the case of encrypted parameters...
//                    headerMap.put(name.split(":")[0], EncryptionOp
//                            .encryptToB64String(value + ""));
//
//                    LogUtils.i("RestClient", "Encrypted params: " + name.split(":")[0] +
//                            " value: " + EncryptionOp
//                            .encryptToB64String(value + ""));
                } else
                    headerMap.put(name, value + "");
//				it.remove();
            }
        }

        return headerMap;
    }




    /*
    private void getAuthToken(final ServiceName prevServiceName,
                              final RequestMethod prevRequestMethod,
                              final HashMap<String, Object> prevServiceParams,
                              final HashMap<String, Object> prevServiceHeaders,
                              final AsyncResponseCallBack prevDelegate) {

//		DeviceID as string
//		Source as AppSource (1 for ios and 2 for Android)
//		ID as integer ( 0 on first time token generation)

        JSONObject jObj = null;
        try {
            jObj = new JSONObject();
            jObj.put(Keys.DeviceID, DeviceOp.getDeviceID(context));
            jObj.put(Keys.Source, Constants.APP_SOURCE);
            jObj.put(Keys.Token_ID, "0");

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        String[] keyArray = {Keys.Body};
        Object[] valuesArray = {jObj.toString()};

        HashMap<String, Object> tokenServiceParams = WebServiceUtils
                .getServiceParams(keyArray, valuesArray);

        String value = Constants.CONST_HEADER + DateTimeOp.getCurrentDateTimeInUTC(Constants.dateFormat12);

        String[] headerKeyArray = {Keys.Key + Constants.Encrypt};
        Object[] headerValuesArray = {value};

        HashMap<String, Object> tokenServiceHeaderParams = WebServiceUtils
                .getServiceParams(headerKeyArray, headerValuesArray);

        boolean loaderActive = false;
        if (!serviceName.equals(ServiceName.Setting)
                || !serviceName.equals(ServiceName.States))
            loaderActive = true;
        WebServiceAsyncTask task = new WebServiceAsyncTask(context,
                loaderActive, null,
                ServiceName.GenerateToken,
                RequestMethod.POST, tokenServiceParams, tokenServiceHeaderParams,
                new AsyncResponseCallBack() {

                    @Override
                    public void onTaskComplete(TaskItem taskItem) {

//						updateHeader();
                        //call the previous service in which token is expired or invalid...
                        new WebServicesVolleyTask(context, true,
                                null, prevServiceName,
                                prevRequestMethod,
                                prevServiceParams, prevServiceHeaders,
                                prevDelegate);
                    }
                });
        task.execute();
    }
        */

}// mian
