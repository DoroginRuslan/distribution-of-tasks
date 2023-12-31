package ru.era.distributionoftasks.services.distributor.entity;

// Хранит время между точками в минутах
public class AddressTimesMatrix {
    private final int[][] addressTimeMatrixArr;

    public AddressTimesMatrix(int[][] addressTimeMatrixArr) {
        this.addressTimeMatrixArr = addressTimeMatrixArr;
    }

    public int getTimeBetweenAddresses(int addressFrom, int addressTo) {
        if(addressFrom >= addressTimeMatrixArr.length || addressTo >= addressTimeMatrixArr[0].length) {
            System.out.println("Error");
        }
        return addressTimeMatrixArr[addressFrom][addressTo];
    }
}
