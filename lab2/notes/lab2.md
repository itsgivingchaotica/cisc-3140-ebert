# **Lab 2 Analysis: Comparing ZSH with Java**

# *Instructions*
1. Makefile can be exeecuted by specifying `make lab2` from the `lab2` branch: the makefile compiles then runs the java program via file redirection
2. Alternatively, the java program can be compiled and run via the shellscript file `sample.sh` which calls the function `compile_run`from `source build.sh` and then separately runs the shellscript version of the java program in ZSH,`lab2.sh` via file diretion. 
3. If you wanted to make your own input, you may simply delete `< shellinput.txt` and run `sample.sh` that way 

## **FILES**
- **Lab2DueDates.java** is the source file
- **makefile** allows `make lab2` to compile and run java program
- **lab2data.txt** was used for file redirection. Alternatively you can try using the other .txt file for a different set of results
- **lab2.sh** is the shellscript version of the java program
- **build.sh** includes the functions needed to compile and run the java program from shellscript
- **sample.sh** when run, this runs all the code in build.sh for the java program as well as the shellscript version using file direction
- **shellinputl.txt** was used for file redirection for the shellscript program but the other .txt file can also be used
- **.gitignore** file was created specifically for this assignment
- **logs** directory includes logs of sample runs
- **notes** files away this markdown file

# *Comparing ZSH with Java*

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

# *Comparing ZSH script and makefile*

Creating the makefile to compile and run the Java program was a breeze. It was beneficial when preparing the YAML file for my workflow for this project as well. I found that the `build.sh` file was more elaborate to write, but allowed me to use the same variable names `build_class` and `build_exec`. The `compile_run` function allowed them to be run in the shellscript whereas `make` acted as the catalyst for `makefile` with the target `lab2`. So from command line, it's easy for both to type in either `make lab2` or `./sample.sh` I was able to use file redirection in the same way in both files however I was not able to use the `-cp` flag for compiling in shellscript for some reason. Where as in the makefile it was `javac -cp Lab2DueDates.class Lab2DueDates.java`, in the shellscript version a `for` loop was used below:

```
 for JAVA_FILE in $INPUT
    do
    CLASS=${JAVA_FILE/%.java/.class}
    CLASSES="$CLASS $CLASSES"
    echo "Compiling $JAVA_FILE..."
    javac $JAVA_FILE 
    done
```

Then `$CLASSES` would be used as an argument for `build_exec` function. If I did have more than one class it would have been much simpler to use the shellscript because it allowed one variable to hold all the classes needed to run `main` with declaration of the .java filenames declared at the top of the document whereas in the makefile each would need to have their own line of input to create an executable.

Overall I think makefile is best for simpler projecets or more complex projects that require different targets whereas the shellscript version allows everything to be run and compiled with two simple lines of code within the function `compile_run()` which can simply be called from any other shellscript.

# *Thoughts on the AWK Group Project*
This was the first time I collaborated with other students on a technical project. We employed the usual techniques for group collobaration: setting up the cloud-based files (Google Docs, Slides) for sharing ideas and inputting our work as well as creating a group chat for instant communciation. Our group leader found a great dataset and took charge of communication with Professor Chuang about the questions we planned to use for data analysis going into the project. From there on, we worked separately on the questions we picked and added our analysis for each to the presentation slides. We used the group repository to each create branches that had our code and other files included.

From what I've learned from this project, next time I think adding issues we were facing when writing our code would've helped us collaborate more effecitvely. I spent hours hung up on some techniques that could've benefitted from second set of eyes and ears. In addition, there was a group member who fell behind and decided to not submit any work. Therefore, we should've set periodic goals and checked in with one another in a more structured way how far we were. This would've kept everyone on track for completing the assignment. 
