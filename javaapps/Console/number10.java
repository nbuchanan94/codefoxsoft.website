import java.lang.Math;
import java.util.*;
import java.lang.Integer;
 public class number10
{
	public static void main(String[] args)
	{
		Scanner kbReader = new Scanner(System.in);
		double radius = kbReader.nextDouble();
		double circleArea = Math.PI*radius*radius;
		System.out.println(circleArea);

		double circleArea2 = (circleArea +.005)*100;
		double circleArea3 = Math.round(circleArea2);
		System.out.println(circleArea3/100);



	}


}