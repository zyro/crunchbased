package com.github.zyro.crunchbase.service;

import com.github.zyro.crunchbase.entity.Company;
import com.google.common.io.CharStreams;
import com.google.gson.Gson;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.api.Scope;
import lombok.NoArgsConstructor;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

@EBean(scope = Scope.Singleton)
@NoArgsConstructor
public class ApiClient {

    protected static final String API_KEY = "api_key=9za7pzrvfch6quf3vgwp2dja";

    protected Gson gson = new Gson();

    /**
     * Get from the remote API a company's data, identified by permalink.
     *
     * @param permalink The permalink String to get data for.
     * @return The company's data as a Company object.
     * @throws ClientException When there is a connection problem.
     */
    public Company getCompany(final String permalink) throws ClientException {
        final String response = performGetRequest(permalink, "company");
        return gson.fromJson(response, Company.class);
    }

    public String getPerson(final String permalink) throws ClientException {
        return performGetRequest(permalink, "person");
    }

    public String getFinancialOrganization(final String permalink)
            throws ClientException {
        return performGetRequest(permalink, "financial-organization");
    }

    public String getProduct(final String permalink) throws ClientException {
        return performGetRequest(permalink, "product");
    }

    public String getServiceProvider(final String permalink)
            throws ClientException {
        return performGetRequest(permalink, "service-provider");
    }

    /**
     * Retrieve the response string for a given API request.
     *
     * @param permalink The entity permalink to get.
     * @param get The 'get' request namespace. Should be one of: 'company',
     *            'financial-organization', 'person', 'product' or
     *            'service-provider'.
     * @return The raw response String in its entirety.
     * @throws ClientException When there is a connection problem.
     */
    protected String performGetRequest(final String permalink, final String get)
            throws ClientException {
        try {
            final HttpURLConnection connection = (HttpURLConnection) new URL(
                    "http://api.crunchbase.com/v/1/" + get + "/" +
                    URLEncoder.encode(permalink.toLowerCase(), "UTF-8") +
                    ".js?" + API_KEY).openConnection();
            final InputStreamReader in = new InputStreamReader(
                    new BufferedInputStream(connection.getInputStream()));
            final String response = CharStreams.toString(in);
            in.close();
            connection.disconnect();
            return response;
        }
        catch(UnsupportedEncodingException e) {
            throw new ClientException(e);
        }
        catch(IOException e) {
            throw new ClientException(e);
        }
    }

}