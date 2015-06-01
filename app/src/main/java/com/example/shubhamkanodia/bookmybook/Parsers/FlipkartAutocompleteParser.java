package com.example.shubhamkanodia.bookmybook.Parsers;

import android.util.Log;

import com.example.shubhamkanodia.bookmybook.Adapters.BookItem;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by shubhamkanodia on 28/05/15.
 */
public class FlipkartAutocompleteParser {

    final static String apiURL = "http://www.flipkart.com/s?query=";
    static String receivedJSON;

    public static ArrayList<BookItem> getBookAutocompleteJSON(String query){

        DefaultHttpClient defaultClient = new DefaultHttpClient();
        HttpGet httpGetRequest = new HttpGet(apiURL + query.replaceAll("[ \n]+", "+"));
        Log.e("Searching...", apiURL + query.replaceAll("[ \n]+", "+"));

        ArrayList<BookItem> bookList = new ArrayList<>();

        HttpResponse httpResponse = null;
        try {
            httpResponse = defaultClient.execute(httpGetRequest);
        } catch (IOException e) {
            Log.e("JSONPrint", "IOerror...");
            e.printStackTrace();
        }

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent(), "UTF-8"));
            receivedJSON = reader.readLine();

            Pattern pattern = Pattern.compile("\"(978[\\d]{10})\",\"([^\"]+)\",\"([^\"]+)\"", Pattern.MULTILINE);
            Matcher matcher = pattern.matcher(receivedJSON);
            while (matcher.find()) {
                System.out.println("group 1: " + matcher.group(1));
                System.out.println("group 2: " + matcher.group(2));
                System.out.println("group 3: " + matcher.group(3));
            }
            BookItem toInsert = new BookItem();
            toInsert.book_name = "ok";
            toInsert.book_author  = "ok";
            toInsert.book_cover_URL = "ok";
            toInsert.book_ISBN_13 = "12";


        } catch (IOException e) {
            e.printStackTrace();
            Log.e("JSONPrint", "Error1...");

        }
        return bookList;
    }

    private static String normalizeAuthorName(String authorNames){

        //Solves |author appended at the end
        return authorNames.replaceAll("[|]?[aA]uthor[;]", "");

    }
}