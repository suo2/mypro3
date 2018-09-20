set JAVA_HOME=C:\Program Files (x86)\Java\jdk1.8.0_121
set build_id=my_fortify_build
set classpath=D:\m2\repository\**\*.jar
cd /d D:\agent\plugins\codex-checkup\tool\fortify4_2\Samples\advanced\maven-plugin
call mvn clean -Pfast install
cmd /c D:\Android\compile_ePlatform_Android.bat
rem cd /d D:\eOMC_Code_4\TTR5.0
rem call mvn com.fortify.ps.maven.plugin:sca-maven-plugin:clean
rem sourceanalyzer -b "%build_id%" -clean
rem sourceanalyzer -b "%build_id%" -machine-output -cp "%classpath%" -source 1.7 -Xmx1200m mvn com.fortify.ps.maven.plugin:sca-maven-plugin:translate
rem popd

rem gradle clean build