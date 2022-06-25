package me.dickmeister.client;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;
import me.dickmeister.client.core.event.EventDistributor;
import me.dickmeister.client.core.event.EventInjector;
import me.dickmeister.client.core.event.impl.PlayerChatEvent;
import me.dickmeister.client.core.utils.MinecraftUtils;
import me.dickmeister.client.mappings.MappingManager;
import me.dickmeister.client.mappings.VersionMappingsLoader;
import me.dickmeister.client.mappings.loader.MappingsLoader;
import me.dickmeister.client.tasks.TickingTask;
import me.dickmeister.client.version.VersionFinder;
import me.dickmeister.common.util.LogUtil;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ClientMain {
    public static final boolean DEBUG = true;
    private boolean initialized = false;
    private final ScheduledExecutorService tickingService;
    private static ClientMain instance;

    private final MappingManager mappingManager = new MappingManager();
    private final EventDistributor eventDistributor = new EventDistributor();

    public ClientMain(){
        tickingService = Executors.newSingleThreadScheduledExecutor();
        instance = this;
    }

    public void initialize(){
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

        LogUtil.log("Client initialized!");
        this.initialized = true;
    }

    public void start(){
        if(!initialized){
            LogUtil.err("Failed to start: client is not initialized!");
            return;
        }
        LogUtil.log("Starting client...");
    }

    public static void invokeClient(){
        try {
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

    public static MappingManager getMappingManager() {
        return instance.mappingManager;
    }
}
