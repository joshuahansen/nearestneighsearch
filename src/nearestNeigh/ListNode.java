package nearestNeigh;

public class ListNode
{
	private Point pointData;
	private ListNode leftLink;
	private ListNode rightLink;
	private ListNode previousLink;
	private String dim;
	private boolean visited;

	public ListNode()
	{
		pointData = null;
		leftLink = null;
		rightLink = null;
		previousLink = null;
		dim = null;
		visited = false;
	}

	public ListNode(Point pointData, ListNode previousLink, String dem)
	{
		this.pointData = pointData;
		this.leftLink = null;
		this.rightLink = null;
		this.previousLink = previousLink;
		this.dim = dem;
		visited = false;
	}

	public Point getPointData()
	{
		return pointData;
	}
	
	public ListNode getLeftLink()
	{
		return leftLink;
	}
	
	public ListNode getRightLink()
	{
		return rightLink;
	}
	
	public ListNode getPreviousLink()
	{
		return previousLink;
	}

	public String getDim()
	{
		return dim;
	}

	public boolean getVisited()
	{
		return visited;
	}
	
	public void setVisited(boolean visited)
	{
		this.visited = visited;
	}
	public void setLeftLink(ListNode newNode)
	{
		this.leftLink = newNode;
	}
	public void setRightLink(ListNode newNode)
	{
		this.rightLink = newNode;
	}
}
