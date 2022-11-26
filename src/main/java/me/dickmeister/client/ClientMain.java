package me.dickmeister.client;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;
import me.dickmeister.client.core.command.CommandManager;
import me.dickmeister.client.core.event.EventDistributor;
import me.dickmeister.client.core.event.EventInjector;
import me.dickmeister.client.core.event.impl.ClientInternalTickEvent;
import me.dickmeister.client.core.event.impl.Render2DEvent;
import me.dickmeister.client.core.gui.GuiIngameRenderer;
import me.dickmeister.client.core.module.ModuleManager;
import me.dickmeister.client.core.utils.minecraft.MinecraftUtil;
import me.dickmeister.client.mappings.MappingManager;
import me.dickmeister.client.mappings.VersionMappingsLoader;
import me.dickmeister.client.mappings.loader.MappingsLoader;
import me.dickmeister.client.tasks.TickingTask;
import me.dickmeister.client.transformers.TestTransformer;
import me.dickmeister.client.version.VersionFinder;
import me.dickmeister.common.util.LogUtil;
import org.reflections.Reflections;
import org.slf4j.LoggerFactory;

import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientMain {
    public static final boolean DEBUG = true;

    public static Instrumentation instrumentation;
    private boolean initialized = false;
    private final ScheduledExecutorService tickingService;
    private static ClientMain instance;

    private final MappingManager mappingManager = new MappingManager();
    private final EventDistributor eventDistributor = new EventDistributor();
    private final CommandManager commandManager = new CommandManager();
    private final ModuleManager moduleManager = new ModuleManager();

    private MinecraftUtil minecraftUtil;

    public ClientMain(){
        tickingService = Executors.newSingleThreadScheduledExecutor();
        instance = this;
    }

    public void initialize() throws Exception {


        Logger.getLogger("org.reflections").setLevel(Level.OFF);

        LogUtil.log("Initializing client...");
        tickingService.scheduleAtFixedRate(new TickingTask(), 0, 50, TimeUnit.MILLISECONDS);

        LogUtil.log("Checking client version...");
        var version = new VersionFinder().findVersion();
        if(version == null) {
            LogUtil.log("Failed to find client version!");
            return;
        }

        LogUtil.log("Client version: %s", version.getVersion());
        LogUtil.log("Client protocol: %s", version.getProtocol());

        LogUtil.log("Downloading mappings...");
        var mappingsLoader = new MappingsLoader(mappingManager);
        var mappingsUrl = new VersionMappingsLoader().loadFromVersion(version);
        mappingsLoader.loadFromUrl(mappingsUrl);

        if(mappingManager.getMappedClasses().isEmpty()){
            LogUtil.err("Failed to load mappings data!");
            return;
        }

        LogUtil.log("Loaded %s mapped classes!", mappingManager.getMappedClasses().size());

        LogUtil.log("Injecting events...");
        var eventInjector = new EventInjector();
        eventInjector.initialize();

        commandManager.initialize();
        moduleManager.initialize();

        minecraftUtil = MinecraftUtil.getFromVersion(version.getVersion());
        if(minecraftUtil == null){
            LogUtil.err("Unsupported version: %s", version.getVersion());
            return;
        }

        LogUtil.log("Client initialized!");

        instrumentation.addTransformer(new TestTransformer());
        var clazz =  ClientMain.getMappingManager().translateClassName("net.minecraft.world.entity.player.Player");
        instrumentation.retransformClasses(Class.forName(clazz));

        this.initialized = true;
    }

    public void start(){
        if(!initialized){
            LogUtil.err("Failed to start: client is not initialized!");
            return;
        }

        EventManager.register(new GuiIngameRenderer());
        LogUtil.log("Starting client...");
    }

    public static void invokeClient(Instrumentation inst){
        try {
            instrumentation = inst;
            instance.initialize();
            instance.start();
        }catch(Exception e){
            LogUtil.err("Failed to start client!");
            e.printStackTrace();
        }
    }

    public static ClientMain getInstance() {
        return instance;
    }

    public EventDistributor getEventDistributor() {
        return eventDistributor;
    }

    public ModuleManager getModuleManager() {
        return moduleManager;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }
    public static MinecraftUtil getMinecraftUtil() {
        return getInstance().minecraftUtil;
    }

    public static MappingManager getMappingManager() {
        return instance.mappingManager;
    }
}
