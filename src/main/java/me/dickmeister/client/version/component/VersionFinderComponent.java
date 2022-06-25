package me.dickmeister.client.version.component;

import me.dickmeister.client.version.VersionFinder;

public abstract class VersionFinderComponent {

    public abstract VersionFinder.ClientVersion search();

}
