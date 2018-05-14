package com.example.ensai.switchbetweenactivities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CSVFile {
    InputStream inputStream;

    public CSVFile(InputStream inputStream){
        this.inputStream = inputStream;
    }

    public String read(int colonne){
        String resultat="";
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            String csvLine;

            csvLine = reader.readLine();
            while ((csvLine = reader.readLine()) != null) {
                String[] row = csvLine.split(";");
                resultat=resultat+";"+row[colonne];
            }
        }
        catch (IOException ex) {
            throw new RuntimeException("Error in reading CSV file: "+ex);
        }
        finally {
            try {
                inputStream.close();
            }
            catch (IOException e) {
                throw new RuntimeException("Error while closing input stream: "+e);
            }
        }
        return resultat;
    }

    public static String match(InputStream inputStream,int colonne1,int colonne2, String match1, String match2, int colonne3){
        String resultat="";
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            String csvLine;
            Boolean found=false;

            while ((csvLine = reader.readLine()) != null&&!found) {
                String[] row = csvLine.split(";");
                if (row[colonne1].equals(match1)) {
                    String destination=row[colonne2].split(" ")[0];
                    if(destination.equals(match2)){
                        resultat = row[colonne3];
                        found=true;
                    }

                }
            }
        }
        catch (IOException ex) {
            throw new RuntimeException("Error in reading CSV file: "+ex);
        }
        finally {
            try {
                inputStream.close();
            }
            catch (IOException e) {
                throw new RuntimeException("Error while closing input stream: "+e);
            }
        }
        return resultat;
    }
}