createDate() {
    dateInput=$1
    shift
    today=$(date +%m%d%y)
    array=("lab1" "lab2" "lab3" "lab4" "quiz1" "quiz2" "quiz3" "quiz4" "quiz5" "finalExam")
    year=$(date +%Y)
    isSavedDate=false
    # for s in "$@"; do
    
    
    #set -x
    for s in "${array[@]}"; do
    # echo $s " "
    #if (($array[(Ie)$dateInput]))
        #if [[ ${array[(ie)$dateInput]} -le ${#array} ]]
    if [[ $s == $dateInput ]]
        then
        echo "isSavedDate returns true"
        isSavedate=true
        break
        else
        isSavedate=false
        fi
    #set +x
    done
    if [[ $isSavedate == true ]]
    then
       # process $dateInput
        formattedDate="$(process $dateInput)"
        echo "was a saved date" $formattedDate
        echo $formattedDate

    elif [[ $isSavedDate == false ]]
    then    
        echo "not a saved date"
        # set -x
        echo $dateInput
        removed=$(echo $dateInput | sed 's/[-,/]//g')
        #set -x
        echo $removed
        
        #length=$(echo -n $removed | wc -m)
        length=$(echo ${#removed})
        if [[ "$length" ==  4 ]]
        then
            let nextYear=($year + 1)
            formattedDate="$removed$nextYear"
            echo "Length 5" $formattedDate
            echo $formattedDate
        elif [[ "$length" == 8 ]]
        then
            formattedDate=$removed
            echo "Length 10" $formattedDate
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
    finalExam="12292022"
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
read input
returned_value="$(createDate $input)"
echo $returned_value
