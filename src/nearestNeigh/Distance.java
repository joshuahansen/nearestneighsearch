package nearestNeigh;

import java.util.ArrayList;

public class Distance 
{
	public double distance = 0;
	public Point point = null;
	public boolean added = false;
	
	public Distance()
	{
		this.distance = 0;
		this.point = null;
		this.added = false;
	}
	
	public Distance(double distance, Point point)
	{
		this.distance = distance;
		this.point = point;
		this.added = false;
	}
	
	public double getDistance()
	{
		return this.distance;
	}
	
	public void setDistance(double distance)
	{
		this.distance = distance;
	}
	
	public Point getPoint()
	{
		return this.point;
	}
	
	public void setPoint(Point point)
	{
		this.point = point;
	}
	
	public boolean getAdded()
	{
		return this.added;
	}
	
	public void setAdded(boolean added)
	{
		this.added = added;
	}
	
	public Distance getSmallestDistance(ArrayList<Distance> distances)
	{
		int numberOfDistances = distances.size();
		int smallestDistanceIndex = 0;
		int counter = 0;
		
		Distance smallestDistance = distances.get(0);
		
		for (counter = 0; counter < numberOfDistances; counter ++)
		{
			if ((distances.get(counter)).added == false)
			{
				if ((distances.get(counter)).distance < smallestDistance.distance)
				{
					smallestDistance = distances.get(counter);
					
					smallestDistanceIndex = counter;
				}
				else
				{
					continue;
				}
			}
			else
			{
				continue;
			}
		}
		
		(distances.get(smallestDistanceIndex)).added = true;
		
		return smallestDistance;
	}
}
