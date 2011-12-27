package gateway.socket.client.impl;

import gateway.socket.client.ConnectClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xsocket.MaxReadSizeExceededException;
import org.xsocket.connection.*;

import java.io.IOException;
import java.nio.BufferUnderflowException;

/**
 * 客户端接收服务端信息
 *
 * @author zxb
 */
public class XSocketBlockClient extends ConnectClient implements IConnectHandler {

    private static final Logger logger = LoggerFactory.getLogger(XSocketBlockClient.class);
    private IBlockingConnection blockingConnection;
    private INonBlockingConnection nonBlockingConnection;
    private int headLength;
    private int remainRecvLength;
    private boolean isRecvStart;
    private boolean isRecvOver;

    private String dataGaram;

    public XSocketBlockClient(String serverIP, int serverPort, long timeoutMills) throws IOException {
        super(serverIP, serverPort);
        nonBlockingConnection = new NonBlockingConnection(serverIP, serverPort, this);
        blockingConnection = new BlockingConnection(nonBlockingConnection);
        blockingConnection.setConnectionTimeoutMillis(timeoutMills);
        blockingConnection.setEncoding("GBK");
        blockingConnection.setAutoflush(true);  //  设置自动清空缓存
    }

    @Override
    public boolean onConnect(INonBlockingConnection nbc) throws IOException, BufferUnderflowException, MaxReadSizeExceededException {
        String remoteName = nbc.getRemoteAddress().getHostName();
        logger.info("【本地客户端】与远程主机:" + remoteName + "建立连接。");
        return true;
    }

    public String sendDataUntilRcv(String dataContent) throws Exception {

        logger.info("【本地客户端】发送报文：" + dataContent);
        String dataGaram = null;
        if (sendData(dataContent)) {
            int garamLength = Integer.parseInt(blockingConnection.readStringByLength(12));
            logger.info("【本地客户端】接收报文内容长度：" + garamLength);
            dataGaram = blockingConnection.readStringByLength(garamLength - 12);
        }
        logger.info("【本地客户端】接收报文内容：" + dataGaram);
        return dataGaram;
    }

    private boolean sendData(String dataContent) throws IOException {
        if (blockingConnection == null || !blockingConnection.isOpen()) {
            throw new RuntimeException("未建立连接！");
        } else {
            blockingConnection.write(dataContent);
            blockingConnection.flush();
        }
        return true;
    }

    //  @Override
/*
    public boolean onData(INonBlockingConnection iNonBlockingConnection) throws IOException, BufferUnderflowException, MaxReadSizeExceededException {

        int avlb = iNonBlockingConnection.available();

        logger.info(iNonBlockingConnection.readStringByLength(avlb));
       */
/* if (!isRecvStart) {
            isRecvStart = true;
            if (avlb < headLength) throw new RuntimeException("可接收报文头长度过短，无法确定全部报文长度！");
            dataGaram = iNonBlockingConnection.readStringByLength(headLength);
            int dataGaramLength = Integer.parseInt(dataGaram);
            if (avlb < dataGaramLength) {
                dataGaram = iNonBlockingConnection.readStringByLength(avlb - headLength);
                remainRecvLength = dataGaramLength - avlb;
            } else {
                dataGaram = iNonBlockingConnection.readStringByLength(dataGaramLength - headLength).substring(64);
                isRecvOver = true;
                isRecvStart = false;
            }
        } else {
            if (avlb < remainRecvLength) {
                dataGaram += iNonBlockingConnection.readStringByLength(avlb);
                remainRecvLength -= avlb;
            } else {
                dataGaram += iNonBlockingConnection.readStringByLength(remainRecvLength);
                dataGaram = dataGaram.substring(64);
                isRecvOver = true;
                isRecvStart = false;
            }
        }

*//*

        return false;
    }
*/

    /**
     * 关闭客户端链接
     *
     * @return
     * @throws java.io.IOException
     */
    public boolean close() throws IOException {
        if (blockingConnection != null && blockingConnection.isOpen()) {
            blockingConnection.close();
            blockingConnection = null;
        }
        if (nonBlockingConnection != null && nonBlockingConnection.isOpen()) {
            nonBlockingConnection.close();
            nonBlockingConnection = null;
        }
        return true;
    }

    public boolean isRecvOver() {
        return isRecvOver;
    }

    public void setRecvOver(boolean recvOver) {
        isRecvOver = recvOver;
    }

    public IBlockingConnection getBlockingConnection() {
        return blockingConnection;
    }

    public void setBlockingConnection(IBlockingConnection blockingConnection) {
        this.blockingConnection = blockingConnection;
    }

    public String getDataGaram() {
        return dataGaram;
    }

    public void setDataGaram(String dataGaram) {
        this.dataGaram = dataGaram;
    }

    public long getHeadLength() {
        return headLength;
    }

    public void setHeadLength(int headLength) {
        this.headLength = headLength;
    }

    public long getRemainRecvLength() {
        return remainRecvLength;
    }

    public void setRemainRecvLength(int remainRecvLength) {
        this.remainRecvLength = remainRecvLength;
    }
}
