package http_connections;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;


public class HttpConnectionImageDecoding {

	
	int imageWidth;
	int imageHeight;
	String imageType;
	
	
	
	public String getJSONString(String URL1, Context context){
		String JSONString = "";
		
		if(CheckInternet(context)){
			HttpClient client = new DefaultHttpClient();
			HttpGet getrequest = new  HttpGet(URL1);
			try {
			
			
				HttpResponse getresponse = client.execute(getrequest);
				HttpParams parameters = new BasicHttpParams();
				HttpConnectionParams.setConnectionTimeout(parameters, 6000);
				int responseCode = getresponse.getStatusLine().getStatusCode();
			
				if(responseCode == 200){
					BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getresponse.getEntity().getContent(), "UTF-8"));
					String temp = "";
					while((temp = bufferedReader.readLine()) != null){
						JSONString += temp;
					}
				}
				else{
				
					AlertDialog.Builder builder = new AlertDialog.Builder(context);
					builder.setTitle("Error");
					builder.setMessage("Error Code ="+responseCode);
					client.getConnectionManager().closeExpiredConnections();
					client.getConnectionManager().shutdown();
					builder.show();
				
				}
			} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
			// TODO Auto-generated catch block
				
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				builder.setTitle("Error with network connection");
				builder.setMessage("Error occured getting data");
				builder.show();
			}
		}
		else{
			
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			builder.setTitle("Error with network connection");
			builder.setMessage("No Internet connection found. Internet connection is required for this app");
			builder.show();
			
		}
					
		return JSONString;
	}
	
	
	public void getInitialDecodedValues(String URL) {
		
				
		try {
			URL url = new URL(URL);
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(url.openConnection().getInputStream());
			imageHeight = options.outHeight;
			imageWidth = options.outWidth;
			imageType = options.outMimeType;
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public int calculateSampleSize(int gotWidth, int gotHeight, int reqWidth, int reqHeight){
		
		int inSampleSize = 1;
		if(gotHeight > reqHeight || gotWidth > reqWidth){
			
			int heightRatio = Math.round(gotHeight/reqHeight);
			int widthRatio = Math.round(gotWidth/reqWidth);
			// Choose the smallest ratio as inSampleSize value, this will guarantee
	        // a final image with both dimensions larger than or equal to the
	        // requested height and width.
			
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
			
		}
		
		
		return inSampleSize;
	}
	
	
	public Bitmap decodeBitmapFromURL(String url1, int reqWidth, int reqHeight){
		
		Bitmap pics1 = null;
		try{
		final BitmapFactory.Options options = new BitmapFactory.Options();
	    options.inJustDecodeBounds = true;
	    BitmapFactory.decodeStream(new URL(url1).openConnection().getInputStream());

	    // Calculate inSampleSize
	    options.inSampleSize = calculateSampleSize(imageWidth,imageHeight, reqWidth, reqHeight);
		
	    options.inJustDecodeBounds = false;
		
	    pics1 = BitmapFactory.decodeStream(new URL(url1).openConnection().getInputStream());
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return pics1;
		
	}
	
	public boolean CheckInternet(Context context) 
	{
	    ConnectivityManager connec = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    android.net.NetworkInfo wifi = connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
	    android.net.NetworkInfo mobile = connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

	    // Here if condition check for wifi and mobile network is available or not.
	    // If anyone of them is available or connected then it will return true, otherwise false;

	    if (wifi.isConnected()) {
	        return true;
	    } else if (mobile.isConnected()) {
	        return true;
	    }
	    return false;
	}
	
	
}
