package data;

import model.Node;
import model.Player;

public class BinarySearchTree {
    private Node root;

    public BinarySearchTree() {
        root = null;
    }

    public Node getRoot() {
        return root;
    }

    public boolean containsRanking(int ranking) {
        return searchByRanking(root, ranking) != null;
    }

    public void insert(Player player) {
        root = insert(root, player);
    }

    public boolean search(String name) {
        return search(root, name) != null;
    }

    public Player remove(String name) {
        Node found = search(root, name);
        if (found == null) {
            return null;
        }
        Player player = found.getPlayer();
        int removedRanking = player.getRanking();
        root = remove(root, name);
        decrementRankingsAfter(root, removedRanking);
        return player;
    }

    public ArrayList<Node> getPathTo(String name) {
        ArrayList<Node> path = new ArrayList<>();
        findPath(root, name, path);
        return path;
    }

    public void inOrder() {
        inOrder(root);
        System.out.println();
    }

    private Node insert(Node current, Player player) {
        if (current == null) {
            return new Node(player);
        }
        if (player.getRanking() < current.getPlayer().getRanking()) {
            current.setLeft(insert(current.getLeft(), player));
        } else if (player.getRanking() > current.getPlayer().getRanking()) {
            current.setRight(insert(current.getRight(), player));
        }
        return current;
    }

    private Node search(Node current, String name) {
        if (current == null) {
            return null;
        }
        if (current.getPlayer().getNickname().equals(name)) {
            return current;
        }
        Node leftResult = search(current.getLeft(), name);
        if (leftResult != null) {
            return leftResult;
        }
        return search(current.getRight(), name);
    }

    private Node remove(Node current, String name) {
        if (current == null) {
            return null;
        }
        if (current.getPlayer().getNickname().equals(name)) {
            if (current.getLeft() == null && current.getRight() == null) {
                return null;
            }
            if (current.getLeft() == null) {
                return current.getRight();
            }
            if (current.getRight() == null) {
                return current.getLeft();
            }
            Node successor = findMin(current.getRight());
            current.setPlayer(successor.getPlayer());
            current.setRight(removeMin(current.getRight()));
            return current;
        }
        current.setLeft(remove(current.getLeft(), name));
        current.setRight(remove(current.getRight(), name));
        return current;
    }

    private Node findMin(Node node) {
        if (node.getLeft() == null) {
            return node;
        }
        return findMin(node.getLeft());
    }

    private Node removeMin(Node node) {
        if (node.getLeft() == null) {
            return node.getRight();
        }
        node.setLeft(removeMin(node.getLeft()));
        return node;
    }

    private Node searchByRanking(Node current, int ranking) {
        if (current == null) {
            return null;
        }
        if (current.getPlayer().getRanking() == ranking) {
            return current;
        }
        if (ranking < current.getPlayer().getRanking()) {
            return searchByRanking(current.getLeft(), ranking);
        }
        return searchByRanking(current.getRight(), ranking);
    }

    private void decrementRankingsAfter(Node current, int removedRanking) {
        if (current == null) {
            return;
        }
        if (current.getPlayer().getRanking() > removedRanking) {
            current.getPlayer().setRanking(current.getPlayer().getRanking() - 1);
        }
        decrementRankingsAfter(current.getLeft(), removedRanking);
        decrementRankingsAfter(current.getRight(), removedRanking);
    }

    private boolean findPath(Node current, String name, ArrayList<Node> path) {
        if (current == null) {
            return false;
        }
        path.add(current);
        if (current.getPlayer().getNickname().equals(name)) {
            return true;
        }
        if (findPath(current.getLeft(), name, path)) {
            return true;
        }
        if (findPath(current.getRight(), name, path)) {
            return true;
        }
        path.remove(path.size() - 1);
        return false;
    }

    private void inOrder(Node current) {
        if (current == null) {
            return;
        }
        inOrder(current.getLeft());
        System.out.print(current.getPlayer() + " ");
        inOrder(current.getRight());
    }
}
