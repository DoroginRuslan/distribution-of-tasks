package ru.era.distributionoftasks.yandexgeocoder.caching.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.era.distributionoftasks.yandexgeocoder.caching.entities.GeoPointCache;

import java.util.Optional;

@Repository
public interface GeoPointCacheRepository extends CrudRepository<GeoPointCache, Long> {
    Optional<GeoPointCache> findByAddress(String address);
}
