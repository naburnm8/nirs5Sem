package ru.naburnm8.bmstu.android.datamanagementnirapp.misc;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import com.aspose.words.Document;
import com.aspose.words.DocumentBuilder;
import com.aspose.words.FindReplaceOptions;
import com.aspose.words.Table;
import ru.naburnm8.bmstu.android.datamanagementnirapp.recyclerViewStuff.ordersList.OrderUnited;

import java.io.File;
import java.io.InputStream;

public class ReportGenerator extends AsyncTask<ReportData, Void, String> {
    private final InputStream inputStream;
    private final OnGenerationListener context;
    private final File FILES_DIR;
    public ReportGenerator(InputStream inputStream, OnGenerationListener context, File filesDir){
        this.inputStream = inputStream;
        this.context = context;
        FILES_DIR = filesDir;
    }
    @Override
    protected String doInBackground(ReportData... reportData1) {
        ReportData reportData = reportData1[0];
        try{
            Document doc = new Document(inputStream);
            doc.getRange().replace("username", reportData.getUsername(), new FindReplaceOptions());
            doc.getRange().replace("start_date", reportData.getStartDate(), new FindReplaceOptions());
            doc.getRange().replace("end_date", reportData.getEndDate(), new FindReplaceOptions());
            doc.getRange().replace("date_generated", reportData.getDateGenerated(), new FindReplaceOptions());
            doc.getRange().replace("order_price_total", reportData.getOrderPriceTotal() + "₽", new FindReplaceOptions());

            DocumentBuilder builder = new DocumentBuilder(doc);
            builder.writeln();

            Table table = builder.startTable();
            builder.insertCell();
            builder.write("Name");
            builder.insertCell();
            builder.write("Date of Transaction");
            builder.insertCell();
            builder.write("Total");
            builder.endRow();

            for(OrderUnited order: reportData.getOrders()){
                builder.insertCell();
                builder.write(order.getClient().getFirstName() + " " + order.getClient().getLastName());
                builder.insertCell();
                builder.write(order.getDateOfTransaction());
                builder.insertCell();
                builder.write(order.getTotalCostFormatted());
                builder.endRow();
            }
            String outputPath = FILES_DIR + "/sales_report_" + reportData.getDateGenerated() + ".docx";
            doc.save(outputPath);

        } catch (Exception e){
            Log.println(Log.ERROR, "Report Generator", e.toString());
            return "Fail";
        }
        return "Success";
    }

    @Override
    protected void onPostExecute(String data){
        context.onGenerationResult(data);
    }


}
