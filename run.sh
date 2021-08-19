mvn -T 1C -am compile \
&& mvn exec:java -Dexec.mainClass=com.steeelydan.server.Server