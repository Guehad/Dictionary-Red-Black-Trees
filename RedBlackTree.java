import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.Math.log;
import static java.lang.Math.max;
import java.util.Scanner;

public class RedBlackTree {

    private final int RED = 0;
    private final int BLACK = 1;
    int count=0;
    int height=0;

    private class Node {

        int  color = BLACK;
        String value;
        Node left = nil, right = nil, parent = nil;

        Node(String value) {
            this.value = value;
        } 
    }

    private final Node nil = new Node(null); 
    private Node root = nil;

    public void printTree(Node node) throws IOException {
   
        if (node == nil) {
            return;
        }
        printTree(node.left);
        System.out.print(((node.color==RED)?"Color: Red ":"Color: Black ")+"Key: "+node.value+" Parent: "+node.parent.value+"\n");
        
   
try(FileWriter fw = new FileWriter("D:/college/spring 2018/Data Structures 2/English Dictionary/dict.txt", true);
    BufferedWriter bw = new BufferedWriter(fw);
        
    PrintWriter out = new PrintWriter(bw))
{
    out.println(node.value);
   
} catch (IOException e) {

}
   
    printTree(node.right);
}
    

    private Node findNode(Node findNode, Node node) {
        if (root == nil) {
            return null;
        }

        if ((findNode.value).compareTo(node.value)<0) {
            if (node.left != nil) {
                return findNode(findNode, node.left);
            }
        } else if ((findNode.value).compareTo(node.value)>0) {
            if (node.right != nil) {
                return findNode(findNode, node.right);
            }
        } else if ((findNode.value).compareTo(node.value)==0) {
            return node;
        }
        return null;
    }
   int treeHeight(Node n) {
    if (n==null) 
    return -1;
    return max(treeHeight(n.left), treeHeight(n.right)) + 1;
}

    private void insert(Node node) {
        if(findNode(node,root)!=null){
            System.out.println("ALready Exists");
            return;
        }
        count=count+1;
        Node temp = root;
        if (root == nil) {
            root = node;
            node.color = BLACK;
            node.parent = nil;
        } else {
            node.color = RED;
            while (true) {
                if ((node.value).compareTo( temp.value)<0) {
                    if (temp.left == nil) {
                        temp.left = node;
                        node.parent = temp;
                        break;
                    } else {
                        temp = temp.left;
                    }
                } else if ((node.value).compareTo( temp.value)>=0) {
                    if (temp.right == nil) {
                        temp.right = node;
                        node.parent = temp;
                        break;
                    } else {
                        temp = temp.right;
                    }
                }
            }
            fixTree(node);
        }
    }

    //Takes as argument the newly inserted node
    private void fixTree(Node node) {
        while (node.parent.color == RED) {
            Node uncle = nil;
            if (node.parent == node.parent.parent.left) {
                uncle = node.parent.parent.right;

                if (uncle != nil && uncle.color == RED) {
                    node.parent.color = BLACK;
                    uncle.color = BLACK;
                    node.parent.parent.color = RED;
                    node = node.parent.parent;
                    continue;
                } 
                if (node == node.parent.right) {
                    //Double rotation needed
                    node = node.parent;
                    rotateLeft(node);
                } 
                node.parent.color = BLACK;
                node.parent.parent.color = RED;
                //if the "else if" code hasn't executed, this
                //is a case where we only need a single rotation 
                rotateRight(node.parent.parent);
            } else {
                uncle = node.parent.parent.left;
                 if (uncle != nil && uncle.color == RED) {
                    node.parent.color = BLACK;
                    uncle.color = BLACK;
                    node.parent.parent.color = RED;
                    node = node.parent.parent;
                    continue;
                }
                if (node == node.parent.left) {
                    //Double rotation needed
                    node = node.parent;
                    rotateRight(node);
                }
                node.parent.color = BLACK;
                node.parent.parent.color = RED;
                //if the "else if" code hasn't executed, this
                //is a case where we only need a single rotation
                rotateLeft(node.parent.parent);
            }
        }
        root.color = BLACK;
    }

    void rotateLeft(Node node) {
        if (node.parent != nil) {
            if (node == node.parent.left) {
                node.parent.left = node.right;
            } else {
                node.parent.right = node.right;
            }
            node.right.parent = node.parent;
            node.parent = node.right;
            if (node.right.left != nil) {
                node.right.left.parent = node;
            }
            node.right = node.right.left;
            node.parent.left = node;
        } else {//Need to rotate root
            Node right = root.right;
            root.right = right.left;
            right.left.parent = root;
            root.parent = right;
            right.left = root;
            right.parent = nil;
            root = right;
        }
    }

    void rotateRight(Node node) {
        if (node.parent != nil) {
            if (node == node.parent.left) {
                node.parent.left = node.left;
            } else {
                node.parent.right = node.left;
            }

            node.left.parent = node.parent;
            node.parent = node.left;
            if (node.left.right != nil) {
                node.left.right.parent = node;
            }
            node.left = node.left.right;
            node.parent.right = node;
        } else {//Need to rotate root
            Node left = root.left;
            root.left = root.left.right;
            left.right.parent = root;
            root.parent = left;
            left.right = root;
            left.parent = nil;
            root = left;
        }
    }

    //Deletes whole tree
    void deleteTree(){
        root = nil;
        count=0;
    }
    
   
    void transplant(Node target, Node with){ 
          if(target.parent == nil){
              root = with;
          }else if(target == target.parent.left){
              target.parent.left = with;
          }else
              target.parent.right = with;
          with.parent = target.parent;
    }
    
    boolean delete(Node z){
        if((z = findNode(z, root))==null)return false;
        Node x;
        Node y = z; // temporary reference y
        int y_original_color = y.color;
        
        if(z.left == nil){
            x = z.right;  
            transplant(z, z.right);  
        }else if(z.right == nil){
            x = z.left;
            transplant(z, z.left); 
        }else{
            y = treeMinimum(z.right);
            y_original_color = y.color;
            x = y.right;
            if(y.parent == z)
                x.parent = y;
            else{
                transplant(y, y.right);
                y.right = z.right;
                y.right.parent = y;
            }
            transplant(z, y);
            y.left = z.left;
            y.left.parent = y;
            y.color = z.color; 
        }
        if(y_original_color==BLACK)
            deleteFixup(x);
        count--;
        return true;
    }
    
    void deleteFixup(Node x){
        while(x!=root && x.color == BLACK){ 
            if(x == x.parent.left){
                Node w = x.parent.right;
                if(w.color == RED){
                    w.color = BLACK;
                    x.parent.color = RED;
                    rotateLeft(x.parent);
                    w = x.parent.right;
                }
                if(w.left.color == BLACK && w.right.color == BLACK){
                    w.color = RED;
                    x = x.parent;
                    continue;
                }
                else if(w.right.color == BLACK){
                    w.left.color = BLACK;
                    w.color = RED;
                    rotateRight(w);
                    w = x.parent.right;
                }
                if(w.right.color == RED){
                    w.color = x.parent.color;
                    x.parent.color = BLACK;
                    w.right.color = BLACK;
                    rotateLeft(x.parent);
                    x = root;
                }
            }else{
                Node w = x.parent.left;
                if(w.color == RED){
                    w.color = BLACK;
                    x.parent.color = RED;
                    rotateRight(x.parent);
                    w = x.parent.left;
                }
                if(w.right.color == BLACK && w.left.color == BLACK){
                    w.color = RED;
                    x = x.parent;
                    continue;
                }
                else if(w.left.color == BLACK){
                    w.right.color = BLACK;
                    w.color = RED;
                    rotateLeft(w);
                    w = x.parent.left;
                }
                if(w.left.color == RED){
                    w.color = x.parent.color;
                    x.parent.color = BLACK;
                    w.left.color = BLACK;
                    rotateRight(x.parent);
                    x = root;
                }
            }
        }
        x.color = BLACK; 
    }
    
    Node treeMinimum(Node subTreeRoot){
        while(subTreeRoot.left!=nil){
            subTreeRoot = subTreeRoot.left;
        }
        return subTreeRoot;
    }
    
    public void consoleUI() throws FileNotFoundException, IOException {
        Scanner scan = new Scanner(System.in);
        
        File dict=new File("D:/college/spring 2018/Data Structures 2/English Dictionary/dictionary.txt");
        Scanner scanin=new Scanner (dict);
        
        Node n=new Node(scanin.nextLine());
        insert(n);
        while(scanin.hasNextLine()){
            //x=scanin.nextLine();
            n=new Node(scanin.nextLine());
            insert(n);
            
        }
        while (true) {
            System.out.println("\n1.Insert item\n"
                    + "2.Delete item\n"
                    + "3.Check item\n"
                    + "4.Print tree\n"
                    + "5.Delete tree\n"
                    + "6.Print Length\n"
                    + "7.End\n");
            int choice = scan.nextInt();

            String item;
            Node node;
            switch (choice) {
                case 1:
                   
                     item = scan.next();
                   
                        node = new Node(item);
                        insert(node);
                        System.out.println("Added word: "+item);
                        System.out.println("Size of Dictionary is : "+count);
                        
                        System.out.println("Height of Tree is : "+treeHeight(root));
                       
                    break;
                case 2:
                    item = scan.next();
                    String items[] = item.split(",");
                    for (int i = 0; i < items.length; i++) {
                        String item1 = items[i];
                        node = new Node(item1);
                        System.out.print("\nDeleting item " + item1);
                        if (delete(node)) {
                            System.out.print(": deleted!");
                        } else {
                            System.out.print(": does not exist!");
                        }
                        
                    }
                        
                   
                    System.out.println("\nSize of Dictionary is : "+count);
                     System.out.println("Height of Tree is : "+treeHeight(root));
                    System.out.println();
                    
    
                    break;
                case 3:
                    item = scan.next();
                    
                        node = new Node(item);
                        System.out.println((findNode(node, root) != null) ? "found" : "not found");
                      
                    
                    break;
                case 4:
                    PrintWriter writer = new PrintWriter("D:/college/spring 2018/Data Structures 2/English Dictionary/dict.txt");
                    writer.print("");
                    writer.close();
                    printTree(root);
                    System.out.println("\nSize of Dictionary is : "+count);
                    System.out.println("Height of Tree is : "+treeHeight(root));
                    break;
                case 5:
                    deleteTree();
                    System.out.println("Tree deleted!");
                    System.out.println("Size of Dictionary is : "+count);
                    break;
                case 6:
                    System.out.println("Size of Dictionary is : "+count);
                     height=(int) (2*(Math.log(count+2)/Math.log(2)));
                        System.out.println("Height of Tree is : "+height);
                    break;
                case 7:
                    System.exit(0);
            }
        }
    }
    public static void main(String[] args) throws FileNotFoundException, IOException {
        RedBlackTree rbt = new RedBlackTree();
        rbt.consoleUI();
    }
}