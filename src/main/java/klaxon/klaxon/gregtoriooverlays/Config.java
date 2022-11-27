package klaxon.klaxon.gregtoriooverlays;

import java.io.File;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class Config {

    private static class Defaults {
        public static final String greeting = "Come with me/and you can see/the results of your industrialization!";
    }

    private static class Categories {
        public static final String general = "general";
    }

    public static String greeting = Defaults.greeting;

    public static void syncronizeConfiguration(File configFile) {
        Configuration configuration = new Configuration(configFile);
        configuration.load();

        Property greetingProperty =
                configuration.get(Categories.general, "greeting", Defaults.greeting, "How shall I greet?");
        greeting = greetingProperty.getString();

        if (configuration.hasChanged()) {
            configuration.save();
        }
    }
}
