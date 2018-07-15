package Model;

public class BinaryNode {
    private String data;
    private BinaryNode leftBinaryNode;
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

    public boolean isLeaf()
    {
        return leftBinaryNode == null && rightBinaryNode == null;
    }
}
