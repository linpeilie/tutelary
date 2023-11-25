package cn.easii.tutelary.common.utils;

import java.net.InetSocketAddress;

public class NetUtils {

    public static String toAddressString(InetSocketAddress address) {
        return address.getAddress().getHostAddress() + ":" + address.getPort();
    }

}
