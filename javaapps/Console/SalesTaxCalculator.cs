// Nicholas Buchanan
// 2/17/10

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace ConsoleApplication1
{
    class Program
    {
        static void Main(string[] args)
        {
            Console.Write("How many products? ");
            int times = Int32.Parse(Console.ReadLine());
            double price = 0;
            for (int i = 1; i <= times; i++)
            {
                Console.Write("What is the price of your product? ");
                price = price + double.Parse(Console.ReadLine());
            }
            double total1 = price * .06;
            double total = total1 + price;
            Console.WriteLine("Your total before tax " + price.ToString("C"));
            Console.WriteLine("Your tax is " + total1.ToString("C"));
            Console.WriteLine("Your total is: " + total.ToString("C"));
        }
    }
}

/*
 * PROGRAM DESCRIPTION:
 * 
 * This program calculates the total cost of a shopping trip including tax.
 * 
 * HOW IT WORKS:
 * 1. Asks user for number of items.
 * 2. Uses a loop to accumulate the price of each item entered by the user.
 * 3. Calculates a 6% sales tax on the subtotal.
 * 4. Outputs the subtotal, tax amount, and final total using currency formatting.
 */
