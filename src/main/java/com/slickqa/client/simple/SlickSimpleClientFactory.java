package com.slickqa.client.simple;

import com.slickqa.client.simple.impl.SlickSimpleClientImpl;
import org.glassfish.jersey.client.JerseyClientBuilder;

import javax.ws.rs.client.Client;

/**
 * Created by Keith on 10/26/16.
 */
public class SlickSimpleClientFactory {
    private SlickSimpleClientFactory() {}

    public static SlickSimpleClientImpl getSlickSimpleClient(String baseUrl) {
        Client restClient = JerseyClientBuilder.createClient();
        return new SlickSimpleClientImpl(baseUrl, restClient);
    }
}
