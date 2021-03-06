package com.github.nicolassmith.urlevaluator;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.net.Uri;
import android.util.Log;

/** This is the simplest version of the {@link EvaluatorTask} class. **/
public class GeneralEvaluatorTask extends EvaluatorTask {

	private static final String TAG = "GeneralEvaluatorTask";

	public GeneralEvaluatorTask(EvaluatorTaskCaller passedCaller) {
		super(passedCaller);
	}

	@Override
	public Uri evaluate(Uri inputUri) {
		HttpURLConnection con;
		int responseCode = 0;
		String location = null;
		try {
			// thanks to StackOverflow user inno (question 2659000)
			con = (HttpURLConnection) (new URL(inputUri.toString()).openConnection());
			con.setInstanceFollowRedirects(false);
			con.setRequestMethod("HEAD");
			// This turns off gzip compression, because some servers lie!
			// And this confuses the HttpEngine decoder.
			con.setRequestProperty("Accept-Encoding", "identity");
			con.connect();
			responseCode = con.getResponseCode();
			location = con.getHeaderField("Location");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (Log.isLoggable(TAG, Log.DEBUG)) {
			Log.d(TAG, "response code = " + responseCode);
			Log.d(TAG, "Location = " + location);
		}

		if (location == null){
			return null;
		} else {
			return Uri.parse(location);
		}
	}

}
