package by.sushi;

import net.minidev.json.JSONObject;
import org.apache.http.entity.ContentType;

import org.apache.jmeter.protocol.http.util.HTTPConstants;
import org.junit.jupiter.api.Test;
import us.abstracta.jmeter.javadsl.core.TestPlanStats;

import java.time.Duration;


import static by.sushi.DataHelper.getRandomDouble;
import static by.sushi.DataHelper.getRandomInteger;
import static by.sushi.TestExecutionLogger.sentInfluxDbListener;
import static org.assertj.core.api.Assertions.assertThat;
import static us.abstracta.jmeter.javadsl.JmeterDsl.*;

public class PerformanceTestUserRegister {

    private static final Integer THREADS = 5;
    private static final Integer ITERATIONS = 10;
    @Test
    public void saveGM() throws Exception {

        TestPlanStats stats = testPlan(
                threadGroup(THREADS, ITERATIONS,
                        httpSampler(ConfigHelper.url() + "/api/v1/auth/register")
                                .method(HTTPConstants.POST)
                                .post(s -> buildRequestBody(), ContentType.APPLICATION_JSON)
                                .children(jsonExtractor("firstname", "userId"))
                                .children(jsonAssertion("dateTime")),

                        httpSampler(ConfigHelper.url() + "/users/${userIdVar}/measurements")
                                .method(HTTPConstants.GET)
                                .contentType(ContentType.APPLICATION_JSON)
                                .children(jsonAssertion("[*].userId"))
                ), sentInfluxDbListener()
        ).run();
        assertThat(stats.overall().sampleTimePercentile99()).isLessThan(Duration.ofSeconds(5));
    }

    public static String buildRequestBody() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", getRandomInteger());
        jsonObject.put("gas", getRandomDouble());
        jsonObject.put("coldWater", getRandomDouble());
        jsonObject.put("hotWater", getRandomDouble());
        return jsonObject.toJSONString();
    }
}
