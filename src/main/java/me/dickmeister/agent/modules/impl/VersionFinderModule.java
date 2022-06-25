package me.dickmeister.agent.modules.impl;

import me.dickmeister.agent.modules.AgentModule;

import java.lang.instrument.Instrumentation;

public class VersionFinderModule extends AgentModule {

    @Override
    public Object execute(Instrumentation instrumentation,Object arg) throws Exception {
        var classes = instrumentation.getAllLoadedClasses();
        for (var clazz : classes) {
            if(clazz.getSimpleName().equals("ab")) { //SharedConstants
                var versionStringField = clazz.getDeclaredField("d"); //VERSION_STRING
                return versionStringField.get(null);
            }
        }

        return null;
    }

}
