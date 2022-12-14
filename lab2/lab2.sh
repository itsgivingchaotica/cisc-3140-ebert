#!/bin/zsh

createDate() {
    dateInput=$1
    today=$(date +%m%d%y)
    array=("lab1" "lab2" "lab3" "lab4" "quiz1" "quiz2" "quiz3" "quiz4" "quiz5" "finalExam")
    year=$(date +%y)
    isSavedDate=false
    
    for s in "${array[@]}"; do
    if [[ $s == $dateInput ]]
    then
        isSavedate=true
        break
    else
        isSavedate=false
    fi
    done

    if [[ $isSavedate == true ]]
    then
        formattedDate="$(process $dateInput)"
        echo $formattedDate

    elif [[ $isSavedDate == false ]]
    then    
        removed=$(echo $dateInput | sed 's/[-,/]//g')
        month_only=$(echo $dateInput | cut -c1 -c2)
        current_month=$(date +%m)
        length=$(echo ${#removed})
        
        if [[ "$length" ==  4 ]] && [[ $month_only -lt $current_month ]]
        then
            nextYear=$(($year+1))
            formattedDate="$removed$nextYear"
            echo $formattedDate
        set -x
        elif [[ "$length" ==  4 ]] && [[ $month_only -le $current_month ]]
        set +x
        then
            formattedDate=$removed
            # returns the date as a string of ints without special characters
            echo $formattedDate
        elif [[ "$length" == 8 ]] 
        then
            formattedDate=$removed
            # returns the date as a string of ints without special characters
            echo $formattedDate
        else
            echo "Error, please enter a valid input: "
        fi
    fi

}   


process() {
    dateInput=$1
    lab1="09282022"
    lab2="10262022"
    lab3="11232022"
    lab4="12072022"
    quiz1="09142022"
    quiz2="09282022"
    quiz3="10262022"
    quiz4="11162022"
    quiz5="12072022"
    finalExam="12192022"
if [[ $1 == "lab1" ]]
then
    echo $lab1
elif [[ $1 == "lab2" ]]
then
    echo $lab2
elif [[ $1 == "lab3" ]]
then
    echo $lab3
elif [[ $1 == "lab4" ]]
then
    echo $lab4
elif [[ $1 == "quiz1" ]]
then
    echo $quiz1
elif [[ $1 == "quiz2" ]]
then
    echo $quiz2
elif [[ $1 == "quiz3" ]]
then
    echo $quiz3
elif [[ $1 == "quiz4" ]]
then
    echo $quiz4
elif [[ $1 == "quiz5" ]]
then
    echo $quiz5
elif [[ $1 == "finalExam" ]]
then
    echo $finalExam
else
    echo $dateInput
fi
}

isSavedDate() {
    dateInput=$1
    savedDateList=("lab1" "lab2" "lab3" "lab4" "quiz1" "quiz2" "quiz3" "quiz4" "quiz5" "finalExam")
    #array=("$@")
    for s in "${savedDateList[@]}"; do
    echo $s " "
        if [[ $s == $dateInput ]]
        then    
            echo "true"
        else    
            echo "false"
        fi
    done
}

daysDifference() {
end=$1
begin=$2
lengthEND=$(echo ${#end})
lengthBEGIN=$(echo ${#begin})
if [[ $lengthEND == 4 ]] && [[ $lengthBEGIN == 4 ]]
then
start_date=$(date -j -f "%m%d" $2 "+%s")
end_date=$(date -j -f "%m%d" $1 "+%s")

elif [[ $lengthEND == 8 ]] && [[ $lengthBEGIN == 8 ]]
then
start_date=$(date -j -f "%m%d%Y" $2 "+%s")
end_date=$(date -j -f "%m%d%Y" $1 "+%s")

elif [[ $lengthEND == 4 ]] && [[ $lengthBEGIN == 8 ]]
then
start_date=$(date -j -f "%m%d%Y" $2 "+%s")
end_date=$(date -j -f "%m%d" $1 "+%s")

elif [[ $lengthEND == 8 ]] && [[ $lengthBEGIN == 4 ]]
then
start_date=$(date -j -f "%m%d" $2 "+%s")
end_date=$(date -j -f "%m%d%Y" $1 "+%s")

elif [[ $lengthEND == 6 ]] && [[ $lengthBEGIN == 6 ]]
then 
start_date=$(date -j -f "%m%d%y" $2 "+%s")
end_date=$(date -j -f "%m%d%y" $1 "+%s")

elif [[ $lengthEND == 4 ]] && [[ $lengthBEGIN == 6 ]]
then 
start_date=$(date -j -f "%m%d%y" $2 "+%s")
end_date=$(date -j -f "%m%d" $1 "+%s")

elif [[ $lengthEND == 6 ]] && [[ $lengthBEGIN == 4 ]]
then 
start_date=$(date -j -f "%m%d" $2 "+%s")
end_date=$(date -j -f "%m%d%y" $1 "+%s")

elif [[ $lengthEND == 8 ]] && [[ $lengthBEGIN == 6 ]]
then 
start_date=$(date -j -f "%m%d%y" $2 "+%s")
end_date=$(date -j -f "%m%d%Y" $1 "+%s")

elif [[ $lengthEND == 6 ]] && [[ $lengthBEGIN == 8 ]]
then 
start_date=$(date -j -f "%m%d%Y" $2 "+%s")
end_date=$(date -j -f "%m%d%y" $1 "+%s")
fi

return $(( ($end_date - $start_date) / (60 * 60 * 24) ))
}

# MAIN
savedDateList=("lab1" "lab2" "lab3" "lab4" "quiz1" "quiz2" "quiz3" "quiz4" "quiz5" "finalExam")
# show options for preset due dates
for s in ${savedDateList[@]}; do
    echo $s " "     
done
echo "Enter the start date (MM/DD/YYYY) or (MM-DD) or an option from above: "
read date1
while [[ $savedDateList != null ]] do;
    begin=$(createDate $date1)
    echo "Enter the end date: "
    read date2
    end=$(createDate $date2)
    daysDifference $end $begin
    difference=$?
if [[ $difference < 0 ]]
then
    absoluteVal=$(echo $difference| cut -c2-)
    echo "The assignment is $absoluteVal days overdue"
else
    echo "There are $difference days between the dates"
    set +x
fi
echo -n "Enter another begin date (q to quit)) : "
read date1
if [[ "$date1" = "q" ]]
then
    break
else
    continue
fi
done