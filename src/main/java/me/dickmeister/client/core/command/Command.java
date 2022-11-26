package me.dickmeister.client.core.command;

public abstract class Command {

    private final String prefix;
    private final String usage;
    private final String description;

    public Command(String prefix, String usage, String description) {
        this.prefix = prefix;
        this.usage = usage;
        this.description = description;
    }

    public abstract void execute(String[] args) throws Exception;

    public String getDescription() {
        return description;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getUsage() {
        return usage;
    }
}
