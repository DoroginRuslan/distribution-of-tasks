package ru.era.distributionoftasks.graphhopper;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.era.distributionoftasks.graphhopper.jsonobjects.GeocodeAnswer;
import ru.era.distributionoftasks.graphhopper.jsonobjects.MatrixWeightsAnswer;
import ru.era.distributionoftasks.graphhopper.jsonobjects.Point;

import java.io.IOException;
import java.util.List;

@Service
public class RoutesService {

    @Value("graphhopper.key")
    private String graphhopperApiKey;
    @Value("graphhopper.geocode")
    private String geocodeUrl;
    @Value("graphhopper.matrix")
    private String matrixRul;
    private final ObjectMapper objectMapper;
    private final OkHttpClient client;

    public RoutesService() {
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        client = new OkHttpClient();
    }

    public Point getGeocodeFromAddress(String address) {
        GeocodeAnswer geocodeAnswer = null;
        try {
            geocodeAnswer = getGeocodeAnswer(address);
        } catch (IOException e) {
            throw new GraphhopperErrorException("Ошибка при подключении к серверу graphhopper", e);
        }
//        System.out.println(geocodeAnswer);
        if(geocodeAnswer.getHits() == null || geocodeAnswer.getHits().isEmpty()) {
            throw new GraphhopperErrorException("Не удалось найти соответствие адресу: " + address);
        }
        return geocodeAnswer.getHits().get(0).getPoint();
    }

    private GeocodeAnswer getGeocodeAnswer(String address) throws IOException {
        Request request = new Request.Builder()
                .url(geocodeUrl + "?" +
                        "q=" + address +
                        "&locale=" + "ru" +
                        "&key=" + graphhopperApiKey)
                .get()
                .build();
        Response response = client.newCall(request).execute();
        String answerBody = response.body().string();
        System.out.println(response);
//        String answerBody = "{\"hits\":[{\"point\":{\"lat\":52.5170365,\"lng\":13.3888599},\"extent\":[13.088345,52.3382448,13.7611609,52.6755087],\"name\":\"Berlin\",\"country\":\"Deutschland\",\"countrycode\":\"DE\",\"osm_id\":62422,\"osm_type\":\"R\",\"osm_key\":\"place\",\"osm_value\":\"city\"},{\"point\":{\"lat\":52.5127537,\"lng\":13.3814231},\"name\":\"Schlacht um Berlin\",\"country\":\"Deutschland\",\"countrycode\":\"DE\",\"city\":\"Berlin\",\"street\":\"Gertrud-Kolmar-Straße\",\"postcode\":\"10117\",\"osm_id\":1078631331,\"osm_type\":\"N\",\"osm_key\":\"historic\",\"osm_value\":\"battlefield\"},{\"point\":{\"lat\":48.8009528,\"lng\":7.2418378},\"extent\":[7.2242061,48.7891741,7.2609089,48.8104634],\"name\":\"Berling\",\"country\":\"France\",\"countrycode\":\"FR\",\"state\":\"Grand Est\",\"postcode\":\"57370\",\"osm_id\":1152723,\"osm_type\":\"R\",\"osm_key\":\"place\",\"osm_value\":\"village\"},{\"point\":{\"lat\":52.5190822,\"lng\":13.40109417160804},\"extent\":[13.4003048,52.5186346,13.4017508,52.5195378],\"name\":\"Berliner Dom\",\"country\":\"Deutschland\",\"countrycode\":\"DE\",\"city\":\"Berlin\",\"street\":\"Am Lustgarten\",\"postcode\":\"10178\",\"osm_id\":313670734,\"osm_type\":\"W\",\"osm_key\":\"amenity\",\"osm_value\":\"place_of_worship\"},{\"point\":{\"lat\":52.5190822,\"lng\":13.40109417160804},\"extent\":[13.4003048,52.5186346,13.4017508,52.5195378],\"name\":\"Berliner Dom\",\"country\":\"Deutschland\",\"countrycode\":\"DE\",\"city\":\"Berlin\",\"street\":\"Am Lustgarten\",\"postcode\":\"10178\",\"osm_id\":313670734,\"osm_type\":\"W\",\"osm_key\":\"tourism\",\"osm_value\":\"attraction\"}],\"locale\":\"default\"}";
        return objectMapper.readValue(answerBody, GeocodeAnswer.class);
    }

    // TODO: 09.11.2023 Переделать в POST реализацию
    public MatrixWeightsAnswer getMatrixWeightsAnswers(List<Point> points) {
        Request request = new Request.Builder()
                .url(matrixRul + "?" +
                        transformPointsToParamString(points) +
                        "&type=" + "json" +
                        "&profile=" + "car" +
                        // Эти параметры можно отключать
                        "&out_array=" + "weights" +     // Сложность пути
                        "&out_array=" + "times" +       // Время пути (в секундах)
                        "&out_array=" + "distances" +   // Расстояние (в метрах)
                        // --
                        "&key=" + graphhopperApiKey)
                .get()
                .build();
        try {
            Response response = client.newCall(request).execute();
            String answerBody = response.body().string();
            System.out.println(response);
            MatrixWeightsAnswer matrixWeightsAnswer = objectMapper.readValue(answerBody, MatrixWeightsAnswer.class);
            System.out.println(matrixWeightsAnswer);
            return matrixWeightsAnswer;
        } catch (IOException e) {
            throw new GraphhopperErrorException("Ошибка при работе с сервисом graphhopper", e);
        }
    }

    private String transformPointsToParamString(List<Point> points) {
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < points.size(); i++) {
            if(i!=0) {
                stringBuilder.append("&");
            }
            stringBuilder.append("point=").append(points.get(i).getByWebParam());
        }
        return stringBuilder.toString();
    }

}