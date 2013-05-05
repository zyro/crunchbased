package com.github.zyro.crunchbase.service;

import com.github.zyro.crunchbase.entity.Company;
import com.google.gson.Gson;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.api.Scope;
import org.apache.http.HttpHost;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@EBean(scope = Scope.Singleton)
public class ApiClient {

    private static final String API_KEY = "api_key=9za7pzrvfch6quf3vgwp2dja";

    private HttpHost target;
    private DefaultHttpClient client;

    private Gson gson;

    public ApiClient() {
        target = new HttpHost("api.crunchbase.com", 80, "http");
        final HttpParams params = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(params, 10000);
        HttpConnectionParams.setSoTimeout(params, 10000);
        client = new DefaultHttpClient(params);
        gson = new Gson();
    }

    public String showCompany(final String entity) {
        final String response = performShowRequest(entity, "company");
        return gson.fromJson(response, Company.class).toString();
    }

    public String showPerson(final String entity) {
        return performShowRequest(entity, "person");
    }

    public String showFinancialOrganization(final String entity) {
        return performShowRequest(entity, "financial-organization");
    }

    public String showProduct(final String entity) {
        return performShowRequest(entity, "product");
    }

    public String showServiceProvider(final String entity) {
        return performShowRequest(entity, "service-provider");
    }

    private String performShowRequest(final String entity, final String show) {
        try {
            final HttpGet request = new HttpGet("/v/1/" + show + "/" +
                    URLEncoder.encode(entity.toLowerCase(), "UTF-8") + ".js?" +
                    API_KEY);
            final ResponseHandler<String> handler = new BasicResponseHandler();
            return client.execute(target, request, handler);
        }
        catch(UnsupportedEncodingException e) {
            return e.getMessage();
        }
        catch(IOException e) {
            return e.getMessage();
        }
    }

}