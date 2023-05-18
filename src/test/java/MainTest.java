import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    @Disabled
    @Timeout(value = 22, unit = TimeUnit.SECONDS)
    @Test
    void testMainDurationNoMore22() throws InterruptedException {
        assertTimeoutPreemptively(Duration.ofSeconds(22), () -> {
            Main.main(new String[]{});
        });
    }
}