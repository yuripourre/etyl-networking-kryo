package com.harium.etyl.networking.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

/**
 * Class that allows a device to identify itself on the INTRANET.
 * @author Decoded4620 2016
 * 
 * Found at: http://stackoverflow.com/a/35516638
 */
public class NetIdentity {

    private String loopbackHost = "";
    private String host = "";

    private String loopbackIp = "";
    private String ip = "";
    public NetIdentity() {

        try{
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();

            while(interfaces.hasMoreElements()){
                NetworkInterface i = interfaces.nextElement();
                if(i != null){
                    Enumeration<InetAddress> addresses = i.getInetAddresses();
                    //System.out.println(i.getDisplayName());
                    while(addresses.hasMoreElements()){
                        InetAddress address = addresses.nextElement();
                        String hostAddr = address.getHostAddress();

                        // local loopback
                        if(hostAddr.indexOf("127.") == 0 ){
                            this.loopbackIp = address.getHostAddress();
                            this.loopbackHost = address.getHostName();
                        }

                        // internal ip addresses (behind this router)
                        if( hostAddr.indexOf("192.168") == 0 || 
                                hostAddr.indexOf("10.") == 0 || 
                                hostAddr.indexOf("172.16") == 0 ){
                            this.host = address.getHostName();
                            this.ip = address.getHostAddress();
                        }
                    }
                }
            }
        }
        catch(SocketException e){

        }
        try{
            InetAddress loopbackIpAddress = InetAddress.getLocalHost();
            this.loopbackIp = loopbackIpAddress.getHostName();
            System.out.println("LOCALHOST: " + loopbackIp);
        }
        catch(UnknownHostException e){
            System.err.println("ERR: " + e.toString());
        }
    }

    public String getLoopbackHost(){
        return loopbackHost;
    }

    public String getHost(){
        return host;
    }
    public String getIp(){
        return ip;
    }
    public String getLoopbackIp(){
        return loopbackIp;
    }
}
