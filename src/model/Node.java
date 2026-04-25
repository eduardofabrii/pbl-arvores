package model;

public class Node {
    private Player player;
    private Node left;
    private Node right;
    private double renderX;
    private double renderY;

    public Node(Player player) {
        this.player = player;
        this.left = null;
        this.right = null;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public double getRenderX() {
        return renderX;
    }

    public void setRenderX(double renderX) {
        this.renderX = renderX;
    }

    public double getRenderY() {
        return renderY;
    }

    public void setRenderY(double renderY) {
        this.renderY = renderY;
    }
}
