package tan.handler;

import cpw.mods.fml.common.network.IConnectionHandler;
import cpw.mods.fml.common.network.Player;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.NetLoginHandler;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet1Login;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.network.PacketDispatcher;
import tan.api.utils.TANPlayerStatUtils;
import tan.network.PacketTypeHandler;
import tan.network.packet.PacketSendStats;
import tan.stats.TemperatureStat;
import tan.stats.ThirstStat;

public class ConnectionHandler implements IConnectionHandler
{
    @Override
    public void playerLoggedIn(PlayerEntity player, NetHandler netHandler, INetworkManager manager)
    {
            PlayerEntity PlayerEntity = (PlayerEntity)player;
            
            TemperatureStat temperatureStat = TANPlayerStatUtils.getPlayerStat(PlayerEntity, TemperatureStat.class);
            ThirstStat thirstStat = TANPlayerStatUtils.getPlayerStat(PlayerEntity, ThirstStat.class);
            
            PacketDispatcher.sendPacketToPlayer(PacketTypeHandler.populatePacket(new PacketSendStats(PlayerEntity)), (Player)player);
    }

    @Override
    public String connectionReceived(NetLoginHandler netHandler, INetworkManager manager)
    {
        return null;
    }

    @Override
    public void connectionOpened(NetHandler netClientHandler, String server,int port, INetworkManager manager)
    {
    }

    @Override
    public void connectionOpened(NetHandler netClientHandler, MinecraftServer server, INetworkManager manager)
    {
    }

    @Override
    public void connectionClosed(INetworkManager manager)
    {
    }

    @Override
    public void clientLoggedIn(NetHandler clientHandler, INetworkManager manager, Packet1Login login)
    {
    }
}
