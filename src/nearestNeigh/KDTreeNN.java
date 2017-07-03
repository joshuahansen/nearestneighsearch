package nearestNeigh;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is required to be implemented.  Kd-tree implementation.
 *
 * @author Jeffrey, Youhan
 */
	
public class KDTreeNN implements NearestNeigh{

	PointKDTree kdTreeRestaurant = new PointKDTree();
	PointKDTree kdTreeEducation = new PointKDTree();
	PointKDTree kdTreeHospital = new PointKDTree();
	
    @Override
    public void buildIndex(List<Point> points) {
    	char sort = 'x';
    	recursion(points, sort);
    }

    @Override
    public List<Point> search(Point searchTerm, int k) {
        List<Point> kNearestNeigh = new ArrayList<Point>();
        //some code to add points to array
        PointKDTree kdTree = getKDTree(searchTerm);

//        kdTree.current = kdTree.root;
		kdTree.previous = kdTree.root;
		kdTree.current = goToBottom(kdTree, searchTerm, kdTree.root);
		
		kdTree.current = kdTree.previous;
//		System.out.println("\n\n\t\tBottom Node: " + kdTree.current.getPointData());
		kNearestNeigh.add(kdTree.current.getPointData());
		kdTree.current.setVisited(true);

		while(kdTree.current != null)
		{
			//find point farthest from the searchTerm
			Point largestDist = kNearestNeigh.get(0);
			for(int i = 0; i < kNearestNeigh.size(); i++)
			{
				if(kNearestNeigh.get(i).distTo(searchTerm) >largestDist.distTo(searchTerm))
				{
					largestDist = kNearestNeigh.get(i);
				}
			}
			Point lineDist;
			
			if(kdTree.current.getRightLink() != null && kdTree.current.getRightLink().getVisited() == false)
			{
				if(kdTree.current.getDim() == "x")
				{
					lineDist = new Point(null, searchTerm.cat, kdTree.current.getPointData().lat, searchTerm.lon);
				}
				else
				{
					lineDist = new Point(null, searchTerm.cat, searchTerm.lat, kdTree.current.getPointData().lon);
				}
//				System.out.println(lineDist);
				if(searchTerm.distTo(lineDist) < searchTerm.distTo(largestDist))
				{
//					System.out.println("check right subtree");
					kdTree.current = goToBottom(kdTree, searchTerm, kdTree.current.getRightLink());
					kdTree.current = kdTree.previous;
				}
				else
				{
//					System.out.println("Don't check right subtree");
					kdTree.current = kdTree.current.getPreviousLink();
				}				
			}
			else if(kdTree.current.getLeftLink() != null && kdTree.current.getLeftLink().getVisited() == false)
			{
				if(kdTree.current.getDim() == "x")
				{
					lineDist = new Point(null, searchTerm.cat, kdTree.current.getPointData().lat, searchTerm.lon);
				}
				else
				{
					lineDist = new Point(null, searchTerm.cat, searchTerm.lat, kdTree.current.getPointData().lon);
				}
//				System.out.println(lineDist);
				if(searchTerm.distTo(lineDist) < searchTerm.distTo(largestDist))
				{
//					System.out.println("check left subtree");
					kdTree.current = goToBottom(kdTree, searchTerm, kdTree.current.getLeftLink());
					kdTree.current = kdTree.previous;
				}
				else
				{
//					System.out.println("Don't check left subtree");
					kdTree.current = kdTree.current.getPreviousLink();
				}
			}
			else if(kdTree.current.getVisited() == true)
			{
//				System.out.println("node " + kdTree.current.getPointData() + " has been visited");
				kdTree.current = kdTree.current.getPreviousLink();
			}
			else{
//				System.out.println("node " + kdTree.current.getPointData() + " has no children");
				kdTree.current = kdTree.current.getPreviousLink();
			}

			//only add points if the array is less than or equal to k amount of points
			if(kdTree.current == null)
			{
				continue;
			}
			boolean match = false;
			for(int i = 0; i < kNearestNeigh.size(); i++)
			{
				if(kNearestNeigh.get(i).equals(kdTree.current.getPointData()))
				{
//					System.out.println("Point already in array " + kNearestNeigh.get(i));
					match = true;
				}
			}
			
			if(match != true)
			{
				if(kNearestNeigh.size() < k)
				{
					kNearestNeigh.add(kdTree.current.getPointData());
//					System.out.println("\tAdd " + kdTree.current.getPointData());
				}
				else
				{
					for(int i = 0; i < k; i++)
					{
//						System.out.println(kdTree.current.getPointData().distTo(searchTerm) < kNearestNeigh.get(i).distTo(searchTerm));
						if(kNearestNeigh.get(i).equals(largestDist) &&	kdTree.current.getPointData().distTo(searchTerm) < kNearestNeigh.get(i).distTo(searchTerm))
						{
//							System.out.println("\tremove " + kNearestNeigh.get(i));
							kNearestNeigh.remove(i);
//							System.out.println("\tAdd " + kdTree.current.getPointData());
							kNearestNeigh.add(kdTree.current.getPointData());
							
						}
					}
				}
			}
			kdTree.current.setVisited(true);
		}		
		
        SortPoints sorter = new SortPoints();
        sorter.sortDist(kNearestNeigh, searchTerm);
        
        //uncheck all nodes
        recursionResetVisited(kdTree.root); 
        
        return kNearestNeigh;
    }

    @Override
    public boolean addPoint(Point point) {

    	PointKDTree kdTree = getKDTree(point);
	if(isPointIn(point))
	{
		return false;
	}    	
		if(kdTree.root == null)
		{
//			System.out.println("set as root " + point);
			ListNode newNode = new ListNode(point, null, "x");
			kdTree.root = newNode;
		}
		else
		{
			kdTree.previous = kdTree.root;
			
			kdTree.current = goToBottom(kdTree, point, kdTree.root);

			String dim;
			if(kdTree.previous.getDim().equals("x"))
			{
				dim = "y";
			}
			else
			{
				dim = "x";
			}
			ListNode newNode = new ListNode(point, kdTree.previous, dim);
			
			if(dim.equals("y"))
			{
				if(newNode.getPointData().lat <= kdTree.previous.getPointData().lat)
				{
//					System.out.println("set " + newNode.getPointData() +" node to left of " + kdTree.previous.getPointData());
					kdTree.previous.setLeftLink(newNode);
				}
				else if (newNode.getPointData().lat > kdTree.previous.getPointData().lat){
//					System.out.println("set " + newNode.getPointData() +" node to right of " + kdTree.previous.getPointData());
						kdTree.previous.setRightLink(newNode);
				}
				else if (newNode.getPointData().lat == kdTree.previous.getPointData().lat && newNode.getPointData().lon == kdTree.previous.getPointData().lon)
				{
//					System.out.println("\n\nAlready in Tree\n\n");
					return false;
				}
			}
			else
			{
				if(newNode.getPointData().lon <= kdTree.previous.getPointData().lon)
				{
//					System.out.println("set " + newNode.getPointData() + " node to left of " + kdTree.previous.getPointData());
					kdTree.previous.setLeftLink(newNode);
				}
				else if(newNode.getPointData().lon > kdTree.previous.getPointData().lon)
				{
//					System.out.println("set " + newNode.getPointData() + " node to right of " + kdTree.previous.getPointData());
						kdTree.previous.setRightLink(newNode); 
				}
			}
		}
		return true;
    }

    @Override
    public boolean deletePoint(Point point) {
       
    	PointKDTree kdTree = getKDTree(point);
    	
    	if(isPointIn(point))
        {
	        ListNode deleteNode = kdTree.current;
	        ListNode leftSubTree = kdTree.current.getLeftLink();
	        ListNode rightSubTree = kdTree.current.getRightLink();
	        /*
	         * if the current node does not have any children, leftLink and rightLink are both null
	         * then set the link from the previous node to null. The node that is deleted is no longer referenced so 
	         * is considered deleted
	         */
	        if(leftSubTree == null && rightSubTree == null)
	        {
//	        	System.out.println("Node has no children");
	        	if(deleteNode.getPreviousLink().getDim().equals("x"))
	        	{
	        		if(deleteNode.getPointData().lat <= deleteNode.getPreviousLink().getPointData().lat)
	        		{
	        			deleteNode.getPreviousLink().setLeftLink(null);
	        		}
	        		else
	        		{
	        			deleteNode.getPreviousLink().setRightLink(null);
	        		}
	        	}
	        	else
	        	{
	        		if(deleteNode.getPointData().lon <= deleteNode.getPreviousLink().getPointData().lon)
	        		{
	        			deleteNode.getPreviousLink().setLeftLink(null);
	        		}
	        		else
	        		{
	        			deleteNode.getPreviousLink().setRightLink(null);
	        		}
	        	}
	        	return true;
	        }
	        /*
	         * if the node to delete has one or 2 children then re-populate the tree from the deleted node using the
	         * sub-tree  
	         */
	        //array for subtree
	        List<Point> subtree = new ArrayList<Point>();
	        getSubtreeRecursion(deleteNode, subtree);
	        subtree.remove(0);
//	        for(int i = 0; i < subtree.size(); i++)
//	        {
//	        	System.out.println("(" + subtree.get(i).lat + "," + subtree.get(i).lon + ")");
//	        }
	        if(deleteNode.getPreviousLink().getDim().equals("x"))
        	{
        		if(deleteNode.getPointData().lat <= deleteNode.getPreviousLink().getPointData().lat)
        		{
        			deleteNode.getPreviousLink().setLeftLink(null);
        		}
        		else
        		{
        			deleteNode.getPreviousLink().setRightLink(null);
        		}
        	}
        	else
        	{
        		if(deleteNode.getPointData().lon <= deleteNode.getPreviousLink().getPointData().lon)
        		{
        			deleteNode.getPreviousLink().setLeftLink(null);
        		}
        		else
        		{
        			deleteNode.getPreviousLink().setRightLink(null);
        		}
        	}
	        recursion(subtree, 'x');
	        return true;
        }
        return false;
    }

    @Override
    public boolean isPointIn(Point point) {
//    	 System.out.println("\n\nSearching for " + point + "\n\n");
     	PointKDTree kdTree = getKDTree(point);
     	
 	 	boolean match = false;
 		if(kdTree.root == null)
 		{
// 			System.out.println("Tree is empty");
 		}
 		else
 		{
 			kdTree.current = kdTree.root;
 			kdTree.previous = kdTree.root;
 			while(kdTree.current != null)
 			{
 				if(kdTree.current.getDim().equals("x") == true)
 				{
// 					System.out.println("Split x dim");
 					if (point.lat == kdTree.current.getPointData().lat && point.lon == kdTree.current.getPointData().lon)
 					{
// 						System.out.println("Compare " + point.lat + " with " + kdTree.current.getPointData().lat);
// 						System.out.println("Compare " + point.lon + " with " + kdTree.current.getPointData().lon);
// 						System.out.println("\n\nMarch found\n\n");
 						match = true;
 						return match;
 					}	
 					else if(kdTree.current.getPointData().lat >= point.lat)
 					{
// 						System.out.println("Compare " + point.lat + " with " + kdTree.current.getPointData().lat);
 						kdTree.previous = kdTree.current;
 						kdTree.current = kdTree.current.getLeftLink();
// 						System.out.println("get left link");
 					}
 					else if(kdTree.current.getPointData().lat < point.lat)
 					{
// 						System.out.println("Compare " + point.lat + " with " + kdTree.current.getPointData().lat);
 						kdTree.previous = kdTree.current;
 						kdTree.current = kdTree.current.getRightLink();
// 						System.out.println("get right link");
 					}
 				}
 				else
 				{
// 					System.out.println("Split y dim");
 					if (point.lat == kdTree.current.getPointData().lat && point.lon == kdTree.current.getPointData().lon)
 					{
// 						System.out.println("Compare " + point.lat + " with " + kdTree.current.getPointData().lat);
// 						System.out.println("Compare " + point.lon + " with " + kdTree.current.getPointData().lon);
// 						System.out.println("\n\nMatch found\n\n");
 						match = true;
 						return match;
 					}
 					else if(kdTree.current.getPointData().lon >= point.lon)
 					{
// 						System.out.println("Compare " + point.lon + " with " + kdTree.current.getPointData().lon);
 						kdTree.previous = kdTree.current;
 						kdTree.current = kdTree.current.getLeftLink();
// 						System.out.println("get left link");
 					}
 					else if(kdTree.current.getPointData().lon < point.lon)
 					{
// 						System.out.println("Compare " + point.lon + " with " + kdTree.current.getPointData().lon);
 						kdTree.previous = kdTree.current;
 						kdTree.current = kdTree.current.getRightLink();
// 						System.out.println("get right link");
 					}
 				}
 			}
 		}
// 		System.out.println(point + " is not in the tree");
 		return match;
    }

    private void recursion(List<Point> points, char sort)
    {
    	SortPoints sorter = new SortPoints();
    	if(points.size() == 0)
    	{
//    		System.out.println("Array of 0");
    		return;
    	}
    	else if(points.size() == 1)
    	{
//    		System.out.println("Array of 1");
    		addPoint(points.get(0));
    	}
    	else
    	{
    		if(sort == 'x')
    		{
    			sorter.sortLat(points);
    			sort = 'y';
    		}
    		else
    		{
    			sorter.sortLon(points);
    			sort = 'x';
    		}

	    	int median = (points.size()/2);
	    	if(points.size() % 2 == 0)
	    	{
	    		median = median - 1;
	    	}
//	    	System.out.println("Median " + median);
	    	addPoint(points.get(median));
	    	List<Point> leftSubTree = new ArrayList<Point>();
//    		System.out.println("\n\nLEFTSUB TREE\n\n");
	    	for(int i = 0; i < median; i++)
	    	{
//	    		System.out.println("(" + points.get(i).lat + ", " + points.get(i).lon + ")");
	    		leftSubTree.add(points.get(i));
	    	}
	    	
	    	List<Point> rightSubTree = new ArrayList<Point>();
//	    	System.out.println("\n\nRIGHTSUB TREE\n\n");
	    	for(int i = median + 1; i < points.size(); i++)
	    	{
//	    		System.out.println("(" + points.get(i).lat + ", " + points.get(i).lon + ")");
	    		rightSubTree.add(points.get(i));
	    	}
	    	recursion(leftSubTree, sort);
	    	recursion(rightSubTree, sort);
    	}
    }

    private void getSubtreeRecursion(ListNode current, List<Point> subtree)
    {
    	if(current == null)
    	{
    		return;
    	}
    	
    	subtree.add(current.getPointData());
    	
    	getSubtreeRecursion(current.getLeftLink(), subtree);
    	getSubtreeRecursion(current.getRightLink(), subtree);
    }

    private PointKDTree getKDTree(Point point)
    {
    	if(point.cat == Category.RESTAURANT)
    	{
    		return kdTreeRestaurant;
    	}
    	else if( point.cat == Category.EDUCATION)
    	{
    		return kdTreeEducation;
    	}
    	else
    	{
    		return kdTreeHospital;
    	}
    }

    private ListNode goToBottom(PointKDTree kdTree, Point searchTerm, ListNode currentStart)
    {
    	kdTree.current = currentStart;
    	while(kdTree.current != null)
		{
			if(kdTree.current.getDim().equals("x") == true)
			{
//				System.out.println("Split x dim");
							
				if(kdTree.current.getPointData().lat >= searchTerm.lat)
				{
//					System.out.println("Compare " + searchTerm.lat + " with " + kdTree.current.getPointData().lat);
					kdTree.previous = kdTree.current;
					kdTree.current = kdTree.current.getLeftLink();
//					System.out.println("get left link");
				}
				else if(kdTree.current.getPointData().lat < searchTerm.lat)
				{
//					System.out.println("Compare " + searchTerm.lat + " with " + kdTree.current.getPointData().lat);
					kdTree.previous = kdTree.current;
					kdTree.current = kdTree.current.getRightLink();
//					System.out.println("get right link");
				}
			}
			else
			{
//				System.out.println("Split y dim");
				
				if(kdTree.current.getPointData().lon >= searchTerm.lon)
				{
//					System.out.println("Compare " + searchTerm.lon + " with " + kdTree.current.getPointData().lon);
					kdTree.previous = kdTree.current;
					kdTree.current = kdTree.current.getLeftLink();
//					System.out.println("get left link");
				}
				else if(kdTree.current.getPointData().lon < searchTerm.lon)
				{
//					System.out.println("Compare " + searchTerm.lon + " with " + kdTree.current.getPointData().lon);
					kdTree.previous = kdTree.current;
					kdTree.current = kdTree.current.getRightLink();
//					System.out.println("get right link");
				}
			}
		}
    	return kdTree.current;
    }

	private void recursionResetVisited(ListNode current)
	{
		if(current == null)
		{
			return;
		}
		current.setVisited(false);

		recursionResetVisited(current.getLeftLink());
		recursionResetVisited(current.getRightLink());
	}
}
