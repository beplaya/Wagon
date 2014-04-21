package com.aj.wagon;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;

public interface IWagonPostListener {

	public void onHTTPResponse(HttpResponse response);

	public void onClientProtocolException(ClientProtocolException e);

	public void onIOException(IOException e);

	public void onInvalidURI(String uri);

	public void onIllegalStateException(IllegalStateException e);

}
