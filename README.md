# kart-racing-analysis-service
kart racing analysis service

## Build

mvn package

## Test

mvn surefire:test -Dtest=KartRaceTests

## Observations
> This app is not ready for processing large files. All file content is stored in memory!\
> Processing time is fixed to 1 minute, for increasing the value (in minutes) you can pass a second parameter to the app
