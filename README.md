# spark as rest API example

mist is a nice rest api for  spark
Version of Spark is 2.0.2.

**start the server**

- hydrosphere/mist https://github.com/Hydrospheredata/mist.git
- need to build locally as not on maven central

```
git clone https://github.com/Hydrospheredata/mist.git
cd mist
sbt +publishM2
sbt -DsparkVersion=2.0.2 assembly
./bin/mist start master --config /TODO/config
```
  
*build the job*
TODO
**run the job locally (without mist)**
  - execute `sbt run`
