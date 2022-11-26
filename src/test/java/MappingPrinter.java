import me.dickmeister.client.mappings.MappingManager;
import me.dickmeister.client.mappings.VersionMappingsLoader;
import me.dickmeister.client.mappings.loader.MappingsLoader;
import me.dickmeister.client.version.VersionFinder;

public class MappingPrinter {

    public static void main(String[] args) {
        var version = new VersionFinder.ClientVersion("1.18",761,false);
        var mappingManager = new MappingManager();

        var mappingsLoader = new MappingsLoader(mappingManager);
        var mappingsUrl = new VersionMappingsLoader().loadFromVersion(version);
        mappingsLoader.loadFromUrl(mappingsUrl);

        var mappings = mappingManager.getMappedClasses();
        mappings.forEach((key,clazz) -> {
            if(!key.startsWith("net.minecraft.world.entity.player.Player")) return;
            System.out.println(key + " " + clazz.getNewClassName());
            clazz.getMappedFieldMap().forEach((fieldKey,field) -> {
                System.out.println("\t" + fieldKey + " " + field.getNewFieldName());
            });
            clazz.getMappedMethodMap().forEach((method,mappedMethod) -> {
                System.out.println("\t" + method + " " + mappedMethod.getNewMethodName());
            });
        });
    }

}
