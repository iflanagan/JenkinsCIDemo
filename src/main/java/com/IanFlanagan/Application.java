package com.IanFlanagan;

import static com.rollbar.notifier.config.ConfigBuilder.withAccessToken;
import com.rollbar.notifier.Rollbar;
import com.rollbar.notifier.config.Config;

public class Application {

    private static Rollbar rollbar;

    public Application() {
        Config config = withAccessToken(System.getenv("ROLLBAR_ACCESS_TOKEN"))
                .environment("production")
                .codeVersion("1.0.0")
                .build();
        this.rollbar = Rollbar.init(config);
    }

    public static void main(String[] args) throws Exception {
        Application app = new Application();
        app.execute();
        app.GenerateErrors();
    }

    public static void GenerateErrors() throws Exception {

        try
        {
            throw new RuntimeException("Exception thrown");
        } catch (Exception e) {


            rollbar.log(e,"Major Issue");
            rollbar.warning(e);
            rollbar.critical(e,"Critical issue");
            rollbar.info(e);
        }
        finally {
            rollbar.close(true);
        }
    }

    public void execute() {
        try {
            throw new RuntimeException("Some error");
        } catch (Exception e) {
            rollbar.error(e, "Hello, Rollbar");
        }
    }
}