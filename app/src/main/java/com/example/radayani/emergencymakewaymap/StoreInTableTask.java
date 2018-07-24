package com.example.radayani.emergencymakewaymap;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.table.CloudTable;
import com.microsoft.azure.storage.table.CloudTableClient;
import com.microsoft.azure.storage.table.TableOperation;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

/**
 * Created by radayani on 03-04-2017.
 */

public class StoreInTableTask extends AsyncTask<String, String, String> {

    Activity activity;
    float distance = 0,  signalBearing = 0, vehicleBearing = 0;
    String EVcrossedTS = "False";
    String latOfTS = "0";
    String longOfTS ="0";
    String signalRoadName = "null";
    String TSreceivedESsignal = "FALSE";
    String trafficSignalID = "null";

    public StoreInTableTask(Activity activity, float distance, float signalBearing, float vehicleBearing, String EVcrossedTS,
                            String latOfTS, String longOfTS, String signalRoadName, String trafficSignalID,
                            String TSreceivedESsignal)
    {
        this.activity = activity;
        this.distance = distance;
        this.signalBearing = signalBearing;
        this.vehicleBearing = vehicleBearing;
        this.EVcrossedTS = EVcrossedTS;
        this.latOfTS = latOfTS;
        this.longOfTS = longOfTS;
        this.signalRoadName =signalRoadName;
        this.trafficSignalID = trafficSignalID;
        this.TSreceivedESsignal = TSreceivedESsignal;
    }

    //Define the connection-string with your values.
    public static final String storageConnectionString=
            "DefaultEndpointsProtocol=https;AccountName=emergencydatastorage;AccountKey=xukjboxSnTWnaGa3+QeZP6t7812uRQ/px8tumYxu3wXlPeSYbo9ZdoL82NbLpr+M7n6oo/Q0BO29X9/VoNpRyQ==;EndpointSuffix=core.windows.net";

    @Override
    protected String doInBackground(String... params) {
        try
        {
            //Retrieve storage account from connection-string.
            CloudStorageAccount storageAccount= CloudStorageAccount.parse(storageConnectionString);

            //Create the table client.
            CloudTableClient tableClient=storageAccount.createCloudTableClient();

            //Create a cloud table object for the table.
            CloudTable cloudTable = tableClient.getTableReference("emergencyMakeWayTable");

            // RowKey = VehicleID_SignalID_DirectionOfVehicle
            String vehicleID = "A2";
            String signalID = "S1";
            String directionOfVehicle = "N";
            String rowKey =vehicleID + "_" + trafficSignalID + "_" + directionOfVehicle;

            // what type of emergency vehicle? =  Partition Key
            String vehicleType = "NA";
            if(vehicleID.contains("A"))
                vehicleType = "Ambulance";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Calendar c = Calendar.getInstance();


            //Create a new emergency entity.
            EmergencyEntity emergencyEntity=new EmergencyEntity(vehicleType + "_" + sdf.format(c.getTime()), rowKey);
            emergencyEntity.setEmergencyVehicleID(vehicleID);
            emergencyEntity.setWaitTimeBeforeSignalChangeTrigger("5");
            emergencyEntity.setDistanceOfEVandTS(String.valueOf(distance));
            emergencyEntity.setAreaDomain("500032");
            emergencyEntity.setTSreceivedEVsignal(TSreceivedESsignal);
            emergencyEntity.setTrafficSignalID(trafficSignalID);
            sdf = new SimpleDateFormat("yyyyMMddHHmmssSS");
            emergencyEntity.setTime(Long.parseLong(sdf.format(c.getTime())));
            emergencyEntity.setDirectionOfEV(String.valueOf(vehicleBearing));
            emergencyEntity.setDirectionOfTS(String.valueOf(signalBearing));
            emergencyEntity.setLatitudeOfEV("");
            emergencyEntity.setLatitudeOfTS(latOfTS);
            emergencyEntity.setLongitudeOfEV("");
            emergencyEntity.setLongitudeOfTS(longOfTS);
            emergencyEntity.setSignalRoadName(signalRoadName);
            if(EVcrossedTS.equalsIgnoreCase("True") ) {

                emergencyEntity.setEVcrossedTS("True");
                TableOperation update=TableOperation.insertOrReplace(emergencyEntity);

                //Submit the operation to the table service.
                cloudTable.execute(update);

            }
            else {
                emergencyEntity.setEVcrossedTS("False");
                TableOperation insert=TableOperation.insert(emergencyEntity);

                //Submit the operation to the table service.
                cloudTable.execute(insert);

            }
            //Create an operation to add the new customer to the people table.
            TableOperation insert=TableOperation.insert(emergencyEntity);

            //Submit the operation to the table service.
            cloudTable.execute(insert);
        }
        catch(Exception e)
        {
            //Output the stack trace.
            return null  ;
        }

        return "Recorded Successfully. signal:  " + signalBearing + " vehicle: " + vehicleBearing;

    }

    @Override
    protected void onPostExecute(String message)
    {
        if(message== null)
            //do nothing
        ;
        else
            Toast.makeText(this.activity, message,Toast.LENGTH_SHORT).show();
    }
}