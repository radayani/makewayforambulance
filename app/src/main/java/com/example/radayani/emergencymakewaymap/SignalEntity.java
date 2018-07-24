package com.example.radayani.emergencymakewaymap;

import com.microsoft.azure.storage.table.TableServiceEntity;

/**
 * Created by radayani on 02-04-2017.
 */

public class SignalEntity extends TableServiceEntity {
    public SignalEntity(String zipCode, String signalID) {
        this.partitionKey = zipCode;
        this.rowKey = signalID;
    }

    public SignalEntity() { }

    public String getBearing() {
        return Bearing;
    }

    public void setBearing(String bearing) {
        Bearing = bearing;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getRoute() {
        return Route;
    }

    public void setRoute(String route) {
        Route = route;
    }

    public String getSignalRoad() {
        return SignalRoad;
    }

    public void setSignalRoad(String signalRoad) {
        SignalRoad = signalRoad;
    }

    String Bearing;
    String Latitude;
    String Longitude;
    String Route;
    String SignalRoad;

}