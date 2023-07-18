# Opentelemetry Demo Application
Based on architecture defined in: https://gitlab.com/lwronski/observability.

# How to run
1. Inside docker directory run:
`docker compose up`
2. Run demo app:
`./run.sh`
3. Generate some requests: http://localhost:8080/rolldice
4. Open Grafana http://localhost:3000 and OpenSearch Dashboards http://localhost:5601
5. Import predefined dashboards from dashboards directory

Now you should be able to view some graphs and logs.
