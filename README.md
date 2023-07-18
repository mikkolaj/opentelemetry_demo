# Opentelemetry Demo Application
Based on architecture defined in: https://gitlab.com/lwronski/observability.

# How to run
1. `mvn package`
2. Inside docker directory run:
`docker compose up`
3. Run demo app:
`./run.sh`
4. Generate some requests: http://localhost:8080/rolldice
5. Open Grafana http://localhost:3000 and OpenSearch Dashboards http://localhost:5601
6. Import predefined dashboards from dashboards directory

Now you should be able to view some graphs and logs.
