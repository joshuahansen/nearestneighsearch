package nearestNeigh;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is required to be implemented.  Naive approach implementation.
 *
 * @author Jeffrey, Youhan
 */
public class NaiveNN implements NearestNeigh
{
	List<Point> index;// = new ArrayList<Point>();
	
	public NaiveNN()
	{	
		this.index = null;
	}
	
    @Override
    public void buildIndex(List<Point> points) 
    {
	index = new ArrayList<Point>(points);	
    }
    
    @Override
    public List<Point> search(Point searchTerm, int k) 
    {
        // To be implemented.
    	// Check if searchTerm matches a point in the index.
    		// If so, add searchTerm to nearestNeighbours and increment kCounter.
    	// Calculate the distance between searchTerm and all points in the index.
    	// Store k smallest distances in nearestNeighbours and return nearestNeighbours.
    	
    	ArrayList<Point> nearestNeighbours = new ArrayList<Point>();
    	ArrayList<Distance> distances = new ArrayList<Distance>();
    	
    	int numberOfPoints = (this.index).size();
    	int numberOfNeighbours = distances.size();
    	int counter = 0;
    	int kCounter = 0;
    	
    	for (counter = 0; counter < numberOfPoints; counter++)
		{
    		if (searchTerm.cat == ((this.index).get(counter)).cat)
    		{
    			double distanceToPoint = searchTerm.distTo((this.index).get(counter));
    			
    			Distance distance = new Distance(distanceToPoint, (this.index).get(counter));
    			
    			distances.add(distance);
    		}
    		else
    		{
    			continue;
    		}
		}
    	
    	numberOfNeighbours = distances.size();
    	
    	if (numberOfNeighbours > k)
    	{
    		while (kCounter < k)
        	{
        		// Loop through the distances array list and store the point with the
        		// smallest distance in nearestNeighbours. Repeat until kCounter = k
        		// i.e when nearestNeighbours has k elements.
        		
        		Distance smallestDistance = new Distance();
        		
        		smallestDistance = smallestDistance.getSmallestDistance(distances);
        		
        		nearestNeighbours.add(smallestDistance.getPoint());
        		
        		kCounter++;
        	}
    	}
    	else
    	{
    		// There are not enough points of type cat in the index for k neighbours.
    		// Simply store all distances and return nearestNeighbours.
    		
    		int numberOfDistances = distances.size();
    		
    		for (counter = 0; counter < numberOfDistances; counter++)
    		{
    			nearestNeighbours.add((distances.get(counter)).getPoint());
    			
    			(distances.get(counter)).added = true;
    		}
    	}
    	
        return nearestNeighbours;
    }

    @Override
    public boolean addPoint(Point point) 
    {
    	// Check if given point exists in index.
    		// If so, do nothing and return false.
    		// If not, add point to index and return true.
    	
    	boolean pointFound = this.isPointIn(point);
    	
    	if (pointFound == true)
    	{
    		return false;
    	}
    	else
    	{
    		this.index.add(point);
    		
    		return true;
    	}
    }

    @Override
    public boolean deletePoint(Point point) 
    {
    	// Call isPointIn on given point.
	    	// If isPointIn returns true, delete point and return true.
	    	// If isPointIn returns false, do nothing and return false.
    	
    	boolean pointFound = this.isPointIn(point);
    	
    	if (pointFound == true)
    	{
    		(this.index).remove(point);
    		
    		return true;
    	}
    	else
    	{
    		return false;
    	}
    }

    @Override
    public boolean isPointIn(Point point) 
    {
    	// Check if given point exists in index.
			// If so, return true.
			// If not, return false.
		
	if(this.index != null)
	{    	
    		int numberOfPoints = (this.index).size();
    		
    		int counter = 0;
    		
    		for (counter = 0; counter < numberOfPoints; counter++)
    		{
    			if (point.equals((this.index).get(counter)))
    			{
    				return true;
    			}
    		}
    	}
        return false;
    }
}
