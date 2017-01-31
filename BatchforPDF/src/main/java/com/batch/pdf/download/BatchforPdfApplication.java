package com.batch.pdf.download;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

@SpringBootApplication
public class BatchforPdfApplication {
	
	public static void main(String[] args) throws IOException, InterruptedException {
		// SpringApplication.run(BatchforPdfApplication.class, args);
		MongoClient mongoClient = new MongoClient("54.91.89.255", 27017);
		String dbName = "IBMDATA";
		String tableName = "original";
		while (true) {
			for (int i = 0; i <= 3; i++) {
				Thread.sleep(6000);
			}
			DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
			Calendar calobj = Calendar.getInstance();
			System.out.println("--1 > " + df.format(calobj.getTime()));

			@SuppressWarnings("deprecation")
			DB db = mongoClient.getDB(dbName);
			DBCollection coll = db.getCollection(tableName);

			DBCursor cursor = coll.find();
			System.out.println(cursor.getCollection());
			while (cursor.hasNext()) {
				DBObject obj = cursor.next();
				String l = cursor.curr().get("PriceCaselink").toString();
				System.out.println("cursor  - > ");
				JSONObject jsonObj = new JSONObject(l);
				System.out.println("PriceCaselink found -  successfully");
				JSONArray m = jsonObj.getJSONArray("content");
				System.out.println("content found successfully");
				JSONObject finaldata = (JSONObject) m.get(4);
				String menu78 = (String) finaldata.get("data");
				System.out.println("Base64 data found - - !");
				int filerandomNumber = (int) (Math.random() * ((20000 - 20) + 1) + 20);
				File file = new File("d:/" + filerandomNumber + menu78.substring(0, 5) + "file.pdf");
				BufferedOutputStream writer = new BufferedOutputStream(new FileOutputStream(file));
				writer.write(Base64.decodeBase64(menu78));
				writer.flush();
				writer.close();
				System.err.println("File converted and saved...!");
			}

			Thread.sleep(6000);
			System.out.println("--2 > " + df.format(calobj.getTime()));
		}
	}
}
