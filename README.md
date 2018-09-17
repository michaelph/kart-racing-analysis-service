# kart racing analysis service
App for Gympass interview

## Build

mvn package

## Test

mvn surefire:test -Dtest=KartRaceTests

## Execute
java -jar kart-racing-analysis-service-1.0.jar input_log_file

## Observations
> This app is not ready for processing large files. All file content is stored in memory!\
> Processing time is fixed to 1 minute, for increasing the value (in minutes) you can pass a second parameter to the app
