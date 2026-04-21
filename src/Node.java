public class Node {
    Player player;
    Node left;
    Node right;
    double renderX;
    double renderY;

    public Node(Player player) {
        this.player = player;
        this.left = null;
        this.right = null;
    }
}
