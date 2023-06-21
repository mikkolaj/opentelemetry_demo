java -javaagent:agent/opentelemetry-javaagent.jar \
     -Dotel.service.name=opentelemetry-demo \
     -Dotel.jmx.target.system=opentelemetry-demo \
     -jar target/opentelemetry-demo.jar