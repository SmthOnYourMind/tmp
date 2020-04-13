package Client;

import java.util.ArrayList;

public class Room
{

    public int height;
    public int width;

    public ArrayList<Platform> platforms;

    public void addObjects(Platform obj)
    {
        platforms.add(obj);
    }

    public Room(int H, int W)
    {
        platforms = new ArrayList<Platform>();
        height = H;
        width = W;
    }

    public Platform getPlatformUnderPlayer(Player player)
    {
        for (Platform p: platforms)
        {
            if (p.y == player.getY() && p.x <= player.getX() && p.x + p.width >= player.getX())
                return p;
        }

        return null;
    }
}
