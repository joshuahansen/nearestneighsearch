package nearestNeigh;

//import java.util.ArrayList;
import java.util.List;

public class SortPoints {
    
	public void sortLat(List<Point> points) 
	{

        boolean swapped = false;
        int remainingLen = points.size();

        do {
            swapped = false;
            for (int i = 0; i < remainingLen - 1; i++) 
            {
                // check if we need to swap
                if (points.get(i).lat > points.get(i+1).lat) 
                {
                    Point temp = points.get(i);
                    points.set(i, points.get(i+1));
                    points.set(i+1, temp);
                    swapped = true;
                }
            }

            remainingLen--;

        } while (swapped);

    }

	public void sortLon(List<Point> points) 
	{

        boolean swapped = false;
        int remainingLen = points.size();

        do {
            swapped = false;
            for (int i = 0; i < remainingLen - 1; i++) 
            {
            // check if we need to swap
            	if(points.get(i).lon > points.get(i+1).lon)
            	{
                    Point temp = points.get(i);
                    points.set(i, points.get(i+1));
                    points.set(i+1, temp);
                    swapped = true;
            	}
            }

            remainingLen--;

        } while (swapped);

    }
	
	public void sortDist(List<Point> points, Point searchTerm) 
	{

        boolean swapped = false;
        int remainingLen = points.size();

        do {
            swapped = false;
            for (int i = 0; i < remainingLen - 1; i++) 
            {
                // check if we need to swap
                if (points.get(i).distTo(searchTerm) > points.get(i+1).distTo(searchTerm)) 
                {
                    Point temp = points.get(i);
                    points.set(i, points.get(i+1));
                    points.set(i+1, temp);
                    swapped = true;
                }
            }

            remainingLen--;

        } while (swapped);

    }
}
