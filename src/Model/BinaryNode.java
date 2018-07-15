package Model;

/**
 * Stellt einen Knoten in einem binären Baum dar
 */
public class BinaryNode {

    /**
     * Die enthaltenen Daten
     */
    private String data;

    /**
     * Linker Knoten
     */
    private BinaryNode leftBinaryNode;

    /**
     * Rechter Knoten
     */
    private BinaryNode rightBinaryNode;

    public BinaryNode(String data) {
        this.data = data;
        leftBinaryNode = null;
        rightBinaryNode = null;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public BinaryNode getRightBinaryNode() {
        return rightBinaryNode;
    }

    public void setRightBinaryNode(BinaryNode rightBinaryNode) {
        this.rightBinaryNode = rightBinaryNode;
    }

    public BinaryNode getLeftBinaryNode() {
        return leftBinaryNode;
    }

    public void setLeftBinaryNode(BinaryNode leftBinaryNode) {
        this.leftBinaryNode = leftBinaryNode;
    }

    /**
     * Fügt einen neuen Knoten mit dem gegebenen Wert an den nächsten freien Knoten an
     * @param nodeValue
     * @return
     */
    public boolean addNode(String nodeValue)
    {
        if(nodeValue.equals(data))
            return false;

        if(nodeValue.compareTo(data) < 0)
        {
            if(leftBinaryNode != null)
            {
                return leftBinaryNode.addNode(nodeValue);
            }
            else
            {
                leftBinaryNode = new BinaryNode(nodeValue);
                return true;
            }
        }
        else if(nodeValue.compareTo(data) > 0)
        {
            if(rightBinaryNode != null)
            {
                return rightBinaryNode.addNode(nodeValue);
            }
            else
            {
                rightBinaryNode = new BinaryNode(nodeValue);
                return true;
            }
        }

        return false;
    }

    /**
     * Ist der gegebene Knoten ein Blatt (hat also keine Kinder)?
     * @return
     */
    public boolean isLeaf()
    {
        return leftBinaryNode == null && rightBinaryNode == null;
    }
}
