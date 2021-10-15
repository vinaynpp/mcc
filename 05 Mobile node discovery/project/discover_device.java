package BtNodeDiscovery;

import java.io.IOException;
import javax.bluetooth.*;

import javax.bluetooth.DiscoveryListener; import javax.microedition. lcdui.*; import javax.microedition.midlet.*;

public class discover_device extends MIDlet implements CommandListener,DiscoveryListener {

private final List deviceList;

private final Command Exit,Refresh; private String deviceName; private DiscoveryAgent agent; private Alert dialog;

public discover_device()
{
deviceList = new List("List of Devices",List.IMPLICIT);
Exit= new Command("Exit",Command.EXIT,  0);
Refresh = new
Command("Refresh",Command.SCREEN,  1);

deviceList.addCommand(Exit); deviceList.addCommand(Refresh);

deviceList.setCommandListener(this); Display.getDisplay(this).setCurrent(deviceList);
}

public void startApp() {
try {
deviceList.deleteAll();

LocalDevice local = LocalDevice.getLocalDevice(); local.setDiscoverable(DiscoveryAgent.GIAC); deviceName = local.getFriendlyName();

agent = local.getDiscoveryAgent(); }

catch (BluetoothStateException ex) { ex.printStackTrace(); }

try {
agent.startInquiry(DiscoveryAgent.GIAC,  this);

}

catch (BluetoothStateException ex) { ex.printStackTrace(); }

}

public void pauseApp() {
}
public void destroyApp(boolean unconditional) {
}

public void commandAction(Command  c, Displayable
d)  {
if(c==Exit)
{
this.destroyApp(true);

notifyDestroyed();
}
if(c==Refresh){
this.startApp();
}
}

public void deviceDiscovered(RemoteDevice btDevice, DeviceClass cod) { String deviceaddress = null;
try {
deviceaddress =

btDevice.getBluetoothAddress();//btDevice. getFriendl


} catch (Exception ex) { ex.printStackTrace();
}
deviceList.insert(0,  deviceaddress , null);
}

public void servicesDiscovered(int transID, ServiceRecord[] servRecord) {
throw new UnsupportedOperationException("Not supported yet.");
}

public void serviceSearchCompleted(int transID, int respCode) {

throw new UnsupportedOperationException("Not supported yet.");
}





public void inquiryCompleted(int discType) { Alert dialog = null;
if (discType !=
DiscoveryListener.INQUIRY_COMPLETED) {

dialog = new Alert("Bluetooth Error","The inquiry failed to complete normally",null, AlertType.ERROR);

}
else {

dialog = new Alert("Inquiry Completed","The inquiry completed normally", null,AlertType.INFO);
}
dialog.setTimeout(500);


Display.getDisplay(this).setCurrent(dialog);                          }
}
