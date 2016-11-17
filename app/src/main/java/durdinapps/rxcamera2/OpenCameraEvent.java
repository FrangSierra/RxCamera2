package durdinapps.rxcamera2;


import android.hardware.camera2.CameraDevice;

public class OpenCameraEvent {
    final CameraDevice cameraDevice;
    final EventType eventType;

    public OpenCameraEvent(CameraDevice cameraDevice, EventType eventType) {
        this.cameraDevice = cameraDevice;
        this.eventType = eventType;
    }

    public static enum EventType {
        OPENED,
        DISCONNECTED;
        private EventType() {
        }
    }
}
