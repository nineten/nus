import java.io.*;
import java.net.*;
import java.util.*;

public class Monitor
{
    public int waitSendTime=10000;
    public int waitScheduleTime=10000;
    private int count=0;
    private boolean trigger=false;

    private static final int MAX_TRIES=1;
    private static final int MAX_WAIT=8000;

    private DatagramSocket socket = null;
    private Random generator = null;

    public Monitor() throws Exception
    {
        socket = new DatagramSocket();
        socket.setSoTimeout(MAX_WAIT);
        generator = new Random();
    }
    
    public void getData() throws Exception
    {
        FileWriter out = new FileWriter("remote/monitor" + count + ".txt");
        String temp = "temperature:" + generator.nextInt(100) + "\n" +
                      "wind speed:" + generator.nextInt(20);

        out.write(temp,0,temp.length());
        out.close();
        FileOutputStream out2 = new FileOutputStream("remote/monitor" + count + ".jpg");
        int seed = generator.nextInt(4);
        FileInputStream in = new FileInputStream("source"+seed+".jpg");
        int c;
        while ((c=in.read()) != -1)
        {
            out2.write(c);
        }
        out2.close();
        in.close();
    }

    public void sendData() throws Exception
    {
        int count2=0;
        byte[] buf = new byte[2048];
        InetAddress address = InetAddress.getByName("127.0.0.1");
        int x=0;
        FileInputStream in = new FileInputStream("remote/monitor"+count+".txt");
        int c;
        while ((c=in.read()) != -1)
        {
            buf[x] = (byte)c;
            x++;
        }
        in.close();
        int border = x;
        FileInputStream in2 = new FileInputStream("remote/monitor"+count+".jpg");
        while ((c=in2.read()) != -1)
        {
            buf[x] = (byte)c;
            x++;
        }
        in2.close();

        byte[] data = new byte[3+x];
        for (int y=3;y<3+x;y++)
        {
            data[y] = buf[y-3];
        }

        boolean flag=true;

        while (flag)
        {
            flag = false;

            data[0] = 1;
            data[1] = (byte)count;
            data[2] = (byte)border;

            DatagramPacket packet = new DatagramPacket(data,data.length,address,1337);
            socket.send(packet);

            packet = new DatagramPacket(buf,buf.length);
            try
            {
                System.out.println("Waiting for ack");
                socket.receive(packet);
                data = packet.getData();
                System.out.println("Data Recieved:" + data[0]);
                switch (data[0])
                {
                    case 5:
                        System.out.println("ACK + Schedule received");
                        if (data[1]!=count)
                        {
                            System.out.println("Old ACK - Dropped");
                            break;
                        }
                        waitScheduleTime = data[2]*1000;
                        System.out.println("Wait Schedule Updated");
                        waitSendTime = data[3]*1000;
                        System.out.println("Send Schedule Updated");
                        break;
                    case 6:
                        System.out.println("ACK received");
                        if (data[1]!=count)
                        {
                            System.out.println("Old ACK - Dropped");
                            flag = true;
                            break;
                        }
                        break;
                }
            }
            catch (SocketTimeoutException e)
            {
                System.out.println("Timeout "+count2);
                count2++;
                if (count2<=MAX_TRIES)
                {
                    flag = true;
                }
                else
                {
                    System.out.println("ERROR:Max Tries Reached");
                }
            }
        }
        count++;
    }

    public static void main(String[] args) throws Exception
    {
        Monitor mon = new Monitor();
        while(true)
        {
            System.out.println("Waiting for "+mon.waitScheduleTime+ "ms of Schedule Time");
            Thread.sleep(mon.waitScheduleTime);
            mon.getData();
            System.out.println("Waiting for "+mon.waitSendTime+ "ms of wait send Time");
            Thread.sleep(mon.waitSendTime);
            System.out.println("Sent data packet");
            mon.sendData();
            System.out.println("---------------------------------------------------------");
        }
    }
}
