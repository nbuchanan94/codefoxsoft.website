using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
//nick buchanan
//2/17/10
//pd 8
namespace ConsoleApplication1
{
    class AREAPERIMETER
    {
        static void Main(string[] args)
        {
            Console.Write("Enter length: ");
            int length = Int32.Parse(Console.ReadLine());

            Console.Write("Enter width: ");
            int width = Int32.Parse(Console.ReadLine());

            int area = length * width;
            int perimeter = (length * 2) + (width * 2);
            Console.WriteLine("\nArea of rectangle: " + area);
            Console.WriteLine("Perimeter of Rectangle: " + perimeter);
           
        }
    }
}
