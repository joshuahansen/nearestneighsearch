   package nearestNeigh;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Tester {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		List<Point> points = new ArrayList<Point>();
		
		List<Point> points2 = new ArrayList<Point>();

		
		
		System.out.println("\n\nNew Tree Assignemnt 1\n\n");
		
		KDTreeNN assignment = new KDTreeNN();
		List<Point> kPoints = new ArrayList<Point>();
		
		SortPoints sorter = new SortPoints();
		
		Point newPoint = new Point(null, Category.RESTAURANT, 8,9);
		points.add(newPoint);
		newPoint = new Point(null, Category.RESTAURANT, 4,6);
		points.add(newPoint);
		newPoint = new Point(null, Category.RESTAURANT, 14,13);
		points.add(newPoint);
		newPoint = new Point(null, Category.RESTAURANT, 3,15);
		points.add(newPoint);
		newPoint = new Point(null, Category.RESTAURANT, 3,2);
		points.add(newPoint);
		newPoint = new Point(null, Category.RESTAURANT, 10,18);
		points.add(newPoint);
		newPoint = new Point(null, Category.RESTAURANT, 11,10);
		points.add(newPoint);
		newPoint = new Point(null, Category.RESTAURANT, 6,4);
		points.add(newPoint);
		newPoint = new Point(null, Category.RESTAURANT, 16,8);
		points.add(newPoint);
		
		sorter.sortLat(points);
		System.out.println("\nSorted Points\n");
		for(int i = 0; i < points.size(); i++)
		{
			System.out.println("(" + points.get(i).lat + "," + points.get(i).lon + ")");
		}
		assignment.buildIndex(points);
//		newPoint = new Point(null, null, 5,15);
//		if(assignment.isPointIn(newPoint))
//		{
//			System.out.println(newPoint + " is in the tree");
//		}
//		
//		if(assignment.deletePoint(newPoint))
//		{
//			System.out.println("Point " + newPoint + " was deleted");
//			if(assignment.isPointIn(newPoint))
//			{
//				System.out.println(newPoint + " is in the tree");
//			}
//		}
//		else
//		{
//			System.out.println("Point " + newPoint + " was not deleted");
//		}
//		
		
//		Point searchTerm = new Point(null, null, 16,13);
//		
//		sorter.sortDist(points, searchTerm);
//		
//		System.out.println("\n\nOrder points closest to " + searchTerm + "\n\n");
//		for(int i = 0; i < points.size(); i++)
//		{
//			System.out.println(points.get(i) + " " + points.get(i).distTo(searchTerm));
//		}
//		int k = 3;
//		sorter.kNearestNeigh(points, k);
//		
//		System.out.println("\n\nOrder points closest to " + searchTerm + "for " + k + " items\n\n");
//		for(int i = 0; i < points.size(); i++)
//		{
//			System.out.println(points.get(i) + " " + points.get(i).distTo(searchTerm));
//		}

//		System.out.println("\n\nNew Tree Example\n\n");
//		
//		newPoint = new Point(null, null, 6, 11);
//		points2.add(newPoint);
//		newPoint = new Point(null, null, 8, 13);
//		points2.add(newPoint);
//		newPoint = new Point(null, null, 10, 15);
//		points2.add(newPoint);
//		newPoint = new Point(null, null, 12, 18);
//		points2.add(newPoint);
//		newPoint = new Point(null, null, 9, 12);
//		points2.add(newPoint);
//		newPoint = new Point(null, null, 8, 7);
//		points2.add(newPoint);
//		newPoint = new Point(null, null, 11, 8);
//		points2.add(newPoint);
//		newPoint = new Point(null, null, 9, 3);
//		points2.add(newPoint);
//		newPoint = new Point(null, null, 12, 7);
//		points2.add(newPoint);
//		newPoint = new Point(null, null, 15, 9);
//		points2.add(newPoint);
//		newPoint = new Point(null, null, 16, 12);
//		points2.add(newPoint);
//		
//		sorter.sortLat(points2);
//		System.out.println("\nSorted Points\n");
//		for(int i = 0; i < points2.size(); i++)
//		{
//			System.out.println("(" + points2.get(i).lat + "," + points2.get(i).lon + ")");
//		}
//		example.buildIndex(points2);
		
		newPoint = new Point(null, Category.RESTAURANT, 5,5);
		if(assignment.isPointIn(newPoint))
		{
			System.out.println(newPoint + " is in the tree");
		}
		
//		System.out.println("\n\n\t\tDelete Node\n\n");
//		if(assignment.deletePoint(newPoint))
//		{
//			System.out.println("Point " + newPoint + " was deleted");
//			if(assignment.isPointIn(newPoint))
//			{
//				System.out.println(newPoint + " is in the tree");
//			}
//		}
//		else
//		{
//			System.out.println("Point " + newPoint + " was not deleted");
//		}
		int k = 2;
		kPoints = assignment.search(newPoint, k);
		
		System.out.println("\n\n\t\t" + k + " closest points");
		for(int i = 0; i < kPoints.size(); i++)
		{
			System.out.println(kPoints.get(i).toString());
			System.out.println("Distance: " + kPoints.get(i).distTo(newPoint));
		}
	}
}

