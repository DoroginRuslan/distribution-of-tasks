package ru.era.distributionoftasks.services.distributor.entity;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Route {
    private final Integer startPoint;
    private final AddressTimesMatrix addressTimesMatrix;
    private final List<AgencyPoint> agencyPointList;
    private int time;
    private int profit;

    public Route(Integer startPoint, AddressTimesMatrix addressTimesMatrix, List<AgencyPoint> agencyPointList) {
        this.startPoint = startPoint;
        this.addressTimesMatrix = addressTimesMatrix;
        this.agencyPointList = agencyPointList;
        time = calcRouteTime();
    }

    public Route(Integer startPoint, AddressTimesMatrix addressTimesMatrix) {
        this.startPoint = startPoint;
        this.addressTimesMatrix = addressTimesMatrix;
        agencyPointList = new ArrayList<>();
        time = calcRouteTime();
    }

    public Route(Route route) {
        this.startPoint = route.startPoint;
        this.addressTimesMatrix = route.addressTimesMatrix;
        this.agencyPointList = new ArrayList<>(route.agencyPointList);
        time = route.time;
    }

    public Route(Route route, AgencyPoint agencyPoint) {
        this.startPoint = route.startPoint;
        this.addressTimesMatrix = route.addressTimesMatrix;
        this.agencyPointList = new ArrayList<>(route.agencyPointList);
        time = route.time;
        route.addAgencyPointToEnd(agencyPoint);
    }

    public int calcTimeRoadFromLastPoint(AgencyPoint agencyPoint) {
        if(agencyPointList.isEmpty()) {
            return addressTimesMatrix.getTimeBetweenAddresses(
                        startPoint,
                        agencyPoint.getAddressId()) +
                    agencyPoint.getTask().getTimeInMinutes();
        } else {
            return addressTimesMatrix.getTimeBetweenAddresses(
                        agencyPointList.get(agencyPointList.size() - 1).getAddressId(),
                        agencyPoint.getAddressId()) +
                    agencyPoint.getTask().getTimeInMinutes();
        }
    }

    public int getTimeWithNewPoint(AgencyPoint agencyPoint) {
        return time + calcTimeRoadFromLastPoint(agencyPoint);
    }

    public void addAgencyPointToEnd(AgencyPoint agencyPoint) {
        time = getTimeWithNewPoint(agencyPoint);
        agencyPointList.add(agencyPoint);
    }

    public boolean isEmpty() {
        return agencyPointList.isEmpty();
    }

    public boolean contains(AgencyPoint agencyPoint) {
        return agencyPointList.contains(agencyPoint);
    }

    private int calcRouteTime() {
        if(agencyPointList.isEmpty()) {
            return 0;
        } else {
            int time = addressTimesMatrix.getTimeBetweenAddresses(
                        startPoint,
                        agencyPointList.get(0).getAddressId()) +
                    agencyPointList.get(0).getTask().getTimeInMinutes();
            for(int i = 0; i < agencyPointList.size()-1; i++) {
                time += addressTimesMatrix.getTimeBetweenAddresses(
                            agencyPointList.get(i).getAddressId(),
                            agencyPointList.get(i+1).getAddressId()) +
                        agencyPointList.get(i+1).getTask().getTimeInMinutes();
            }
            return time;
        }
    }

    public void updateProfit() {
        if(agencyPointList.isEmpty()) {
            profit = 0;
        } else {
            profit = 0;
            for(AgencyPoint agencyPoint : agencyPointList) {
                profit += agencyPoint.getTask().getProfit();
            }
        }
    }

    @Override
    public String toString() {
        return "Route{" +
                "startPoint=" + startPoint +
                ", agencyPointList=" + agencyPointList +
                ", time=" + time +
                '}';
    }
}
