import java.io.*;
import java.net.*;
import java.util.*;
import java.text.*;

public class Intrusion
{
    public int waitSendTime=5000;
    public int waitScheduleTime=10000;
    private int count=0;
    private boolean trigger=false;

    private static final int MAX_TRIES=1;
    private static final int MAX_WAIT=8000;

    private DatagramSocket socket = null;
    private Random generator = null;

    public Intrusion() throws Exception
    {
        socket = new DatagramSocket();
        socket.setSoTimeout(MAX_WAIT);
        generator = new Random();
    }
    
    public void getData() throws Exception
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        
        String mytime = dateFormat.format(date);

        FileWriter out = new FileWriter("remote/alarm" + count + ".txt");
        String temp = "Intrusion detected at " + generator.nextInt(100) + 
                      "\nTime detected:"+mytime;

        out.write(temp,0,temp.length());
        out.close();
        FileOutputStream out2 = new FileOutputStream("remote/alarm" + count + ".jpg");
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
        FileInputStream in = new FileInputStream("remote/alarm"+count+".txt");
        int c;
        while ((c=in.read()) != -1)
        {
            buf[x] = (byte)c;
            x++;
        }
        in.close();
        int border = x;
        FileInputStream in2 = new FileInputStream("remote/alarm"+count+".jpg");
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

            data[0] = 0;
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
                    case 3:
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
                    case 4:
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
        Intrusion intrude = new Intrusion();
        Scanner in = new Scanner(System.in);
        while(true)
        {
            System.out.println("Press enter to trigger intrusion alarm");
            in.nextLine();
            intrude.getData();
            System.out.println("Waiting for "+intrude.waitSendTime+ "ms of wait send Time");
            Thread.sleep(intrude.waitSendTime);
            System.out.println("Sent data packet");
            intrude.sendData();
            System.out.println("---------------------------------------------------------");
        }
    }
}
