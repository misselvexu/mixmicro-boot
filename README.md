## Mixmicro OSS Parent

> OSS Parent Pom Defined .

### Overview

>

### Requirements

Maven Repository Configuration

> [Configuration Reference](https://github.com/misselvexu/Acmedcare-Maven-Nexus/blob/master/README.md)


### Building from Source

You don’t need to build from source to use `Mixmicro+ OSS` (binaries in [repo.maven.apache.org](http://maven.apache.org)), 
but if you want to try out the latest and greatest, 
`Mixmicro+ OSS` can be easily built with the maven wrapper. You also need JDK 1.8.

*First* : git clone source from gitlab
 
```bash
$ git clone ssh://git@git.hgj.net:8022/mixmicro/mixmicro-oss.git
```

*Second* : build

```bash
$ mvn clean install
```

If you want to build with the regular `mvn` command, you will need [Maven v3.5.0 or above](https://maven.apache.org/run-maven/index.html).


### Document

#### How to Use

```xml
<pom>
   <dependencyManagement>
       <dependencies>
           <groupId>xyz.vopen.framework</groupId>
           <artifactId>mixmicro-boot-dependencies</artifactId>
           <version>2020.04.RELEASE</version>
           <scope>import</scope>
           <type>pom</type>
       </dependencies>
    </dependencyManagement>
</pom>

```


### License
 
```
Copyright (c) 2020 VOPEN.XYZ+

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

```