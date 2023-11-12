package ru.era.distributionoftasks.yandexgeocoder;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.message.BasicHeader;

import java.io.IOException;

import java.net.URLEncoder;
public class YaGeocoder {

    private static final String GEOCODER_HOST = "https://geocode-maps.yandex.ru/1.x/";
    private static final int HTTP_OK = 200;

    private XmlResponseParser responseParser = new XmlResponseParser();

    private HttpClient httpClient;
    private Header refererHeader;



    public YaGeocoder(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public YaGeocoder(HttpClient httpClient, String referer) {
        this.httpClient = httpClient;
        refererHeader = new BasicHeader("Referer", referer);
    }

    public GeocoderResponse directGeocode(String geocode) throws IOException {
        String url = GEOCODER_HOST +
//                "?apikey=" + "61f5780b-ee71-47a9-93a5-6ac5fd5262e2" +
                "?apikey=" + "5c822cbe-26e7-454a-9e8f-f61626e86dc3" +
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