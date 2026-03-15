@echo off
setlocal EnableExtensions

set "VERSION=%~1"
if "%VERSION%"=="" set "VERSION=1.0.0"

set "REGISTRY=homelab:5000"
set "IMAGE_NAME=multi-bambu-api"
set "IMAGE=%REGISTRY%/%IMAGE_NAME%"
set "PLATFORMS=linux/amd64,linux/arm64"

set "SCRIPT_DIR=%~dp0"
set "ROOT=%SCRIPT_DIR%"
if "%ROOT:~-1%"=="\" set "ROOT=%ROOT:~0,-1%"

set "DOCKERFILE=%ROOT%\Dockerfile"

echo.
echo Building local Maven package...
call mvn clean package -DskipTests || goto :error

echo.
echo Switching docker context to default...
docker context use default || goto :error

echo Using default buildx builder...
docker buildx use default || goto :error

echo Bootstrapping builder...
docker buildx inspect --bootstrap || goto :error

echo Logging in to %REGISTRY%...
docker login %REGISTRY% || goto :error

echo.
echo Building ^& pushing API (%VERSION% + latest)...
docker buildx build --platform %PLATFORMS% ^
  -t %IMAGE%:%VERSION% -t %IMAGE%:latest ^
  -f "%DOCKERFILE%" ^
  --push "%ROOT%" || goto :error

echo.
echo ============================================
echo API build ^& push completed successfully.
echo Image: %IMAGE%:%VERSION%
echo ============================================
echo.
exit /b 0

:error
echo.
echo ============================================
echo FAILED (api). Check the output above.
echo ============================================
echo.
exit /b 1