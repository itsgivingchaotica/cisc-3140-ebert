import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;

public class Lab1DueDates {
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

	public static void main(String[] args) {
			LocalDate today = LocalDate.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM);
			String formattedDate = today.format(formatter);
			LocalDate lab1 = LocalDate.of(2022, 9, 28);
			LocalDate lab2 = LocalDate.of(2022, 10, 26);
			LocalDate lab3 = LocalDate.of(2022, 11, 23);
			LocalDate lab4 = LocalDate.of(2022, 12, 7);
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
	
