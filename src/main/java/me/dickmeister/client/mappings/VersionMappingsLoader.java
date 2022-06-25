package me.dickmeister.client.mappings;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import me.dickmeister.client.version.VersionFinder;
import me.dickmeister.common.util.LogUtil;
import me.dickmeister.common.util.URLUtils;

public class VersionMappingsLoader {
    private final Gson gson = new GsonBuilder().create();

    public String loadFromVersion(VersionFinder.ClientVersion clientVersion){
        LogUtil.log("Loading manifest data for version: %s", clientVersion.getVersion());
        try{
            var data = URLUtils.readAllFromUrl("https://launchermeta.mojang.com/mc/game/version_manifest.json");
            var object = this.gson.fromJson(data, JsonObject.class);
            var versionManifestUrl = this.readVersionList(object,clientVersion);
            var mappingsUrl = this.readVersionManifest(versionManifestUrl);

            LogUtil.log("Found mappings url: " + mappingsUrl);
            return mappingsUrl;
        }catch(Exception e){
            LogUtil.err("Failed to read version list from URL!");
            LogUtil.err(e.getClass().getSimpleName() + " " + e.getMessage());
        }

        return null;
    }

    private String readVersionManifest(String url){
        try{
            var data = URLUtils.readAllFromUrl(url);
            var object = gson.fromJson(data,JsonObject.class);

            var downloads = object.get("downloads").getAsJsonObject();
            var clientMappings = downloads.get("client_mappings").getAsJsonObject();
            return clientMappings.get("url").getAsString();
        }catch(Exception e){
            LogUtil.err("Failed to read version manifest!");
        }
        return null;
    }

    private String readVersionList(JsonObject object, VersionFinder.ClientVersion clientVersion){
        try{
            LogUtil.log("Reading version list...");
            var versions = object.getAsJsonArray("versions");
            for(JsonElement element : versions){
                var version = element.getAsJsonObject();
                if(version.get("id").getAsString().equals(clientVersion.getVersion())){
                    LogUtil.log("Found version manifest for: " + clientVersion.getVersion());
                    return version.get("url").getAsString();
                }
            }
        }catch(Exception e){
            LogUtil.err("Failed to read version manifest!");
            return null;
        }
        LogUtil.err("No matching version found!");
        return null;
    }

}
