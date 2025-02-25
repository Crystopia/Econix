# Econix - Lightweight Economy Provider

## What is Econix

Econix is an economy provider to have a simple handling with multiple currencies and to address them with a simple developer api. Econix is very simple and easy to integrate as well as to use as an own plugin.

## What does Econix offer

- Developer API - an interface to access Econix
- In-game command - for each currency you have a command with which you can manage the currency.
- Infinite currencies - you can register infinite currencies and also register some via API.
- Item Export - You can export the currency as an item and trade it this way
---
- ``[SOON]`` Trade System - To trade with other players
- ``[SOON]`` Bank Feature - A bank feature will be added as needed to give players a safe place for their money
- ``[SOON]`` Pay, Transactions, etc. - More commands and cool features will follow.

## Permissions

- ``crystopia.commands.econix.<currenxyId>``- Access to the /<currenxy> Command
- ``crystopia.commands.econix.econix`` - Access to the main /econix Command

## Hooks

- Placeholder API - You can display the current balance with placeholder
- Vault - The plugin supports Vault and can release a currency for vault

## Commands

- ``/<currency>`` - Manage your currency with commands like set, give, remove, item, etc.
- ``/econix`` - Manage the plugin and reload the data

## Developer API

You can import our plugin via Maven or Gradle

#### Maven

```
<repositories>
    <repository>
        <id>github-crystopia-econix</id>
        <url>https://maven.pkg.github.com/Crystopia/Econix</url>
        <releases>
            <enabled>true</enabled>
        </releases>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>me.jesforge</groupId>
        <artifactId>econix</artifactId>
        <version>${version}</version>
        <scope>provided</scope>
    </dependency>
</dependencies>

<profiles>
    <profile>
        <id>github</id>
        <properties>
            <github.username>${env.USER}</github.username>
            <github.token>${env.TOKEN}</github.token>
        </properties>
    </profile>
</profiles>
```

#### Gradle 
```
repositories {
    maven {
        url = uri("https://maven.pkg.github.com/Crystopia/Econix")
        credentials {
            username = findProperty("USER") ?: ""
            password = findProperty("TOKEN") ?: ""
        }
    }
}

dependencies {
    compileOnly "me.jesforge:econix:<version>"
}

```


#### Gradle (KTS)


```kts
 repositories {
    maven {
        url = uri("https://maven.pkg.github.com/Crystopia/Econix")
        credentials {
            username = findProperty("USER") as String
            password = findProperty("TOKEN") as String
        }
    }

}

dependencies {
    // Econix
    compileOnly("me.jesforge:econix:<version>")
}
```

_``<version>`` is the latest version here on modrinth_


#### Zugriff auf die API Instance


```kotlin
class Main : JavaPlugin() {

    private var econix: EconixAPI? = null

    override fun onEnable() {
        // Econix Hook
        if (server.pluginManager.getPlugin("Econix")?.isEnabled == true) {
            logger.info("Hooking into Econix")
            econix = Econix.getAPI()
        } else {
            logger.warning("No Econix version Found!")
            server.pluginManager.disablePlugin(this)
        }
    }

    fun getEconix(): EconixAPI? {
        return econix
    }
}

```


```java
public class Main extends JavaPlugin {

    private EconixAPI econix;

    @Override
    public void onEnable() {
        // Hook into Econix API
        if (Bukkit.getServer().getPluginManager().getPlugin("Econix") != null && Bukkit.getServer().getPluginManager().getPlugin("Econix").isEnabled()) {
            getLogger().info("Hooking into Econix");
            econix = Econix.getAPI();
        } else {
            getLogger().warning("No Econix version Found!");
            Bukkit.getServer().getPluginManager().disablePlugin(this);
        }
    }

    public EconixAPI getEconix() {
        return econix;
    }
}

```
