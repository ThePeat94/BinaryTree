package Model;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Verwaltung der JSON-Dateien. Wird genutzt, um aus JSONs Bäume zu parsen oder Bäume in JSONs zu speichern. <br>
 * Stellt Funktionen bereit, um die JSON auszulesen, zu leeren und um Elemente einzuspeichern.
 */
public class JSONManager {

    /**
     * Liest die gegebene JSON Datei aus und parsed daraus den Baum
     * @return Den in der JSON gespeicherten Datei
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
     * Speichert einen Baum in einer JSON
     * @param treeToSave Der zu speichernde Baum
     * @param filePath Zieldatei
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
