package sample;

import Model.BinaryNode;
import Model.BinaryStringTree;
import Model.JSONManager;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class Controller {
    public TextField tbValueToInsert;
    public TextField tbValueToDelete;
    public Button btnInsertValue;
    public Button btnDeleteValue;
    public BorderPane bpMain;
    public AnchorPane spTreeView;

    final private double PANE_WIDTH = 800;
    final private double PANE_HEIGHT = 700;
    final private double CIRCLE_RADIUS = 20;
    public TextField tbBinaryTreeFileName;
    public Label tbStatusText;

    private BinaryStringTree bst;

    public void  initialize()
    {
        bst = new BinaryStringTree();
    }

    private void renderBinaryTree()
    {
        spTreeView.getChildren().clear();

        if(bst.getRoot() != null)
            renderNode(bst.getRoot(), 0, 1, 1,null);

    }

    /**
     * Rendert einen Binären Baum rekursiv
     * @param binaryNodeToRender Der aktuell zu rendernde Knoten
     * @param treeDepth Die Ebene im Baum
     * @param rowId Die ID des Knoten in der aktuellen Ebene
     * @param treeId Die ID des Knoten im gesamten Baum
     * @param parent Der Kreis des Elternteils
     */
    private void renderNode(BinaryNode binaryNodeToRender, int treeDepth, int rowId, int treeId, Circle parent)
    {
        if(binaryNodeToRender == null)
            return;

        Circle circle = new Circle(19, Paint.valueOf("white"));
        circle.setStrokeWidth(1);
        circle.setStroke(Color.BLACK);

        // 2^x Elemente pro "Zeile"
        int maxElementCount = (int) Math.pow(2, treeDepth);

        double offsetX = (PANE_WIDTH/(maxElementCount+1)) * rowId;

        spTreeView.getChildren().add(circle);
        circle.setLayoutX(offsetX);
        circle.setLayoutY(50 + (100*treeDepth));

        Label circleText = new Label(binaryNodeToRender.getData());
        spTreeView.getChildren().add(circleText);
        circleText.setLayoutX(circle.getLayoutX() - circle.getRadius()/2);
        circleText.setLayoutY(circle.getLayoutY() - circle.getRadius()/2);

        if(parent != null)
            connectCircles(parent, circle);

        if(binaryNodeToRender.isLeaf())
        {
            return;
        }
        else
        {
            int currentTreeDepth = treeDepth;

            // FORMEL:
            // TreeID*2 = ID des linken Kinds (auf den gesamten Baum bezogen)
            // TreeID*2+1 = ID des Rechten Kinds (auf den gesamten Baum bezogen)
            // (int) Math.pow(2, currentTreeDepth + 1) = Anzahl der Elemente in der nächsten Ebene
            // +1 = Verhindert, dass 0 entsteht
            int nextRowId = treeId * 2 - (int) Math.pow(2, currentTreeDepth + 1) + 1;
            renderNode(binaryNodeToRender.getLeftBinaryNode(), currentTreeDepth + 1, nextRowId, treeId*2, circle);
            nextRowId = treeId * 2 + 1 - (int) Math.pow(2, currentTreeDepth + 1) + 1;
            renderNode(binaryNodeToRender.getRightBinaryNode(), currentTreeDepth + 1, nextRowId, treeId*2+1, circle);
        }

    }

    /**
     * Verbindet zwei Kreise mit einer Linie
     * @param parent Der "Elternkreis"
     * @param child Der "Kindkreis"
     */
    private void connectCircles(Circle parent, Circle child)
    {
        Line connectingLine = new Line();
        spTreeView.getChildren().add(0, connectingLine);

        connectingLine.setStartX(parent.getLayoutX());
        connectingLine.setStartY(parent.getLayoutY() + parent.getRadius());

        connectingLine.setEndX(child.getLayoutX());
        connectingLine.setEndY(child.getLayoutY() - child.getRadius());


        connectingLine.setLayoutX(0);
        connectingLine.setLayoutY(0);


    }

    /**
     * Fügt einen neuen Knoten zum aktuell dargestellten Baum hinzu
     * @param actionEvent
     */
    public void addNewNode(ActionEvent actionEvent) {
        try
        {
            String value = tbValueToInsert.getText().trim();

            if(value.length() > 3)
            {
                tbStatusText.setText("Der eingegebene Wert beinhaltet mehr als 3 Zeichen. Es sind nur 3 Zeichen zulässig!");
                return;
            }

            if(value.length() > 0)
            {

                bst.addNode(value);
                renderBinaryTree();
            }
            else
            {
                tbStatusText.setText("Der eingegebene Wert ist leer.");
            }
        }
        catch (Exception ex)
        {
            displayMessage(Alert.AlertType.ERROR, "Unerwarteter Fehler beim Hinzufügen eines Knotens",
                    "Es ist ein Fehler beim Hinzufügen eines Knotens in den Baum aufgetreten!", ex.getMessage());
        }

    }

    /**
     * Löscht einen Knoten aus dem aktuell dargestellten Baum
     * @param actionEvent
     */
    public void deleteNode(ActionEvent actionEvent)
    {
        try
        {
            String value = tbValueToDelete.getText().trim();

            if(value.length() > 3)
            {
                tbStatusText.setText("Der zu löschende Wert beinhaltet mehr als 3 Zeichen, daher existiert dieser auch nicht.");
                return;
            }

            if(!value.isEmpty())
            {
                if(bst.deleteNode(value))
                {
                    tbStatusText.setText("Wert " + value + " wurde erfolgreich gelöscht.");
                }
                else
                {
                    tbStatusText.setText("Wert " + value + " wurde nicht gelöscht, da er nicht vorhanden ist.");
                }
                renderBinaryTree();
            }
            else
            {
                tbStatusText.setText("Es wurde kein Wert zum Löschen angegeben.");
            }
        }
        catch (Exception ex)
        {
            displayMessage(Alert.AlertType.ERROR, "Unerwarteter Fehler beim Löschen eines Knotens",
                    "Es ist ein Fehler beim Löschen eines Knotens aufgetreten!", ex.getMessage());
        }

    }

    /**
     * Speichert den aktuell dargestellten Baum
     * @param actionEvent
     */
    public void saveTree(ActionEvent actionEvent)
    {
        try
        {
            String fileName = tbBinaryTreeFileName.getText().trim();

            if(fileName.length() > 0)
            {
                if(!fileName.toLowerCase().endsWith(".json"))
                    fileName += ".json";

                // Speichern des Baums im current working Directory
                JSONManager.saveTreeToJson(bst, fileName);
                tbStatusText.setText("Baum gespeichert unter: " + System.getProperty("user.dir")+"\\"+fileName);
            }
            else
            {
                tbStatusText.setText("Es wurde kein Dateiname angegeben.");
            }
        }
        catch(Exception ex)
        {
            displayMessage(Alert.AlertType.ERROR, "Unerwarteter Fehler beim Speichern des Baumes!",
                    "Es ist ein Fehler beim Speichern des Baumes in die Datei aufgetreten!", ex.getMessage());
        }

    }

    /**
     * Öffnet einen Dialog zur Auswahl einer JSON-Datei, die einen Baum beinhaltet.
     * @param actionEvent
     */
    public void loadTreeFromJson(ActionEvent actionEvent)
    {
        try
        {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("JSON Files", "*.json")
                    );
            File selectedFile = fileChooser.showOpenDialog(null);
            if (selectedFile != null) {
                bst = JSONManager.getTreeFromFile(selectedFile.getAbsolutePath());
                renderBinaryTree();
            }
        }
        catch (Exception ex)
        {
            displayMessage(Alert.AlertType.ERROR, "Unerwarteter Fehler beim Laden des Baumes!",
                    "Es ist ein Fehler beim Laden eines Baumes aus der Datei aufgetreten!", ex.getMessage());
        }

    }

    /**
     * Löscht den aktuell dargestellten Baum komplett
     * @param actionEvent
     */
    public void deleteTree(ActionEvent actionEvent) {
        bst.deleteTree();
        renderBinaryTree();
    }

    public void insertValueKeyPressed(KeyEvent keyEvent) {
        if(keyEvent.getCode().equals(KeyCode.ENTER))
        {
            addNewNode(null);
        }
    }

    public void deleteValueKeyPressed(KeyEvent keyEvent) {
        if(keyEvent.getCode().equals(KeyCode.ENTER))
        {
            deleteNode(null);
        }
    }

    private void displayMessage(Alert.AlertType alertType, String title, String headerText, String contentText)
    {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
}
