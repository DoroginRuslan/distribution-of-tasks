package ru.era.distributionoftasks.yandexgeocoder;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.apache.http.impl.client.DefaultHttpClient;
import ru.era.distributionoftasks.yandexgeocoder.caching.services.GeoPointCacheService;

import java.io.IOException;

@Service
public class YandexGeocoderService {
    @Autowired
    GeoPointCacheService geoPointCacheService;
    @Autowired
    YaGeocoder yaGeocoder;

    public GeoPoint sendRequestForConverting(String address)
    {
        GeoPoint geoPoint = geoPointCacheService.findGeoPoint(address);
        if(geoPoint != null) {
            return geoPoint;
        } else {
            GeocoderResponse response = null;
            try {
                response = yaGeocoder.directGeocode(address);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            GeoObject geoObject = response.getGeoObjects().get(0);
            GeoPoint geoPoint1 =  geoObject.getPoint();
            geoPointCacheService.saveGeoPoint(address, geoPoint1);
            return geoPoint1;
        }
    }
}
