import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class InspectNewPipe {
    public static void printClass(String className) {
        try {
            Class<?> clazz = Class.forName(className);
            System.out.println("--- " + className + " ---");
            for (Method m : clazz.getMethods()) {
                if (Modifier.isPublic(m.getModifiers())) {
                    System.out.print(m.getName() + "(");
                    Class<?>[] params = m.getParameterTypes();
                    for (int i = 0; i < params.length; i++) {
                        System.out.print(params[i].getSimpleName());
                        if (i < params.length - 1) System.out.print(", ");
                    }
                    System.out.println("): " + m.getReturnType().getSimpleName());
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        printClass("org.schabi.newpipe.extractor.stream.StreamInfoItem");
        printClass("org.schabi.newpipe.extractor.search.SearchInfo");
        printClass("org.schabi.newpipe.extractor.localization.Localization");
        printClass("org.schabi.newpipe.extractor.services.youtube.YoutubeSearchExtractor");
        printClass("org.schabi.newpipe.extractor.search.SearchExtractor");
        printClass("org.schabi.newpipe.extractor.ListInfo");
        printClass("org.schabi.newpipe.extractor.Info");
        printClass("org.schabi.newpipe.extractor.stream.StreamInfo");
        printClass("org.schabi.newpipe.extractor.Page");
    }
}
