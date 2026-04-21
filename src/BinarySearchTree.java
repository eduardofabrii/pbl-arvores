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
        Player player = found.player;
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
        if (player.getRanking() < current.player.getRanking()) {
            current.left = insert(current.left, player);
        } else if (player.getRanking() > current.player.getRanking()) {
            current.right = insert(current.right, player);
        }
        return current;
    }

    private Node search(Node current, String name) {
        if (current == null) {
            return null;
        }
        if (current.player.getNickname().equals(name)) {
            return current;
        }
        Node leftResult = search(current.left, name);
        if (leftResult != null) {
            return leftResult;
        }
        return search(current.right, name);
    }

    private Node remove(Node current, String name) {
        if (current == null) {
            return null;
        }
        if (current.player.getNickname().equals(name)) {
            if (current.left == null && current.right == null) {
                return null;
            }
            if (current.left == null) {
                return current.right;
            }
            if (current.right == null) {
                return current.left;
            }
            Node successor = findMin(current.right);
            current.player = successor.player;
            current.right = removeMin(current.right);
            return current;
        }
        current.left = remove(current.left, name);
        current.right = remove(current.right, name);
        return current;
    }

    private Node findMin(Node node) {
        if (node.left == null) {
            return node;
        }
        return findMin(node.left);
    }

    private Node removeMin(Node node) {
        if (node.left == null) {
            return node.right;
        }
        node.left = removeMin(node.left);
        return node;
    }

    private Node searchByRanking(Node current, int ranking) {
        if (current == null) {
            return null;
        }
        if (current.player.getRanking() == ranking) {
            return current;
        }
        if (ranking < current.player.getRanking()) {
            return searchByRanking(current.left, ranking);
        }
        return searchByRanking(current.right, ranking);
    }

    private void decrementRankingsAfter(Node current, int removedRanking) {
        if (current == null) {
            return;
        }
        if (current.player.getRanking() > removedRanking) {
            current.player.setRanking(current.player.getRanking() - 1);
        }
        decrementRankingsAfter(current.left, removedRanking);
        decrementRankingsAfter(current.right, removedRanking);
    }

    private boolean findPath(Node current, String name, ArrayList<Node> path) {
        if (current == null) {
            return false;
        }
        path.add(current);
        if (current.player.getNickname().equals(name)) {
            return true;
        }
        if (findPath(current.left, name, path)) {
            return true;
        }
        if (findPath(current.right, name, path)) {
            return true;
        }
        path.remove(path.size() - 1);
        return false;
    }

    private void inOrder(Node current) {
        if (current == null) {
            return;
        }
        inOrder(current.left);
        System.out.print(current.player + " ");
        inOrder(current.right);
    }
}
