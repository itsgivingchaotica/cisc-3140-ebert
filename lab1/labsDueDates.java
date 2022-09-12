import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;

class Main {

	public static void main(String[] args) {
		try {
			LocalDate today = LocalDate.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM);
			String formattedDate = today.format(formatter);
			LocalDate lab1 = LocalDate.of(2022, 9, 28);
			LocalDate lab2 = LocalDate.of(2022, 10, 26);
			LocalDate lab3 = LocalDate.of(2022, 11, 23);
			LocalDate lab4 = LocalDate.of(2022, 12, 7);
			long daysBetween1 = ChronoUnit.DAYS.between(today, lab1);
			long weeksBetween1 = ChronoUnit.WEEKS.between(today, lab1);
			long daysBetween2 = ChronoUnit.DAYS.between(today, lab2);
			long weeksBetween2 = ChronoUnit.WEEKS.between(today, lab2);
			long daysBetween3 = ChronoUnit.DAYS.between(today, lab3);
			long weeksBetween3 = ChronoUnit.WEEKS.between(today, lab3);
			long daysBetween4 = ChronoUnit.DAYS.between(today, lab4);
			long weeksBetween4 = ChronoUnit.WEEKS.between(today, lab4);
			System.out.println("Today is " + formattedDate);
			
			System.out.printf("Lab 1 is due in %d days, ~ %d weeks\n", daysBetween1, weeksBetween1);
			System.out.printf("Lab 2 is due in %d days, ~ %d weeks\n", daysBetween2, weeksBetween2);
			System.out.printf("Lab 3 is due in %d days, ~ %d weeks\n", daysBetween3, weeksBetween3);
			System.out.printf("Lab 4 is due in %d days, ~ %d weeks\n", daysBetween4, weeksBetween4);
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
		}
	}
}
