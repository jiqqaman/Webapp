package com.weblogin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * @author Jose Santiago
 * @link http://jiqqa.net
 */
public class Register extends Activity implements OnClickListener {
	
	
	// change YOURWEBSITE.COM TO POINT TO YOUR DOMAIN!!
	static final String url = "http://YOURWEBSITE.COM/webapp/index.php/handler/register";
	static final String username_used = "username_used";
	static final String registered = "registered";
	static final String email_used = "email_used";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		
		Button register = (Button) findViewById(R.id.register);
		register.setOnClickListener(this);
				
	}

	public void onClick(View v) {
		
		EditText username = (EditText) findViewById(R.id.username);
		EditText password = (EditText) findViewById(R.id.password);
		EditText email = (EditText) findViewById(R.id.email);
		
		String username_text = username.getText().toString();
		String password_text = password.getText().toString();
		String email_text = email.getText().toString();
		
		switch (v.getId()) {
		
		case R.id.register:
			doRegister(username_text, password_text, email_text);
		}

	}
	
	public void doRegister (String username, String password, String email) {
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(url);
		try {
			List<NameValuePair> nvp = new ArrayList<NameValuePair>(2);
			nvp.add(new BasicNameValuePair("username", username));
			nvp.add(new BasicNameValuePair("password", password));
			nvp.add(new BasicNameValuePair("email", email));
			
			httppost.setHeader("Content-Type", "application/x-www-form-urlencoded");
			httppost.setEntity(new UrlEncodedFormEntity(nvp));
			
			HttpResponse response = httpclient.execute(httppost);
			String ret = EntityUtils.toString(response.getEntity());
			
			Log.w("Response: ", "" + ret);
			
			// The username has already been registered
			if (ret.equals(username_used)) {
				showToast("Username is already taken!", 0);
			}
			
			// The username has already been registered
			if (ret.equals(email_used)) {
				showToast("Email has already been registered!", 0);
			}
			
			// The registration was successful
			if (ret.equals(registered)){
				showToast("Registration Successful!", 0);
				finish();
			}
			
		} catch (ClientProtocolException e) {
			
		} catch (IOException e) {
			
		}
	}
	
	
	public void showToast(String msg, int length) {
		// 0 = Toast.LENGTH_LONG
		// 1 = Toast.LENGTH_SHORT
		Toast.makeText(getApplicationContext(), msg, length).show();		
	}

}
