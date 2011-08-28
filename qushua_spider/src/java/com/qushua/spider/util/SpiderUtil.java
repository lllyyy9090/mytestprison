package com.qushua.spider.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
public class SpiderUtil {
	 public static String getContent(String url, String charset) {
	        HttpClient httpclient = new DefaultHttpClient();
	        HttpGet httpget = new HttpGet(url);
	        HttpResponse response = null;
	        try {
	            response = httpclient.execute(httpget);           
	            HttpEntity entity = response.getEntity();
	           
	            if (entity != null) {
	                long len = entity.getContentLength();
	                
	                if (len != -1 && len < 2048) {
	                    return EntityUtils.toString(entity,charset);
	                } else {
	                    ByteArrayOutputStream byteOutput= new ByteArrayOutputStream();
	                    InputStream is = entity.getContent();
	                    byte [] bs = new byte[2048];
	                    int length = 0;
	                    while ((length = is.read(bs,0,2048)) != -1) {
	                        byteOutput.write(bs,0,length);           
	                    }       
	                    byteOutput.flush();
	                    byteOutput.close();
	                    return byteOutput.toString(charset);

	                }
	            }
	        } catch (ClientProtocolException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return null;
	    }
	 
	 


}
