import java.lang.reflect.*;
import org.schabi.newpipe.extractor.ServiceList;
import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.search.SearchExtractor;

public class InspectSearch {
    public static void main(String[] args) throws Exception {
        StreamingService yt = ServiceList.YouTube;
        System.out.println("Service: " + yt.getServiceInfo().getName());
        for (Method m : yt.getClass().getMethods()) {
            if (m.getName().toLowerCase().contains("search")) {
                System.out.println("  " + m.getName() + " -> " + Arrays.toString(m.getParameterTypes()));
            }
        }
        System.out.println("Search query handler:");
        SearchExtractor se = yt.getSearchExtractor("test");
        System.out.println("Class: " + se.getClass().getName());
        for (Method m : se.getClass().getMethods()) {
            if (m.getDeclaringClass() != Object.class) {
                System.out.println("  se: " + m.getName() + "(" + Arrays.toString(m.getParameterTypes()) + ")");
            }
        }
    }
}
