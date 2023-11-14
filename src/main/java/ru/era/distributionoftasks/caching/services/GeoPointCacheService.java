package ru.era.distributionoftasks.caching.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.era.distributionoftasks.caching.entities.GeoPointCache;
import ru.era.distributionoftasks.caching.repositories.GeoPointCacheRepository;
import ru.era.distributionoftasks.yandexgeocoder.GeoPoint;

@Service
public class GeoPointCacheService {
    @Autowired
    private GeoPointCacheRepository geoPointCacheRepository;
    public GeoPoint findGeoPoint(String address) {
        GeoPointCache geoPointCache = geoPointCacheRepository.findById(address).orElse(null);
        if(geoPointCache != null) {
            return new GeoPoint(geoPointCache.getLongitude(), geoPointCache.getLatitude());
        } else {
            return null;
        }
    }

    public void saveGeoPoint(String address, GeoPoint geoPoint) {
        GeoPointCache geoPointCache = new GeoPointCache()
                .setAddress(address)
                .setLongitude(geoPoint.lon)
                .setLatitude(geoPoint.lat);
        geoPointCacheRepository.save(geoPointCache);
    }
}
