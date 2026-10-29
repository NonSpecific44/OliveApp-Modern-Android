package olive.app;

import java.util.Hashtable;
import org.teleal.cling.model.message.header.EXTHeader;
import org.teleal.cling.model.meta.Device;
import org.teleal.cling.model.meta.DeviceDetails;
import org.teleal.cling.model.meta.LocalDevice;
import org.teleal.cling.model.meta.RemoteDevice;
import org.teleal.cling.registry.Registry;
import org.teleal.cling.registry.RegistryListener;

/* loaded from: classes.dex */
public class BrowseRegistryListener implements RegistryListener {
    private Hashtable<String, String> devices = new Hashtable<>();

    @Override // org.teleal.cling.registry.RegistryListener
    public void remoteDeviceDiscoveryStarted(Registry registry, RemoteDevice device) {
        deviceAdded(device);
    }

    @Override // org.teleal.cling.registry.RegistryListener
    public void remoteDeviceDiscoveryFailed(Registry registry, RemoteDevice device, Exception ex) {
        System.out.println("Discovery failed of '" + device.getDisplayString() + "': " + (ex != null ? ex.toString() : "Couldn't retrieve device/service descriptors"));
        deviceRemoved(device);
    }

    @Override // org.teleal.cling.registry.RegistryListener
    public void remoteDeviceAdded(Registry registry, RemoteDevice device) {
        deviceAdded(device);
    }

    @Override // org.teleal.cling.registry.RegistryListener
    public void remoteDeviceRemoved(Registry registry, RemoteDevice device) {
        deviceRemoved(device);
    }

    @Override // org.teleal.cling.registry.RegistryListener
    public void localDeviceAdded(Registry registry, LocalDevice device) {
        deviceAdded(device);
    }

    @Override // org.teleal.cling.registry.RegistryListener
    public void localDeviceRemoved(Registry registry, LocalDevice device) {
        deviceRemoved(device);
    }

    public void deviceAdded(Device device) {
        String[] details = device.getIdentity().toString().split(":");
        String url = details[4].replace("//", EXTHeader.DEFAULT_VALUE);
        if (!this.devices.containsKey(url) && (details[5].indexOf("upnpdesc_1.xml") > -1 || details[5].indexOf("MediaServer1.xml") > -1)) {
            this.devices.put(url, String.valueOf(device.getDetails().getFriendlyName().replace("Renderer", EXTHeader.DEFAULT_VALUE)) + " (" + url + ")");
        } else if (details[5].indexOf("MediaServer1.xml") > -1) {
            this.devices.put(url, String.valueOf(device.getDetails().getFriendlyName().replace("Renderer", EXTHeader.DEFAULT_VALUE)) + " (" + url + ")");
        }
    }

    public Hashtable<String, String> getDevices() {
        return this.devices;
    }

    public void deviceRemoved(Device device) {
        DeviceDetails d = device.getDetails();
        d.getFriendlyName();
    }

    @Override // org.teleal.cling.registry.RegistryListener
    public void afterShutdown() {
    }

    @Override // org.teleal.cling.registry.RegistryListener
    public void beforeShutdown(Registry arg0) {
    }

    @Override // org.teleal.cling.registry.RegistryListener
    public void remoteDeviceUpdated(Registry arg0, RemoteDevice arg1) {
    }
}
