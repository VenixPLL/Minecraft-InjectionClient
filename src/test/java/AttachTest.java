import me.dickmeister.agent.attacher.AgentAttacher;

public class AttachTest {

    public static void main(String[] args) throws Exception {
        var path = "C:\\Users\\dickmeister\\Desktop\\Projekty\\InjectionClientTest\\out\\artifacts\\InjectionClientTest_jar\\InjectClient.jar";
        AgentAttacher.attachDynamic(path,"6824");
    }

}
