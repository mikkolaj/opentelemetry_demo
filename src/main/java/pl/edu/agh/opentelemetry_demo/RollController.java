package pl.edu.agh.opentelemetry_demo;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.logs.GlobalLoggerProvider;
import io.opentelemetry.api.metrics.LongCounter;
import io.opentelemetry.api.metrics.Meter;
import io.opentelemetry.exporter.otlp.logs.OtlpGrpcLogRecordExporter;
import io.opentelemetry.sdk.logs.SdkLoggerProvider;
import io.opentelemetry.sdk.logs.export.SimpleLogRecordProcessor;
import io.opentelemetry.sdk.resources.Resource;
import io.opentelemetry.semconv.resource.attributes.ResourceAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import static io.opentelemetry.api.common.AttributeKey.stringKey;

@RestController
public class RollController {
    private static final Logger logger = LoggerFactory.getLogger(RollController.class);
    private final static SdkLoggerProvider loggerProvider = SdkLoggerProvider.builder()
            .setResource(
                    Resource.getDefault()
                            .toBuilder()
                            .put(ResourceAttributes.SERVICE_NAME, "opentelemetry-demo")
                            .build()
            )
            .addLogRecordProcessor(
                    SimpleLogRecordProcessor.create(
                                    OtlpGrpcLogRecordExporter.builder()
                                            .setEndpoint("http://localhost:4317")
                                            .build())
                            )
            .build();

    private final Meter meter = GlobalOpenTelemetry.getMeter("opentelemetry-demo");
    private final LongCounter requests = meter
            .counterBuilder("processed_jobs")
            .setDescription("Processed jobs")
            .setUnit("1")
            .build();

    static {
        GlobalLoggerProvider.set(loggerProvider);
    }


    @GetMapping("/rolldice")
    public String index(@RequestParam("player") Optional<String> player) {
        int result = this.getRandomNumber(1, 6);
        Attributes attributes = Attributes.of(stringKey("Number"), String.valueOf(result));
        requests.add(1, attributes);
        if (player.isPresent()) {
            logger.info("{} is rolling the dice: {}", player.get(), result);
        } else {
            logger.warn("Anonymous player is rolling the dice: {}", result);
        }
        return Integer.toString(result);
    }

    public int getRandomNumber(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
}