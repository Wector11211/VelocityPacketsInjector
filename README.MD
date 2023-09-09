# VelocityPacketsInjector

VPI is a lightweight **reflectionless** library for listening and injecting into player's packets for Velocity. It is meant to be a more optimized alternative to [Protocolize](https://github.com/Exceptionflug/protocolize).

Roadmap:
* [x] Listening to incoming/outcoming packets
* [x] Modifying and cancelling packets
* [x] Send packets (native velocity)
* [ ] Enable encoding for unsupported by default packets 
* [ ] Preregistered unsupported by default packets such as
  * Named sound packet
  * Inventory packet
* [ ] Easily register new unsupported by default packets

---
### How to use

Implement `VelocityPacketListener` for the packet you need to listen to. And register it using static getter
```java
VelocityPacketInjector packetInjector = VelocityPacketInjector.get();
packetInjector.registerPacketListener(plugin, new MyPacketListener());
```

Example:
```java
public class MyPacketListener implements VelocityPacketListener<LegacyChat> {

    @Override
    public Class<LegacyChat> getPacketType() {
        return LegacyChat.class;
    }

    @Override
    public void onPacketReceive(PacketEvent<LegacyChat> event) {
        LegacyChat chatPacket = event.getPacket();
        // handle player -> proxy packet
    }

    @Override
    public void onPacketSend(PacketEvent<LegacyChat> event) {
        LegacyChat chatPacket = event.getPacket();
        // handle proxy -> player packet
    }
}
```