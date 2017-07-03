package nearestNeigh;

import java.util.ArrayList;

public class PointKDTree
{
	protected ListNode root;
	protected ListNode current;
	protected ListNode previous;
	
	ArrayList<ListNode> checkedNodes = new ArrayList<ListNode>();
	
	public PointKDTree()
	{
		root = null;
		current = null;
		previous = null;
	}
	
	public ListNode getRoot()
	{
		return root;
	}
	
	public ListNode getCurrent()
	{
		return current;
	}
	
	public ListNode getPrevious()
	{
		return previous;
	}
	
	public void uncheck()
	{
		for(int i = 0; i < checkedNodes.size(); i++)
		{
			checkedNodes.get(i).setVisited(false);
		}
	}
}
