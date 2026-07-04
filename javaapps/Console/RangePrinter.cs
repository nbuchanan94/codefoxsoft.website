// Nicholas Buchanan
// 2/17/10

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace ConsoleApplication1
{
    class RangePrinter
    {
        static void Main(string[] args)
        {
            Console.WriteLine("Enter the starting number:");
            int num1 = Int32.Parse(Console.ReadLine());

            Console.WriteLine("Enter the ending number:");
            int num2 = Int32.Parse(Console.ReadLine());

            Console.WriteLine("\nPrinting numbers from " + num1 + " to " + num2 + ":");
            Console.WriteLine(num1); // Print starting number

            while (num1 < num2)
            {
                num1++;
                Console.WriteLine(num1);
            }
        }
    }
}

/*
 * PROGRAM DESCRIPTION:
 * 
 * This program demonstrates a basic 'while' loop.
 * 
 * HOW IT WORKS:
 * 1. Prompts variable 'num1' (start) and 'num2' (end).
 * 2. It prints the starting number.
 * 3. It increments 'num1' until it reaches 'num2', printing each step.
 * 4. Effectively lists all integers in the specified range.
 */
