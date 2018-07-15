package Model;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Verwaltet die Polynome.json <br>
 * Stellt Funktionen bereit, um die JSON auszulesen, zu leeren und um Elemente einzuspeichern.
 */
public class JSONManager {

    /**
     * Liest die JSON aus und gibt alle enthaltenen Polynome zurück
     * @return Die gespeicherten Polynome in der JSON
     */
    public static BinaryStringTree getTreeFromFile(String filePath) throws IOException
    {
        File jsonFile = new File(filePath);

        if(!checkAndCreateFile(jsonFile))
        {
            return null;
        }

        Gson gson = new Gson();
        try (JsonReader jsonReader = new JsonReader(new FileReader(jsonFile))) {

            // Wenn die Datei leer ist, ist object null
            return gson.fromJson(jsonReader, BinaryStringTree.class);

        }
    }

    /**
     * Speichert ein Polynom in der JSON
     * @param treeToSave Das zu speichernde Polynom
     * @param filePath Zieldateo
     */
    public static void saveTreeToJson(BinaryStringTree treeToSave, String filePath) throws IOException
    {
        Gson gson = new Gson();
        File jsonFile = new File(filePath);

        // Wenn die JSON Datei existiert, können wir diese auch auslesen
        checkAndCreateFile(jsonFile);

        // Schreiben in die Datei
        try (JsonWriter jsonWriter = new JsonWriter(new FileWriter(filePath))) {
            gson.toJson(gson.toJsonTree(treeToSave), jsonWriter);
        }
    }

    /**
     * Lösht die Inhalte der Polynome.json
     * @throws IOException Bei Fehlern, die Lese-/Schreibeprozesse auf der Datei beenden oder unterbrechen
     */
    public static void clearFile(String filePath) throws IOException
    {
        File jsonFile = new File(filePath);
        if(checkAndCreateFile(jsonFile))
        {
            FileWriter fileWriter = new FileWriter(jsonFile);
            fileWriter.close();
        }
    }

    /**
     * Prüft ob die Datei existiert und erstellt sie ggf.
     * @param jsonFile Die zu überprüfende Datei
     * @return true, wenn die Datei bereits existierte, false, wenn sie erst erstellt werden musste
     * @throws IOException Bei Fehlern, die Lese-/Schreibeprozesse auf der Datei beenden oder unterbrechen
     */
    private static boolean checkAndCreateFile(File jsonFile) throws IOException
    {
        // Wenn die Datei nicht existiert, Datei erst anlegen und dann abbrechen.
        if(!jsonFile.exists() && !jsonFile.isDirectory())
        {
            try(FileWriter fw = new FileWriter(jsonFile))
            {
                return false;
            }

        }
        return true;
    }
}
