# **Lab 2 Analysis: Comparing ZSH with Java**

*Comparing ZSH with Java*

Between my Java program and zsh script I was able to replicate the goal of the prompt - to accept user input and process the number of days between the two. Although I didn't delve in `case`s in this assignment, I instead printed a menu of the pre-set date options and used that strategy for both. 

Certain features did not translate well to shell script. For example, the following code to determine if the input was of the correct length did not have the desired effect and was extremely buggy. Functions tended to work differently - I found that instead of returning a value back to the variable to store the result of the function, I simply had to use `echo` and store that way. However I couldn't figure it out when it came to returning pure boolean values. I attempted using manual strings as well as 0 and 1 and it didn't seem to work. So I moved on for the time being.
```
isCorrectLength() {
dateInput=$1
removed=$(echo $dateInput | sed 's/[-,/]//g')
length=$(echo ${#removed})
if [[ $legnth -ne 8 ]]
then
    if [[ $length -ne 4 ]]
    then
        echo "false" #or return false, return 0
    fi
else 
    echo "true"
fi

}

#(from main)
saved=$(isSavedDate $date2)
correct=$(isCorrectLength $date2)
while [[ "$correct" = "false" ]] && [[ "$saved" = "false" ]]
do
    echo "Invalid length, try again: "
    read local date2
    saved=$(isSavedDate $date2 "${savedDateList[@]}")
    correct=$(isCorrectLength $date2)
done
```
However, in Java I simply called my boolean method `isCorrectLength()` which returned `true` or `false`. I could loop until the inputted date from the user matched the program's expectations. 

I also ran into issue with logical operators because scripting differentiates between `=` and `==` whether a  `string` or an `int` therefore I was consulting examples through other code online to see which would be the best option in terms of syntax when writing the zsh script. Because Bash seems to be standard for shell scripting, using zsh sometimes meant that what I expected to run correctly did not. This was especially true with the conditional statements and brackets but also working with array conditionals. 

Because Java includes built-in libraries, I was able to import a decent amount and use them to make my program more efficient. For example, I was able to create the `isValid()` method and use the  `parse` to determine if the format was valid. Whereas in my zsh script program I had to manually use if statements to determine how I would be manipulating date format 

```
if [[ "$length" ==  4 ]] && [[ $month_only -lt $current_month ]]
        then
            nextYear=$(($year+1))
            formattedDate="$removed$nextYear"
            echo $formattedDate
            ...
        fi
```
In this example you can see the in zsh the if statements use different syntax that seems a bit more complicated. And instead of using the `LocalDate` class object methods to format I needed to format the date usage either myself or using the built-in date command manipulated around the user input

In Java:
```
else if (dateInput.length()==5 && dateInput.substring(2,3).equals("-") && !monthError && !dayError && Integer.parseInt(dateInput.substring(0,2))!=0 && Integer.parseInt(dateInput.substring(3,5)) !=0)
        {
            month = Integer.parseInt(dateInput.substring(0,2));
            day = Integer.parseInt(dateInput.substring(3,5));
            if (Integer.parseInt(dateInput.substring(0,2)) < today.getMonthValue())
            {
                year = today.getYear() + 1;
            }
            ...
        }
```
I spent a lot of time trying to reinterpret a complex if statement that used classes such as `Pattern` and `Matcher` into a more straightforward script program. Ultimately I lost the ability to get a result in which if the user inputs a way-off input that an error would simply be thrown with no ability to reinput the values needed. 

In addition, `daysDifference` methods between the two differed. Because I used the `LocalDate` class object to store my dates inputted, I was able to use `ChronoUnit` to accurately and simply find the number of days between the two dates. It was certainly not this straightforward with zsh. Instead, I was forced to reformat the days inputted such as below by analyzing the length of the `string`s associated with the dates

```
#an example
start_date=$(date -j -f "%m%d" $2 "+%s")
end_date=$(date -j -f "%m%d" $1 "+%s")
```

then finally I could `return $(( ($end_date - $start_date) / (60 * 60 * 24) ))`

In Java this was much simpler. `return (int)ChronoUnit.DAYS.between(begin_date,end_date);`

Therefore, the number of lines of code doing artihmetic was much less than in the zsh program because of `ChronoUnit`. I much prefer working with that because working with the built-in date command in z shell meant the `daysDifference()` function ended up clocking in at ~30 lines of code compared to Java's 2 lines.

For the method `isSavedDate(String,List<String>)` I was able to pass the entire `savedDateList` as
a parameter, however in the zsh script I was facing diffuculty with passing the array in as an argument and it was showcasing strange behavior such as inputting prior input into the array. I experimented with `shift` and found that it seemed clunky, so instead I reinitialized an array into that section of code. But this got me thinking, perhaps it's better to do so rather than passing in an array everytime you call the method. It does depend on how the program would be used in real life.

My zsh script was 120 lines shorter than my Java program, but that was too be expected as some features were missing and several short functions were rendered useless. To accept user input: from main and create a formatted date, 1 + 1+(50) = 52 lines for each date (not including calling function from the current function) whereas in my Java program was 12 lines in main and 40 lines in the method. Again, this doesn't include the other methods used within the `createDate()` method. Therefore they tended to be similar in size. The difference was that the Java program was more detailed in determining the validity of user input and gave more options when making mistakes in input. 

However, working with user input via the scanner was frustrating at times because of the way the data is read in often throws off the way it is stored as variables for later use. Zsh is much simpler: `read $X` so when designing a straightforward program such as this it became more useful and less time consuming to debug any input issues. When it comes to output, the two programs were pretty similar except Java program had more options for correcting the mistakes of the user and more opportunities to exit the program. However, because zsh has no built-in absolute value command, I was forced to use `cut` to create a substring of the negative result to output to the "days overdue" message whereas in Java I simply caled `Math.abs()` This required importing `lang.Math`

## **Java:**

```
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
```


## **ZSH:**

```
daysDifference $end $begin
    difference=$?
    if [[ $difference < 0 ]]
    then
        absoluteVal=$(echo $difference| cut -c2-) #manually return absolute value by using cut
        echo "The assignment is $absoluteVal days overdue"
    else
    echo "There are $difference days between the dates"
```

All in all I was able to replicate many of the functions and strategies used in the Java program in the zsh script save for a couple that need further analyzing on my part. Simply put, the way in which you approach a problem in Java is different than in shell scripting. But on the other side of the coin, relying on built-in classes with Java sometimes means that you are accomodating for them instead of using them as a tool. Upon finishing the zsh program I realized there were instances in the Java code that seemed too complicated and less polished. in all I had 11 import lines:
```
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
```

Of course anything involving `regex` could be done manually, but in this case the others I needed to use because I was heavily relying on  `LocalDate` because manually creating a `Date` object would be too time consuming and wouldn't give the power needed to fulfill the programs full potential.
