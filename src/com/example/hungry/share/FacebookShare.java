package com.example.hungry.share;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.AsyncFacebookRunner.RequestListener;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Util;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;

public class FacebookShare {
	private Activity activity;
	private AsyncFacebookRunner mAsyncFacebookRunner;
	public static final String APP_ID = "332584900177245";
	//public static final String PAGE_ID = "322888714433978";

	private static Facebook facebook = new Facebook(APP_ID);
	private static final String[] PERMISSION = new String[] { "email",
		"publish_checkins", "user_events", "publish_stream"};
	
	public FacebookShare(Activity activity) {
		this.activity = activity;
		mAsyncFacebookRunner = new AsyncFacebookRunner(facebook);
	}

	public boolean isSessionValid() {	
		return SessionStore.restore(facebook, activity);
	}
	
	public boolean extendAccessToken() {
		return facebook.extendAccessTokenIfNeeded(activity, null);
	}

	public void authorize() {

		facebook.authorize(activity, PERMISSION,
				new DialogListener() {

					@Override
					public void onComplete(Bundle values) {
						Log.i("Complete", "Success");
						SessionStore.save(facebook, activity);
						SessionEvents.onLoginSuccess();
					}

					@Override
					public void onFacebookError(FacebookError e) {
						Log.i("Error", "FacebookError");
						SessionEvents.onLoginError(e.getMessage());
					}

					@Override
					public void onError(DialogError e) {
						Log.i("Error", "Error");
						SessionEvents.onLoginError(e.getMessage());
					}

					@Override
					public void onCancel() {
						Log.i("Error", "Cancel");
						SessionEvents.onLoginError("Action Cancel");
					}
				});

	}

	public void logout() {
		mAsyncFacebookRunner.logout(activity, new RequestListener() {

			@Override
			public void onMalformedURLException(MalformedURLException e,
					Object state) {

			}

			@Override
			public void onIOException(IOException e, Object state) {

			}

			@Override
			public void onFileNotFoundException(FileNotFoundException e,
					Object state) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onFacebookError(FacebookError e, Object state) {

			}

			@Override
			public void onComplete(String response, Object state) {
				SessionStore.clear(activity);
				Log.i("Logout", "Logout");
			}
		});
	}

	public void showPostDialog(Bundle postData) {
		Log.i("", "showPostDialog");
		facebook.dialog(activity, "feed", postData, new DialogListener() {

			@Override
			public void onFacebookError(FacebookError e) {
				SessionEvents.onPostFail();
			}

			@Override
			public void onError(DialogError e) {
				SessionEvents.onPostFail();
			}

			@Override
			public void onComplete(Bundle values) {

				final String postId = values.getString("post_id");
				if (postId != null) {
					SessionEvents.onPostSucceed();
				} else {
					final String error = values.getString("error");
					if (error != null)
						FacebookShare.this.authorize();
					else
						SessionEvents.onPostFail();
				}
			}

			@Override
			public void onCancel() {
				SessionEvents.onPostFail();
			}
		});
	}
	
	/*public void getName() {
		mAsyncFacebookRunner.request("me", new RequestListener() {

			@Override
			public void onMalformedURLException(MalformedURLException e,
					Object state) {
				activity.runOnUiThread(new Runnable() {

					@Override
					public void run() {
						SessionEvents.onGetFail();									
					}});
			}

			@Override
			public void onIOException(IOException e, Object state) {
			}

			@Override
			public void onFileNotFoundException(FileNotFoundException e,
					Object state) {
				activity.runOnUiThread(new Runnable() {

					@Override
					public void run() {
						SessionEvents.onGetFail();									
					}});
			}

			@Override
			public void onFacebookError(FacebookError e, Object state) {
				activity.runOnUiThread(new Runnable() {

					@Override
					public void run() {
						SessionEvents.onGetFail();									
					}});
			}

			@Override
			public void onComplete(String response, Object state) {
				Log.i("Reponse", response);

				try {
					JSONObject json = Util.parseJson(response);
					final String name = json.getString("name");
					final Bundle data = new Bundle();
					data.putString("name", name);
					activity.runOnUiThread(new Runnable() {

						@Override
						public void run() {
							SessionEvents.onGetSucceed(data);									
						}});
				} catch (JSONException e) {
					e.printStackTrace();
					activity.runOnUiThread(new Runnable() {

						@Override
						public void run() {
							SessionEvents.onGetError();									
						}});
				} catch (FacebookError e) {
					e.printStackTrace();
					activity.runOnUiThread(new Runnable() {

						@Override
						public void run() {
							SessionEvents.onGetError();									
						}});
				}
			}

		});
	}*/
	
	
	
	public void post(Bundle data) {
		mAsyncFacebookRunner.request("feed", data, "POST",
				new RequestListener() {

					@Override
					public void onComplete(String response, Object state) {
						Log.i("Post_response", response);

						JSONObject json;
						try {
							json = Util.parseJson(response);
							final String id = json.getString("id");
							activity.runOnUiThread(new Runnable() {

								@Override
								public void run() {
									SessionEvents.onPostSucceed();									
								}});
						} catch (JSONException e) {
							activity.runOnUiThread(new Runnable() {

								@Override
								public void run() {
									SessionEvents.onPostError();
									
								}});
							e.printStackTrace();
						} catch (FacebookError e) {
							activity.runOnUiThread(new Runnable() {

								@Override
								public void run() {
									SessionEvents.onPostError();
								}});							
							e.printStackTrace();
						}

					}

					@Override
					public void onIOException(IOException e, Object state) {
						activity.runOnUiThread(new Runnable() {

							@Override
							public void run() {
								SessionEvents.onPostFail();
							}});
					}

					@Override
					public void onFileNotFoundException(
							FileNotFoundException e, Object state) {
						activity.runOnUiThread(new Runnable() {

							@Override
							public void run() {
								SessionEvents.onPostFail();
							}});
					}

					@Override
					public void onMalformedURLException(
							MalformedURLException e, Object state) {
						activity.runOnUiThread(new Runnable() {

							@Override
							public void run() {
								SessionEvents.onPostFail();
							}});
					}

					@Override
					public void onFacebookError(FacebookError e, Object state) {
						activity.runOnUiThread(new Runnable() {

							@Override
							public void run() {
								SessionEvents.onPostFail();
							}});
					}
				}, null);
	}
	
	public void authorizeCallback(int requestCode, int resultCode, Intent data) {
		facebook.authorizeCallback(requestCode, resultCode, data);
	}
}
