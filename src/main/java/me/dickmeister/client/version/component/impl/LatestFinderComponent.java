package me.dickmeister.client.version.component.impl;

import me.dickmeister.client.version.VersionFinder;
import me.dickmeister.client.version.component.VersionFinderComponent;

public class LatestFinderComponent extends VersionFinderComponent {

    @Override
    public VersionFinder.ClientVersion search() {
        try {
            var sharedConstants = Class.forName("ab"); //net.minecraft.SharedConstants
            var versionField = sharedConstants.getDeclaredField("d"); //java.lang.String VERSION_STRING
            var protocolField = sharedConstants.getDeclaredField("f"); //int RELEASE_NETWORK_PROTOCOL_VERSION
            var snapshotField = sharedConstants.getDeclaredField("a"); //boolean SNAPSHOT

            var version = (String)versionField.get(null);
            var protocol = (int)protocolField.get(null);
            var snapshot = (boolean)snapshotField.get(null);

            if(snapshot) {
                return new VersionFinder.ClientVersion(version,protocol, true);
            }

            return new VersionFinder.ClientVersion(version,protocol, false);
        }catch(Exception e){
            return null;
        }
    }

}
