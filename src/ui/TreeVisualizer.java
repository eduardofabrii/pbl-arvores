package ui;

import data.ArrayList;
import data.BinarySearchTree;
import model.Node;

import javax.swing.*;
import java.awt.*;

public class TreeVisualizer extends JFrame {

    private static final int NODE_RADIUS = 32;
    private static final int H_GAP = 80;
    private static final int V_GAP = 100;
    private static final int MARGIN = 60;

    private final BinarySearchTree bst;
    private final String targetNickname;
    private JScrollPane scrollPane;

    private TreeVisualizer(BinarySearchTree bst, String targetNickname) {
        super(targetNickname == null
                ? "Ranking - Exibição em Ordem"
                : "Ranking - Buscando: " + targetNickname);
        this.bst = bst;
        this.targetNickname = targetNickname;
        setupUI();
    }

    public static void openSearch(BinarySearchTree bst, String nickname) {
        SwingUtilities.invokeLater(() -> new TreeVisualizer(bst, nickname));
    }

    public static void openAll(BinarySearchTree bst) {
        SwingUtilities.invokeLater(() -> new TreeVisualizer(bst, null));
    }

    private void setupUI() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        int width = Math.max(countNodes(bst.getRoot()) * H_GAP + MARGIN * 2, 800);
        int height = Math.max((treeHeight(bst.getRoot()) + 1) * V_GAP + MARGIN * 2, 200);

        TreePanel treePanel = new TreePanel();
        treePanel.setPreferredSize(new Dimension(width, height));

        scrollPane = new JScrollPane(treePanel);
        scrollPane.setPreferredSize(new Dimension(1100, 700));

        add(scrollPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        if (targetNickname != null) {
            int[] counter = {0};
            assignPositions(bst.getRoot(), counter, 0);
            scrollToTarget();
        }
    }

    private void scrollToTarget() {
        Node target = findNode(bst.getRoot(), targetNickname);
        if (target == null) return;

        SwingUtilities.invokeLater(() -> {
            JViewport viewport = scrollPane.getViewport();
            Dimension viewSize = viewport.getExtentSize();
            int scrollX = Math.max(0, (int) target.getRenderX() - viewSize.width / 2);
            int scrollY = Math.max(0, (int) target.getRenderY() - viewSize.height / 2);
            viewport.setViewPosition(new Point(scrollX, scrollY));
        });
    }

    private void assignPositions(Node node, int[] counter, int depth) {
        if (node == null) return;
        assignPositions(node.getLeft(), counter, depth + 1);
        node.setRenderX(counter[0] * H_GAP + MARGIN);
        node.setRenderY(depth * V_GAP + MARGIN);
        counter[0]++;
        assignPositions(node.getRight(), counter, depth + 1);
    }

    private Node findNode(Node current, String name) {
        if (current == null) return null;
        if (current.getPlayer().getNickname().equals(name)) return current;
        Node left = findNode(current.getLeft(), name);
        if (left != null) return left;
        return findNode(current.getRight(), name);
    }

    private int countNodes(Node node) {
        if (node == null) return 0;
        return 1 + countNodes(node.getLeft()) + countNodes(node.getRight());
    }

    private int treeHeight(Node node) {
        if (node == null) return 0;
        return 1 + Math.max(treeHeight(node.getLeft()), treeHeight(node.getRight()));
    }

    private class TreePanel extends JPanel {

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (bst.getRoot() == null) return;

            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int[] counter = {0};
            assignPositions(bst.getRoot(), counter, 0);

            ArrayList<Node> path = targetNickname != null
                    ? bst.getPathTo(targetNickname)
                    : new ArrayList<>();

            drawEdges(g2, bst.getRoot(), path);
            drawNodes(g2, bst.getRoot(), path);
        }

        private void drawEdges(Graphics2D g2, Node node, ArrayList<Node> path) {
            if (node == null) return;
            g2.setColor(new Color(68, 68, 68));
            g2.setStroke(new BasicStroke(1.5f));
            if (node.getLeft() != null) {
                g2.drawLine((int) node.getRenderX(), (int) node.getRenderY(),
                        (int) node.getLeft().getRenderX(), (int) node.getLeft().getRenderY());
                drawEdges(g2, node.getLeft(), path);
            }
            if (node.getRight() != null) {
                g2.drawLine((int) node.getRenderX(), (int) node.getRenderY(),
                        (int) node.getRight().getRenderX(), (int) node.getRight().getRenderY());
                drawEdges(g2, node.getRight(), path);
            }
        }

        private void drawNodes(Graphics2D g2, Node node, ArrayList<Node> path) {
            if (node == null) return;

            int x = (int) node.getRenderX();
            int y = (int) node.getRenderY();

            boolean isTarget = targetNickname != null
                    && node.getPlayer().getNickname().equals(targetNickname);
            boolean isAncestor = !isTarget && isInPath(path, node);

            Color fill, border;
            float strokeWidth;

            if (isTarget) {
                fill = new Color(255, 140, 0);
                border = new Color(180, 90, 0);
                strokeWidth = 3f;
            } else if (isAncestor) {
                fill = new Color(120, 190, 240);
                border = new Color(46, 117, 182);
                strokeWidth = 2f;
            } else {
                fill = new Color(91, 155, 213);
                border = new Color(46, 117, 182);
                strokeWidth = 2f;
            }

            g2.setColor(fill);
            g2.fillOval(x - NODE_RADIUS, y - NODE_RADIUS, NODE_RADIUS * 2, NODE_RADIUS * 2);
            g2.setColor(border);
            g2.setStroke(new BasicStroke(strokeWidth));
            g2.drawOval(x - NODE_RADIUS, y - NODE_RADIUS, NODE_RADIUS * 2, NODE_RADIUS * 2);

            g2.setColor(Color.WHITE);

            String name = node.getPlayer().getNickname();
            if (name.length() > 11) name = name.substring(0, 10) + ".";
            String rank = "#" + node.getPlayer().getRanking();

            g2.setFont(new Font("SansSerif", Font.PLAIN, 9));
            FontMetrics fm = g2.getFontMetrics();
            g2.drawString(name, x - fm.stringWidth(name) / 2, y - 2);

            g2.setFont(new Font("SansSerif", Font.BOLD, 9));
            fm = g2.getFontMetrics();
            g2.drawString(rank, x - fm.stringWidth(rank) / 2, y + 10);

            drawNodes(g2, node.getLeft(), path);
            drawNodes(g2, node.getRight(), path);
        }

        private boolean isInPath(ArrayList<Node> path, Node node) {
            for (int i = 0; i < path.size(); i++) {
                if (path.get(i) == node) return true;
            }
            return false;
        }
    }
}
