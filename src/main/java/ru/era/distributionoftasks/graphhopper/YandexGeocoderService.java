package ru.era.distributionoftasks.graphhopper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;

@Service
public class YandexGeocoderService {

    public List<List<Double>> convertToCoordinates(List<String> adressArray)
    {
        List<List<Double>> allConvertedAdress = new ArrayList<List<Double>>();
        for (String curAdress : adressArray)
        {
            allConvertedAdress.add(convertToCoordinate(curAdress));
        }
        return allConvertedAdress;
    }

    public List<Double> convertToCoordinate(String adress)
    {
        return sendRequestForConverting(adress);
    }

    public List<Double> sendRequestForConverting(String adress)
    {
        YaGeocoder geocoder = new YaGeocoder(new DefaultHttpClient());
        GeocoderResponse response = null;
        try {
            response = geocoder.directGeocode(adress);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        GeoObject geoObject = response.getGeoObjects().get(0);
        List<Double> convertedResult = new ArrayList<Double>();
        convertedResult.add(geoObject.getPoint().lat);
        convertedResult.add(geoObject.getPoint().lon);
        return convertedResult;
    }
}
