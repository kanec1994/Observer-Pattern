package studentCoursesBackup.util;

import studentCoursesBackup.myTree.Node;
import java.util.ArrayList;
import java.lang.NumberFormatException;

public class TreeBuilder
{
    private Node root, root_backup_1, root_backup_2;

   /**
    * Constructor for BST used to store Students
    * bNumbers and Courses. Root is initialized to null.
    *
    * @return - nothing returned in constructor
    */
    public TreeBuilder()
    {
	root = null;
	root_backup_1 = null;
	root_backup_2 = null;
    }

   /**             
    * This function creates a node and inserts that node
    * and it's backups into their respective trees if the node
    * is not found in the tree. Otherwise inserts the course. 
    *                                                                                   
    * @param int - BNum of Node to be found in tree.                                              
    */
    public void insert(String bn, String course)
    {
	int bNum = 0;	
	try
	{
	    bNum = Integer.parseInt(bn);
	}
	catch(NumberFormatException e)
	{
            e.printStackTrace();
	} 

        Node n = findNode(root, bNum);
	if(n != null)
	{
	    n.insertClass(course);
	}
	else
	{
	    Node ins = new Node(bNum, 0);
	    ins.insertClass(course);
	    
	    Node ins_b1 = ins.clone();
	    Node ins_b2 = ins.clone();

	    ins.setBackup(ins_b1);
	    ins.setBackup(ins_b2);
	    
	    insertNode(root, root_backup_1, root_backup_2, ins, ins_b1, ins_b2);
	}
    }
	
   /**
    * This function creates a node and inserts that node
    * and it's backups into their respective trees.
    *
    * @param Node - current Node                                                     
    * @param Node - node to be inserted into the tree.
    */
    private void insertNode(Node curr, Node curr_b1, Node curr_b2, Node ins, Node ins_b1, Node ins_b2)
    {
	if(root == null)
	{
	    root = ins;
	    root_backup_1 = ins_b1;
	    root_backup_2 = ins_b2;
	}
	else if((ins.getBNum() < curr.getBNum()) && (curr.getLeft() != null))
	{
	    insertNode(curr.getLeft(), curr_b1.getLeft(), curr_b2.getLeft(), ins, ins_b1, ins_b2);
	}
	else if((ins.getBNum() > curr.getBNum()) && (curr.getRight() != null))
	{
	    insertNode(curr.getRight(), curr_b1.getRight(), curr_b2.getRight(), ins, ins_b1, ins_b2);
	}
	else if((ins.getBNum() < curr.getBNum()) && (curr.getLeft() == null))
	{
	    curr.setLeft(ins);
	    curr_b1.setLeft(ins_b1);
	    curr_b2.setLeft(ins_b2);
	    
	    ins.setParent(curr);
	    ins_b1.setParent(curr_b1);
	    ins_b2.setParent(curr_b2);
	}
	else if((ins.getBNum() > curr.getBNum()) && (curr.getRight() == null))
	{
	    curr.setRight(ins);
	    curr_b1.setRight(ins_b1);
	    curr_b2.setRight(ins_b2);

	    ins.setParent(curr);
	    ins_b1.setParent(curr_b1);
	    ins_b2.setParent(curr_b2);
	}
	balanceTree(ins, root, 0);
	balanceTree(ins_b1, root_backup_1, 1);
	balanceTree(ins_b2, root_backup_2, 2);
    }	

   /**
    * This function rebalances the tree after insertion.
    * Note: 0 is red, 1 is black
    *
    * NOTE: Obtained and adapted algorithm for balanced insertion from:
    * www.geeksforgeeks.org/red-black-tree-set-2-insert/
    *
    * @param Node - the node just inserted
    */
    private void balanceTree(Node curr, Node curr_root, int root_type)
    {
	if(curr.getBNum() == curr_root.getBNum())
	{
	    curr.setColor(1);
	    if(root_type == 0)
	    {
		root.setColor(1);
	    }
	    else if(root_type == 1)
	    {
		root_backup_1.setColor(1);
	    }
	    else
	    {
	        root_backup_2.setColor(1);
	    }
	}
	else if(curr.getParent().getColor() == 0)
	{
	    Node parent, grandParent, uncle;
	    parent = curr.getParent();
	    grandParent = parent.getParent();
	    if((grandParent.getLeft() != null) && (grandParent.getLeft().getBNum() == parent.getBNum()))
	    {
		uncle = grandParent.getRight();
	    }
	    else
	    {
		uncle = grandParent.getLeft();
	    }

	    if(uncle == null || uncle.getColor() == 1)
	    {
	        if(parent.getLeft() != null)
		{
		    if((grandParent.getLeft() != null) && (grandParent.getLeft().getBNum() == parent.getBNum()) && (parent.getLeft().getBNum() == curr.getBNum()))
		    {
		        rotateRight(grandParent, curr_root, root_type);
		        grandParent.setColor(0);
		        parent.setColor(1);
		    }
		    else if((grandParent.getRight() != null) && (grandParent.getRight().getBNum() == parent.getBNum()) && (parent.getLeft().getBNum() == curr.getBNum()))
		    {
			rotateRight(parent, curr_root, root_type);
		        rotateLeft(grandParent, curr_root, root_type);
		        grandParent.setColor(0);
		        curr.setColor(1);
		    }
		}

		if(parent.getRight() != null)
		{
		    if((grandParent.getLeft() != null) && (grandParent.getLeft().getBNum() == parent.getBNum()) && (parent.getRight().getBNum() == curr.getBNum()))
		    {
			rotateLeft(parent, curr_root, root_type);
		        rotateRight(grandParent, curr_root, root_type);
		        grandParent.setColor(0);
		        curr.setColor(1);
		    }
		    else if((grandParent.getRight() != null) && (grandParent.getRight().getBNum() == parent.getBNum()) && (parent.getRight().getBNum() == curr.getBNum()))
		    {
			rotateLeft(grandParent, curr_root, root_type);
		        grandParent.setColor(0);
		        parent.setColor(1);
		    }
		}
	    }
	    else
	    {
		uncle.setColor(1);
		parent.setColor(1);
		grandParent.setColor(0);
		balanceTree(grandParent, curr_root, root_type);
	    }
	}
    }

   /**
    * Perform Left Rotation on Tree.
    *
    * NOTE: Adapted rotation algorithm from:
    * www.geeksforgeeks.org/c-program-red-black-tree-insertion/
    *
    * @param Node - current node
    * @param Node - current node's child
    * @return void - nothing returned
    */
    private void rotateLeft(Node rotate_on, Node curr_root, int root_type)
    {
	Node rotate_child = rotate_on.getRight();
	rotate_on.setRight(rotate_child.getLeft());

        if(rotate_on.getRight() != null)
	{
	    rotate_on.getRight().setParent(rotate_on);
	}

	rotate_child.setParent(rotate_on.getParent());

	if(rotate_on.getParent() == null)
	{
	    if(root_type == 0)
	    {
		root = rotate_child;
	    }
	    else if(root_type == 1)
	    {
		root_backup_1 = rotate_child;
	    }
	    else
	    {
		root_backup_2 = rotate_child;
	    }	
	}
        else if(rotate_on.getParent().getLeft().getBNum() == rotate_on.getBNum())
	{
            rotate_on.getParent().setLeft(rotate_child);
	}
	else
	{
            rotate_on.getParent().setRight(rotate_child);
	}
	
	rotate_child.setLeft(rotate_on);
	rotate_on.setParent(rotate_child);
    }

   /**
    * Perform Right Rotation on Tree.                                                                                                                                                                   
    *           
    * NOTE: Adapted rotation algorithm from:
    * www.geeksforgeeks.org/c-program-red-black-tree-insertion/
    *                                                                                                                                            
    * @param Node - current node                                                                                                                                                                        
    * @param Node - current node's child 
    * @return void - nothing returned                                                                                                                                                                    
    */
    private void rotateRight(Node rotate_on, Node curr_root, int root_type)
    {
	Node rotate_child = rotate_on.getLeft();
	rotate_on.setLeft(rotate_child.getRight());

        if(rotate_on.getLeft() != null)
	{
	    rotate_on.getLeft().setParent(rotate_on);
	}

	rotate_child.setParent(rotate_on.getParent());

	if(rotate_on.getParent() == null)
	{
	    if(root_type == 0)
	    {
		root = rotate_child;
	    }
	    else if(root_type == 1)
	    {
		root_backup_1 = rotate_child;
	    }
	    else
	    {
		root_backup_2 = rotate_child;
	    }	
	}
        else if(rotate_on.getParent().getLeft().getBNum() == rotate_on.getBNum())
	{
            rotate_on.getParent().setLeft(rotate_child);
	}
	else
	{
            rotate_on.getParent().setRight(rotate_child);
	}
	
	rotate_child.setRight(rotate_on);
	rotate_on.setParent(rotate_child);	
    }
       
   /**
    * This function reads in the B-number of a student, finds
    * the node in the tree, and deletes the class specified
    * if the node exists.
    *
    * @param int - B-Number of the student
    * @param String - course to be deleted
    */
    public void delete(String bn, String course)
    {
	int bNum = 0;	
	try
	{
	    bNum = Integer.parseInt(bn);
	}
	catch(NumberFormatException e)
	{
            e.printStackTrace();
	}        
	
	Node n = findNode(root, bNum);
	if(n != null)
	{
	    n.deleteClass(course);
	}
    }
	
   /**
    * This function will search for a BNum in the tree
    * to see if it exists before creating a new Node.
    * This function is O(logn) since the tree is balanced.
    *
    * @param Node - current Node
    * @param int - BNum
    * @return Node - Node with BNum, null if none
    */
    private Node findNode(Node curr, int bNum)
    {
	if(curr == null)
	{
	    return null;
	}
	else if(bNum < curr.getBNum())
	{
	    return findNode(curr.getLeft(), bNum);
	}
	else if(bNum > curr.getBNum())
	{
	    return findNode(curr.getRight(), bNum);
	}
	else
	{
	    return curr;
	}
    }

   /**
    * This function will call inOrder to obtain an
    * ArrayList of Strings each String containing a 
    * Student id and the classes they are taking. Then
    * the function will return it to the calling function
    *
    * @param int - which tree to get results from
    * @return ArrayList<String> - ArrayList of Result Strings
    */
    public void printNodes(Results r, int tree)
    {
	if(tree == 0)
	{
	    r.storeNewResult(inOrder(root));
	}
	else if(tree == 1)
	{
	    r.storeNewResult(inOrder(root_backup_1));
	}
	else
	{
	    r.storeNewResult(inOrder(root_backup_2));	
	}
    }

   /**
    * This function will create an ArrayList of Strings from
    * whichever Tree is specified by the caller. 
    *
    * @param Node - which root to start from
    * @return ArrayList<String> - ArrayList of Result Strings
    */
    private ArrayList<String> inOrder(Node curr)
    {
	ArrayList inOrderList = new ArrayList<String>();	
	if(curr != null)
	{
	    inOrderList.addAll(inOrder(curr.getLeft()));
	    String entry = curr.getBNum() + ":";
	    ArrayList<String> courses = curr.getCourses();
	    for(int i=0; i<courses.size(); i++)
	    {
		 entry += " " + courses.get(i);
	    }
	    inOrderList.add(entry);
	    inOrderList.addAll(inOrder(curr.getRight()));
	}
	return inOrderList;
    }
}
