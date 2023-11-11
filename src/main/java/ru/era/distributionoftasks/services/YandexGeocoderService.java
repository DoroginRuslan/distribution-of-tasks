package ru.era.distributionoftasks.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.message.BasicHeader;

import java.net.URLEncoder;






class GeocoderResponse {
    private List<GeoObject> geoObjects = new ArrayList<GeoObject>();
    private String request;
    private int found;
    private int results;

    public GeocoderResponse() {
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public void setFound(int found) {
        this.found = found;
    }

    public void addGeoObject(GeoObject geoObject){
        geoObjects.add(geoObject);
    }

    public List<GeoObject> getGeoObjects() {
        return geoObjects;
    }

    public String getRequest() {
        return request;
    }

    public int getFound() {
        return found;
    }

    public int getResults() {
        return results;
    }

    public void setResults(int results) {
        this.results = results;
    }
}




class GeoObject {
    private String name;
    private String kind;
    private String text;
    private String precision;
    private String address;
    private String country;
    private String countryCode;
    private String administrativeArea;
    private String subAdministrativeArea;
    private String locality;
    private String thoroughfare;
    private String premise;

    private GeoPoint point;
    private GeoPoint lowerCorner;
    private GeoPoint upperCorner;

    public GeoObject() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPrecision() {
        return precision;
    }

    public void setPrecision(String precision) {
        this.precision = precision;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getAdministrativeArea() {
        return administrativeArea;
    }

    public void setAdministrativeArea(String administrativeArea) {
        this.administrativeArea = administrativeArea;
    }

    public String getSubAdministrativeArea() {
        return subAdministrativeArea;
    }

    public void setSubAdministrativeArea(String subAdministrativeArea) {
        this.subAdministrativeArea = subAdministrativeArea;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getThoroughfare() {
        return thoroughfare;
    }

    public void setThoroughfare(String thoroughfare) {
        this.thoroughfare = thoroughfare;
    }

    public String getPremise() {
        return premise;
    }

    public void setPremise(String premise) {
        this.premise = premise;
    }

    public GeoPoint getPoint() {
        return point;
    }

    public void setPoint(GeoPoint point) {
        this.point = point;
    }

    public GeoPoint getLowerCorner() {
        return lowerCorner;
    }

    public void setLowerCorner(GeoPoint lowerCorner) {
        this.lowerCorner = lowerCorner;
    }

    public GeoPoint getUpperCorner() {
        return upperCorner;
    }

    public void setUpperCorner(GeoPoint upperCorner) {
        this.upperCorner = upperCorner;
    }

    @Override
    public String toString() {
        return getAddress();
    }
}




class GeoPoint {

    public double lon;
    public double lat;

    public GeoPoint(double lon, double lat) {
        this.lon = lon;
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public double getLat() {
        return lat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GeoPoint geoPoint = (GeoPoint) o;

        if (Double.compare(geoPoint.lat, lat) != 0) return false;
        if (Double.compare(geoPoint.lon, lon) != 0) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(lon);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(lat);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "GeoPoint{" +
                "lon=" + lon +
                ", lat=" + lat +
                '}';
    }

    public static GeoPoint parse(String string) {
        String[] splits = string.split(" ");
        return new GeoPoint(Double.parseDouble(splits[0]), Double.parseDouble(splits[1]));
    }
}




class XmlResponseParser {
    private SAXParser parser;

    public XmlResponseParser() {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            parser = factory.newSAXParser();
        } catch (ParserConfigurationException | SAXException e) {
            throw new RuntimeException(e);
        }
    }

    public GeocoderResponse parse(InputStream inputStream) throws IOException {
        YaGeocoderHandler handler = new YaGeocoderHandler();
        try {
            parser.parse(inputStream, handler);
        } catch (Exception e) {
            throw new IOException("Failed to parse geocoder result: " + e.toString(), e);
        }
        return handler.getResult();
    }

    private static class YaGeocoderHandler extends DefaultHandler {

        private StringBuilder stringBuilder = new StringBuilder();

        private GeocoderResponse response = new GeocoderResponse();
        private GeoObject geoObject = null;

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            switch (qName) {
                case "GeoObject":
                    geoObject = new GeoObject();
                    break;
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            switch (qName) {
                case "request":
                    response.setRequest(getString());
                    break;
                case "found":
                    response.setFound(getInt());
                    break;
                case "results":
                    response.setResults(getInt());
                    break;
                case "GeoObject":
                    response.addGeoObject(geoObject);
                    geoObject = null;
                    break;
                case "kind":
                    geoObject.setKind(getString());
                    break;
                case "text":
                    geoObject.setText(getString());
                    break;
                case "precision":
                    geoObject.setPrecision(getString());
                    break;
                case "AddressLine":
                    geoObject.setAddress(getString());
                    break;
                case "CountryNameCode":
                    geoObject.setCountryCode(getString());
                    break;
                case "CountryName":
                    geoObject.setCountry(getString());
                    break;
                case "AdministrativeAreaName":
                    geoObject.setAdministrativeArea(getString());
                    break;
                case "SubAdministrativeAreaName":
                    geoObject.setSubAdministrativeArea(getString());
                    break;
                case "LocalityName":
                case "locality": //people
                    geoObject.setLocality(getString());
                    break;
                case "ThoroughfareName":
                case "thoroughfare": //people
                    geoObject.setThoroughfare(getString());
                    break;
                case "PremiseNumber":
                case "premiseNumber": //people
                    geoObject.setPremise(getString());
                    break;
                case "name":
                    geoObject.setName(getString());
                    break;
                case "lowerCorner":
                    if (geoObject != null){
                        geoObject.setLowerCorner(getGeoPoint());
                    }
                    break;
                case "upperCorner":
                    if (geoObject != null){
                        geoObject.setUpperCorner(getGeoPoint());
                    }
                    break;
                case "pos":
                    geoObject.setPoint(getGeoPoint());
                    break;
            }
            stringBuilder.setLength(0);
        }

        private String getString() {
            return stringBuilder.toString().trim();
        }

        private int getInt() {
            return Integer.parseInt(getString());
        }

        private GeoPoint getGeoPoint() {
            return  GeoPoint.parse(getString());
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            stringBuilder.append(ch, start, length);
        }

        public GeocoderResponse getResult() {
            return response;
        }

    }
}




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
        String url = GEOCODER_HOST + "?geocode=" + URLEncoder.encode(geocode, "UTF-8");
        HttpUriRequest request = new HttpGet(url);
        request.addHeader(refererHeader);
        HttpResponse response = httpClient.execute(request);
        if (response.getStatusLine().getStatusCode() != HTTP_OK) {
            throw new IOException(response.getStatusLine().toString());
        }
        return responseParser.parse(response.getEntity().getContent());
    }
}




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
        GeocoderResponse response = geocoder.directGeocode(request);
        GeoObject geoObject = response.getGeoObjects().get(0);
        List<Double> convertedResult = new ArrayList<Double>();
        convertedResult.add(geoObject.getPoint().lon);
        convertedResult.add(geoObject.getPoint().lat);
        return convertedResult;
    }
}
