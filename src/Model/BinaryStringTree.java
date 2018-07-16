package Model;

import javax.xml.soap.Node;

public class BinaryStringTree
{
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
            // Knoten mit den Daten gefunden
            if(currentBinaryNode.getData().equals(valueToDelete))
            {
                binaryNodeToDelete = currentBinaryNode;
                break;
            }

            parentBinaryNode = currentBinaryNode;
            // Wert ist kleiner -> Links runter
            if(valueToDelete.compareTo(currentBinaryNode.getData()) < 0)
            {
                currentBinaryNode = currentBinaryNode.getLeftBinaryNode();
            }
            // Wert ist größer -> Rechts runter
            else if(valueToDelete.compareTo(currentBinaryNode.getData()) > 0)
            {
                currentBinaryNode = currentBinaryNode.getRightBinaryNode();
            }
        } while(!currentBinaryNode.isLeaf());

        // Nichts gefunden, abbrechen
        if(binaryNodeToDelete == null)
        {
            return false;
        }

        // Wenn zuvor keiner gefunden wurde, ist der letzte geprüfte Knoten evtl. der zu löschende
        if(currentBinaryNode.getData().equals(valueToDelete))
        {
            binaryNodeToDelete = currentBinaryNode;
        }

        // Der zu löschende Knoten ist ein Blatt -> hat keine Kinder, kann einfach so gelöscht werden
        if(binaryNodeToDelete.isLeaf())
        {
            if(binaryNodeToDelete == root)
            {
                root = null;
            }
            else if(parentBinaryNode.getRightBinaryNode() == binaryNodeToDelete)
            {
                parentBinaryNode.setRightBinaryNode(null);
            }
            else if(parentBinaryNode.getLeftBinaryNode() == binaryNodeToDelete)
            {
                parentBinaryNode.setLeftBinaryNode(null);
            }
            return true;
        }

        // Linkes und rechtes Kind des zu löschenden Knoten abfragen
        BinaryNode rightChildBinaryNode = binaryNodeToDelete.getRightBinaryNode();
        BinaryNode leftChildBinaryNode = binaryNodeToDelete.getLeftBinaryNode();

        // Ist der zu löschende Knoten der linke Knoten des Elternknotens?
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

        // Der zu löschende Knoten hat zwei Kinder
        // Der rechte Teilbaum des zu löschenden Knotens wird an den rechtesten Knoten des linken Kindknoten gehangen

        if(binaryNodeToDelete == root)
        {
            getMostRightNode(root.getLeftBinaryNode()).setRightBinaryNode(root.getRightBinaryNode());
            root = root.getLeftBinaryNode();
            return true;
        }

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

    /**
     * Ermittelt den rechtesten Knoten eines Knoten
     * @param node Der Knoten, für den der rechteste Knoten ermittelt werden soll
     * @return Der rechteste Knoten
     */
    private BinaryNode getMostRightNode(BinaryNode node)
    {
        BinaryNode current = node;
        while(current.getRightBinaryNode() != null)
        {
            current = current.getRightBinaryNode();
        }

        return current;
    }

    /**
     * Ermittelt den linksten Knoten eines Knoten
     * @param node Der Knoten, für den der linksten Knoten ermittelt werden soll
     * @return Der linkste Knoten
     */
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
