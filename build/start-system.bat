@echo off
setlocal

rem set file name
set "jar-analyzer=jar-analyzer-2.16.jar"

rem env
set "command=wmic os get freephysicalmemory"

rem get free memory
for /f "skip=1" %%p in ('%command%') do ( 
  set "m=%%p"
  goto :done
)
:done
set /A "freemem = %m% / 1024"

rem use 2/3 free memory
set /A "heapsize = freemem * 2 / 3"

rem jvm args
set "other_args=-Dfile.encoding=UTF-8"
set "no_agent_args=-XX:+DisableAttachMechanism"
set "java_args=%no_agent_args% -Xmx%heapsize%M -Xms%heapsize%M %other_args%"
set "java_cp=lib\%jar-analyzer%;lib\tools.jar"
set "main_class=me.n1ar4.jar.analyzer.starter.Application"
set "agent_path=lib\rasp.jar"
set "boot_args=-Xbootclasspath/a:%agent_path%"
set "java_agent=-javaagent:%agent_path%"

rem start jar
java %java_agent% %boot_args% %java_args% -cp %java_cp% %main_class% gui

endlocal