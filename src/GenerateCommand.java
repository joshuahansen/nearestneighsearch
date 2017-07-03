
import java.io.*;
import java.util.*;
import nearestNeigh.*;

public class GenerateCommand {

	public static void main(String[] args)
	{
		if(args.length != 4)
		{
			System.out.println("Incorrect number of arguments.");
			System.exit(1);
		}
		
		String commandType = args[0];
		String inputFileName = args[1];
		List<Point> points = new ArrayList<Point>();
		try {
			File inputFile = new File(inputFileName);
			Scanner scanner = new Scanner(inputFile);
			while(scanner.hasNext())
			{
				String id = scanner.next();
				Category cat = Point.parseCat(scanner.next());
				Point point = new Point(id, cat, scanner.nextDouble(), scanner.nextDouble());
				points.add(point);
			}
			scanner.close();
		}catch (FileNotFoundException e)
		{
			System.out.println("Data File not found");
			System.exit(1);
		}
		
		String outputFileName = args[2];
		Random rand = new Random();
		double minLat = -40, maxLat = -34, latRange = maxLat - minLat;
		double minLon = 140, maxLon = 150, lonRange  = maxLon - minLon;
		double latScaled, latShifted, lonScaled, lonShifted;
		String id;
		int idCount = 1241;
		String cat;
		File outputFile = new File(outputFileName);
		try {
			PrintWriter writer = new PrintWriter(outputFile);
	
			if(commandType.equalsIgnoreCase("scenario1"))
			{
				for(int i = 0; i < Integer.valueOf(args[3]); i++)
				{	
					int randNumber = rand.nextInt() % 3;
					if(randNumber == 0)
						cat = "restaurant ";
					else if(randNumber == 1)
						cat = "education ";
					else
						cat = "hospital ";

					latScaled = rand.nextDouble() * latRange;
					latShifted = latScaled + minLat;
					lonScaled = rand.nextDouble() * lonRange;
					lonShifted = lonScaled + minLon;
					
					for(int j = 5; j < 31; j += 5)
					{
						String searchPoint = "S " + cat + latShifted + " " + lonShifted + " " + Integer.toString(j);
						writer.println(searchPoint);
					}
				}
				
			}
			else if(commandType.equalsIgnoreCase("scenario2"))
			{
				//add points
				for(int i = 0; i < Integer.valueOf(args[3]); i++)
				{
					int randNumber = rand.nextInt() % 3;
					if(randNumber == 0)
						cat = "restaurant ";
					else if(randNumber == 1)
						cat = "education ";
					else
						cat = "hospital ";

					latScaled = rand.nextDouble() * latRange;
					latShifted = latScaled + minLat;
					lonScaled = rand.nextDouble() * lonRange;
					lonShifted = lonScaled + minLon;
					id = "id" + Integer.toString(idCount);
					String addPoint = "A " + id + " " + cat + latShifted + " " + lonShifted;
					writer.println(addPoint);
					idCount++;
				}
				
				//	String addPoint = "A " + id + " " + cat + " " + lat + " " + lon;
					
				//delete points
				for(int i = 0; i < Integer.valueOf(args[3]); i++)
				{
					Point newPoint = points.get(rand.nextInt(1240) + 1);
					if(newPoint.cat == Category.RESTAURANT)
						cat = "restaurant";
					else if(newPoint.cat == Category.EDUCATION)
						cat = "education";
					else
						cat = "hospital";
					
					String pointString = "D " + newPoint.id + " " + cat + " " + newPoint.lat + " " + newPoint.lon;
					writer.println(pointString);
				}
				
				String searchPoint = "S restaurant 34.77 147.43 10";
				writer.println(searchPoint);
			}
			else if(commandType.equalsIgnoreCase("sampledata"))
			{
				idCount = 0;
				for(int i = 0; i < Integer.valueOf(args[3]); i++)
				{
					int randNumber = rand.nextInt() % 3;
					if(randNumber == 0)
						cat = "restaurant ";
					else if(randNumber == 1)
						cat = "education ";
					else
						cat = "hospital ";

					latScaled = rand.nextDouble() * latRange;
					latShifted = latScaled + minLat;
					lonScaled = rand.nextDouble() * lonRange;
					lonShifted = lonScaled + minLon;
					id = "id" + Integer.toString(idCount);
					String addPoint = id + " " + cat + latShifted + " " + lonShifted;
					writer.println(addPoint);
					idCount++;
				}
			}	
			else
			{
				System.out.println("please enter scenario1 or scenario2 to generate commands");
				System.exit(1);
			}
			writer.close();
		}catch(FileNotFoundException e) {
			System.out.println("File not found");
			System.exit(0);
		}
	}
}
