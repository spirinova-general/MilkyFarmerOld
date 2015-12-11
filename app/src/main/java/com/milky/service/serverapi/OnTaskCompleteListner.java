package com.milky.service.databaseutils.serverapi;

import org.json.JSONObject;

public interface OnTaskCompleteListner {
	 void onTaskCompleted(JSONObject result, String type);
}
