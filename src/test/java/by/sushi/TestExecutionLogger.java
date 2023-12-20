package by.sushi;

import org.junit.platform.engine.TestExecutionResult;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.abstracta.jmeter.javadsl.core.listeners.InfluxDbBackendListener;

import static us.abstracta.jmeter.javadsl.JmeterDsl.influxDbListener;

public class TestExecutionLogger implements TestExecutionListener {

    private static final Logger LOG = LoggerFactory.getLogger(TestExecutionLogger.class);

    @Override
    public void executionStarted(TestIdentifier testIdentifier) {
        LOG.debug("Started test {}", testIdentifier.getDisplayName());
    }

    @Override
    public void executionFinished(TestIdentifier testIdentifier,
                                  TestExecutionResult testExecutionResult) {
        LOG.debug("Ended test {}: {}", testIdentifier.getDisplayName(), testExecutionResult.getStatus());
    }

    //Send metric to InfluxDB
    public static InfluxDbBackendListener sentInfluxDbListener() {
        return influxDbListener("http://localhost:8086/write?db=jmeter")
                .measurement("jmeter")
                .application("jmeter")
                .token(ConfigHelper.getToken());
    }
}
