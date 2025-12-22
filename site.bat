@echo off
title CodeFoxSoft GitHub Clone & Push
color 0A

set REPO_URL=https://github.com/codefoxsoft/codefoxsoft.com
set REPO_DIR=codefoxsoft.com

:: Check if repo folder exists
if not exist "%REPO_DIR%\.git" (
    echo Repository not found locally. Cloning...
    git clone %REPO_URL%
    if errorlevel 1 (
        echo Failed to clone repository.
        pause
        exit /b
    )
)

cd %REPO_DIR%

echo ============================================
echo   CodeFoxSoft GitHub Sync Utility
echo   Repository: %REPO_URL%
echo ============================================
echo.
echo [1] Download (git pull)
echo [2] Upload   (git push)
echo.

set /p choice=Enter choice (1 or 2): 

if "%choice%"=="1" goto download
if "%choice%"=="2" goto upload

echo Invalid choice. Exiting...
goto end

:download
echo.
echo --- Downloading latest changes from GitHub ---
git pull origin main
goto end

:upload
echo.
set /p msg=Enter commit message: 
git add -A
git commit -m "%msg%"
git push origin main
goto end

:end
echo.
echo Operation complete.
pause
