package com.services;

import org.junit.Test;
import javax.xml.soap.*;

public class ServicesWithSAAJ {

	public static void main(String args[]) {

		// SOAP with Attachments API for Java (SAAJ)
		String soapEndpointUrl = "http://www.dneonline.com/calculator.asmx";
		String soapAction = "http://tempuri.org/Add";

		callSoapWebService(soapEndpointUrl, soapAction);

	}

	 @SuppressWarnings("restriction")
	private static void createSoapEnvelope(SOAPMessage soapMessage) throws SOAPException {
	        SOAPPart soapPart = soapMessage.getSOAPPart();

	        String myNamespace = "nms";
	        String myNamespaceURI = "http://tempuri.org/";

	        // SOAP Envelope
	        SOAPEnvelope envelope = soapPart.getEnvelope();
	        envelope.addNamespaceDeclaration(myNamespace, myNamespaceURI);

	            /*
	            Constructed SOAP Request Message:
				<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/" xmlns:nms="http://tempuri.org/"><SOAP-ENV:Header/>
				    <SOAP-ENV:Body>
				        <nms:Add>
				            <nms:intA>100</nms:intA>
				            <nms:intB>100</nms:intB>
				        </nms:Add>
				    </SOAP-ENV:Body>
				</SOAP-ENV:Envelope>
	            */

	        // SOAP Body
	        SOAPBody soapBody = envelope.getBody();
	        SOAPElement soapBodyElem = soapBody.addChildElement("Add", myNamespace);
	        SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("intA", myNamespace);
	        SOAPElement soapBodyElem2 = soapBodyElem.addChildElement("intB", myNamespace);
	        soapBodyElem1.addTextNode("100");
	        soapBodyElem2.addTextNode("100");

	    }

	    @SuppressWarnings("restriction")
		private static void callSoapWebService(String soapEndpointUrl, String soapAction) {
	        try {
	            // Create SOAP Connection
	            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
	            SOAPConnection soapConnection = soapConnectionFactory.createConnection();

	            // Send SOAP Message to SOAP Server
	            @SuppressWarnings("restriction")
				SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(soapAction), soapEndpointUrl);

	            // Print the SOAP Response
	            System.out.println("Response SOAP Message:");
	            soapResponse.writeTo(System.out);
	            System.out.println();

	            soapConnection.close();
	        } catch (Exception e) {
	            System.err.println("\nError occurred while sending SOAP Request to Server!\nMake sure you have the correct endpoint URL and SOAPAction!\n");
	            e.printStackTrace();
	        }
	    }

	    @SuppressWarnings("restriction")
		private static SOAPMessage createSOAPRequest(String soapAction) throws Exception {
	        MessageFactory messageFactory = MessageFactory.newInstance();
	        SOAPMessage soapMessage = messageFactory.createMessage();

	        createSoapEnvelope(soapMessage);

	        MimeHeaders headers = soapMessage.getMimeHeaders();
	        headers.addHeader("SOAPAction", soapAction);

	        soapMessage.saveChanges();

	        /* Print the request message, just for debugging purposes */
	        System.out.println("Request SOAP Message:");
	        soapMessage.writeTo(System.out);
	        System.out.println("\n");

	        return soapMessage;
	    }
}
