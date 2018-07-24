package com.example.radayani.emergencymakewaymap;

import android.hardware.Camera;

import com.microsoft.azure.storage.table.TableServiceEntity;

/**
 * Created by radayani on 02-04-2017.
 */

public class EmergencyEntity extends TableServiceEntity {
    public EmergencyEntity(String emergencyVehicleType, String uniqueValue) {
        this.partitionKey = emergencyVehicleType;
        this.rowKey = uniqueValue;
    }

    public EmergencyEntity() { }

    public String getAreaDomain() {
        return AreaDomain;
    }

    public void setAreaDomain(String areaDomain) {
        AreaDomain = areaDomain;
    }

    public String getDirectionOfEV() {
        return DirectionOfEV;
    }

    public void setDirectionOfEV(String directionOfEV) {
        DirectionOfEV = directionOfEV;
    }

    public String getDirectionOfTS() {
        return DirectionOfTS;
    }

    public void setDirectionOfTS(String directionOfTS) {
        DirectionOfTS = directionOfTS;
    }

    public String getDistanceOfEVandTS() {
        return DistanceOfEVandTS;
    }


    public void setDistanceOfEVandTS(String distanceOfEVandTS) {
        DistanceOfEVandTS = distanceOfEVandTS;
    }

    public String getEmergencyVehicleID() {
        return EmergencyVehicleID;
    }

    public void setEmergencyVehicleID(String emergencyVehicleID) {
        EmergencyVehicleID = emergencyVehicleID;
    }

    public String getLatitudeOfEV() {
        return LatitudeOfEV;
    }

    public void setLatitudeOfEV(String latitudeOfEV) {
        LatitudeOfEV = latitudeOfEV;
    }

    public String getLongitudeOfEV() {
        return LongitudeOfEV;
    }

    public void setLongitudeOfEV(String longitudeOfEV) {
        LongitudeOfEV = longitudeOfEV;
    }

    public String getLatitudeOfTS() {
        return LatitudeOfTS;
    }

    public void setLatitudeOfTS(String latitudeOfTS) {
        LatitudeOfTS = latitudeOfTS;
    }

    public String getLongitudeOfTS() {
        return LongitudeOfTS;
    }

    public void setLongitudeOfTS(String longitudeOfTS) {
        LongitudeOfTS = longitudeOfTS;
    }

    public String getTSreceivedEVsignal() {
        return TSreceivedEVsignal;
    }

    public void setTSreceivedEVsignal(String TSreceivedEVsignal) {
        this.TSreceivedEVsignal = TSreceivedEVsignal;
    }

    public Long getTime() {
        return Time;
    }

    public void setTime(Long time) {
        Time = time;
    }

    public String getTrafficSignalID() {
        return TrafficSignalID;
    }

    public void setTrafficSignalID(String trafficSignalID) {
        TrafficSignalID = trafficSignalID;
    }

    public String getWaitTimeBeforeSignalChangeTrigger() {
        return WaitTimeBeforeSignalChangeTrigger;
    }

    public void setWaitTimeBeforeSignalChangeTrigger(String waitTimeBeforeSignalChangeTrigger) {
        WaitTimeBeforeSignalChangeTrigger = waitTimeBeforeSignalChangeTrigger;
    }

    String AreaDomain;
    String DirectionOfEV;
    String DirectionOfTS;
    String DistanceOfEVandTS;
    String EmergencyVehicleID;
    String LatitudeOfEV;
    String LongitudeOfEV;
    String LatitudeOfTS;
    String LongitudeOfTS;
    String TSreceivedEVsignal;
    Long Time;
    String TrafficSignalID;
    String WaitTimeBeforeSignalChangeTrigger;

    public String getSignalRoadName() {
        return SignalRoadName;
    }

    public void setSignalRoadName(String signalRoadName) {
        this.SignalRoadName = signalRoadName;
    }

    String SignalRoadName;

    public String getEVcrossedTS() {
        return EVcrossedTS;
    }

    public void setEVcrossedTS(String EVcrossedTS) {
        this.EVcrossedTS = EVcrossedTS;
    }

    String EVcrossedTS;


}