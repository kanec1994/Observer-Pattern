package studentCoursesBackup.myTree;

import java.util.ArrayList;

public class Node implements SubjectI, ObserverI, Cloneable
{
    private int bNum, type, color;
    private ArrayList<String> courses;
    private ArrayList<Node> backups;
    private Node left, right, parent;

   /**
    * Constructor for the backup nodes, essentially does the
    * same as the constructor for the orig_node, but without
    * needing to pass in backup nodes. Constructor private
    * in order to have it only be able to be called from
    * the clone method.
    *
    * @param int - the B-Number of the student which the Node 
    *              refers to.  
    * @param int - 0 specifies type as orig_node, 1 as backups.
    * @return - no return type given for the constructor. 
    */
    private Node(int id, int n_type, ArrayList<String> classes)
    {
	bNum = id;
	type = n_type;
	color = 0;
	courses = new ArrayList<String>(classes);
	backups = new ArrayList<Node>();
	left = null;
	right = null;
	parent = null;
    }
	
   /**
    * Constructor for Node class which initializes a student's
    * B-Number and creates a new Arraylist called courses which
    * will store the names of classes the student is taking or
    * has taken.
    *
    * @param int - the B-Number of the student which the Node
    *              refers to.
    * @param int - 0 specifies type as orig_node, 1 as backups.
    * @param Node - backup_node_1
    * @param Node - backup_node_2
    * @return - no return type given for the constructor.
    */
    public Node(int id, int n_type)
    {
	bNum = id;
	type = n_type;
	color = 0;
	courses = new ArrayList<String>();
        backups = new ArrayList<Node>();
	left = null;
	right = null;
	parent = null;
    }

   /**
    * This function inserts a class into the courses ArrayList.
    * If the class already exists in the ArrayList, this function
    * will ignore the value passed and will not insert anything. 
    * Inserts classes in Alphabetical Order. Additionally, if type
    * is 0 (meaning orig_node) then the function will notify 
    * observers of the change.
    *
    * @param String - the class which is to be added to courses.
    * @return void - nothing is returned.
    */
    public void insertClass(String course)
    {
	int i = 0;
        boolean inserted = false;
	for(String s : courses)
	{
	    if(s.compareTo(course) == 1)
	    {
		inserted = true;
	        i = courses.indexOf(s);
	    }
            else if(s.equals(course))
	    {
		return;
	    }
	}

	if(!inserted)
	{
	    courses.add(course);
	}
	else
	{
	    courses.add(i, course);
	}

	if(type == 0)
	{
            notifyAll(course, 0);
	}
    }

   /**
    * This function deletes the class specified in the parameter
    * from the courses ArrayList. If the course does not exist in
    * the ArrayList, then the function will not modify the list in
    * any way and will return. Additionally, if type is 0 (meaning
    * orig_node) then the function will notify observers of the
    * change.
    *
    * @param String - the class which is to be added to courses.
    * @return void - nothing is returned.
    */
    public void deleteClass(String course)
    {
	int i = 0;
        boolean found = false;
	for(String s : courses)
	{
	    if(s.equals(course))
	    {
		found = true;
		i = courses.indexOf(s);
		break;
	    }
	}

	if(found)
	{
	    courses.remove(i);
	}
	
	if(type == 0)
	{
            notifyAll(course, 1);
	}
    }
	
   /**
    * This function implements the notifyObservers function of
    * the SubjectI Interface. The function will call the update
    * function of the ObserverI Interface and pass in the value
    * to insert or delete. This function will be called whenever
    * an update is made to the original node in the tree.
    *
    * @param String - the value inserted or deleted by the orig_node
    *                 and will be passed to backup nodes.
    * @param int - specifies whether an insert or delete is needed.
    * @return void - nothing is returned.
    */
    public void notifyAll(String updateVal, int mode)
    {
        for(Node n : backups)
	{
	    n.update(updateVal, mode);
	}
    }

   /**
    * This function implements the update function of the ObserverI
    * Interface. The function will call insert/delete based off
    * the mode passed to it by the subject.
    *
    * @param String - the value inserted or deleted by the orig_node
    *                 and will be passed to backup nodes.
    * @param int - specifies whether an insert or delete is needed.
    * @return void - nothing is returned   
    */
    public void update(String updateVal, int mode)
    {
	if(mode == 0)
	{
	    insertClass(updateVal);
	}
	else
	{
	    deleteClass(updateVal);
	}
    }

   /**
    * This function implements the clone method of the cloneable
    * interface. The function creates a new Node object to pass
    * back to tree builder.
    */
    public Node clone()
    {
        return new Node(bNum, 1, courses);
    }

   /**
    * This function adds a Node to the orig_node's ArrayList of
    * backups. These Nodes are notified when classes are inserted
    * or deleted from a Node.
    *
    * @param Node - backup node to be inserted into backups ArrayList
    * @return void - nothing is returned 
    */
    public void setBackup(Node n)
    {
	backups.add(n);
    }

   /**
    * This function returns the B-Number of the given student
    *
    * @return int - B-Number of Node
    */
    public int getBNum()
    {
	return bNum;
    }

   /**
    * This function returns the courses ArrayList.
    *
    * @return ArrayList<String> - courses ArrayList
    */
    public ArrayList<String> getCourses()
    {
	return courses;
    }
	
   /**
    * This function returns parent of the Node
    *
    * @return Node - parent Node
    */
    public Node getParent()
    {
	return parent;
    }

   /**
    * This function sets the parent of the Node
    *
    * @param Node - Node to set as parent
    * @return void - nothing returned
    */
    public void setParent(Node n)
    {
        parent = n;
    }

   /**
    * This function returns the left child of the Node
    *
    * @return Node - left child Node
    */
    public Node getLeft()
    {
	return left;
    }

   /**
    * This function sets the left child of the Node
    *
    * @param Node - Node to set as the left child
    * @return void - nothing returned
    */
    public void setLeft(Node n)
    {
	left = n;
    }

   /**
    * This function returns the right child of the Node
    *
    * @return Node - right child Node
    */
    public Node getRight()
    {
	return right;
    }

   /**
    * This function sets the right child of the Node
    *
    * @param Node - Node to set as the right child
    * @return void - nothing returned
    */
    public void setRight(Node n)
    {
	right = n;
    }

   /**
    * This function gets the color of the node
    *
    * @return int - color of the node
    */
    public int getColor()
    {
	return color;
    }

   /**
    * This function sets the color of the node
    *
    * @param int - color to be set to Node
    * @return void - nothing returned
    */
    public void setColor(int c)
    {
	color = c;
    }
}
