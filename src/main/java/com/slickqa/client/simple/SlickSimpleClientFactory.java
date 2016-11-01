package com.slickqa.client.simple;

import com.slickqa.client.simple.impl.SlickSimpleClientImpl;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

/**
 * Created by Keith on 10/26/16.
 */
public class SlickSimpleClientFactory {
    private SlickSimpleClientFactory() {}

    public static SlickSimpleClientImpl getSlickSimpleClient(String baseUrl) {
        Client restClient = ClientBuilder.newClient();
        return new SlickSimpleClientImpl(baseUrl, restClient);
    }

    public static SlickSimpleClientImpl getSlickSimpleClient(String baseUrl, Client restClient) {
        return new SlickSimpleClientImpl(baseUrl, restClient);
    }
}
