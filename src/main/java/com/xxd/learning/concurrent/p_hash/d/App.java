package com.xxd.jdksource.concurrent.p_hash.d;

/**
 * @author gao
 * @time 2020/05/23 09:35:23
 */

/*
    红黑树
    https://www.cs.usfca.edu/~galles/visualization/RedBlack.html

    红黑树的性质：
    1. 每个节点不是红色就是黑色
    2. 不可能有连在一起的红色节点
    3. 根节点都是黑色
    4. 每个红色节点的两个子节点都是黑色，叶子节点都是黑色，两个黑色节点是可以连接在一起的。
    5. 从任一节点到其每个叶子的所有路径都包含相同数目的黑色节点。

    为了满足红黑树的性质，红黑树就有了3种变换规则：
    1. 改变颜色：红变黑，黑变红
    2. 左旋
    3. 右旋


    旋转和颜色变换规则：
    1. 所有插入的点默认为红色
    2. 变颜色的情况：当前节点的父亲是红色，且它的祖父节点的另一个子节点也是红色。（也就是叔叔节点也是红色）
        a. 把父节点设为黑色
        b. 把叔叔也设为黑色
        c. 把祖父（也就是父亲的父亲）设为红的
        d. 把指针定义到祖父节点，设置为当前要操作的，再分析点的变化规则
    3. 左旋：当前父节点是红色，叔叔节点是黑色的时候，且当前的节点是右子树。进行左旋（以父节点作为左旋）
    4. 右旋：当前父节点是红色，叔叔节点是黑色的时候，且当前的节点是左子树。进行右旋
        a. 把父节点变为黑色
        b. 把祖父节点变为红色
        c. 以祖父节点旋转

    变换规则演示：
    https://processon.com/diagraming/5ec9d619f346fb690712ba37

*/

class RedBlackTree {
    private final int R = 0;
    private final int B = 1;
    private Node root = null;       // 红黑树的根节点

    class Node {
        int data;                   // 存的具体数字
        int color = R;              // 所有插入的新节点默认都是红色
        Node left;
        Node right;
        Node parent;

        public Node(int data) {
            this.data = data;
        }
    }

    public void insert(Node root, int data) {
        Node newNode = new Node(data);
        if (data > root.data) {
            if(root.right == null) {
                root.right = newNode;
                newNode.parent = root;
            } else {
                insert(root.right, data);
            }
        } else {
            if (root.left == null) {
                root.left = newNode;
                newNode.parent = root;
            } else {
                insert(root.left, data);
            }
        }
    }

    public void leftRotate(Node node) {
        if (node.parent == null) {
            // node，就是我们的root节点
            Node E = root;
            Node S = E.right;

            E.right = S.left;
            S.left.parent = E;

            E.parent = S;
            S.parent = null;

            S.left = E;
        } else {
            // 如果node右父节点，那么就要操作三个点
            Node E = node;
            Node S = E.right;
            // 判断E是其父节点的左节点还是右节点
            if (E == E.parent.left) {
                E.right = S.left;
                S.left.parent = E;

                E.parent = S;
                S.parent = E.parent.parent;

                S.left = E;
            }
        }
    }


    public void rightRotate(Node node) {

    }
}

public class App {

}







































