package me.dickmeister.client.mappings.loader;

import me.dickmeister.client.mappings.MappingManager;
import me.dickmeister.client.mappings.proguard.MappingReader;
import me.dickmeister.client.mappings.proguard.processor.impl.ClientMappingProcessor;
import me.dickmeister.common.util.LogUtil;
import me.dickmeister.common.util.URLUtils;

public class MappingsLoader {

    private final MappingManager manager;

    public MappingsLoader(MappingManager manager) {
        this.manager = manager;
    }

    public void loadFromUrl(String url){
        try {
            var data = URLUtils.readAllFromUrl(url);
            var reader = new MappingReader(data);

            var startTime = System.currentTimeMillis();
            LogUtil.log("Loading mapping data to memory...");
            reader.pump(new ClientMappingProcessor(manager));

            var endTime = System.currentTimeMillis() - startTime;
            LogUtil.log("Loaded in %sms",String.valueOf(endTime));

        }catch(Exception e){
            LogUtil.err("Failed to load mappings data!");
            LogUtil.err(e.getClass().getSimpleName() + " " + e.getMessage());
        }
    }

}
