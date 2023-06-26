# BlfScheduler (Bukkit-Like Fabric Scheduler)
A Fabric library mod that allows task scheduling like with Bukkit!  
A convenient tool for some types of Minecraft servers.  
Tasks are not saved and therefore cancelled upon server stop.
## Examples
Delaying a task:
```
BlfScheduler.delay(20, new BlfRunnable() {
    @Override
    public void run() {
        player.sendMessage(Text.of("This message is delayed 20 ticks!"));
    }
});
```
Repeating a task:
```
BlfScheduler.repeat(10, 15, new BlfRunnable() {
    @Override
    public void run() {
        player.sendMessage(Text.of("This message is delayed 10 ticks and repeated every 15 ticks!"));
    }
});
```
