package BtNodeDiscovery;



import javax.microedition.midlet.*;

import javax.microedition.lcdui.*;

import javax.microedition.io.*;

import javax.bluetooth.*;

import java.util.*;
/**
 * @author VYD
 */
public class Blue extends MIDlet implements
        CommandListener, DiscoveryListener {

    private List activeDevices;
    private Command select, exit;
    private Display display;
    private LocalDevice local = null;
    private DiscoveryAgent agent = null;
    private Vector devicesFound = null;
    private ServiceRecord[] servicesFound = null;
    private String connectionURL = null;

    public void startApp() {

        display = Display.getDisplay(this);

        activeDevices = new List("Active Devices", List.IMPLICIT);
        select = new Command("Search Again", Command.OK, 0);

        exit = new Command("Exit",
                Command.EXIT, 0);

        activeDevices.addCommand(exit);

        activeDevices.setCommandListener(this);
        try {

            local = LocalDevice.getLocalDevice();
        } catch (Exception e) {
        }

        doDeviceDiscovery();

        display.setCurrent(activeDevices);

    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
        notifyDestroyed();
    }

    public void commandAction(Command cmd, Displayable disp) {

        if (cmd == select && disp == activeDevices) {

            activeDevices.deleteAll();

            doDeviceDiscovery();

        }

        if (cmd == exit) {

            destroyApp(false);
        }

    }

    public void inquiryCompleted(int param) {
        try {

            switch (param) {

                case DiscoveryListener.INQUIRY_COMPLETED:

                    if (devicesFound.size() > 0) {

                        activeDevices.addCommand(select);

                        activeDevices.setSelectCommand(select);

                    } else {

                        activeDevices.append("No Devices Found", null);
                    }
                    break;

            }

        } catch (Exception e) {
        }

    }

    public void serviceSearchCompleted(int transID, int respCode) {
    }

    public void servicesDiscovered(int transID, ServiceRecord[] serviceRecord) {
    }

    public void deviceDiscovered(RemoteDevice remoteDevice, DeviceClass deviceClass) {

        String str = null;

        try {

            str = remoteDevice.getBluetoothAddress() + " - ";

            str += remoteDevice.getFriendlyName(true);

        } catch (Exception e) {
        }
        activeDevices.append(str, null);

        devicesFound.addElement(remoteDevice);

        try {

            if (!agent.startInquiry(DiscoveryAgent.GIAC, this)) {
            }
        } catch (BluetoothStateException e) {

//	TODO Auto-generated  catch block

            e.printStackTrace();

        }
    }

    private void doDeviceDiscovery() {

        try {
            local = LocalDevice.getLocalDevice();
            agent = local.getDiscoveryAgent();
            devicesFound = new Vector();
        } catch (Exception e) {
        }
    }
}