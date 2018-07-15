package sample;

import Model.BinaryNode;
import Model.BinaryStringTree;
import Model.JSONManager;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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

    BinaryStringTree bst;

    public void  initialize()
    {
        bst = new BinaryStringTree();
    }

    private void renderBinaryTree()
    {
        spTreeView.getChildren().clear();
        renderNode(bst.getRoot(), 0, 1, 1,null);

    }

    /**
     * Rendert einen Binären Baum rekursiv
     * @param binaryNodeToRender Der aktuell zu rendernde Knoten
     * @param treeDepth Die Ebene im Baum
     * @param rowId Die ID des Knoten in der Reihe
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

            int nextRowId = treeId * 2 - (int) Math.pow(2, currentTreeDepth + 1) + 1;
            renderNode(binaryNodeToRender.getLeftBinaryNode(), currentTreeDepth + 1, nextRowId, treeId*2, circle);
            nextRowId = treeId * 2 + 1 - (int) Math.pow(2, currentTreeDepth + 1) + 1;
            renderNode(binaryNodeToRender.getRightBinaryNode(), currentTreeDepth + 1, nextRowId, treeId*2+1, circle);
        }

    }

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

    public void addNewNode(ActionEvent actionEvent) {
        String value = tbValueToInsert.getText().trim();
        bst.addNode(value);
        renderBinaryTree();
    }

    public void deleteNode(ActionEvent actionEvent) {
        String value = tbValueToDelete.getText().trim();
        bst.deleteNode(value);
        renderBinaryTree();
    }

    public void saveTree(ActionEvent actionEvent)
    {
        try
        {
            JSONManager.saveTreeToJson(bst, tbBinaryTreeFileName.getText());
        }
        catch(Exception ex)
        {

        }

    }

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
            System.out.println(ex.getMessage());
        }

    }
}
