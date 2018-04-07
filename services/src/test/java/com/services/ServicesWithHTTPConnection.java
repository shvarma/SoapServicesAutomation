package com.services;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.w3c.dom.Document;

public class ServicesWithHTTPConnection {
	
	public static void main(String args[]) throws IOException {

		String soapEndpointUrl = "http://www.dneonline.com/calculator.asmx";
		String soapAction = "http://tempuri.org/Add";
		URL url = new URL(soapEndpointUrl);
		String responseString = "";
		String outputString = "";
		URLConnection connection = url.openConnection();
		HttpURLConnection httpConn = (HttpURLConnection) connection;
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		String xmlInput =
		"<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:nms=\"http://tempuri.org/\"><SOAP-ENV:Header/>"
			 +"<SOAP-ENV:Body>"
			 	+"<nms:Add>"
			 		+"<nms:intA>22</nms:intA>"
			 		+"<nms:intB>23</nms:intB>"
			 	+"</nms:Add>"
			 +"</SOAP-ENV:Body>"
		+"</SOAP-ENV:Envelope>";
		System.out.println("Request: " + xmlInput);
		byte[] buffer = new byte[xmlInput.length()];
		buffer = xmlInput.getBytes();
		bout.write(buffer);
		byte[] b = bout.toByteArray();
		
		// Set the appropriate HTTP parameters.
		httpConn.setRequestProperty("Content-Length", String.valueOf(b.length));
		httpConn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
		httpConn.setRequestProperty("SOAPAction", soapAction);
		httpConn.setRequestMethod("POST");
		httpConn.setDoOutput(true);
		httpConn.setDoInput(true);
		OutputStream out = httpConn.getOutputStream();
		//Write the content of the request to the outputstream of the HTTP Connection.
		out.write(b);
		out.close();
		//Ready with sending the request.
		
		
		//Read the response.
		InputStreamReader isr = new InputStreamReader(httpConn.getInputStream());
		BufferedReader in = new BufferedReader(isr);
		 
		//Write the SOAP message response to a String.
		while ((responseString = in.readLine()) != null) {
		outputString = outputString + responseString;
		}
		System.out.println("Response: " + outputString);
		
	}
}
