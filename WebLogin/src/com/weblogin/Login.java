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
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * @author Jose Santiago
 * @link http://jiqqa.net
 */
public class Login extends Activity implements OnClickListener{
	
	// change YOURWEBSITE.COM TO POINT TO YOUR DOMAIN!!
	static final String url = "http://YOURDOMAIN.COM/webapp/index.php/handler/login";
	static final String login_ok = "ok";
	static final String login_fail = "fail";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button login = (Button) findViewById(R.id.login);
		login.setOnClickListener(this);
		
        Button register = (Button) findViewById(R.id.register);
		register.setOnClickListener(this);
        
    }

	@Override
	public void onClick(View v) {
		
		EditText username = (EditText) findViewById(R.id.username);
		EditText password = (EditText) findViewById(R.id.password);
		
		String username_text = username.getText().toString();
		String password_text = password.getText().toString();
		
		switch (v.getId()) {
		case R.id.register:
			Intent reg = new Intent (getApplicationContext(), Register.class);
			startActivity(reg);
			break;
		case R.id.login:
			doLogin(username_text, password_text);
		}
		
		
	}
	
	public void doLogin (String username, String password) {
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(url);
		
		try {
			List<NameValuePair> nvp = new ArrayList<NameValuePair>(2);
			nvp.add(new BasicNameValuePair("username", username));
			nvp.add(new BasicNameValuePair("password", password));
			
			httppost.setHeader("Content-Type", "application/x-www-form-urlencoded");
			httppost.setEntity(new UrlEncodedFormEntity(nvp));
			
			HttpResponse response = httpclient.execute(httppost);
			String ret = EntityUtils.toString(response.getEntity());
			
			
			// The login was successful!!
			if (ret.equals(login_ok)) {
				showToast("Logged In!", 0);
			}
			
			// Invailed username or password
			if (ret.equals(login_fail)){
				showToast("Login failed!", 0);
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