# spark as rest API example

mist is a nice rest api for  spark
Version of Spark is 2.0.2.

**start the server**

- hydrosphere/mist https://github.com/Hydrospheredata/mist.git
- need to build locally current version/ master branch not on maven central

```
git clone https://github.com/Hydrospheredata/mist.git
cd mist
sbt +publishM2
sbt -DsparkVersion=2.0.2 assembly
# TODO use correct path here
./bin/mist start master --config ./configs/mySampleDefault.conf
```
  
*build the job*
```
sbt assembly
```
**run the job locally (without mist)**
```
sbt run
```

**run unit test**
```
TODO
```

**run the job with as an API**
```
curl --header "Content-Type: application/json" -X POST http://localhost:2003/api/simple-context --data '{"digits": [1, 2, 3, 4, 5, 6, 7, 8, 9, 0]}'
```
