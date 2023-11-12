package ru.era.distributionoftasks.yandexgeocoder;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;

@Service
public class YandexGeocoderService {
    public GeoPoint sendRequestForConverting(String adress)
    {
        YaGeocoder geocoder = new YaGeocoder(new DefaultHttpClient());
        GeocoderResponse response = null;
        try {
            response = geocoder.directGeocode(adress);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        GeoObject geoObject = response.getGeoObjects().get(0);
        return geoObject.getPoint();
    }
}
