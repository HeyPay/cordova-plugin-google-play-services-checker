package com.dinamexoft.googleplayserviceschecker;

import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GooglePlayServicesChecker extends CordovaPlugin {

    private static final String APPTAG = "GooglePlayServicesChecker";

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("check")) {
            Log.i(APPTAG, "checkPlayServices");
            JSONObject json = new JSONObject();
            final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
            GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
            int result = googleAPI.isGooglePlayServicesAvailable(this.cordova.getContext());
            if (result != ConnectionResult.SUCCESS) {
                Log.i(APPTAG, "result != ConnectionResult.SUCCESS");
                json.put("status", false).put("ConnectionResult", result);
                if (googleAPI.isUserResolvableError(result)) {
                    Log.i(APPTAG, "googleAPI.isUserResolvableError(result)");
                    json.put("message", "user action required");
                }
            } else {
                json.put("status", true);
            }
            callbackContext.success(json);
            return true;
        } else {
            callbackContext.error("Method not allowed");
            return false;
        }
    }
}
