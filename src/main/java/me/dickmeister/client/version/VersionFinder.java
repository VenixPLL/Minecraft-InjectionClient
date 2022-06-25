package me.dickmeister.client.version;

import me.dickmeister.client.version.component.VersionFinderComponent;
import me.dickmeister.client.version.component.impl.LatestFinderComponent;
import me.dickmeister.common.util.LogUtil;

import java.util.HashMap;
import java.util.Map;

public class VersionFinder {
    private final Map<String, VersionFinderComponent> componentMap = new HashMap<>();

    {
        //Initialize components
        componentMap.put("latest",new LatestFinderComponent());
    }

    /**
     * Feeding all the components to find the version that client is running on currently!
     */
    public ClientVersion findVersion(){
        for(var key : componentMap.keySet()){
            var component = componentMap.get(key);

            LogUtil.log("Attempting to search for version using component: %s",key);
            var version = component.search();

            if(version != null) {
                LogUtil.log("Found client version name: %s",version.getVersion());
                LogUtil.log("Found client protocol identifier: %s", String.valueOf(version.getProtocol()));
                return version;
            }
        }

        return null;
    }

    /**
     * Client version holding class
     */
    public static class ClientVersion {

        private final String VERSION_STRING;
        private final int RELEASE_NETWORK_PROTOCOL_VERSION;
        private final boolean SNAPSHOT;

        public ClientVersion(String version_string, int release_network_protocol_version, boolean snapshot) {
            VERSION_STRING = version_string;
            RELEASE_NETWORK_PROTOCOL_VERSION = release_network_protocol_version;
            SNAPSHOT = snapshot;
        }

        public int getProtocol() {
            return RELEASE_NETWORK_PROTOCOL_VERSION;
        }

        public String getVersion() {
            return VERSION_STRING;
        }

        public boolean isSnapshot() {
            return SNAPSHOT;
        }
    }

}
