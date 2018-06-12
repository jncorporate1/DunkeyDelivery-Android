package app.com.dunkeydelivery.tasks;

import android.content.Context;

import java.util.HashMap;

import app.com.dunkeydelivery.interfaces.AsyncResponseCallBack;
import app.com.dunkeydelivery.items.TaskItem;
import app.com.dunkeydelivery.utils.EnumUtils;


public class SampleWebserviceCall {

	private void callStreamActionService(final int action, final boolean showLoader, Context context) {
		HashMap<String, Object> serviceParams = new HashMap<String, Object>();
		HashMap<String, Object> tokenServiceHeaderParams = new HashMap<String, Object>();


		new WebServicesVolleyTask(context, showLoader, "",
				EnumUtils.ServiceName.login, EnumUtils.ServiceName.getServicePath(EnumUtils.ServiceName.login),
				EnumUtils.RequestMethod.POST, serviceParams, tokenServiceHeaderParams, new AsyncResponseCallBack() {

			@Override
			public void onTaskComplete(TaskItem taskItem) {
				if (taskItem.isError()) {

				} else {
					try {


					} catch (Exception ex) {
						ex.printStackTrace();
					}
					// if response is successful then do something
				}
			}
		});
	}

}
