package me.dickmeister.agent.modules.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import me.dickmeister.agent.modules.AgentModule;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.instrument.Instrumentation;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class VersionMappingDownloaderModule extends AgentModule {

    private final String VERSION_MANIFEST = "https://launchermeta.mojang.com/mc/game/version_manifest.json";

    @Override
    public Object execute(Instrumentation instrumentation, Object arg) throws Exception {
        var gson = new GsonBuilder().create();

        var versionString = (String) arg;
        var manifestData = this.readAllFromURL(VERSION_MANIFEST);
        var versionManifestUrl = this.readVersionLists(versionString, gson.fromJson(manifestData, JsonObject.class));
        var mappingsUrl = this.readVersionManifest(gson,versionManifestUrl);
        return readAllFromURL(mappingsUrl);
    }

    /**
     * Get mappings from the client manifest
     * @param gson Gson instance
     * @param url URL to the client manifest
     * @return Mappings url
     * @throws Exception If the url is not found
     */
    private String readVersionManifest(Gson gson, String url) throws Exception{
        var data = this.readAllFromURL(url);
        var object = gson.fromJson(data, JsonObject.class);
        var downloads = object.get("downloads").getAsJsonObject();
        var clientMappings = downloads.get("client_mappings").getAsJsonObject();
        return clientMappings.get("url").getAsString();
    }

    /**
     * Get version manifest from version lists
     * @param versionName The version name to get the manifest for
     * @param object The version list object
     * @return The manifest url
     */
    private String readVersionLists(String versionName, JsonObject object){
        var versions = object.getAsJsonArray("versions");
        for(JsonElement element : versions){
            var version = element.getAsJsonObject();
            if(version.get("id").getAsString().equals(versionName)) return version.get("url").getAsString();
        }

        return null;
    }

    /**
     * Read all data as string from URL
     */
    private String readAllFromURL(String url) throws Exception{
        try (var is = new URL(url).openStream()) {
            var reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            var sb = new StringBuilder();
            int cp;
            while ((cp = reader.read()) != -1) {
                sb.append((char) cp);
            }
            return sb.toString();
        }
    }

}
