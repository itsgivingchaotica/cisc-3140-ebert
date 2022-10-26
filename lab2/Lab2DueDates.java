import java.text.ParseException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.lang.Math;

public class Lab2DueDates {
	
	/**
	 * isValid checks date is in standard format
	 * @param date : input from user
	 * @param dateFormatter 
	 * @return if the date input was formatted correctly (true/false)
	 */
	
	public static boolean isValid(String date, DateTimeFormatter dateFormatter)
	{
		try
		{
			LocalDate.parse(date,dateFormatter);
			
		} catch (DateTimeException e)
		{
			return false;
		}
		return true;
	}
	/**
	 * create the formatted date to use when taking the time difference between the two LocalDate objects
	 * @param dateInput : the day inputted by user
	 * @param today : the date today
	 * @param savedDateList : the array of menu options for preset due dates
	 * @return the formatted date, null if unable to process
	 * @throws ParseException
	 */
	public static LocalDate createDate(String dateInput, LocalDate today, List<String> savedDateList) throws ParseException
	{
		//if you wanted to use a different format such as "yyyy-MM-dd" you may specify below's DateTimeFormatter object argument pattern
		DateTimeFormatter longDate = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		LocalDate formattedDate;
		int month, day, year;
		if (isSavedDate(dateInput,savedDateList)) {
			formattedDate=process(dateInput);	
			return formattedDate;
		}
		String testMonth = dateInput.substring(0,2);
		String testDay = dateInput.substring(3,5);
		Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(testMonth);
		Matcher d = p.matcher(testDay);
		boolean monthError = m.find();
		boolean dayError = d.find();
		if (isValid(dateInput,longDate))
		{
			month = Integer.parseInt(dateInput.substring(0,2));
			day = Integer.parseInt(dateInput.substring(3,5));
			year = Integer.parseInt(dateInput.substring(6,10));
			formattedDate = LocalDate.of(year, month, day);
			return formattedDate;
		}
		else if (dateInput.length()==5 && dateInput.substring(2,3).equals("-") && !monthError && !dayError && Integer.parseInt(dateInput.substring(0,2))!=0 && Integer.parseInt(dateInput.substring(3,5)) !=0)
		{
			month = Integer.parseInt(dateInput.substring(0,2));
			day = Integer.parseInt(dateInput.substring(3,5));
			if (Integer.parseInt(dateInput.substring(0,2)) < today.getMonthValue())
			{
				year = today.getYear() + 1;
			}
			else {
			year = today.getYear();
			}
			formattedDate = LocalDate.of(year, month, day);
			return formattedDate;
		}
		else 
		{
			//or "(YYYY/MM/DD)"
			System.out.print("Error: Please enter a valid input (MM/DD/YYYY) or (MM-DD): ");
		}
		return null;
	}
			
	/**
	 * daysDifference finds the days between the beginning and end dates
	 * @param end the second date input
	 * @param begin the first date input
	 * @return the days differences as an integer value
	 */
	public static int daysDifference(LocalDate end_date, LocalDate begin_date)
	{
		int daysDiff = (int)ChronoUnit.DAYS.between(begin_date,end_date);
				
		return daysDiff;
	}
			
	/**
	 * isSavedDate checks to see if the input string matches that of the menu options in main
	 * @param name : the assignment title
	 * @param savedDateList : the list of assignments due
	 * @return true/false if the input string was indeed a saved date
	 */
	public static boolean isSavedDate(String name, List<String> savedDateList) 
	{
		for (String s : savedDateList)
		{
			if (s.equals(name))
			{
				return true;
			}
		}
		return false;
	}
			
	/**
	 * process user input and return formatted date which corresponds to the preset due dates
	 * @param date : user input date
	 * @return the formatted date corresponding to the name of the preset due date
	 */
	public static LocalDate process(String date)
	{
		LocalDate today = LocalDate.now();
		LocalDate lab1 = LocalDate.of(2022, 9, 28);
		LocalDate lab2 = LocalDate.of(2022, 10, 26);
		LocalDate lab3 = LocalDate.of(2022, 11, 23);
		LocalDate lab4 = LocalDate.of(2022, 12, 7);		
		LocalDate quiz1 = LocalDate.of(2022, 9, 14);
		LocalDate quiz2 = LocalDate.of(2022, 9, 28);
		LocalDate quiz3 = LocalDate.of(2022, 10, 26);
		LocalDate quiz4 = LocalDate.of(2022, 11, 16);	
		LocalDate quiz5 = LocalDate.of(2022, 12, 7);
		LocalDate finalExam = LocalDate.of(2022, 12, 29);
		if (date.equals("lab1"))
		{
			return lab1;
		}
		if (date.equals("lab2"))
		{
			return lab2;
		}
		if (date.equals("lab3"))
		{
			return lab3;
		}
		if (date.equals("lab4"))
		{
			return lab4;
		}
		if (date.equals("quiz1"))
		{
			return quiz1;
		}
		if (date.equals("quiz2"))
		{
			return quiz2;
		}
		if (date.equals("quiz3"))
		{
			return quiz3;
		}
		if (date.equals("quiz4"))
		{
			return quiz4;
		}
		if (date.equals("quiz5"))
		{
			return quiz5;
		}
		if (date.equals("finalExam"))
		{
			return finalExam;
		}
		if (date.equals("today"))
		{
			return today;
		}
			return null;
		}
			
		/**
		* isCorrectLength checks to see if the length of the input can be used later in the program
	 	* @param str : the input
	 	* @return true/false if correct length needed
	 	*/
		public static boolean isCorrectLength(String str)
		{
			if (str.length() != 10) {
				if (str.length() !=5) {
					return false;
				}
			}
			return true;
		}

		
		public static void main(String[] args) throws ParseException 
		{
			String date1, date2;
			LocalDate today = LocalDate.now();
			LocalDate begin;
			LocalDate end;
			List<String> savedDateList = Arrays.asList("lab1","lab2","lab3","lab4","quiz1","quiz2","quiz3","quiz4","quiz5","finalExam","today");
			//BufferedWriter out = new BufferedWriter(new FileWriter("output.txt"); 
			//^this allows the anything printed out to a text file rather than to console
			Scanner sc = new Scanner(System.in);
			//Example: out.write("The following assignments for this class are: ");
			System.out.println("The following assignments for this class are: ");
			//list menu options
			for (String s : savedDateList)
			{
				System.out.print(s + "   ");
			}
			System.out.println();
			//or "Enter start date (YYYY/MM/DD)"
			System.out.print("Enter the start date (MM/DD/YYYY) or (MM-DD) or an option from above, q to quit: ");
			//while user input
			while (sc.hasNext()) 
			{	
				date1 = sc.next();
				System.out.println();
				//press q to quit
				if (date1.substring(0).equals("q") || date1.substring(0).equals("q"))
				{
					System.out.print("Done\n");
					break;
				}
				//check for errors in input
				while (isSavedDate(date1,savedDateList) == false && isCorrectLength(date1) == false)
				{
					System.out.print("Invalid date length, try again: ");
					date1 = sc.next();
					//if user quits
					if (date1.substring(0).equals("q") || date1.substring(0).equals("q"))
					{
						System.out.print("You quit the process.\n");
						System.exit(0);
					}
					//try to parse date to correct format
					begin=createDate(date1,today,savedDateList);
				}
				begin=createDate(date1,today,savedDateList);
				while (begin==null)
				{
					date1 = sc.next();
					begin=createDate(date1,today,savedDateList);
				}
			
			System.out.print("Enter the end date: ");
			date2 = sc.next();
			if (date2.substring(0).equals("q") || date2.substring(0).equals("q"))
			{
				System.out.print("Done\n");
				break;
			}
			while (isSavedDate(date2,savedDateList) == false && isCorrectLength(date2) == false)
			{
				System.out.print("Invalid date length, try again: ");
				date2 = sc.next();
				if (date2.substring(0).equals("q") || date2.substring(0).equals("q"))
				{
					System.out.print("You quit the process.");
					System.exit(0);
				}
				end=createDate(date2,today,savedDateList);
			}
			//create the format for second date 
			end=createDate(date2,today,savedDateList);
			while (end==null)
			{
				date2 = sc.next();
				end=createDate(date2,today,savedDateList);
			}
			//calculate the difference between the two dates
			int difference = daysDifference(end,begin);
			//if a negative value, concert to positive value for overdue status
			if (difference<0)
			{
				System.out.print("The assignment is " + Math.abs(difference) + " days overdue\n");
			}
			else {
				//printing the number of days between the two dates
				System.out.print("There are " + difference + " days between the two dates.\n");
			}
			//continue loop
			System.out.print("Continue? Enter today's date: ");
		}
			//out.close(); if using BufferedWriter object out
			sc.close();
	}
	
}
