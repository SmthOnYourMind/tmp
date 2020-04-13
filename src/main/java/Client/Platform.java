package Client;

public class Platform
{
    public int x;
    public int y;

    public int height;
    public int width;

    enum Construction
    {
        FLOOR,
        WALL,
        NONE
    }

    Construction construction = Construction.NONE;

    public Platform(int X, int Y, int H, int W, Construction type)
    {
        height = H;
        width = W;
        construction = type;
        x = X;
        y = Y;
    }

}
