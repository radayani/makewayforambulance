package com.example.radayani.emergencymakewaymap;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.table.CloudTable;
import com.microsoft.azure.storage.table.CloudTableClient;
import com.microsoft.azure.storage.table.TableOperation;
import com.microsoft.azure.storage.table.TableQuery;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

/**
 * Created by radayani on 03-04-2017.
 */

public class RetrieveSignalsInfo extends AsyncTask<SignalEntity, String, List<SignalEntity>> {

    Activity activity;

    public RetrieveSignalsInfo (Activity activity)
    {
        this.activity = activity;
    }

    //Define the connection-string with your values.
    public static final String storageConnectionString=
            "DefaultEndpointsProtocol=https;AccountName=emergencydatastorage;AccountKey=xukjboxSnTWnaGa3+QeZP6t7812uRQ/px8tumYxu3wXlPeSYbo9ZdoL82NbLpr+M7n6oo/Q0BO29X9/VoNpRyQ==;EndpointSuffix=core.windows.net";

    @Override
    protected List<SignalEntity> doInBackground(SignalEntity... signalEntity) {
        try {
            //Retrieve storage account from connection-string.
            CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageConnectionString);

            //Create the table client.
            CloudTableClient tableClient = storageAccount.createCloudTableClient();

            //Create a cloud table object for the table.
            CloudTable cloudTable = tableClient.getTableReference("signalTable");

            String partitionFilter = TableQuery.generateFilterCondition(
                    "PartitionKey",
                    TableQuery.QueryComparisons.EQUAL,
                    "500032");

            // Specify a partition query, using "Smith" as the partition key filter.

            TableQuery<SignalEntity> partitionQuery = TableQuery.from(SignalEntity.class).where(partitionFilter);
            List<SignalEntity> s = null;

            // Loop through the results, displaying information about the entity.
            /*for (SignalEntity entity : cloudTable.execute(partitionQuery)) {
                signalEntity.add(entity);
            }*/
            for(SignalEntity s1 : s)
            {
                s.add(s1);
            }
            return s;
        }
        catch  (Exception e)
        {
            return null;
        }
    }

}
