package ru.era.distributionoftasks.yandexgeocoder;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

import java.net.URLEncoder;

@Service
public class YaGeocoder {

    @Value("${yandex.geocoder.host}")
    private String geocoderHost;
    @Value("${yandex.geocoder.key}")
    private String geocodeKey;

    private static final int HTTP_OK = 200;

    private XmlResponseParser responseParser = new XmlResponseParser();

    private HttpClient httpClient;
    private Header refererHeader;



    public YaGeocoder() {
        this.httpClient = new DefaultHttpClient();
    }

    public YaGeocoder(HttpClient httpClient, String referer) {
        this.httpClient = httpClient;
        refererHeader = new BasicHeader("Referer", referer);
    }

    public GeocoderResponse directGeocode(String geocode) throws IOException {
        String url = geocoderHost + "/" +
                "?apikey=" + geocodeKey +
                "&geocode=" + URLEncoder.encode(geocode, "UTF-8");
        HttpUriRequest request = new HttpGet(url);
        request.addHeader(refererHeader);
        HttpResponse response = httpClient.execute(request);
        if (response.getStatusLine().getStatusCode() != HTTP_OK) {
            throw new IOException(response.getStatusLine().toString());
        }
        return responseParser.parse(response.getEntity().getContent());
    }
}