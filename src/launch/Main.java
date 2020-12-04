package launch;
import java.io.File;

import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.naming.resources.VirtualDirContext;

public class Main {

    public static void main(String[] args) throws Exception {
    	String webappDirLocation = "src/main/webapp/";
    	Tomcat tomcat = new Tomcat();
    	
    	//The port that we should run on can be set into an environment variable
        //Look for that variable and default to 8080 if it isn't there.
        String webPort = System.getenv("PORT");
        if(webPort == null || webPort.isEmpty()) {
            webPort = "8080";
        }

        tomcat.setPort(Integer.valueOf(webPort));

    	StandardContext ctx = (StandardContext) tomcat.addWebapp("/embeddedTomcat",
    	                new File(webappDirLocation).getAbsolutePath());

    	//declare an alternate location for your "WEB-INF/classes" dir:     
    	File additionWebInfClasses = new File("target/classes");
    	VirtualDirContext resources = new VirtualDirContext();
    	resources.setExtraResourcePaths("/WEB-INF/classes=" + additionWebInfClasses);
    	ctx.setResources(resources);

    	tomcat.start();
    	tomcat.getServer().await();
    }
}