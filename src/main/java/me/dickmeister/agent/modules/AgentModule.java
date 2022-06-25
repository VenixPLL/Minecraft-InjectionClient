package me.dickmeister.agent.modules;

import java.lang.instrument.Instrumentation;

public abstract class AgentModule {

    public abstract Object execute(Instrumentation instrumentation,Object arg) throws Exception;

}
