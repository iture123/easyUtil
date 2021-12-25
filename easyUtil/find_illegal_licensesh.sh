#!/bin/bash
files=`find -type f -name "*.java" -o -name "*.xml" -o -name "*.scala"  -o -name "*.sh" -o -name "*.js" -o -name "*.scss"  -o -name "*.properties"|grep -v "\\.idea" |grep -v "\\.git"`
for file in $files;do
        line_cnt=`cat -n $file | grep "http://www.apache.org/licenses/LICENSE-2.0" | awk '{print $1}'|head -1`
	if [[ ! -n "$line_cnt" || $line_cnt -gt 10 || $line_cnt -lt 8 ]];then
                echo "$file=$line_cnt"
        fi
done
