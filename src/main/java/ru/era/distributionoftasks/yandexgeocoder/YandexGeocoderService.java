package ru.era.distributionoftasks.yandexgeocoder;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.apache.http.impl.client.DefaultHttpClient;
import ru.era.distributionoftasks.caching.entities.GeoPointCache;
import ru.era.distributionoftasks.caching.services.GeoPointCacheService;

import java.io.IOException;

@Service
public class YandexGeocoderService {
    @Autowired
    private GeoPointCacheService geoPointCacheService;

    public GeoPoint sendRequestForConverting(String address)
    {
        GeoPoint geoPoint = geoPointCacheService.findGeoPoint(address);
        if(geoPoint != null) {
            return geoPoint;
        } else {
            YaGeocoder geocoder = new YaGeocoder(new DefaultHttpClient());
            GeocoderResponse response = null;
            try {
                response = geocoder.directGeocode(address);
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
