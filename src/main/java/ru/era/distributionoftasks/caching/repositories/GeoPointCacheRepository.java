package ru.era.distributionoftasks.caching.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.era.distributionoftasks.caching.entities.GeoPointCache;

@Repository
public interface GeoPointCacheRepository extends CrudRepository<GeoPointCache, String> {
}
