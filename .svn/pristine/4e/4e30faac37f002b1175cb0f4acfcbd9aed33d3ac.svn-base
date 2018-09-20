set JAVA_HOME="C:\Program Files\Java\jdk1.8.0_131"
set build_id=my_fortify_build
set classpath=D:\apache-maven-3.2.1\repository\**\*.jar
cd /d D:\Android
rem call mvn com.fortify.ps.maven.plugin:sca-maven-plugin:clean
call gradle clean build --offline
sourceanalyzer -b "%build_id%" -clean
sourceanalyzer -b "%build_id%" -machine-output -cp "%classpath%" -source 1.8 -Xmx1200m "D:\apache-maven-3.2.1\bin\mvn" com.fortify.ps.maven.plugin:sca-maven-plugin:translate
popd
