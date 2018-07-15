package Model;

import javax.xml.soap.Node;

public class BinaryStringTree
{
    /**
     * 1. leeren Baum erzeugen
     * 2. Knoten hinzufügen
     * 3. Baum anzeigen (alphanumerisch als Werteliste oder grafisch im Dialogfenster)
     * 4. Knoten löschen
     * 5. gesamten Baum löschen
     * 6. Baum in externe Datei speichern (ASCII oder binär)
     * 7. Baum aus externer Datei lesen (ASCII oder binär)
     */


    private BinaryNode root;


    public BinaryStringTree()
    {
        this.root = null;
    }

    /**
     * Knoten hinzufügen
     * @param nodeValue Wert des neuen Knoten
     * @return true, wenn das hinzufügen erfolgreich war, false wenn nicht
     */
    public boolean addNode(String nodeValue)
    {
        if(root == null)
        {
            root = new BinaryNode(nodeValue);
            return true;
        }

        return root.addNode(nodeValue);
    }

    public boolean deleteNode(String valueToDelete)
    {
        BinaryNode currentBinaryNode = root;
        BinaryNode parentBinaryNode = currentBinaryNode;
        BinaryNode binaryNodeToDelete = null;

        do
        {
            if(currentBinaryNode.getData().equals(valueToDelete))
            {
                binaryNodeToDelete = currentBinaryNode;
                break;
            }

            parentBinaryNode = currentBinaryNode;
            if(valueToDelete.compareTo(currentBinaryNode.getData()) < 0)
            {
                currentBinaryNode = currentBinaryNode.getLeftBinaryNode();
            }
            else if(valueToDelete.compareTo(currentBinaryNode.getData()) > 0)
            {
                currentBinaryNode = currentBinaryNode.getRightBinaryNode();
            }
        } while(!currentBinaryNode.isLeaf());

        if(currentBinaryNode.getData().equals(valueToDelete))
        {
            binaryNodeToDelete = currentBinaryNode;
        }

        if(binaryNodeToDelete == null)
        {
            return false;
        }

        // Der zu löschende Knoten ist ein Blatt -> hat keine Kinder, kann einfach so gelöscht werden
        if(binaryNodeToDelete.isLeaf())
        {
            if(parentBinaryNode.getRightBinaryNode() == binaryNodeToDelete)
            {
                parentBinaryNode.setRightBinaryNode(null);
            }
            else if(parentBinaryNode.getLeftBinaryNode() == binaryNodeToDelete)
            {
                parentBinaryNode.setLeftBinaryNode(null);
            }
            else if(binaryNodeToDelete == root)
            {
                root = null;
            }
            return true;
        }


        BinaryNode rightChildBinaryNode = binaryNodeToDelete.getRightBinaryNode();
        BinaryNode leftChildBinaryNode = binaryNodeToDelete.getLeftBinaryNode();

        boolean isLeftNode = parentBinaryNode.getLeftBinaryNode() == binaryNodeToDelete;

        // Der Knoten hat nur 1 Kind -> Den zu löschenden Knoten durch sein Kind ersetzen
        if(rightChildBinaryNode != null && leftChildBinaryNode == null)
        {
            if(binaryNodeToDelete == root)
            {
                root = rightChildBinaryNode;
                return true;
            }

            if(isLeftNode)
                parentBinaryNode.setLeftBinaryNode(rightChildBinaryNode);
            else
                parentBinaryNode.setRightBinaryNode(rightChildBinaryNode);

            return true;
        }
        else if(rightChildBinaryNode == null && leftChildBinaryNode != null)
        {
            if(binaryNodeToDelete == root)
            {
                root = leftChildBinaryNode;
                return true;
            }


            if(isLeftNode)
                parentBinaryNode.setLeftBinaryNode(leftChildBinaryNode);
            else
                parentBinaryNode.setRightBinaryNode(leftChildBinaryNode);
            return true;
        }

        if(binaryNodeToDelete == root)
        {
            getMostRightNode(root.getLeftBinaryNode()).setRightBinaryNode(root.getRightBinaryNode());
            root = root.getLeftBinaryNode();
            return true;
        }

        // Der Knoten hat 2 Kinder
        if(isLeftNode)
        {


            getMostRightNode(binaryNodeToDelete.getLeftBinaryNode()).setRightBinaryNode(binaryNodeToDelete.getRightBinaryNode());
            parentBinaryNode.setLeftBinaryNode(binaryNodeToDelete.getLeftBinaryNode());
            return true;
        }
        else
        {
            getMostRightNode(binaryNodeToDelete.getLeftBinaryNode()).setRightBinaryNode(binaryNodeToDelete.getRightBinaryNode());
            parentBinaryNode.setRightBinaryNode(binaryNodeToDelete.getLeftBinaryNode());
            return true;
        }
    }

    private BinaryNode getMostRightNode(BinaryNode node)
    {
        BinaryNode current = node;
        while(current.getRightBinaryNode() != null)
        {
            current = current.getRightBinaryNode();
        }

        return current;
    }

    private BinaryNode getMostLeftNode(BinaryNode node)
    {
        BinaryNode current = node;
        while(current.getLeftBinaryNode() != null)
        {
            current = current.getLeftBinaryNode();
        }
        return current;
    }

    /**
     * Baum löschen
     */
    public void deleteTree()
    {
        root = null;
    }

    public BinaryStringTree(BinaryNode root) {
        this.root = root;
    }

    public BinaryNode getRoot() {
        return root;
    }

    public void setRoot(BinaryNode root) {
        this.root = root;
    }
}
