# Minecart Fix
Minecraft Plugin for fixing issues related to (furnace) minecarts.

## The Problem
If you used furnace minecarts in Minecraft before, you might have noticed some odd and sometimes buggy behaviour. Specifically, 
if you let them drive a longer distance alone without being nearby, they tend to get stuck, 
no matter how much coal you provide them with.

This happens due to the game trying to save performance by unloading chunks far from players. This unfortunately leads to the furnace minecarts being unloaded as well,
which means they don't go any further unless a player gets close to them, making the game load the chunk again.

## The Solution
This rather simple plugin aims at fixing the afore mentioned issue by simply forcing the game to keep all chunks containing minecarts loaded, so you
can be sure that they arrive wherever you need them to be.



## FAQ
#### <ins>What versions is the plugin compatible with?</ins>
MinecartFix has been tested in Minecraft Versions **1.19.2**, **1.19.3** and **1.19.4**, although you should be able to use it with older versions of the game as well.
Make sure to back up your data though, as versions prior to 1.19 might break.

#### <ins>How do I install the plugin?</ins>
As long as you're running **CraftBukkit, Spigot or a fork such as PaperMC**, simply download the .jar file from the GitHub releases page and place it in your *plugins*
folder. After that, **restart your server**.

#### <ins>Does this affect performance?</ins>
Depending on your server and the amount of minecarts you use, the plugin might slightly lower your performance. Due to additional chunks being force-loaded for each
minecart, if you have a lot of them spread out over multiple chunks, you might see an increase in RAM usage. There's also an option for increasing the update delay
in the **plugin.yml** which lowers the speed at which the plugin updates chunks, potentially increasing performance.
