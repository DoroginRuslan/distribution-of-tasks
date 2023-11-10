package ru.era.distributionoftasks.services.distributor.entity;

public class AddressTimesMatrix {
    private final int[][] addressTimeMatrixArr;

    public AddressTimesMatrix(int[][] addressTimeMatrixArr) {
        this.addressTimeMatrixArr = addressTimeMatrixArr;
    }

    public int getTimeBetweenAddresses(int addressFrom, int addressTo) {
        return addressTimeMatrixArr[addressFrom][addressTo];
    }
}
