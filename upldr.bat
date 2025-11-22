@echo off
cd /d C:\Users\psych\Documents\codefoxsoftsite\codefoxsoft.website
set /p msg=Enter commit message: 
git add -A
git commit -m "%msg%"
git push origin main
pause
