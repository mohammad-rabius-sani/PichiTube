$env:JAVA_HOME = "D:\java\jdk-17.0.12+7"
$env:Path = "D:\java\jdk-17.0.12+7\bin;" + $env:Path
$jars = (Get-ChildItem -Path "$env:USERPROFILE\.gradle\caches\modules-2\files-2.1\org.schabi.newpipe.extractor" -Filter "*.jar" -Recurse | Select-Object -ExpandProperty FullName) -join ";"
javac -cp ".;$jars" InspectNewPipe.java
java -cp ".;$jars" InspectNewPipe > scratch/out.txt
Get-Content scratch/out.txt
